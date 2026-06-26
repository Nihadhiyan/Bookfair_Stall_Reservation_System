package com.bookfair.backend.controller;

import com.bookfair.backend.model.SystemRole;
import com.bookfair.backend.dto.organization.request.InviteRequest;
import com.bookfair.backend.model.OrganizationRole;
import com.bookfair.backend.security.CustomUserPrincipal;
import com.bookfair.backend.service.InviteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.AccessDeniedException;



@RestController
@RequestMapping("/api/organizations/invites")
@RequiredArgsConstructor
public class InviteController {

    private final InviteService inviteService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> sendInvite(
            @Valid @RequestBody InviteRequest request,
            @AuthenticationPrincipal CustomUserPrincipal principal) {

        boolean isSuperAdmin = SystemRole.SUPER_ADMIN.name().equals(principal.getSystemRole());
        String orgIdStr = request.getOrgId().toString();
        boolean isOrgAdmin = principal.getOrgRoles() != null &&
                OrganizationRole.ORG_ADMIN.name().equals(principal.getOrgRoles().get(orgIdStr));

        if (!isSuperAdmin && !isOrgAdmin) {
            throw new AccessDeniedException("You do not have permission to invite users to this organization");
        }

        inviteService.inviteUser(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> acceptInvite(
            @RequestParam String token,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        inviteService.acceptInvite(token, principal.getId());
        return ResponseEntity.ok().build();
    }
}
