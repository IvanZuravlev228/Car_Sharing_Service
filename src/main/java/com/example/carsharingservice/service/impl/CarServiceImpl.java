package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.repository.CarRepository;
import com.example.carsharingservice.service.CarService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    @Override
    public Car addCarToInventory(Long id) {
        Car car = getById(id);
        car.setInventory(car.getInventory() + 1);
        return carRepository.save(car);
    }

    @Override
    public Car removeCarFromInventory(Long id) {
        Car car = getById(id);
        if (car.getInventory() > 0) {
            car.setInventory(car.getInventory() - 1);
            return carRepository.save(car);
        }
        throw new RuntimeException("Can't take car with id " + id + " from inventory");
    }

    @Override
    public Car createNewCar(Car car) {
        Optional<Car> carOptional = carRepository.findByModelAndBrandAndDailyFeeAndType(
                car.getModel(), car.getBrand(), car.getDailyFee(), car.getType());
        return carOptional.orElseGet(() -> carRepository.save(car));
    }

    @Override
    public List<Car> getAll() {
        return carRepository.findAll();
    }

    @Override
    public Car getById(Long id) {
        return carRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Can't get car by id " + id));
    }

    @Override
    public Car update(Car car) {
        return carRepository.save(car);
    }

    @Override
    public void delete(Long id) {
        carRepository.deleteById(id);
    }
}
