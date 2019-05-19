package com.example.auta.controllers;

import com.example.auta.models.classes.Car;
import com.example.auta.models.classes.Customer;
import com.example.auta.models.classes.Employee;
import com.example.auta.services.BranchService;
import com.example.auta.services.CarService;
import com.example.auta.services.CustomerService;
import com.example.auta.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/branch")
public class BranchController {

    @Autowired
    private BranchService branchService;
    @Autowired
    private CarService carService;


    @PostMapping("/{branchUUID}/addcar")
    public UUID addCar(@PathVariable UUID branchUUID,
                       @RequestBody Car car) throws Exception {
        return carService.addCar(branchUUID, car);
    }

    @DeleteMapping("/{branchUUID}/deletecar/{carUUID}")
    public boolean deleteCar(@PathVariable UUID branchUUID,
                             @PathVariable UUID carUUID) throws Exception {
        return carService.deleteCar(branchUUID, carUUID);
    }

    @GetMapping("/{branchUUID}/employeelist")
    public Map<UUID, Employee> getEmployee(@PathVariable UUID branchUUID) {
        return branchService.getEmployees(branchUUID);
    }
    @GetMapping("{branchUUID}/carlist")
    public Map<UUID, Car> getCars(@PathVariable UUID branchUUID) {
        return branchService.getCars(branchUUID);
    }


}
