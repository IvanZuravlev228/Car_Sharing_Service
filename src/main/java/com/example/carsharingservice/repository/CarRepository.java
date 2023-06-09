package com.example.carsharingservice.repository;

import com.example.carsharingservice.model.Car;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByModelAndBrandAndDailyFeeAndType(String model,
                                                        String brand,
                                                        BigDecimal dailyFee,
                                                        Car.Type type);
}
