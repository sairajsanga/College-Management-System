package com.project.uber.services;

import com.project.uber.dto.DriverDto;
import com.project.uber.dto.RideDto;
import com.project.uber.dto.RideRequestDto;
import com.project.uber.dto.RiderDto;
import com.project.uber.entities.Rider;
import com.project.uber.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RiderService {

    RideRequestDto requestRide(RideRequestDto rideRequestDto);

    RideDto cancelRide(Long rideId);

    RiderDto getRiderProfile();

    Page<RideDto> getAllMyRides(Pageable pageRequest);

    DriverDto rateDriver(Long rideId, Double rating);

    void createNewRider(User savedUser);

    Rider getCurrentRider();

    Rider getRiderById(Long id);

    Rider updateRating(Rider rider, Double rating);
}
