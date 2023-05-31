package com.example.carsharingservice.dto.user;

import lombok.Data;

@Data
public class UserRegisterDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
