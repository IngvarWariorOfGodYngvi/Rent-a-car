package com.example.auta.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reservation {

    private LocalDate raservationDate;
    private Customer customer;
    private Car car;
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;
    private Branch rentalBranch;
    private Branch returnBranch;
    private BigDecimal totalPrice;

}
