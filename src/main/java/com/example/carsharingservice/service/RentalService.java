package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.model.User;
import java.time.LocalDate;
import java.util.List;

public interface RentalService {
    Rental createNewRental(Car car, User user, LocalDate returnDate);

    List<Rental> getRentalsByUserIdAndIsReturned(Long userId, Boolean isRented);

    Rental getById(Long id);

    Rental returnCar(Long id);
}
