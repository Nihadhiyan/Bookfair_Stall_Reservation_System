package com.bookfair.backend.config;

import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bookfair.backend.security.CustomUserPrincipal;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditConfig {

    public static final UUID SYSTEM_USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @Bean
    public AuditorAware<UUID> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()
                    || authentication.getPrincipal().equals("anonymousUser")) {
                if (SYSTEM_USER_ID != null) {
                    return Optional.of(SYSTEM_USER_ID);
                }
            }

            Object principal = authentication.getPrincipal();

            if (principal instanceof CustomUserPrincipal userPrincipal) {
                return Optional.of(userPrincipal.getId());
            }

            return Optional.of(SYSTEM_USER_ID);
        };
    }
}
