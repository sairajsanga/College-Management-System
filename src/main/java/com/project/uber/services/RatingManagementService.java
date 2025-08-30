package com.project.uber.services;

import com.project.uber.dto.DriverDto;
import com.project.uber.dto.RiderDto;
import com.project.uber.entities.Driver;
import com.project.uber.entities.Ride;
import com.project.uber.entities.Rider;

public interface RatingManagementService {

    DriverDto rateDriver(Ride ride, Driver driver, Double rating);

    RiderDto rateRider(Ride ride, Rider rider, Double rating);

    void createNewRating(Ride ride);
}
