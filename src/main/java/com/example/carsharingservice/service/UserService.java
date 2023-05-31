package com.example.carsharingservice.service;

import java.util.List;
import com.example.carsharingservice.model.User;

public interface UserService {
    User getByUsername(String username);

    User save(User user);

    List<User> findUserByRole(User.Role role);
}
