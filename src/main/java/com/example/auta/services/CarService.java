package com.example.auta.services;

import com.example.auta.domain.repositories.CarRepository;
import com.example.auta.models.classes.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepo){
        this.carRepository = carRepo;
    }

    public UUID addCar(Car car) {
        return null;
    }

    public boolean deleteCar(UUID uuid) {
        return false;
    }

    public boolean updateCarStatus(UUID uuid) {
        return false;
    }

    public UUID updateCarMileage(UUID uuid) {
        return null;
    }
}
