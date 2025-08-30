package com.project.uber.services;

import com.project.uber.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User getUserFromId(Long userId);
}
