package com.project.uber.controllers.driver;

import com.project.uber.dto.DriverDto;
import com.project.uber.dto.DriverRideDto;
import com.project.uber.entities.Driver;
import com.project.uber.services.DriverService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/driver")
@Secured("ROLE_DRIVER")
@AllArgsConstructor
public class DriverGetMapping {

    private DriverService driverService;
    private final int PAGE_SIZE=4;

    @GetMapping("/getMyProfile")
    public ResponseEntity<DriverDto> getDriverProfile(){
        return ResponseEntity.ok(driverService.getDriverProfile());
    }

    @GetMapping("/getMyRides")
    public ResponseEntity<List<DriverRideDto>> getDriverRides(@RequestParam(defaultValue = "id") String sortBy,@RequestParam(defaultValue = "1") Integer pageNumber){
        PageRequest pageRequest=PageRequest.of(pageNumber,PAGE_SIZE, Sort.by(sortBy).ascending());
        Page<DriverRideDto> rides=driverService.getAllMyRides(pageRequest);
        return ResponseEntity.ok(rides.getContent());
    }
}
