package com.bookfair.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookfair.backend.dto.auth.request.LoginRequest;
import com.bookfair.backend.dto.auth.request.RefreshTokenRequest;
import com.bookfair.backend.dto.auth.request.RegisterRequest;
import com.bookfair.backend.dto.auth.request.ResetPasswordRequest;
import com.bookfair.backend.dto.auth.response.AuthResponse;
import com.bookfair.backend.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerRequest));

    }

    @PostMapping("/login") 
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        return ResponseEntity.ok(authService.login(loginRequest));

    }

    @PostMapping("/logout") 
    public ResponseEntity<String> logout(HttpServletRequest request) {
        authService.logout(request.getHeader("Authorization"));
        return ResponseEntity.ok("Successfully logged out");

    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword (@RequestParam("email") String email) {
        authService.forgotPassword(email);
        return ResponseEntity.ok("If an account with that email exists, a reset link has been sent.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.ok("Password has been successfully reset.");
    }

    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@Valid @RequestBody String verificationToken) {
        authService.verifyEmail(verificationToken);
        return ResponseEntity.ok("Email successfully verified. You may now log in.");
    }

    @PostMapping("/send-verification")
    public ResponseEntity<String> sendVerification(@RequestParam("email") String email) {
        authService.sendVerificationEmail(email);
        return ResponseEntity.ok("Verification email sent.");
    }
}
