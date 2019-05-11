package com.example.auta.services;

import com.example.auta.models.classes.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomerService {


    public UUID addCustomer(Customer customer) {
        return null;
    }

    public Map<UUID, Customer> getCustomers() {
        return null;
    }

    public boolean removeCustomer(UUID id) {
        return false;
    }

    public boolean editCustomer(UUID id, Customer customer) {
        return false;
    }
}
