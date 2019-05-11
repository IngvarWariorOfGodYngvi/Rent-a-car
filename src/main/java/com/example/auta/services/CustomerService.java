package com.example.auta.services;

import com.example.auta.domain.entities.CustomerEntity;
import com.example.auta.domain.repositories.CustomerRepositories;
import com.example.auta.models.classes.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepositories customerRepositories;


    public UUID addCustomer(Customer customer) {
        return customerRepositories.save(map(customer)).getId();
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

    private Customer map(CustomerEntity source) {

       return new  Customer().builder()
                .address(source.getAddress())
                .email(source.getEmail())
                .forname(source.getForname())
                .lastname(source.getLastname())
                .build();
    }

    private CustomerEntity map(Customer source) {

        return new CustomerEntity().builder()
                .address(source.getAddress())
                .email(source.getEmail())
                .forname(source.getForname())
                .lastname(source.getLastname())
                .build();
    }
}
