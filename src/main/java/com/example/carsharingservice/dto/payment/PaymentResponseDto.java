package com.example.carsharingservice.dto.payment;

import com.example.carsharingservice.model.Payment;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentResponseDto {
    private Long id;
    private String sessionUrl;
    private BigDecimal amountToPay;
    private Payment.Status status;
    private Payment.Type type;
}
