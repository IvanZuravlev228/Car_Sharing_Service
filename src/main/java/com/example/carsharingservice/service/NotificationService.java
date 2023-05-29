package com.example.carsharingservice.service;

public interface NotificationService {
    void sentMessage(String message);

    void checkOverdueRentals();
}
