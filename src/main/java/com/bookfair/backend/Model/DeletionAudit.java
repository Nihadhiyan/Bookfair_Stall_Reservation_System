package com.bookfair.backend.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeletionAudit {

    @Column(name = "deleted_at", updatable = false)
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by", updatable = false)
    private UUID deletedBy;
}
