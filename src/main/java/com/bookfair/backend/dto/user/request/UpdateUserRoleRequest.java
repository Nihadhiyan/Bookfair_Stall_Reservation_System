package com.bookfair.backend.dto.user.request;

import com.bookfair.backend.model.User.SystemRole;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRoleRequest {

    @NotNull(message = "Role is required")
    private SystemRole role;

}
