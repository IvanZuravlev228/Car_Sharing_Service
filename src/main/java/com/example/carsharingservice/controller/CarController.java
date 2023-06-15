package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.car.CarRequestDto;
import com.example.carsharingservice.dto.car.CarResponseDto;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.service.CarService;
import com.example.carsharingservice.service.NotificationService;
import com.example.carsharingservice.service.mapper.CarMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    private final CarMapper carMapper;
    private final NotificationService notificationService;

    @PostMapping
    @Operation(description = "create new car if such don't exists")
    public CarResponseDto createNewCar(@RequestBody CarRequestDto dto) {
        Car car = carService.createNewCar(carMapper.toModel(dto));
        return carMapper.toDto(car);
    }

    @GetMapping
    @Operation(description = "get all cars")
    public List<CarResponseDto> getAllCars() {
        return carService.getAll().stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(description = "get car by id")
    public CarResponseDto getCarById(@PathVariable @Parameter(description = "car id") Long id) {
        return carMapper.toDto(carService.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(description = "update car by id")
    public CarResponseDto updateCar(@RequestBody CarRequestDto dto,
                                    @PathVariable @Parameter(description = "car id") Long id) {
        Car car = carMapper.toModel(dto);
        car.setId(id);
        Car updatedCar = carService.update(car);
        return carMapper.toDto(updatedCar);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "delete car by id")
    public void deleteCar(@PathVariable @Parameter(description = "car id") Long id) {
        carService.delete(id);
    }

    @PostMapping("/add/{id}")
    @Operation(description = "add one more specific car to inventory")
    public CarResponseDto addCarToInventory(
            @PathVariable @Parameter(description = "car id") Long id) {
        return carMapper.toDto(carService.addCarToInventory(id));
    }

    @DeleteMapping("/remove/{id}")
    @Operation(description = "add one more specific car to inventory")
    public CarResponseDto removeCarFromInventory(
            @PathVariable @Parameter(description = "car id") Long id) {
        return carMapper.toDto(carService.removeCarFromInventory(id));
    }
}
