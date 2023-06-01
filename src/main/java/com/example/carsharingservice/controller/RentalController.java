package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.rental.RentalRequestDto;
import com.example.carsharingservice.dto.rental.RentalResponseDto;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.CarService;
import com.example.carsharingservice.service.RentalService;
import com.example.carsharingservice.service.mapper.RentalMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rentals")
public class RentalController {
    private final RentalService rentalService;
    private final CarService carService;
    private final UserService userService;
    private final RentalMapper rentalMapper;

    @GetMapping("/{id}")
    @Operation(description = "Get rental by id")
    public RentalResponseDto getSpecificRental(@PathVariable Long id) {
        return rentalMapper.toDto(rentalService.getById(id));
    }

    @GetMapping
    @Operation(description = "Get rentals by user id and is active")
    public List<RentalResponseDto> getActiveRental(@RequestParam Long userId,
                                                   @RequestParam Boolean isActive) {
        return rentalService.getRentalsByUserIdAndIsReturned(userId, isActive)
                .stream()
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/{id}/return")
    @Operation(description = "End rental by id")
    public void returnCar(@PathVariable Long id) {
        rentalService.returnCar(id);
    }

    @PostMapping
    @Operation(description = "Create new rental")
    public RentalResponseDto createRental(@RequestBody RentalRequestDto rentalRequestDto) {
        Car car = carService.getById(rentalRequestDto.getCarId());
        User user = userService.getById(rentalRequestDto.getUserId());
        return rentalMapper.toDto(
                rentalService.createNewRental(car, user, rentalRequestDto.getRentalReturn())
        );
    }
}
