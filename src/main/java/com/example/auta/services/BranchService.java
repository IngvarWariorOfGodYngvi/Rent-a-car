package com.example.auta.services;

import com.example.auta.domain.entities.BranchEntity;
import com.example.auta.domain.entities.CarEntity;
import com.example.auta.domain.entities.EmployeeEntity;
import com.example.auta.domain.repositories.BranchRepository;
import com.example.auta.models.classes.Branch;
import com.example.auta.models.classes.Car;
import com.example.auta.models.classes.Customer;
import com.example.auta.models.classes.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BranchService {

    private final BranchRepository branchRepository;
    private final CarService carService;
    private final EmployeeService employeeService;

    @Autowired
    public BranchService(BranchRepository branchRepo,
                         CarService carService,
                         @Lazy EmployeeService employeeService) {
        this.branchRepository = branchRepo;
        this.carService = carService;
        this.employeeService = employeeService;
    }

    public BranchEntity getOrCreateBranchEntity(Branch branch) {
        return Optional.ofNullable(branch)
                .map(e-> branchRepository
                        .findBranchEntityByAddressEquals(e.getAddress())
                        .orElse(branchRepository.saveAndFlush(map(e))))
                .orElse(null);
    }

    public Branch read(BranchEntity branch) {
        return Optional.ofNullable(branch).map(this::map).orElse(null);
    }

    private BranchEntity map(Branch branch) {
        return BranchEntity.builder()
                .address(branch.getAddress())
                .cars(branch.getCars()
                        .stream()
                        .map(carService::saveCar)
                        .collect(Collectors.toSet()))
                .employees(branch.getEmployees()
                        .stream()
                        .map(employeeService::getOrCreateEmployeeEntity)
                        .collect(Collectors.toSet()))
                .build();
    }

    public Branch map(BranchEntity branch) {
        return Branch.builder()
                .address(branch.getAddress())
                .cars(branch.getCars()
                        .stream()
                        .map(carService::readCar)
                        .collect(Collectors.toSet()))
                .employees(branch.getEmployees()
                        .stream()
                        .map(employeeService::readEmployee)
                        .collect(Collectors.toSet()))
                .build();
    }

    public Map<UUID, Employee> getEmployees(UUID branchUUID) {
        Optional<BranchEntity> branchEntity = branchRepository
                .findById(branchUUID);
        if (branchEntity.isPresent()) {
            Map<UUID, Employee> employees = new HashMap<>();
            Set<EmployeeEntity> set = branchEntity
                    .get().getEmployees();
            set.forEach(e -> employees.put(e.getId(), employeeService.readEmployee(e)));
            System.out.println("a");
            return employees;
        } else {
            throw new EntityNotFoundException();
        }
    }

    public Map<UUID, Car> getCars(UUID branchUUID) {
        Optional<BranchEntity> branchEntity = branchRepository
                .findById(branchUUID);
        if (branchEntity.isPresent()) {
            Map<UUID, Car> cars = new HashMap<>();
            Set<CarEntity> set = branchEntity
                    .get()
                    .getCars();
            set.forEach(c -> cars.put(c.getId(), carService.readCar(c)));
            return cars;
        } else {
            throw new EntityNotFoundException();
        }
    }
}
