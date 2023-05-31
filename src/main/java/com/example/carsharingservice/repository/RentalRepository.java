package com.example.carsharingservice.repository;

import java.util.List;
import com.example.carsharingservice.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> getByUserId(Long userId);
}
