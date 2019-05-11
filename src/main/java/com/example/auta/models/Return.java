package com.example.auta.models;

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
public class Return {

    private Employee employee;
    private LocalDate rentalEndDate;
    private Reservation reservation;
    private BigDecimal extraPayment;

}
