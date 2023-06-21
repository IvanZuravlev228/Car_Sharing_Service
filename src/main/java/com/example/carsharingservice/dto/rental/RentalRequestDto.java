package com.example.carsharingservice.dto.rental;

import java.time.LocalDate;
import lombok.Data;

@Data
public class RentalRequestDto {
    private LocalDate rentalStart;
    private LocalDate rentalReturn;
    private Long carId;
}
