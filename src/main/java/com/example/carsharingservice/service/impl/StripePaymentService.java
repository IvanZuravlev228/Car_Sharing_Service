package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.repository.PaymentRepository;
import com.example.carsharingservice.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StripePaymentService implements PaymentService {
    private final PaymentRepository repository;

    @Override
    public List<Payment> getByUser(Long userId) {
        return repository.findAllByRental_User_Id(userId);
    }

    @Override
    public Payment create(Payment payment, Payment.Type paymentType, String urlBase) {
        Rental rental = payment.getRental();
        long quantityDays;
        if (paymentType == Payment.Type.PAYMENT) {
            quantityDays = Duration.between(rental.getRentalStart(),
                    rental.getRentalReturn()).toDays();
        } else {
            quantityDays = Duration.between(rental.getRentalReturn(),
                    rental.getActualRentalReturn()).toDays();
        }
        final BigDecimal pricePerDay = rental.getCar().getDailyFee();
        String priceId = ""; //Should be stored in Car

        Stripe.apiKey = "SECRET_API_KEY";
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(urlBase + "/success.html")
                        .setCancelUrl(urlBase + "/cancel.html")
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(quantityDays)
                                        // Price ID in Stripe-Products
                                        .setPrice(priceId)
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
        BigDecimal amountToPay = pricePerDay.multiply(BigDecimal.valueOf(quantityDays));
        payment.setAmountToPay(amountToPay);
        payment.setStatus(Payment.Status.PENDING);
        return repository.save(payment);
    }

    @Override
    public Payment paymentSuccess(Long paymentId) {
        Payment payment = repository.findById(paymentId).get();
        payment.setStatus(Payment.Status.PAID);
        repository.save(payment);
        return payment;
    }

    @Override
    public Payment paymentCancel(Long paymentId) {
        Payment payment = repository.findById(paymentId).get();
        return payment;
    }
}
