package com.example.auta.services;

import com.example.auta.domain.entities.CustomerEntity;
import com.example.auta.domain.entities.ReservationEntity;
import com.example.auta.domain.repositories.CustomerRepository;
import com.example.auta.domain.repositories.ReservationRepository;
import com.example.auta.models.classes.Customer;
import com.example.auta.models.classes.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
@RequiredArgsConstructor
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

    public boolean editCustomer(UUID id, Customer customer) {
        if (!customerRepository.findById(id).isPresent() || customer == null) {
            throw new IllegalArgumentException("Wrong argument");
        }
        CustomerEntity newEntity = customerRepository.findById(id).get();
        newEntity.setAddress(customer.getAddress());
        newEntity.setEmail(customer.getEmail());
        newEntity.setForname(customer.getForename());
        newEntity.setLastname(customer.getSurname());
        return true;
    }

    private Customer map(CustomerEntity source) {

        return Customer.builder()
                .address(source.getAddress())
                .email(source.getEmail())
                .forename(source.getForname())
                .surname(source.getLastname())
                .build();
    }

    private CustomerEntity map(Customer source) {

        return CustomerEntity.builder()
                .address(source.getAddress())
                .email(source.getEmail())
                .forname(source.getForename())
                .lastname(source.getSurname())
                .build();
    }
}
