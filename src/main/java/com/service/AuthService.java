package com.service;

import com.dto.auth.LoginRequest;
import com.dto.auth.RegisterRequest;

public interface AuthService {

    String login(LoginRequest request);

    String register(RegisterRequest request);
}