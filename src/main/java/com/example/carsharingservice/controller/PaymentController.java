package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.payment.PaymentRequestDto;
import com.example.carsharingservice.dto.payment.PaymentResponseDto;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.service.PaymentService;
import com.example.carsharingservice.service.mapper.PaymentMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;
    private final PaymentMapper mapper;

    @GetMapping
    public List<PaymentResponseDto> getPaymentsByUser(@RequestParam Long userId) {
        return paymentService.getByUser(userId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public PaymentResponseDto createNewPayment(HttpServletRequest request,
                                               @RequestParam Payment.Type paymentType,
                                               @RequestBody PaymentRequestDto dto) {
        Payment payment = paymentService.create(mapper.toModel(dto),
                paymentType, request.getRequestURI());
        return mapper.toDto(payment);
    }

    @GetMapping("/success")
    public PaymentResponseDto paymentSuccess(@RequestParam Long paymentId) {
        Payment payment = paymentService.paymentSuccess(paymentId);
        return mapper.toDto(payment);
    }

    @GetMapping("/cancel")
    public PaymentResponseDto paymentCancel(@RequestParam Long paymentId) {
        Payment payment = paymentService.paymentCancel(paymentId);
        return mapper.toDto(payment);
    }

}
