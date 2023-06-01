package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.User;
import com.example.carsharingservice.repository.UserRepository;
import com.example.carsharingservice.service.AuthenticationService;
import com.example.carsharingservice.service.NotificationService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final NotificationService notificationService;

    @Override
    public User register(User user) {
        user.setRole(User.Role.CUSTOMER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        notificationService.sendMessageToAdministrators("User: " + user + " was registered to DB");
        return userRepository.save(user);
    }

    @Override
    public User login(String login, String password) throws RuntimeException {
        User user = userRepository.getUserByEmail(login).orElseThrow(() ->
                new NoSuchElementException("Can't find user by login: " + login));
        String encodedPassword = passwordEncoder.encode(password);
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new RuntimeException("Incorrect username or password!!!");
        }
        notificationService.sendMessageToAdministrators("User with login "
                + login + " was login to app");
        return user;
    }
}
