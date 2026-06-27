package com.bookfair.backend.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.bookfair.backend.repository.OrganizationRepository;

import lombok.RequiredArgsConstructor;

@Component("orgAuth")
@RequiredArgsConstructor
public class OrganizationSecurityEvaluator {

    private final OrganizationRepository organizationRepository;

    public boolean isOrganizerAdmin(Authentication authentication, UUID orgId) {
        return checkPermission(authentication, orgId, "ORGANIZER", "ORG_ADMIN");
    }

    public boolean isVendorAdmin(Authentication authentication, UUID orgId) {
        return checkPermission(authentication, orgId, "VENDOR", "ORG_ADMIN");
    }

    public boolean isMemberOf(Authentication authentication, UUID orgId) {
        Objects.requireNonNull(orgId, "Organization ID cannot be null");
        Map<String, String> orgRoles = extractOrgRoles(authentication);
        return orgRoles.containsKey(orgId.toString());
    }

    private boolean checkPermission(Authentication authentication, UUID orgId, String requiredType,
            String requiredRole) {
        Objects.requireNonNull(orgId, "Organization ID cannot be null");

        Map<String, String> orgRoles = extractOrgRoles(authentication);
        String orgIdStr = orgId.toString();

        if (!orgRoles.containsKey(orgIdStr)) {
            return false; // Not a member of this org at all
        }

        String actualRole = orgRoles.get(orgIdStr);
        if (!actualRole.equalsIgnoreCase(requiredRole)) {
            return false;
        }

        return organizationRepository.findById(orgId)
                .map(org -> switch (requiredType) {
                    case "ORGANIZER" -> org.isEventOrganizer();
                    case "VENDOR" -> org.isVendor();
                    default -> true;
                })
                .orElse(false);
    }

    private Map<String, String> extractOrgRoles(Authentication authentication) {
        if (authentication == null || authentication.getAuthorities() == null) {
            return Map.of(); // Empty map if not authenticated properly
        }

        Map<String, String> orgRoles = new HashMap<>();

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String authString = authority.getAuthority();

            if (authString != null && authString.startsWith("ORG_")) {
                String withoutPrefix = authString.substring("ORG_".length());

                int underscoreIndex = withoutPrefix.indexOf('_');

                if (underscoreIndex != -1) {
                    String orgIdStr = withoutPrefix.substring(0, underscoreIndex);
                    String role = withoutPrefix.substring(underscoreIndex + 1);

                    try {
                        UUID orgId = UUID.fromString(orgIdStr);

                        if (role.equalsIgnoreCase("ADMIN")) {
                            orgRoles.put(orgId.toString(), "ORG_ADMIN");
                        } else if (role.equalsIgnoreCase("MEMBER")) {
                            orgRoles.putIfAbsent(orgId.toString(), "ORG_MEMBER");
                        }
                    } catch (IllegalArgumentException e) {
                        continue;
                    }
                }
            }

        }

        return orgRoles;
    }
}