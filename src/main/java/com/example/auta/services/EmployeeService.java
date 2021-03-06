package com.example.auta.services;

import com.example.auta.domain.entities.BranchEntity;
import com.example.auta.domain.entities.CompanyEntity;
import com.example.auta.domain.entities.EmployeeEntity;
import com.example.auta.domain.repositories.BranchRepository;
import com.example.auta.domain.repositories.EmployeeRepository;
import com.example.auta.models.classes.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;

    public UUID addEmployee(UUID branchUUID, Employee employee) {
        BranchEntity branch = branchRepository
                .findById(branchUUID)
                .orElseThrow(EntityNotFoundException::new);
        EmployeeEntity employeeEntity = getOrCreateEmployeeEntity(employee);
        branch.getEmployees().add(employeeEntity);
        branchRepository.saveAndFlush(branch);
        return employeeEntity.getId();
    }

    public boolean removeEmployee(UUID id) {
        if (employeeRepository.findById(id).isPresent()) {
            employeeRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateEmployee(UUID employeeUUID, Employee employee) {
        try {
            EmployeeEntity updateEmployeeEntity = employeeRepository
                    .findById(employeeUUID)
                    .orElseThrow(EntityNotFoundException::new);
            if (employee.getForename() != null) {
                updateEmployeeEntity.setForename(employee.getForename());
            }
            if (employee.getSurname() != null) {
                updateEmployeeEntity.setSurname(employee.getSurname());
            }
            if (employee.getPosition() != null) {
                updateEmployeeEntity.setPosition(employee.getPosition());
            }
            employeeRepository.saveAndFlush(updateEmployeeEntity);
            return true;
        } catch (EntityNotFoundException ex) {
            return false;
        }

    }

    public EmployeeEntity getOrCreateEmployeeEntity(Employee employee) {
        return Optional.ofNullable(employee)
                .map(e-> employeeRepository
                             .findEmployeeEntityByForenameEqualsAndSurnameEquals(e.getForename(),
                                                                                 e.getSurname())
                             .orElse(employeeRepository.saveAndFlush(map(e))))
                .orElse(null);
    }

    public Employee readEmployee(EmployeeEntity employee) {
        return map(employee);
    }

    private Employee map(EmployeeEntity source) {

        return Optional.ofNullable(source)
                .map(e-> Employee.builder()
                        .forename(e.getForename())
                        .surname(e.getSurname())
                        .position(e.getPosition())
                        .build()).orElseGet(null);
    }

    private EmployeeEntity map(Employee source) {

        return EmployeeEntity.builder()
                .forename(source.getForename())
                .surname(source.getSurname())
                .position(source.getPosition())
                .build();
    }
}
