package com.bookfair.backend.listener;

import java.util.Objects;
import java.util.UUID;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.bookfair.backend.event.organization.OrganizationDeactivatedEvent;
import com.bookfair.backend.event.user.UserDeletedEvent;
import com.bookfair.backend.event.user.UserAccountLockedEvent;
import com.bookfair.backend.event.user.UserPasswordChangedEvent;
import com.bookfair.backend.repository.OrganizationMemberRepository;
import com.bookfair.backend.service.TokenManagementService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityEnforcementListener {

    private final OrganizationMemberRepository memberRepository;
    private final TokenManagementService tokenManagementService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleSecurityEvents(Object event) {
        Objects.requireNonNull(event, "Security event cannot be null");
        try {
            if (event instanceof UserDeletedEvent e) {
                handleRevocation(e.userId());
            } else if (event instanceof UserPasswordChangedEvent e) {
                handleRevocation(e.userId());
            } else if (event instanceof OrganizationDeactivatedEvent e) {
                memberRepository.findByOrganizationId(e.organizationId())
                        .forEach(m -> {
                            try {
                                handleRevocation(m.getUser().getId());
                            } catch (Exception ex) {
                                log.error("Failed to revoke session for user {} during organization deactivation: {}",
                                        m.getUser().getId(), ex.getMessage());
                            }
                        });
            } else if (event instanceof UserAccountLockedEvent e) {
                handleRevocation(e.userId());
            }
        } catch (Exception ex) {
            log.error("Failed to handle security event {}: {}", event.getClass().getSimpleName(), ex.getMessage(), ex);
        }
    }

    private void handleRevocation(UUID userId) {
        Objects.requireNonNull(userId, "User ID cannot be null during security event revocation");
        tokenManagementService.revokeAllUserSessions(userId);
    }
}
