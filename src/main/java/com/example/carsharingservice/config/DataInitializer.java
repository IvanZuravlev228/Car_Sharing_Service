package com.example.carsharingservice.config;

import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.CarService;
import com.example.carsharingservice.service.RentalService;
import com.example.carsharingservice.service.UserService;
import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DataInitializer {
    private final UserService userService;
    private final CarService carService;
    private final RentalService rentalService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void inject() {
        User user = new User();
        user.setRole(User.Role.MANAGER);
        user.setEmail("manager@i.ua");
        user.setPassword(passwordEncoder.encode("managertest"));
        user.setFirstName("Valera");
        user.setLastName("Boy");
        userService.save(user);

        Car car = new Car();
        car.setModel("x5");
        car.setBrand("bmw");
        car.setType(Car.Type.SUV);
        car.setDailyFee(BigDecimal.valueOf(100));
        car.setInventory(5);
        carService.createNewCar(car);

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setCar(car);
        rentalService.createNewRental(car, user, LocalDate.of(2023, 1, 2));
    }
}
