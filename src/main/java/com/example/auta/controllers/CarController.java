package com.example.auta.controllers;

import com.example.auta.models.classes.Car;
import com.example.auta.models.classes.Customer;
import com.example.auta.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/Car")
public class CarController {


    @Autowired
    private CarService carService;

    @GetMapping("/list")
    public Map<UUID, Car> getCars() {
        return carService.getCars();
    }

    @PostMapping("/{carUUID}/updatestatus")
    public boolean updateCarStatus(@PathVariable UUID carUUID, @RequestBody Car car) {
        return carService.updateCarStatus(carUUID, car);
    }

    @PostMapping("/{carUUID}/updatemileage")
    public boolean updateCarMileage(@PathVariable UUID carUUID, @RequestBody Car car) {
        return carService.updateCarMileage(carUUID, car);
    }

}
