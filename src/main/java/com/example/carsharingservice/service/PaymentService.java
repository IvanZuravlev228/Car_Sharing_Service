package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Payment;
import java.util.List;

public interface PaymentService {
    List<Payment> getByUser(Long userId);

    Payment create(Payment payment, Payment.Type paymentType, String urlBase);

    Payment paymentSuccess(Long paymentId);

    Payment paymentCancel(Long paymentId);
}
