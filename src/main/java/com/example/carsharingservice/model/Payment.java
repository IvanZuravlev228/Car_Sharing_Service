package com.example.carsharingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Long id;
    private Long rentalId;
    private String sessionUrl;
    private String sessionId;
    private BigDecimal amountToPay;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Status {
        PENDING, PAID
    }

    public enum Type {
        PAYMENT, FINE
    }
}
