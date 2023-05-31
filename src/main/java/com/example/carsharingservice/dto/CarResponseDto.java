package com.example.carsharingservice.dto;

import java.math.BigDecimal;
import com.example.carsharingservice.model.Car;
import lombok.Data;

@Data
public class CarResponseDto {
    private Long id;
    private String model;
    private String brand;
    private Car.Type type;
    private int inventory;
    private BigDecimal dailyFee;
}
