package com.project.uber.controllers.rider;

import com.project.uber.dto.RideDto;
import com.project.uber.dto.RiderDto;
import com.project.uber.services.RiderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rider")
@AllArgsConstructor
@Secured("ROLE_RIDER")
public class RiderGetMapping {

    private RiderService riderService;
    private final int PAGE_SIZE=4;

    @GetMapping("/getMyProfile")
    public ResponseEntity<RiderDto> getRiderProfile() {
        return ResponseEntity.ok(riderService.getRiderProfile());
    }

    @GetMapping("/getMyRides")
    public ResponseEntity<List<RideDto>> getALlMyRides(@RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "1") Integer pageNumber){
        PageRequest pageRequest= PageRequest.of(pageNumber,PAGE_SIZE, Sort.by(sortBy).ascending());
        Page<RideDto> rides=riderService.getAllMyRides(pageRequest);
        return ResponseEntity.ok(rides.getContent());
    }
}
