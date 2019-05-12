package com.example.auta.services;

import com.example.auta.domain.entities.CustomerEntity;
import com.example.auta.domain.repositories.CustomerRepositories;
import com.example.auta.models.classes.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

        Map<UUID,Customer> map = new HashMap<>();
        customerRepositories.findAll().forEach(
                element -> map.put(element.getId(),map(element)));
        return map;
    }

    public boolean removeCustomer(UUID id) {
        if(customerRepositories.findById(id).isPresent()){
            customerRepositories.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

    public boolean editCustomer(UUID id, Customer customer) {
        if(!customerRepositories.findById(id).isPresent() || customer == null){
            throw new IllegalArgumentException("Wrong argument");
        }
        CustomerEntity newEntity = customerRepositories.findById(id).get();
        newEntity.setAddress(customer.getAddress());
        newEntity.setEmail(customer.getEmail());
        newEntity.setForname(customer.getForname());
        newEntity.setLastname(customer.getLastname());
        return true;
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
