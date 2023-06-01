package com.example.carsharingservice.dto.rental;

import java.time.LocalDate;
import lombok.Data;

@Data
public class RentalResponseDto {
    Long id;
    LocalDate rentalStart;
    LocalDate rentalReturn;
    LocalDate actualRentalReturn;
    Long carId;
    Long userId;
}
