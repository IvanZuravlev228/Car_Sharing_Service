package com.example.carsharingservice.service.impl;

import java.util.NoSuchElementException;
import java.util.Optional;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {
    private static final Long ID = 1L;
    private static final String EMAIL = "test@gmail.com";
    private static final String PASSWORD = "password";
    private static final String ENCODED_PASSWORD = "encodedPassword";
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(ID);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
    }

    @Test
    void shouldRegisterUser() {
        Mockito.when(passwordEncoder.encode(user.getPassword())).thenReturn(ENCODED_PASSWORD);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User result = authenticationService.register(user);
        Assertions.assertEquals(user, result);
        Assertions.assertEquals(User.Role.CUSTOMER, result.getRole());
        Assertions.assertEquals(ENCODED_PASSWORD, result.getPassword());
    }

    @Test
    void shouldLoginUser() {
        Mockito.when(userRepository.getUserByEmail(EMAIL)).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);
        Mockito.when(passwordEncoder.matches(PASSWORD, ENCODED_PASSWORD)).thenReturn(true);
        User result = authenticationService.login(EMAIL, PASSWORD);
        Assertions.assertEquals(user, result);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        Mockito.when(userRepository.getUserByEmail(EMAIL)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class,
                () -> authenticationService.login(EMAIL, PASSWORD));
    }

    @Test
    void shouldThrowExceptionWhenIncorrectPassword() {
        Mockito.when(userRepository.getUserByEmail(EMAIL)).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);
        Mockito.when(passwordEncoder.matches(PASSWORD, ENCODED_PASSWORD))
                .thenReturn(false);
        Assertions.assertThrows(RuntimeException.class,
                () -> authenticationService.login(EMAIL, PASSWORD));
    }
}
