package com.project.uber.services.implementations;

import com.project.uber.services.DistanceCalculationService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class DistanceCalculationServiceImpl implements DistanceCalculationService {

    public static final String OSRM_API_BASE_URL="https://router.project-osrm.org/route/v1/driving/";

    @Override
    public double calculateDistance(Point src, Point des) {
        try{
            String uri=src.getX()+","+src.getY()+";"+des.getX()+","+des.getY();
            Mono<OSRMResponseDto> osrmResponseDto= WebClient
                    .builder()
                    .baseUrl(OSRM_API_BASE_URL)
                    .build()
                    .get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(OSRMResponseDto.class);
            OSRMResponseDto responseDto=osrmResponseDto.block();
            return responseDto.getRoutes().get(0).getDistance()/1000.00;
        }
        catch(Exception ex){
            throw new RuntimeException("Error getting data from OSRM response API with source"+
                    src+" and destination "+des+" with error message "+ex.getMessage());
        }
    }
}


@NoArgsConstructor
class OSRMResponseDto{
    List<OSRMRoutes> routes;
    public List<OSRMRoutes> getRoutes(){
        return routes;
    };
}


@NoArgsConstructor
class OSRMRoutes{
    private double distance;

    public double getDistance(){
        return distance;
    }
}