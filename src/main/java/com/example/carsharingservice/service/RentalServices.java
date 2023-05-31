package com.example.carsharingservice.service;

import java.time.LocalDate;
import java.util.List;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.model.User;

public interface RentalServices {
    Rental createNewRental(Car car, User user, LocalDate returnDate);

    List<Rental> getRentalsByUserIdAndIsReturned(Long userId, Boolean isRented);

    Rental getById(Long id);

    Rental returnCar(Long id);
}
