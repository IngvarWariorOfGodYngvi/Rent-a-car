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

    @GetMapping("/list")
    public Map<UUID, Employee> employees() {
        return employeeService.getEmployees();
    }

    @PostMapping("{branchUUID}/add")
    public UUID addEmployee (@PathVariable UUID branchUUID, @Valid @RequestBody Employee employee) {
        return employeeService.addEmployee(branchUUID,employee);
    }

    @DeleteMapping("/{employeeUUID}/remove")
    public boolean removeEmployee(@PathVariable UUID employeeUUID) {
        return employeeService.removeEmployee(employeeUUID);
    }

    @PutMapping("/{employeeUUID}/update")
    public boolean updateEmployee(@RequestBody Employee employee, @PathVariable UUID employeeUUID){
            return employeeService.updateEmployee(employeeUUID,employee);

    }
}
