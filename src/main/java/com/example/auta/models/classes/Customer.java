package com.example.auta.models.classes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Customer {

    private String forname;
    private String lastname;
    private String email;
    private String address;
}
