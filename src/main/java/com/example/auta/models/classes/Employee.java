package com.example.auta.models.classes;

import com.example.auta.models.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Employee {

    private String forename;
    private String surname;
    private Position position;
    private Branch branch;

}
