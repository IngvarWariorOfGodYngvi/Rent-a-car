package com.example.auta.models.classes;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Company {

    private String name;
    private String domain;
    private String address;
    private String owner;
    private byte[] logotype;
    @Builder.Default
    private List<Branch> branches = new ArrayList<>();
    @Builder.Default
    private List<Customer> customers = new ArrayList<>();

}
