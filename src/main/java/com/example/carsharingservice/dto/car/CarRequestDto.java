package com.example.carsharingservice.dto.car;

import com.example.carsharingservice.model.Car;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CarRequestDto {
    private String model;
    private String brand;
    private Car.Type type;
    private BigDecimal dailyFee;
}
