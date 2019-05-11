package com.example.auta.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List branches;

}
