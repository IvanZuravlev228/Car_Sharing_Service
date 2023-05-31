package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Payment;

public interface PaymentService {
    Payment create(Payment payment, String urlBase);
}
