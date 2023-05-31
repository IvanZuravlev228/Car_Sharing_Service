package com.example.carsharingservice.service.mapper;

import com.example.carsharingservice.dto.CarRequestDto;
import com.example.carsharingservice.dto.CarResponseDto;
import com.example.carsharingservice.model.Car;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CarMapper {
    CarResponseDto toDto(Car model);
    Car toModel(CarRequestDto dto);
}
