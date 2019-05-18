package com.example.auta.services;

import com.example.auta.domain.entities.CustomerEntity;
import com.example.auta.domain.repositories.CustomerRepository;
import com.example.auta.models.classes.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;


    public UUID addCustomer(Customer customer) {
        return customerRepository.save(map(customer)).getId();
    }

    public Customer readCustomer(CustomerEntity customer) {
        return map(customer);
    }

    public CustomerEntity getOrCreateCustomerEntity(Customer customer) {
        Optional<CustomerEntity> customerEntity = customerRepository
                .findCustomerEntityByFornameEqualsAndLastnameEqualsAndEmailEquals(
                        customer.getForname(), customer.getLastname(), customer.getEmail());
        return customerEntity.orElse(customerRepository.saveAndFlush(map(customer)));
    }

    public Map<UUID, Customer> getCustomers() {

        Map<UUID, Customer> map = new HashMap<>();
        customerRepository.findAll().forEach(
                element -> map.put(element.getId(), map(element)));
        return map;
    }

    public boolean removeCustomer(UUID id) {
        if (customerRepository.findById(id).isPresent()) {
            customerRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean editCustomer(UUID id, Customer customer) {
        if (!customerRepository.findById(id).isPresent() || customer == null) {
            throw new IllegalArgumentException("Wrong argument");
        }
        CustomerEntity newEntity = customerRepository.findById(id).get();
        newEntity.setAddress(customer.getAddress());
        newEntity.setEmail(customer.getEmail());
        newEntity.setForname(customer.getForname());
        newEntity.setLastname(customer.getLastname());
        return true;
    }

    private Customer map(CustomerEntity source) {

        return new Customer().builder()
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
