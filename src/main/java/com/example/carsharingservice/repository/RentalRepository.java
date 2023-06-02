package com.example.carsharingservice.repository;

import com.example.carsharingservice.model.Rental;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> getByUserId(Long userId);

    @Query("SELECT r FROM Rental r "
            + "WHERE (r.actualRentalReturn > :rentalReturn AND r.actualRentalReturn IS NOT NULL) "
            + "OR (r.rentalReturn < :localDate AND r.actualRentalReturn IS NULL)")
    List<Rental> findByOverdueRent(@Param("localDate") LocalDate localDate);
}
