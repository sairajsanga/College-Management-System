package com.project.uber.strategies.implementations;


import com.project.uber.entities.RideRequest;
import com.project.uber.services.DistanceCalculationService;
import com.project.uber.strategies.RideFareCalculation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RideFareDefaultCalculation implements RideFareCalculation {

    private final DistanceCalculationService distanceCalculationService;

    @Override
    public double calculateFare(RideRequest rideRequest) {
        Double distance = distanceCalculationService.calculateDistance(rideRequest.getPickUpLocation(),rideRequest.getDropOffLocation());
        return distance * RIDE_FARE_MULTIPLIER;
    }
}
