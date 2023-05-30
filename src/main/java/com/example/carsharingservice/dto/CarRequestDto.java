package com.example.carsharingservice.dto;

import java.math.BigDecimal;
import com.example.carsharingservice.model.Car;
import lombok.Data;

@Data
public class CarRequestDto {
    private String model;
    private String brand;
    private Car.Type type;
    private BigDecimal dailyFee;
}
