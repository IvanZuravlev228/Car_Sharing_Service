package com.example.carsharingservice.service;

import com.example.carsharingservice.model.User;

public interface UserService {
    User getByUsername(String username);

    User save(User user);
}
