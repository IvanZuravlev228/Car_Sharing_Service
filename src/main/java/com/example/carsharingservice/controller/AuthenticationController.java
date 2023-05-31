package com.example.carsharingservice.controller;

import com.example.carsharingservice.model.User;
import com.example.carsharingservice.security.jwt.JwtTokenProvider;
import com.example.carsharingservice.service.AuthenticationService;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return authenticationService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User userLoginDto)
            throws RuntimeException {
        User user = authenticationService.login(userLoginDto.getEmail(),
                userLoginDto.getPassword());
        String token = jwtTokenProvider.createToken(user.getEmail(), Set.of(user.getRole()));
        return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
    }
}
