package com.example.auta.models.classes;

import com.example.auta.models.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Reservation {

    private LocalDate reservationDate;
    private Customer customer;
    private Car car;
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;
    private Branch rentalBranch;
    private Branch returnBranch;
    private BigDecimal totalPrice;

    private ReservationStatus reservationStatus;

}
