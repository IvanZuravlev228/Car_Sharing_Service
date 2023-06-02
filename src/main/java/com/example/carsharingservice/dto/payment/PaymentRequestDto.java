package com.example.carsharingservice.dto.payment;

import com.example.carsharingservice.model.Payment;
import lombok.Data;

@Data
public class PaymentRequestDto {
    private Long rentalId;
    private Payment.Type type;
}
