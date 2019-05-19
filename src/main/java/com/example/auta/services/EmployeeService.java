package com.example.auta.services;

import com.example.auta.domain.entities.BranchEntity;
import com.example.auta.domain.entities.EmployeeEntity;
import com.example.auta.domain.repositories.EmployeeRepository;
import com.example.auta.models.classes.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
        return employeeRepository.saveAndFlush(map(employee)).getId();
    }



    public boolean removeEmployee(UUID id) {
        if(employeeRepository.findById(id).isPresent()){
            employeeRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }
    //----------------------------------------------------------------------------
    public boolean editEmployee(UUID id, Employee employee) {

        if(!employeeRepository.findById(id).isPresent() || employee == null){
            throw new IllegalArgumentException("Wrong argument");
        }
        EmployeeEntity newEntity = employeeRepository.findById(id).get();
        newEntity.setForename(employee.getForename());
        newEntity.setSurname(employee.getSurname());
        newEntity.setPosition(employee.getPosition());
        return true;
    }
    //----------------------------------------------------------------------------
    public EmployeeEntity getOrCreateEmployeeEntity(Employee employee){
        Optional<EmployeeEntity> employeeEntity = employeeRepository
                .findEmployeeEntityByForenameEqualsAndSurnameEquals(employee.getForename(),
                                                                    employee.getSurname());
        return employeeEntity.orElse(employeeRepository.saveAndFlush(map(employee)));
    }

    public Employee readEmployee(EmployeeEntity employee){
        return map(employee);
    }

    private Employee map(EmployeeEntity source) {

       return Employee.builder()
               .forename(source.getForename())
                .surname(source.getSurname())
                .position(source.getPosition())
                .build();
    }

    private EmployeeEntity map(Employee source) {

        return EmployeeEntity.builder()
                .forename(source.getForename())
                .surname(source.getSurname())
                .position(source.getPosition())
                .build();
    }
}
