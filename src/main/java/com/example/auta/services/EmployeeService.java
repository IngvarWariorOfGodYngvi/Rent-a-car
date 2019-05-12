package com.example.auta.services;

import com.example.auta.domain.entities.EmployeeEntity;
import com.example.auta.domain.repositories.EmployeeRepository;
import com.example.auta.models.classes.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Map<UUID, Employee> getEmployees() {

        Map<UUID, Employee> map = new HashMap<>();
        employeeRepository.findAll().forEach(
                element -> map.put(element.getId(),map(element)));
                return map;
    }

    public UUID addEmployee(Employee employee) {
        return employeeRepository.save(map(employee)).getId();
    }



    public boolean removeCustomer(UUID id) {
        return false;
    }

    public boolean editEmployee(UUID id, Employee employee) {
        return false;
    }

    private Employee map(EmployeeEntity source) {

       return new Employee().builder()
               .forname(source.getForname())
                .lastname(source.getLastname())
                .branch()
                .position(source.getPosition())
                .build();
    }

    private EmployeeEntity map(Employee source) {

        return new EmployeeEntity().builder()
                .forname(source.getForname())
                .lastname(source.getLastname())
               // .branch(source.getBranch())
                .position(source.getPosition())
                .build();
    }
}
