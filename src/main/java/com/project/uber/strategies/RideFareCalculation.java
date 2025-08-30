package com.project.uber.strategies;


import com.project.uber.entities.RideRequest;
import com.project.uber.entities.RideRequest;

public interface RideFareCalculation {

    double RIDE_FARE_MULTIPLIER = 10;

    double calculateFare(RideRequest rideRequest);

}
