package com.project.uber.services.implementations;

import com.project.uber.dto.DriverDto;
import com.project.uber.dto.RiderDto;
import com.project.uber.entities.Driver;
import com.project.uber.entities.Rating;
import com.project.uber.entities.Ride;
import com.project.uber.entities.Rider;
import com.project.uber.exceptions.ResourceNotFoundException;
import com.project.uber.exceptions.RuntimeConflictException;
import com.project.uber.repositories.DriverRepository;
import com.project.uber.repositories.RatingRepository;
import com.project.uber.repositories.RiderRepository;
import com.project.uber.services.RatingManagementService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@AllArgsConstructor
public class RatingManagementServiceImpl implements RatingManagementService {

    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;
    private final ModelMapper modelMapper;



    @Override
    public DriverDto rateDriver(Ride ride, Driver driver, Double rating) {
        Rating ratingObj = ratingRepository
                .findByRide(ride)
                .orElseThrow(()-> new ResourceNotFoundException("Rating not found!"));
        log.info("Driver rating "+ratingObj.getDriverRating());

        if(ratingObj.getDriverRating() != 0.0) throw new RuntimeConflictException("Cannot rate driver again!");

        ratingObj.setDriverRating(rating);
        ratingRepository.save(ratingObj);

        Double newRating = ratingRepository
                .findByDriver(driver)
                .stream()
                .mapToDouble(rating1 -> rating1.getDriverRating())
                .average()
                .orElse(0.0);
        driver.setRating(newRating);
        Driver driverSaved = driverRepository.save(driver);
        return modelMapper.map(driverSaved, DriverDto.class);
    }


    @Override
    public RiderDto rateRider(Ride ride, Rider rider, Double rating) {

        Rating ratingObj = ratingRepository
                .findByRide(ride)
                .orElseThrow(()-> new ResourceNotFoundException("Rating not found!"));

        if(ratingObj.getRiderRating() != 0.0) throw new RuntimeConflictException("Cannot rate rider again!");

        ratingObj.setRiderRating(rating);
        ratingRepository.save(ratingObj);

        Double newRating = ratingRepository
                .findByRider(rider)
                .stream()
                .mapToDouble(rating1 -> rating1.getRiderRating())
                .average()
                .orElse(0.0);
        rider.setRating(newRating);
        Rider savedRider = riderRepository.save(rider);
        return modelMapper.map(savedRider, RiderDto.class);
    }

    @Override
    public void createNewRating(Ride ride) {
        Rating rating = Rating
                .builder()
                .ride(ride)
                .rider(ride.getRider())
                .driver(ride.getDriver())
                .driverRating(0.0)
                .riderRating(0.0)
                .build();
        ratingRepository.save(rating);
    }


}
