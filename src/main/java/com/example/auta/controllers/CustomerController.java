package com.example.auta.controllers;


import com.example.auta.models.classes.Customer;
import com.example.auta.models.classes.Reservation;
import com.example.auta.services.CustomerService;
import com.example.auta.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final ReservationService reservationService;

    @PostMapping("/add")
    public UUID addCustomer(@Valid @RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

    @DeleteMapping("/{customerUUID}/remove")
    public boolean remove(@PathVariable UUID customerUUID) {
        return customerService.removeCustomer(customerUUID);
    }

    @PutMapping("/{customerUUID}/edit")
    public boolean edit(@RequestBody Customer customer, @PathVariable UUID customerUUID) {
        try {
            return customerService.updateCustomer(customerUUID, customer);
        } catch (Exception ex) {
            return false;
        }
    }

    @GetMapping("/{customerUUID}/reservations")
    public Map<UUID, Reservation> getReservations(@PathVariable UUID customerUUID){
        return reservationService.getReservations(customerUUID);
    }

}
