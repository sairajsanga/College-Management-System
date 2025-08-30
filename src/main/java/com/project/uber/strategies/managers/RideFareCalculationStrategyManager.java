package com.project.uber.strategies.managers;

import com.project.uber.strategies.RideFareCalculation;
import com.project.uber.strategies.implementations.RideFareDefaultCalculation;
import com.project.uber.strategies.implementations.RideFareSurgePricing;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@AllArgsConstructor
public class RideFareCalculationStrategyManager {

    private final RideFareDefaultCalculation rideFareDefaultCalculation;
    private final RideFareSurgePricing rideFareSurgePricing;

    public RideFareCalculation rideFareCalculation(){

        LocalTime surgeStartTime = LocalTime.of(18,0);
        LocalTime surgeEndTime = LocalTime.of(21,0);
        LocalTime currentTime = LocalTime.now();

        boolean isSurge = currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);

        if (isSurge){
            return rideFareSurgePricing;
        }
        else {
            return rideFareDefaultCalculation;
        }
    }
}
