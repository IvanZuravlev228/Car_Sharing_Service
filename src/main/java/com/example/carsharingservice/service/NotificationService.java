package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Rental;

public interface NotificationService {

    void sendMessageAboutSuccessRent(Rental rental);

    void checkOverdueRentals();

    void sendMessageToAdministrators(String message);

    void sendMessageToUser(String message);
}
