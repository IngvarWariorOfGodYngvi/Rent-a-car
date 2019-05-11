package models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reservation {

    LocalDate raservationDate;
    Customer customer;
    Car car;
    LocalDate rentalStartDate;
    LocalDate rentalEndDate;
    Branch rentalBranch;
    Branch returnBranch;
    BigDecimal totalPrice;

}
