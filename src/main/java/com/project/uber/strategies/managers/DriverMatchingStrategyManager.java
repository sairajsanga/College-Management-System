package com.project.uber.strategies.managers;

import com.project.uber.strategies.DriverMatchingStrategy;
import com.project.uber.strategies.implementations.DriverMatchingHighestRatedDriver;
import com.project.uber.strategies.implementations.DriverMatchingNearestDrivers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DriverMatchingStrategyManager {

    private final DriverMatchingHighestRatedDriver driverMatchingHighestRatedDriver;
    private final DriverMatchingNearestDrivers driverMatchingNearestDrivers;

    public DriverMatchingStrategy driverMatchingStrategy(double riderRating){

        if (riderRating > 4.8 || riderRating==0){
            return driverMatchingHighestRatedDriver;
        }
        else {
            return driverMatchingNearestDrivers;
        }
    }

}
