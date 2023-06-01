package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.User;
import com.example.carsharingservice.repository.UserRepository;
import com.example.carsharingservice.service.NotificationService;
import com.example.carsharingservice.service.UserService;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final NotificationService notificationService;

    @Override
    public User getByUsername(String username) {
        return userRepository.getUserByEmail(username).orElseThrow(() ->
                new NoSuchElementException("can't get user with email " + username));
    }

    @Override
    public User updateUserRole(User.Role role, Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("can't get user with id " + id));
        user.setRole(role);
        notificationService.sendMessageToAdministrators(messageAboutUpdatedUser());
        return userRepository.save(user);
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
    public User updateUserInfo(User user, Authentication authentication) {
        User userToUpdate = getByUsername(authentication.getName());
        user.setId(userToUpdate.getId());
        user.setEmail(userToUpdate.getEmail());
        User.Role role = userToUpdate.getRole();
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        authentication.setAuthenticated(false);
        notificationService.sendMessageToAdministrators(messageAboutUpdatedUser());
        return userRepository.save(user);
    }

    @Override
    public User getById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()
                -> new NoSuchElementException("Can't find user with id: " + userId));
    }

    private String messageAboutSavedUser() {
        return "User was save to DB";
    }

    private String messageAboutUpdatedUser() {
        return "Info about User was updated to DB";
    }
}
