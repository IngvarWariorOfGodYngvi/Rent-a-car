package com.example.auta.services;

import com.example.auta.domain.entities.CarEntity;
import com.example.auta.domain.entities.CompanyEntity;
import com.example.auta.domain.repositories.CarRepository;
import com.example.auta.models.classes.Car;
import com.example.auta.models.classes.Company;
import com.example.auta.models.enums.CarStatus;
import com.example.auta.models.enums.Suspension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepo){
        this.carRepository = carRepo;
    }

    public UUID addCar(Car car) {
        return carRepository.save(map(car)).getId();
    }

    public boolean deleteCar(UUID uuid) {

        if (carRepository.existsById(uuid)) {
            carRepository.deleteById(uuid);
            return true;
        } else {
            return false;
        }
    }
    public Map<UUID,Car> getCars(){
        Map<UUID,Car> map = new HashMap<>();
        carRepository.findAll().forEach(
                element -> map.put(element.getId(),map(element)));
        return map;
    }

    public boolean updateCarStatus(UUID uuid) {
        return false;
    }

    public boolean updateCarMileage(UUID carUuid) {

        return false;
    }


    private CarEntity map(Car c) {
        CarEntity cc = new CarEntity();

        return cc.builder()
                .make(c.getMake())
                .model(c.getModel())
                .suspension(c.getSuspension())
                .yearOfProduction(c.getYearOfProduction())
                .color(c.getColor())
                .mileage(c.getMileage())
                .carStatus(c.getCarStatus())
                .dailyPrice(c.getDailyPrice())
                .build();
    }


    private Car map(CarEntity c) {
        return new Car().builder()
                .make(c.getMake())
                .model(c.getModel())
                .suspension(c.getSuspension())
                .yearOfProduction(c.getYearOfProduction())
                .color(c.getColor())
                .mileage(c.getMileage())
                .carStatus(c.getCarStatus())
                .dailyPrice(c.getDailyPrice())
                .build();
    }

}
