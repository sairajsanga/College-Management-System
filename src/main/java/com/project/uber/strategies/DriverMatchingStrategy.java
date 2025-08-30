package com.project.uber.strategies;

import com.project.uber.dto.RideRequestDto;
import com.project.uber.entities.Driver;
import com.project.uber.entities.RideRequest;

import java.util.List;

public interface DriverMatchingStrategy {


    List<Driver> findMatchingDrivers(RideRequest rideRequest);
}
