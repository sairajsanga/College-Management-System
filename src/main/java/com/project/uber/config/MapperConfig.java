package com.project.uber.config;

import com.project.uber.dto.PointDto;
import com.project.uber.dto.RideRequestDto;
import com.project.uber.dto.RiderDto;
import com.project.uber.entities.RideRequest;
import com.project.uber.entities.Rider;
import com.project.uber.entities.User;
import com.project.uber.utils.GeometryUtil;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(PointDto.class, Point.class).setConverter(context -> {
            PointDto pointDto = context.getSource();
            if (pointDto == null || pointDto.getCoordinates() == null || pointDto.getCoordinates().length < 2) {
                throw new IllegalArgumentException("Invalid or null coordinates in PointDto");
            }
            return GeometryUtil.createPoint(pointDto);
        });

        modelMapper.typeMap(Point.class, PointDto.class).setConverter(mappingContext -> {
            Point point = mappingContext.getSource();
            double[] coordinates = {point.getX(),point.getY()};
            return new PointDto(coordinates);
        });

        modelMapper.typeMap(RiderDto.class, Rider.class).setConverter(mappingContext -> {
            RiderDto riderDto = mappingContext.getSource();
            Rider rider=new Rider();
            rider.setId(riderDto.getId());
            rider.setRating(riderDto.getRating());
            if (riderDto.getUser() != null) {
                rider.setUser(modelMapper.map(riderDto.getUser(), User.class));
            }
            return rider;
        });

        modelMapper.typeMap(RideRequestDto.class, RideRequest.class).addMappings(mapper -> {
            mapper.map(RideRequestDto::getPickUpLocation, RideRequest::setPickUpLocation);
            mapper.map(RideRequestDto::getDropOffLocation, RideRequest::setDropOffLocation);
            mapper.map(RideRequestDto::getRider, RideRequest::setRider);
            mapper.map(RideRequestDto::getFare, RideRequest::setFare);
            mapper.map(RideRequestDto::getPaymentMethod, RideRequest::setPaymentMethod);
            mapper.map(RideRequestDto::getRequestTime, RideRequest::setRequestTime);
            mapper.map(RideRequestDto::getStatus, RideRequest::setStatus);
        });
        return modelMapper;
    }
}
