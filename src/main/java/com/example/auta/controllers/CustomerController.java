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


    public Map<UUID, Customer> customers(){ return customerService.getCustomers();}

    @PostMapping("/add")
    public UUID addAnimal(@Valid @RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

    @DeleteMapping("/remove/{id}")
    public boolean remove(@PathVariable UUID id) {return customerService.removeCustomer(id);}

    @PutMapping("/edit/{id}")
    public boolean edit(@RequestBody Customer customer, @PathVariable UUID id){
        try {
            return customerService.editCustomer(id,customer);
        }catch (Exception ex){
            return false;
        }
    }

}
