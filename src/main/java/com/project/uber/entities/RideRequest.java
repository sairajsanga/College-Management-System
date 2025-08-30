package com.project.uber.entities;

import com.project.uber.dto.PointDto;
import com.project.uber.entities.enums.PaymentMethod;
import com.project.uber.entities.enums.RideRequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Table(indexes = {
        @Index(name = "idx_ride_request_rider", columnList = "rider_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Pick-up location is required")
    @Column(columnDefinition = "POINT SRID 4326")
    private Point pickUpLocation;

    @NotNull(message = "Drop-off location is required")
    @Column(columnDefinition = "POINT SRID 4326")
    private Point dropOffLocation;

    @CreationTimestamp
    private LocalDateTime requestTime;

    @NotNull(message = "Rider is required")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rider_id", nullable = false)
    private Rider rider;

    @NotNull(message = "Fare is required")
    @Positive(message = "Fare must be greater than zero")
    private Double fare;

    @NotNull(message = "Payment method is required")
    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;

    @NotNull(message = "Request status is required")
    @Enumerated(value = EnumType.STRING)
    private RideRequestStatus status;
}
