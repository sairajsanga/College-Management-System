package com.project.uber.services;

import com.project.uber.entities.RideRequest;

public interface RideRequestService {

    RideRequest findRideRequestById(Long id);

    void updateRideRequest(RideRequest rideRequest);
}
