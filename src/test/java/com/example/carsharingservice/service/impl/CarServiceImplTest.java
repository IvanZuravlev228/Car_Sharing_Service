package com.example.carsharingservice.service.impl;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.repository.CarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {
    private static final Long ID = 0L;
    private static final Long INVALID_ID = 2L;
    private static final String MODEL = "Model";
    private static final String BRAND = "Brand";
    private static final Car.Type CAR_TYPE = Car.Type.HATCHBACK;
    private static final BigDecimal DAILY_FEE = BigDecimal.ONE;
    private static final int INVENTORY = 1;
    private static final int EMPTY_INVENTORY = 0;
    private static final int NEW_INVENTORY = 2;
    @InjectMocks
    private CarServiceImpl carService;
    @Mock
    private CarRepository carRepository;
    private Car carInDb;

    @BeforeEach
    void setUp() {
        carInDb = new Car();
        carInDb.setId(ID);
        carInDb.setModel(MODEL);
        carInDb.setBrand(BRAND);
        carInDb.setType(CAR_TYPE);
        carInDb.setDailyFee(DAILY_FEE);
        carInDb.setInventory(INVENTORY);
    }

    @Test
    void shouldReturnCarIfItAlreadyExists() {
        Mockito.when(carRepository.findByModelAndBrandAndDailyFeeAndType(MODEL,
                        BRAND, DAILY_FEE, CAR_TYPE))
                .thenReturn(Optional.of(carInDb));
        Car newCar = new Car();
        newCar.setModel(MODEL);
        newCar.setBrand(BRAND);
        newCar.setDailyFee(DAILY_FEE);
        newCar.setType(CAR_TYPE);
        Car car = carService.createNewCar(newCar);
        Assertions.assertEquals(carInDb, car,
                "Expected car: " + carInDb + ", but was " + car);
    }

    @Test
    void shouldSaveCarIfItDoesNotExist() {
        Mockito.when(carRepository.findByModelAndBrandAndDailyFeeAndType(MODEL,
                        BRAND, DAILY_FEE, CAR_TYPE))
                .thenReturn(Optional.empty());
        Mockito.when(carRepository.save(carInDb)).thenReturn(carInDb);
        Car car = carService.createNewCar(carInDb);
        Assertions.assertEquals(carInDb, car);
    }

    @Test
    void shouldAddCarToInventory() {
        Mockito.when(carRepository.findById(ID)).thenReturn(Optional.of(carInDb));
        Mockito.when(carRepository.save(carInDb)).thenReturn(carInDb);
        Car car = carService.addCarToInventory(ID);
        Assertions.assertEquals(carInDb, car,
                "Expected car: " + carInDb + ", but was " + car);
        Assertions.assertEquals(NEW_INVENTORY, carInDb.getInventory(),
                "Expected inventory: " + NEW_INVENTORY
                        + ", but was " + carInDb.getInventory());
    }

    @Test
    void shouldThrowExceptionWhenAddingCarToInventoryWithInvalidId() {
        Mockito.when(carRepository.findById(INVALID_ID))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class,
                () -> carService.addCarToInventory(INVALID_ID));
    }

    @Test
    void shouldRemoveCarFromInventory() {
        Mockito.when(carRepository.findById(ID)).thenReturn(Optional.of(carInDb));
        Mockito.when(carRepository.save(carInDb)).thenReturn(carInDb);
        Car car = carService.removeCarFromInventory(ID);
        Assertions.assertEquals(carInDb, car,
                "Expected car: " + carInDb + ", but was " + car);
        Assertions.assertEquals(EMPTY_INVENTORY, carInDb.getInventory(),
                "Expected inventory: " + EMPTY_INVENTORY
                        + ", but was " + carInDb.getInventory());
    }

    @Test
    void shouldThrowExceptionWhenRemovingCarFromInventoryWithInvalidId() {
        Mockito.when(carRepository.findById(INVALID_ID))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class,
                () -> carService.removeCarFromInventory(INVALID_ID));
    }
}
