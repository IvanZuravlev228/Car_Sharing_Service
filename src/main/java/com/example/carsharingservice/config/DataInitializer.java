package com.example.carsharingservice.config;

import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DataInitializer {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void inject() {
        User user = new User();
        user.setRole(User.Role.MANAGER);
        user.setEmail("manager@i.ua");
        user.setPassword(passwordEncoder.encode("manager_password"));
        user.setFirstName("Valera");
        user.setLastName("Boy");
        userService.save(user);
    }
}
