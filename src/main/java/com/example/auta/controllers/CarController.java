package com.example.auta.controllers;

import com.example.auta.models.classes.Car;
import com.example.auta.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/car")
public class CarController {


    @Autowired
    private CarService carService;

    @PostMapping("/addcar")
    public UUID addCar(@RequestBody Car car) {
        return carService.addCar(car);
    }

    @DeleteMapping("/deletecar/{uuid}")
    public boolean deleteCar(@PathVariable UUID uuid) {
        return carService.deleteCar(uuid);

    }

    @PostMapping("/updatecarstatus/{uuid}")
    public boolean updateCarStatus(@PathVariable UUID uuid) {
        return carService.updateCarStatus(uuid);

    }
    @PostMapping("/updatecar/{uuid}")
    public UUID updateCarMileage(@PathVariable UUID uuid){
        return carService.updateCarMileage(uuid);
    }

}
