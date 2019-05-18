package com.example.auta.controllers;

import com.example.auta.models.classes.Rent;
import com.example.auta.services.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/Rent")
public class RentController {

    @Autowired
    private RentService rentService;

    @PostMapping ("/rentlist")
    public Map<UUID, Rent> getRents() {return rentService.getRents();}

    @PostMapping ("/updatecomment/{uuid")
    public boolean updateRentComment(@PathVariable UUID uuid){
        return rentService.updateRentComment(uuid);
    }

    @PostMapping("/updateemployee/{uuid}")
    public boolean updateRentEmployee(@PathVariable UUID uuid){
        return rentService.updateRentEmployee(uuid);
    }



}
