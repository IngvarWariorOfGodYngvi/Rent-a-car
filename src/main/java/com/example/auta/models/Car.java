package com.example.auta.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Car {

    private String make;
    private String model;
    private Suspension suspention;
    private Integer yearOfProduction;
    private String color;
    private Integer mileage;
    private CarStatus carStatus;
    private BigDecimal dailyPrice;

}
