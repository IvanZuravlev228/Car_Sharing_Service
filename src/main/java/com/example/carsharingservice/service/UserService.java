package com.example.carsharingservice.service;

import com.example.carsharingservice.model.User;
import java.util.List;
import org.springframework.security.core.Authentication;

public interface UserService {
    User getByUsername(String username);

    User updateUserRole(User.Role role, Long id);

    User updateUserInfo(User user, Authentication authentication);

    User save(User user);

    List<User> findUserByRole(User.Role role);

    User update(User user);

    User getById(Long id);
}
