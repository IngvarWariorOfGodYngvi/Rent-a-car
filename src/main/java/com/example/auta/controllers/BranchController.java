package com.example.auta.controllers;

import com.example.auta.models.classes.Car;
import com.example.auta.services.BranchService;
import com.example.auta.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/branch")
public class BranchController {

    @Autowired
    private BranchService branchService;
    @Autowired
    private CarService carService;


    @PostMapping("/{branchUUID}/addcar")
    public UUID addCar(@PathVariable UUID branchUUID, @RequestBody Car car) throws Exception {
        return carService.addCar(branchUUID,car);
    }
    @PostMapping("{branchUUID}/deletecar/{carUUID")
    public boolean deleteCar(@PathVariable UUID branchUUID,@PathVariable UUID carUUID) throws Exception {
        return carService.deleteCar(branchUUID,carUUID);
    }
}
