package com.example.auta.controllers;

import com.example.auta.models.classes.Employee;
import com.example.auta.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    public Map<UUID, Employee> employees() {
        return employeeService.getEmployees();
    }

    @PostMapping("/add")
    public UUID addEmployee (@Valid @RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @DeleteMapping("/{employeeUUID}/remove")
    public boolean remove (@PathVariable UUID employeeUUID) {
        return employeeService.removeEmployee(employeeUUID);
    }

    @PutMapping("/{employeeUUID}/edit")
    public boolean edit(@RequestBody Employee employee, @PathVariable UUID employeeUUID){
        try {
            return employeeService.editEmployee(employeeUUID,employee);
        }catch (Exception ex){
            return false;
        }
    }
}
