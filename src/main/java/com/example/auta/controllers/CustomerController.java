package com.example.auta.controllers;


import com.example.auta.models.classes.Customer;
import com.example.auta.models.classes.Reservation;
import com.example.auta.services.CustomerService;
import com.example.auta.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final ReservationService reservationService;

    @PostMapping("/add")
    public UUID addCustomer(@RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

    @DeleteMapping("/{customerUUID}/remove")
    public boolean removeCustomer(@PathVariable UUID customerUUID) {
        return customerService.removeCustomer(customerUUID);
    }

    @PutMapping("/{customerUUID}/update")
    public boolean updateCustomer(@RequestBody Customer customer,
                                  @PathVariable UUID customerUUID) {
        return customerService.updateCustomer(customerUUID, customer);
    }

    @GetMapping("/{customerUUID}/reservations")
    public Map<UUID, Reservation> getReservations(@PathVariable UUID customerUUID){
        return reservationService.getReservation(customerUUID);
    }

}
