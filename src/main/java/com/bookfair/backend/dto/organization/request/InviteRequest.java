package com.bookfair.backend.dto.organization.request;

import com.bookfair.backend.model.OrganizationMember.OrganizationRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class InviteRequest {
    @NotNull(message = "Organization ID is required")
    private UUID orgId;

    @NotNull(message = "Email is required")
    @Email(message = "Valid email is required")
    private String email;

    @NotNull(message = "Role is required")
    private OrganizationRole role;
}
