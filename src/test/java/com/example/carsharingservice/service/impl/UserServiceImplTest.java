package com.example.carsharingservice.service.impl;

import java.util.NoSuchElementException;
import java.util.Optional;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final Long ID = 1L;
    private static final String EMAIL = "test@gmail.com";

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @Test
    void shouldGetUserByUsername() {
        User user = new User();
        user.setId(1L);
        user.setEmail(EMAIL);
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setRole(User.Role.CUSTOMER);
        Mockito.when(userRepository.getUserByEmail(EMAIL)).thenReturn(Optional.of(user));
        User result = userService.getByUsername(EMAIL);
        Assertions.assertEquals(user, result);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        Mockito.when(userRepository.getUserByEmail(EMAIL)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> userService.getByUsername(EMAIL));
    }
}
