package com.example.carsharingservice.service;

import com.example.carsharingservice.model.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    User getByUsername(String username);

    User updateUserRole(User.Role role, Long id);

    User updateUserInfo(User user, Authentication authentication);
}
