package com.example.carsharingservice.service;

import com.example.carsharingservice.model.User;

public interface AuthenticationService {
    User register(User user);

    User login(String login, String password) throws RuntimeException;
}
