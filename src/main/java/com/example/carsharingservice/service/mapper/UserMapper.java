package com.example.carsharingservice.service.mapper;

import com.example.carsharingservice.dto.user.UserRegisterDto;
import com.example.carsharingservice.dto.user.UserResponseDto;
import com.example.carsharingservice.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(UserRegisterDto dto);

    UserResponseDto toDto(User user);
}
