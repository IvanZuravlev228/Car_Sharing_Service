package com.example.carsharingservice.repository;

import java.math.BigDecimal;
import java.util.Optional;
import com.example.carsharingservice.model.Car;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarRepositoryTest {
    private static final String MODEL = "Model";
    private static final String BRAND = "Brand";
    private static final BigDecimal DAILY_FEE = BigDecimal.ONE;
    private static final BigDecimal SECOND_DAILY_FEE = BigDecimal.TEN;
    private static final Car.Type CAR_TYPE = Car.Type.SEDAN;
    @Container
    static MySQLContainer<?> database = new MySQLContainer<>("mysql:8")
            .withDatabaseName("test-database")
            .withPassword("password")
            .withUsername("team5");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
        propertyRegistry.add("spring.datasource.password", database::getPassword);
        propertyRegistry.add("spring.datasource.username", database::getUsername);
    }

    @Autowired
    private CarRepository carRepository;

    @Test
    @Sql("/scripts/init_five_cars.sql")
    void shouldReturnCarModelAndBrandAndDailyFeeAndType() {
        Optional<Car> car = carRepository.findByModelAndBrandAndDailyFeeAndType(MODEL, BRAND, DAILY_FEE, CAR_TYPE);
        Assertions.assertTrue(car.isPresent());
    }

    @Test
    @Sql("/scripts/init_five_cars.sql")
    void shouldReturnEmptyOptional() {
        Optional<Car> car = carRepository.findByModelAndBrandAndDailyFeeAndType(MODEL, BRAND, SECOND_DAILY_FEE, CAR_TYPE);
        Assertions.assertFalse(car.isPresent());
    }
}