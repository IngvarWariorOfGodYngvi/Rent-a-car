package com.example.auta.services;

import com.example.auta.domain.entities.ReturnEntity;
import com.example.auta.domain.repositories.EmployeeRepository;
import com.example.auta.domain.repositories.ReturnRepository;
import com.example.auta.models.classes.Return;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReturnService {

    private final ReturnRepository returnRepository;
    private final EmployeeRepository employeeRepository;

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
}
