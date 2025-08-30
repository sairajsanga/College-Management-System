package com.project.uber.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SecondaryRow;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointDto {

    private double[] coordinates;
    private String type = "Points";

    public PointDto(double[] coordinates) {
        this.coordinates = coordinates;
    }
}
