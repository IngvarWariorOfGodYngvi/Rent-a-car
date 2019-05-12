package com.example.auta.services;

import com.example.auta.domain.entities.BranchEntity;
import com.example.auta.domain.entities.CarEntity;
import com.example.auta.domain.entities.CompanyEntity;
import com.example.auta.domain.repositories.BranchRepository;
import com.example.auta.domain.repositories.CarRepository;
import com.example.auta.models.classes.Branch;
import com.example.auta.models.classes.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final BranchRepository branchRepository;
    private final BranchService branchService;

    @Autowired
    public CarService(CarRepository carRepo, BranchRepository branchRepository, BranchService branchService) {
        this.carRepository = carRepo;
        this.branchRepository = branchRepository;
        this.branchService = branchService;
    }

    public UUID addCar(UUID branchUUID, Car car) throws Exception {
        BranchEntity branchEntity = branchRepository
                .findById(branchUUID)
                .orElseThrow(Exception::new);
        CarEntity carEntity = map(car);
        carEntity = carRepository.saveAndFlush(carEntity);
        branchEntity.getCars().add(carEntity);
        branchRepository.save(branchEntity);
        return carEntity.getId();
    }

    public boolean deleteCar(UUID branchUUID, UUID car) throws Exception {
        BranchEntity branch = branchRepository
                .findById(branchUUID)
                .orElseThrow(Exception::new);
        CarEntity carEntity = carRepository
                .findById(car)
                .orElseThrow(Exception::new);
        boolean result = branch.getCars().remove(carEntity);
        branchRepository.save(branch);

        return result;
    }


    public Map<UUID, Car> getCars() {
        Map<UUID, Car> map = new HashMap<>();
        carRepository.findAll().forEach(
                element -> map.put(element.getId(), map(element)));
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
