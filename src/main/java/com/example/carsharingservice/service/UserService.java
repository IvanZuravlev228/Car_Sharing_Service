package com.example.carsharingservice.service;

import com.example.carsharingservice.model.User;
import java.util.List;
import org.springframework.security.core.Authentication;

public interface UserService {
    User getByUsername(String username);

    User updateUserRole(User.Role role, Long id);

    List<User> findUserByRole(User.Role role);

    User save(User user);

    User updateUserInfo(User user, Authentication authentication);

    User getById(Long id);

}
