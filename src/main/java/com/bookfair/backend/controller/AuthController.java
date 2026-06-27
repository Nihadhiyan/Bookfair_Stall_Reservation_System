package com.bookfair.backend.controller;

import java.time.Instant;

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
import com.bookfair.backend.dto.auth.request.VerifyEmailRequest;
import com.bookfair.backend.dto.auth.response.AuthResponse;
import com.bookfair.backend.dto.common.ApiResponseDto;
import com.bookfair.backend.dto.user.request.ChangePasswordRequest;
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
    public ResponseEntity<ApiResponseDto<AuthResponse>> register(@Valid @RequestBody RegisterRequest registerRequest,
            HttpServletRequest request) {
        AuthResponse data = authService.register(registerRequest, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseDto<AuthResponse>(true, "Registration successful", data, Instant.now()));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest,
            HttpServletRequest request) {
        AuthResponse data = authService.login(loginRequest, request);
        return ResponseEntity.ok(new ApiResponseDto<AuthResponse>(true, "Login successful", data, Instant.now()));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponseDto<Void>> logout(HttpServletRequest request,
            @Valid @RequestBody(required = false) RefreshTokenRequest refreshTokenRequest) {
        authService.logout(request.getHeader("Authorization"), refreshTokenRequest);
        return ResponseEntity.ok(new ApiResponseDto<Void>(true, "Successfully logged out", null, Instant.now()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponseDto<AuthResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest refreshTokenRequest, HttpServletRequest request) {
        AuthResponse data = authService.refreshToken(refreshTokenRequest, request);
        return ResponseEntity
                .ok(new ApiResponseDto<AuthResponse>(true, "Token refreshed successfully", data, Instant.now()));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponseDto<Void>> forgotPassword(@RequestParam("email") String email) {
        authService.forgotPassword(email);
        return ResponseEntity.ok(new ApiResponseDto<Void>(true,
                "If an account with that email exists, a reset link has been sent.", null, Instant.now()));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponseDto<Void>> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        return ResponseEntity
                .ok(new ApiResponseDto<Void>(true, "Password has been successfully reset.", null, Instant.now()));
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponseDto<Void>> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        authService.changePassword(request);
        return ResponseEntity
                .ok(new ApiResponseDto<Void>(true, "Password successfully updated.", null, Instant.now()));
    }

    @PostMapping("/verify-email")
    public ResponseEntity<ApiResponseDto<Void>> verifyEmail(@Valid @RequestBody VerifyEmailRequest verifyEmailRequest) {
        authService.verifyEmail(verifyEmailRequest);
        return ResponseEntity
                .ok(new ApiResponseDto<Void>(true, "Email successfully verified. You may now log in.", null,
                        Instant.now()));
    }

    @PostMapping("/send-verification")
    public ResponseEntity<ApiResponseDto<Void>> sendVerification(@RequestParam("email") String email) {
        authService.sendVerificationEmail(email);
        return ResponseEntity.ok(new ApiResponseDto<Void>(true, "Verification email sent.", null, Instant.now()));
    }
}
