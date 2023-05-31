package com.example.carsharingservice.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.repository.RentalRepository;
import com.example.carsharingservice.service.CarService;
import com.example.carsharingservice.service.RentalService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final CarService carService;
    private final RentalRepository rentalRepository;

    @Override
    public Rental createNewRental(Car car, User user, LocalDate returnDate) {
        Rental rental = new Rental();
        rental.setCar(car);
        rental.setUser(user);
        rental.setRentalStart(LocalDate.now());
        rental.setRentalReturn(returnDate);
        carService.takeCarFromInventory(car.getId());
        rentalRepository.save(rental);
        return rental;
    }

    @Override
    public List<Rental> getRentalsByUserIdAndIsReturned(Long userId, Boolean isRented) {
        List<Rental> rental = rentalRepository.getByUserId(userId);
            return rental.stream()
                    .filter(rental1 -> rental1.getActualRentalReturn() == null && isRented)
                    .collect(Collectors.toList());
    }

    @Override
    public Rental getById(Long id) {
        return rentalRepository.getReferenceById(id);
    }

    @Override
    public Rental returnCar(Long id) {
        Rental rental = getById(id);
        rental.setActualRentalReturn(LocalDate.now());
        carService.addCarToInventory(rental.getCar().getId());
        rentalRepository.save(rental);
        return rental;
    }
}
