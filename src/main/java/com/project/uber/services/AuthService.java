package com.project.uber.services;

import com.project.uber.dto.*;
import com.project.uber.dto.*;
import org.springframework.stereotype.Service;


public interface AuthService {

    String[] login(LoginRequestDto loginRequestDto);
    UserDto signUp(SignupDto signupDto);
    DriverDto onboardNewDriver(Long userId, OnboardDriverDto onboardDriverDto);

    LoginResponseDto refreshToken(String refreshToken);
}
