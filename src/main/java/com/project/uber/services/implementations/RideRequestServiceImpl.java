package com.project.uber.services.implementations;

import com.project.uber.entities.RideRequest;
import com.project.uber.exceptions.ResourceNotFoundException;
import com.project.uber.repositories.RideRequestRepository;
import com.project.uber.services.RideRequestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {

    private final RideRequestRepository rideRequestRepository;


    @Override
    public RideRequest findRideRequestById(Long id) {
        return rideRequestRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No ride request was found with ID: "+id));
    }

    @Override
    public void updateRideRequest(RideRequest rideRequest) {

        RideRequest request = rideRequestRepository
                .findById(rideRequest.getId())
                .orElseThrow(()-> new ResourceNotFoundException("No ride request was found with ID: "+rideRequest.getId()));

        rideRequestRepository.save(rideRequest);

    }
}
