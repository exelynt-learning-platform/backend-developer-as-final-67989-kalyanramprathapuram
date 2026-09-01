package com.serviceImpl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dto.auth.LoginRequest;
import com.dto.auth.RegisterRequest;
import com.entity.User;
import com.repository.UserRepository;
import com.security.JwtService;
import com.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // =========================================================
    // LOGIN
    // =========================================================

    @Override
    public String login(LoginRequest request) {

        // Authenticate username + password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Generate JWT token
        return jwtService.generateToken(request.getUsername());
    }

    // =========================================================
    // REGISTER
    // =========================================================

    @Override
    public String register(RegisterRequest request) {

        // Check whether username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException(
                    "Username already exists: " + request.getUsername()
            );
        }

        // Create new user
        User user = new User();

        user.setUsername(request.getUsername());

        // IMPORTANT:
        // Never save the plain-text password
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        // Set role
        user.setRole(request.getRole());

        // Save user
        userRepository.save(user);

        // Generate JWT after successful registration
        return jwtService.generateToken(user.getUsername());
    }
}