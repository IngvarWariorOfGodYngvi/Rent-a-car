package com.example.auta.models.classes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Branch {

    private String address;
    @Builder.Default
    private Set<Employee> employees = new HashSet<>();
    @Builder.Default
    private Set<Car> cars = new HashSet<>();

}
