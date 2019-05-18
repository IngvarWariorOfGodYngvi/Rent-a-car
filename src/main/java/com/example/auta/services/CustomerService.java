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
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    @Autowired
    public CustomerService(CustomerRepository customerRepo,
                           ReservationRepository reservationRepo,
                           ReservationService reservationServ){
        this.customerRepository =  customerRepo;
        this.reservationRepository = reservationRepo;
        this.reservationService = reservationServ;
    }

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

    public Map<UUID, Reservation> getReservations(UUID customerUUID) throws EntityNotFoundException{
        CustomerEntity customer = customerRepository
                .findById(customerUUID)
                .orElseThrow(EntityNotFoundException::new);
        Set<ReservationEntity> reservationEntities = reservationRepository.findAllByCustomer(customer);
        Map<UUID, Reservation> reservations = new HashMap<>();
        reservationEntities.forEach(e->reservations.put(e.getId(), reservationService.read(e)));
        return reservations;
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
