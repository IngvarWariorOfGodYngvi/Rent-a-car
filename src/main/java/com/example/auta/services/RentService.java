package com.example.auta.services;

import com.example.auta.domain.entities.RentEntity;
import com.example.auta.domain.repositories.EmployeeRepository;
import com.example.auta.domain.repositories.RentRepository;
import com.example.auta.models.classes.Rent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RentService {

    private final RentRepository rentRepository;
    private final EmployeeRepository employeeRepository;

    public Map<UUID, Rent> getRents() {
        return null;
    }

    public boolean updateRentComment(UUID uuid,String comment) {

        if (!rentRepository.findById(uuid).isPresent() || comment ==null){
            throw new IllegalArgumentException("Wrong argument");
        }
        RentEntity newEntity = rentRepository.findById(uuid).get();
        newEntity.setComment(comment);
        return true;
    }

    public boolean updateRentEmployee(UUID uuid, UUID empId) {
        if (!rentRepository.findById(uuid).isPresent() || !employeeRepository.findById(empId).isPresent()){
            throw new IllegalArgumentException("Wrong argument");
        }
        RentEntity newEntity = rentRepository.findById(uuid).get();
        newEntity.setEmployee(employeeRepository.findById(empId).get());
        return true;
    }
}
