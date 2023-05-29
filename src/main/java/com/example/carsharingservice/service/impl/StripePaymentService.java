package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.repository.PaymentRepository;
import com.example.carsharingservice.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StripePaymentService implements PaymentService {
    private final PaymentRepository repository;

    @Override
    public Payment create(Payment payment, String urlBase) {
        Stripe.apiKey = "SECRET_API_KEY";
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(urlBase + "/success.html")
                        .setCancelUrl(urlBase + "/cancel.html")
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        //:TODO get price and quantity from Rental
                                        .setQuantity(10L)
                                        // Price ID in Stripe-Products
                                        .setPrice("Price_ID")
                                        .build())
                        .build();
        Session session = null;
        try {
            session = Session.create(params);
        } catch (StripeException e) {
            throw new RuntimeException("Failed to create Stripe session", e);
        }

        payment.setSessionUrl(session.getUrl());
        payment.setSessionId(session.getId());
        //:TODO calculate from Rental
        payment.setAmountToPay(null);
        return repository.save(payment);
    }
}
