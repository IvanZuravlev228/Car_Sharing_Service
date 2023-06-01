package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.user.UserLoginDto;
import com.example.carsharingservice.dto.user.UserRegisterDto;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.security.jwt.JwtTokenProvider;
import com.example.carsharingservice.service.AuthenticationService;
import com.example.carsharingservice.service.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import java.util.HashMap;
import java.util.HashSet;
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
    private final UserMapper userRegisterMapper;

    @PostMapping("/register")
    @Operation(description = "register new user")
    public User register(@RequestBody UserRegisterDto user) {
        return authenticationService.register(userRegisterMapper.toModel(user));
    }

    @PostMapping("/login")
    @Operation(description = "login with email and password and you will get JWT token in response")
    public ResponseEntity<Object> login(@RequestBody UserLoginDto userLoginDto)
            throws RuntimeException {
        User user = authenticationService.login(userLoginDto.getEmail(),
                userLoginDto.getPassword());
        Set<User.Role> roles = new HashSet<>();
        roles.add(user.getRole());
        String token = jwtTokenProvider.createToken(user.getEmail(), roles);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        return new ResponseEntity<>(tokenMap, HttpStatus.OK);
    }
}
