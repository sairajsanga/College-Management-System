package com.project.uber.entities;

import com.project.uber.entities.enums.PaymentMethod;
import com.project.uber.entities.enums.RideStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Table(indexes = {
        @Index(name = "idx_ride_rider", columnList = "rider_id"),
        @Index(name = "idx_ride_driver", columnList = "driver_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ride {

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
    private LocalDateTime createdTime;

    @NotNull(message = "Rider is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rider_id", nullable = false)
    private Rider rider;

    @NotNull(message = "Driver is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @NotNull(message = "Payment method is required")
    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;

    @NotNull(message = "Ride status is required")
    @Enumerated(value = EnumType.STRING)
    private RideStatus status;

    @NotNull(message = "Fare is required")
    @Positive(message = "Fare must be greater than zero")
    private Double fare;

    @NotBlank(message = "OTP is required")
    @Size(min = 4, max = 6, message = "OTP must be between 4 and 6 characters")
    private String otp;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}
