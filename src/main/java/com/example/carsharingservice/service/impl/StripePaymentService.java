package com.example.carsharingservice.service.impl;

import static java.time.temporal.ChronoUnit.DAYS;

import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.repository.PaymentRepository;
import com.example.carsharingservice.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StripePaymentService implements PaymentService {
    private static final BigDecimal fineMultiplier = BigDecimal.valueOf(1.15);
    private final PaymentRepository repository;
    @Value("${stripe.test.secret-key}")
    private String secretKey;

    @Override
    public List<Payment> getByUser(Long userId) {
        return repository.findAllByRental_User_Id(userId);
    }

    @Override
    public Payment create(Payment payment, Rental rental, String urlBase) {
        payment.setRental(rental);
        repository.save(payment); //to receive id
        long quantityDays;
        if (payment.getType() == Payment.Type.PAYMENT) {
            quantityDays = DAYS.between(rental.getRentalStart(),
                    rental.getRentalReturn());
        } else {
            quantityDays = DAYS.between(rental.getRentalReturn(),
                    rental.getActualRentalReturn());
        }

        BigDecimal pricePerDay = rental.getCar().getDailyFee();
        BigDecimal amountToPay = pricePerDay.multiply(BigDecimal.valueOf(quantityDays));
        if (payment.getType() == Payment.Type.FINE) {
            amountToPay = amountToPay.multiply(fineMultiplier);
        }
        String paymentDescription =  rental.getCar().getBrand() + " "
                + rental.getCar().getModel() + " " + payment.getType().name();
        Stripe.apiKey = secretKey;

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(urlBase + "/success" + "?paymentId=" + payment.getId())
                        .setCancelUrl(urlBase + "/cancel" + "?paymentId=" + payment.getId())
                        .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                        .addLineItem(buildLineItem(amountToPay, paymentDescription))
                        .build();

        Session session = null;
        try {
            session = Session.create(params);
        } catch (StripeException e) {
            throw new RuntimeException("Failed to create Stripe session", e);
        }

        payment.setSessionUrl(session.getUrl());
        payment.setSessionId(session.getId());
        payment.setAmountToPay(amountToPay);
        payment.setStatus(Payment.Status.PENDING);
        return repository.save(payment);
    }

    private SessionCreateParams.LineItem buildLineItem(BigDecimal amountToPay,
                                                       String description) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(1L)
                .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount((long) (amountToPay.doubleValue() * 100))
                                .setProductData(
                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                .setName(description)
                                                .build()
                                )
                                .build()
                )
                .build();
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
        return repository.findById(paymentId).get();
    }
}
