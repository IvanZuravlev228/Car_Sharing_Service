package com.example.carsharingservice.dto.rental;

import java.time.LocalDate;
import lombok.Data;

@Data
public class RentalResponseDto {
    private Long id;
    private LocalDate rentalStart;
    private LocalDate rentalReturn;
    private LocalDate actualRentalReturn;
    private Long carId;
    private Long userId;
}
