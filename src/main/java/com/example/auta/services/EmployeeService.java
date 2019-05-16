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
    private final BranchService branchService;
    public Map<UUID, Employee> getEmployees() {

        Map<UUID, Employee> map = new HashMap<>();
        employeeRepository.findAll().forEach(
                element -> map.put(element.getId(),map(element)));
                return map;
    }

    public UUID addEmployee(Employee employee) {
        return employeeRepository.save(map(employee)).getId();
    }



    public boolean removeEmployee(UUID id) {
        if(employeeRepository.findById(id).isPresent()){
            employeeRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

    public boolean editEmployee(UUID id, Employee employee) {

        if(!employeeRepository.findById(id).isPresent() || employee == null){
            throw new IllegalArgumentException("Wrong argument");
        }
        EmployeeEntity newEntity = employeeRepository.findById(id).get();
        newEntity.setForname(employee.getForename());
        newEntity.setLastname(employee.getSurname());
        newEntity.setPosition(employee.getPosition());
        newEntity.setBranch(branchService.saveBranch(employee.getBranch()));
        return true;
    }

    private Employee map(EmployeeEntity source) {

       return new Employee().builder()
               .forename(source.getForname())
                .surname(source.getLastname())
                .branch(branchService.readBranch(source.getBranch()))
                .position(source.getPosition())
                .build();
    }

    private EmployeeEntity map(Employee source) {

        return new EmployeeEntity().builder()
                .forname(source.getForename())
                .lastname(source.getSurname())
                .branch(branchService.saveBranch(source.getBranch()))
                .position(source.getPosition())
                .build();
    }
}
