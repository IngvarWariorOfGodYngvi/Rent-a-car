package com.example.auta.models.classes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Rent {

    private Employee employee;
    private LocalDate rentalStart;
    private Reservation reservation;
    private String comment;
    private Integer startMileage;
}
