package com.example.carsharingservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Long id;
    private String model;
    private String brand;
    @Enumerated(EnumType.STRING)
    private Type type;
    private int inventory;
    @Column(name = "daily_fee")
    private BigDecimal dailyFee;

    public enum Type {
        SEDAN, SUV, HATCHBACK, UNIVERSAL
    }
}
