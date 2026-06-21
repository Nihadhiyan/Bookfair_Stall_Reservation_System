package com.bookfair.backend.model;

import java.math.BigDecimal;
import java.util.UUID;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
    name = "book_fair_stalls",
    indexes = {
        @Index(name = "idx_bfs_bookfair", columnList = "book_fair_id"),
        @Index(name = "idx_bfs_stall", columnList = "stall_id"),
        @Index(name = "idx_bfs_status", columnList = "status")
    },
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"book_fair_id", "stall_id"},
            name = "uk_book_fair_stall"
        )
    }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookFairStall extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_fair_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private BookFair bookFair;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stall_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Stall stall;

    @Column(nullable = false, precision = 10, scale = 2)
    @Positive(message = "Base price must be positive")
    private BigDecimal basePrice;

    @Column(precision = 10, scale = 2)
    @Positive(message = "Manual override price must be positive")
    private BigDecimal manualOverridePrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AvailabilityStatus status;

    public enum AvailabilityStatus {
        AVAILABLE, BOOKED, BLOCKED
    }
}
