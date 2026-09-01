package com.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dto.auth.AuthResponse;
import com.dto.auth.LoginRequest;
import com.dto.auth.RegisterRequest;
import com.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // =========================================================
    // TEST
    // GET /api/auth/test
    // =========================================================

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth API is working");
    }

    // =========================================================
    // REGISTER
    // POST /api/auth/register
    // =========================================================

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request) {

        String token = authService.register(request);

        return new ResponseEntity<>(
                new AuthResponse(token),
                HttpStatus.CREATED
        );
    }

    // =========================================================
    // LOGIN
    // POST /api/auth/login
    // =========================================================

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {

        String token = authService.login(request);

        return ResponseEntity.ok(
                new AuthResponse(token)
        );
    }
}