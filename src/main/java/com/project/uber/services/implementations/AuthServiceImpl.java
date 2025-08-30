package com.project.uber.services.implementations;

import com.project.uber.dto.*;
import com.project.uber.entities.Driver;
import com.project.uber.entities.User;
import com.project.uber.entities.enums.Roles;
import com.project.uber.exceptions.RuntimeConflictException;
import com.project.uber.repositories.UserRepository;
import com.project.uber.security.JwtService;
import com.project.uber.services.*;
import com.project.uber.utils.GeometryUtil;
import com.project.uber.services.AuthService;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Primary
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DriverService driverService;
    private final UserService userService;
    private final RiderService riderService;

    @Override
    public String[] login(LoginRequestDto loginRequestDto){
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );
        User user=(User) authentication.getPrincipal();
        String accessToken=jwtService.getAccessJwtToken(user);
        String refreshToken=jwtService.getRefreshJwtToken(user);
        return new String[]{accessToken,refreshToken};
    }

    @Override
    @Transactional
    public UserDto signUp(SignupDto signupDto) {
        Optional<User> user=userRepository.findByEmail(signupDto.getEmail());
        if(user.isPresent()){
            throw new RuntimeException("User with this email already exists");
        }
        User mappedUser=modelMapper.map(signupDto,User.class);
        mappedUser.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        mappedUser.setRoles(Set.of(Roles.RIDER));
        User savedUser=userRepository.save(mappedUser);
        riderService.createNewRider(savedUser);
        return modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public DriverDto onboardNewDriver(Long userId, OnboardDriverDto onboardDriverDto)
        {
            User user = userRepository
                    .findById(userId)
                    .orElseThrow(()-> new ResourceAccessException("No user was found with ID: "+userId));

            if(user.getRoles().contains(Roles.DRIVER)){
                throw new RuntimeConflictException("User is already a driver");
            }

            Point currentLocation = GeometryUtil.createPoint(onboardDriverDto.getCurrentLocation());
            Driver createDriver = Driver
                    .builder()
                    .available(true)
                    .rating(0.0)
                    .user(user)
                    .vehicleId(onboardDriverDto.getVehicleId())
                    .currentLocation(currentLocation)
                    .build();

            user.getRoles().add(Roles.DRIVER);
            userRepository.save(user);
            Driver savedDriver = driverService.createNewDriver(createDriver);

            return modelMapper.map(savedDriver, DriverDto.class);
        }

    @Override
    public LoginResponseDto refreshToken(String refreshToken) {
        Long userId = jwtService.getUserId(refreshToken);
        User user = userService.getUserFromId(userId);
        if(user == null) throw new AuthenticationCredentialsNotFoundException("User not found");

        String accessToken = jwtService.getAccessJwtToken(user);
        return new LoginResponseDto(accessToken);
    }
}
