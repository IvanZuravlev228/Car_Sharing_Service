package com.example.carsharingservice.security;

import static org.springframework.security.core.userdetails.User.withUsername;

import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userService.getByUsername(email);
        UserBuilder builder = withUsername(user.getEmail());
        builder.password(user.getPassword());
        builder.roles(user.getRole().name());
        return builder.build();
    }
}
