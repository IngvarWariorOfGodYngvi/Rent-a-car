package com.example.auta.services;

import com.example.auta.domain.entities.CustomerEntity;
import com.example.auta.domain.repositories.CustomerRepository;
import com.example.auta.models.classes.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public UUID addCustomer(Customer customer) {
        return customerRepository.saveAndFlush(map(customer)).getId();
    }

    public Customer readCustomer(CustomerEntity customer) {
        return map(customer);
    }

    public CustomerEntity getOrCreateCustomerEntity(Customer customer) {
        Optional<CustomerEntity> customerEntity = customerRepository
                .findCustomerEntityByForenameEqualsAndSurnameEqualsAndEmailEquals(
                        customer.getForename(), customer.getSurname(), customer.getEmail());
        return customerEntity.orElse(customerRepository.saveAndFlush(map(customer)));
    }

    public boolean removeCustomer(UUID id) {
        if (customerRepository.findById(id).isPresent()) {
            customerRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateCustomer(UUID customerUUID, Customer customer) {
        try {
            CustomerEntity updateCustomerEntity = customerRepository
                    .findById(customerUUID)
                    .orElseThrow(EntityNotFoundException::new);
            if (customer.getForename() != null) {
                updateCustomerEntity.setForename(customer.getForename());
            }
            if (customer.getSurname() != null) {
                updateCustomerEntity.setSurname(customer.getSurname());
            }
            if (customer.getAddress() != null) {
                updateCustomerEntity.setAddress(customer.getAddress());
            }
            if (customer.getEmail() != null) {
                updateCustomerEntity.setEmail(customer.getEmail());
            }
            customerRepository.save(updateCustomerEntity);
            return true;
        } catch (EntityNotFoundException ex) {
            return false;
        }
    }

    private Customer map(CustomerEntity source) {

        return Customer.builder()
                .address(source.getAddress())
                .email(source.getEmail())
                .forename(source.getForename())
                .surname(source.getSurname())
                .build();
    }

    private CustomerEntity map(Customer source) {

        return CustomerEntity.builder()
                .address(source.getAddress())
                .email(source.getEmail())
                .forename(source.getForename())
                .surname(source.getSurname())
                .build();
    }
}
