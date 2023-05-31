package com.example.carsharingservice.service;

import com.example.carsharingservice.model.User;
import java.util.List;

public interface UserService {
    User getByUsername(String username);

    User save(User user);

    List<User> findUserByRole(User.Role role);

    User update(User user);
}
