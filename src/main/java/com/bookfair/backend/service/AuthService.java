package com.bookfair.backend.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookfair.backend.dto.auth.mapper.AuthMapper;
import com.bookfair.backend.dto.auth.request.LoginRequest;
import com.bookfair.backend.dto.auth.request.RefreshTokenRequest;
import com.bookfair.backend.dto.auth.request.RegisterRequest;
import com.bookfair.backend.dto.auth.request.ResetPasswordRequest;
import com.bookfair.backend.dto.auth.response.AuthResponse;
import com.bookfair.backend.exception.BusinessException;
import com.bookfair.backend.exception.DuplicateResourceException;
import com.bookfair.backend.exception.ErrorCode;
import com.bookfair.backend.exception.ResourceNotFoundException;
import com.bookfair.backend.model.User;
import com.bookfair.backend.repository.UserRepository;
import com.bookfair.backend.security.CustomUserDetailsService;
import com.bookfair.backend.security.JwtService;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AuthMapper authMapper;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final EmailService emailService;

    @Transactional
    public AuthResponse register(RegisterRequest registerRequest) {

        if (userRepository.existsByUsernameAndActiveTrue(registerRequest.getUsername())) {
            throw new DuplicateResourceException("Username is already taken", ErrorCode.DUPLICATE_USERNAME);
        }

        if (userRepository.existsByEmailAndActiveTrue(registerRequest.getEmail())) {
            throw new DuplicateResourceException("Email is already registered", ErrorCode.DUPLICATE_EMAIL);
        }

        User user = authMapper.toUserFromRegisterRequest(registerRequest);

        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        User savedUser = userRepository.save(user);

        String accessToken = jwtService.generateToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);

        Long expiresIn = jwtService.getAccessTokenExpirationTime() / 1000; // 1 hour in seconds

        return authMapper.toAuthResponse(savedUser, accessToken, refreshToken, expiresIn);

    }

    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        User user = userRepository.findByUsernameAndActiveTrue(loginRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid username or password", ErrorCode.USER_NOT_FOUND));

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        Long expiresIn = jwtService.getAccessTokenExpirationTime() / 1000; // 1 hour in seconds

        return authMapper.toAuthResponse(user, accessToken, refreshToken, expiresIn);
    }

    public AuthResponse refreshToken (RefreshTokenRequest refreshTokenRequest) {
        String userName = jwtService.extractUsername(refreshTokenRequest.getRefreshToken());

        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        if (userName != null && jwtService.validateToken(userName, userDetails)) {
            User user = userRepository.findByUsernameAndActiveTrue(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found", ErrorCode.USER_NOT_FOUND));

            String newAccessToken = jwtService.generateToken(user);
            String newRefreshToken = jwtService.generateRefreshToken(user);

            Long expiresIn = jwtService.getAccessTokenExpirationTime() / 1000;

            return authMapper.toAuthResponse(user, newAccessToken, newRefreshToken, expiresIn);

        }

        throw new BusinessException("Invalid refresh token", ErrorCode.UNAUTHORIZED);
    }

    public void logout() {
        // Future enhancement: Add token to a Redis blacklist database here
        log.info("User requested logout. Frontend should clear tokens.");
    }

    public void forgotPassword(String email) {
        userRepository.findByEmailAndActiveTrue(email).ifPresent(user -> {
            String resetToken = jwtService.generatePasswordResetToken(user);

            String resetLink = "https://clausis.com/reset-password?token=" + resetToken;

            Map<String, Object> emailVariables = new HashMap<>();
            emailVariables.put("userName", user.getUsername());
            emailVariables.put("resetLink", resetLink);

            emailService.sendEmail(
                user.getEmail(), 
                "Password Reset Request", 
                "password_reset_template", 
                emailVariables, 
                null
            );

        });
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        String username = jwtService.extractUsername(request.getResetToken());

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (username == null || !jwtService.validateToken(request.getResetToken(), userDetails)) {
            throw new BusinessException("Invalid or expired reset token.", ErrorCode.UNAUTHORIZED);
        }

        String tokenPurpose = jwtService.extractPurpose(request.getResetToken());

        if (!"RESET_PASSWORD".equals(tokenPurpose)) {
            throw new BusinessException("Invalid token.", ErrorCode.UNAUTHORIZED);
        }

        User user = userRepository.findByUsernameAndActiveTrue(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found", ErrorCode.USER_NOT_FOUND));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        Map<String, Object> emailVariables = new HashMap<>();
        emailVariables.put("userName", user.getUsername());
        
        emailService.sendEmail(
            user.getEmail(), 
            "Your Password Has Been Changed", 
            "password_reset_success_template", 
            emailVariables, 
            null
        );
    }

    @Transactional
    public void verifyEmail(String verificationToken) {
        String username = jwtService.extractUsername(verificationToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (username == null || !jwtService.validateToken(verificationToken, userDetails)) {
            throw new BusinessException("Invalid or expired verification token.", ErrorCode.UNAUTHORIZED);
        }

        String tokenPurpose = jwtService.extractPurpose(verificationToken);

        if (!"VERIFY_EMAIL".equals(tokenPurpose)) {
            throw new BusinessException("Invalid token.", ErrorCode.UNAUTHORIZED);
        }

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found", ErrorCode.USER_NOT_FOUND));

        user.setEmailVerified(true);
        userRepository.save(user);
    }

    public void sendVerificationEmail (String email) {
        userRepository.findByEmailAndActiveTrue(email).ifPresent(user -> {
            String verificationToken = jwtService.generateVerificationToken(user);

            String verificationLink = "https://clausis.com/verify-email?token=" + verificationToken;

            Map<String, Object> emailVariables = new HashMap<>();
            emailVariables.put("userName", user.getUsername());
            emailVariables.put("verificationLink", verificationLink);

            emailService.sendEmail(
                user.getEmail(), 
                "Verify Your Email", 
                "email_verification_template", 
                emailVariables, 
                null
            );

        });
    }
}
