package com.example.auta.services;

import com.example.auta.domain.entities.EmployeeEntity;
import com.example.auta.domain.entities.RentEntity;
import com.example.auta.domain.entities.ReturnEntity;
import com.example.auta.domain.repositories.EmployeeRepository;
import com.example.auta.domain.repositories.RentRepository;
import com.example.auta.domain.repositories.ReturnRepository;
import com.example.auta.models.classes.Return;
import com.example.auta.models.enums.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReturnService {

    private final ReturnRepository returnRepository;
    private final EmployeeRepository employeeRepository;
    private final RentRepository rentRepository;

    public Map<UUID, Return> getReturns() {
        return null;
    }

    public boolean updateReturnEmployee(UUID uuid, UUID empId) {

        if (!returnRepository.findById(uuid).isPresent() || !employeeRepository.findById(empId).isPresent()) {
            throw new IllegalArgumentException("Wrong argument");
        }
        ReturnEntity newEntity = returnRepository.findById(uuid).get();
        newEntity.setEmployee(employeeRepository.findById(empId).get());
        return true;
    }

    public boolean updateReturnExtraPayment(UUID uuid, Integer extraPay) {
        if (!returnRepository.findById(uuid).isPresent() || extraPay == null) {
            throw new IllegalArgumentException("Wrong argument");
        }
        ReturnEntity newEntity = returnRepository.findById(uuid).get();
        newEntity.setExtraPayment(new BigDecimal(extraPay));
        return true;
    }

    public UUID addReturn(UUID employeeUUID, UUID rentUUID,Return returns) {
        EmployeeEntity employeeEntity = employeeRepository
                .findById(employeeUUID)
                .orElseThrow(EntityNotFoundException::new);
        RentEntity rentEntity = rentRepository
                .findById(rentUUID)
                .orElseThrow(EntityNotFoundException::new);
        ReturnEntity returnEntity =  ReturnEntity.builder()
                .employee(employeeEntity)
                .reservation(rentEntity.getReservation())
                .rentalEndDate(LocalDate.now())
                .endMileage(returns.getEndMileage())
                .build();
        rentEntity.getReservation().getCar().setMileage(returns.getEndMileage());
        rentEntity.getReservation().setReservationStatus(ReservationStatus.RETURNED);
        return returnRepository.saveAndFlush(returnEntity).getId();
    }
}
