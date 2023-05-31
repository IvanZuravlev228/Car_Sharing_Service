package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.User;
import com.example.carsharingservice.repository.UserRepository;
import com.example.carsharingservice.service.NotificationService;
import com.example.carsharingservice.service.UserService;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Override
    public User getByUsername(String username) {
        return userRepository.getUserByEmail(username).orElseThrow(() ->
                new NoSuchElementException("can't get user with email " + username));
    }

    @Override
    public List<User> findUserByRole(User.Role role) {
        return userRepository.findAllByRole(role);
    }

    @Override
    public User save(User user) {
        notificationService.sendMessageToAdministrators(messageAboutSavedUser());
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        notificationService.sendMessageToAdministrators(messageAboutUpdatedUser());
        return userRepository.save(user);
    }

    private String messageAboutSavedUser() {
        return "User was save to DB";
    }

    private String messageAboutUpdatedUser() {
        return "Info about User was updated to DB";
    }
}
