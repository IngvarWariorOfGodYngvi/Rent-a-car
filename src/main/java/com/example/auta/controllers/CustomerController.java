package com.example.auta.controllers;


import com.example.auta.models.classes.Customer;
import com.example.auta.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    public Map<UUID, Customer> customers() {
        return customerService.getCustomers();
    }

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
            return customerService.editCustomer(customerUUID, customer);
        } catch (Exception ex) {
            return false;
        }
    }

}
