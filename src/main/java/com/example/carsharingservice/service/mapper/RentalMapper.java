package com.example.carsharingservice.service.mapper;

import com.example.carsharingservice.dto.rental.RentalRequestDto;
import com.example.carsharingservice.dto.rental.RentalResponseDto;
import com.example.carsharingservice.model.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface RentalMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "carId", source = "car.id")
    RentalResponseDto toDto(Rental rental);

    Rental toModel(RentalRequestDto dto);
}
