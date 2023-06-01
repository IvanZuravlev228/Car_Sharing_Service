package com.example.carsharingservice.dto.rental;

import java.time.LocalDate;
import lombok.Data;

@Data
public class RentalRequestDto {
    private LocalDate rentalStart;
    private LocalDate rentalReturn;
    private LocalDate actualRentalReturn;
    private Long carId;
    private Long userId;
}
