package com.bookfair.backend.model;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "buildings")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Building extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "xCoord", column = @Column(name = "building_x_coord")),
        @AttributeOverride(name = "yCoord", column = @Column(name = "building_y_coord")),
        @AttributeOverride(name = "width", column = @Column(name = "building_width")),
        @AttributeOverride(name = "height", column = @Column(name = "building_height"))
    })
    private LayoutPosition layoutPosition;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<LayoutMarker> markers;

    @Column(name = "square_footage")
    @Min(value = 0, message = "Square footage must be non-negative")
    private double sqrft;

    @Column(name = "number_of_floors")
    @Min(value = 1, message = "Number of floors must be at least 1")
    private Integer numberOfFloors;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BuildingType type;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Floor> floors;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Venue venue;


    public enum BuildingType {
        INDOOR, 
        OUTDOOR, 
        HYBRID
    }
}
