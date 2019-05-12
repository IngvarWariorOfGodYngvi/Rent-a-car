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

    @PostMapping("/carlist")
    public Map<UUID, Car> getCars(){ return carService.getCars();}

    @PostMapping("/updatecarstatus/{uuid}")
    public boolean updateCarStatus(@PathVariable UUID uuid) {
        return carService.updateCarStatus(uuid);
    }
    @PostMapping("/updatecarmileage/{uuid}")
    public boolean updateCarMileage(@PathVariable UUID uuid){
        return carService.updateCarMileage(uuid);
    }

}
