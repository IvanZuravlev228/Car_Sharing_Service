package com.example.carsharingservice.service;

import java.util.List;
import com.example.carsharingservice.model.Car;

public interface CarService {
    Car addCarToInventory(Long id);

    Car removeCarFromInventory(Long id);

    Car createNewCar(Car car);

    List<Car> getAll();

    Car getById(Long id);

    Car update(Car car);

    void delete(Long id);
}
