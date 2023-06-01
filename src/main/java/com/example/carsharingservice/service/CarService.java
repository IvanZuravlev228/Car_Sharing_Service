package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Car;
import java.util.List;

public interface CarService {
    Car addCarToInventory(Long id);

    Car removeCarFromInventory(Long id);

    Car createNewCar(Car car);

    List<Car> getAll();

    Car getById(Long id);

    Car update(Car car);

    void delete(Long id);
}
