package com.example.auta.controllers;

import com.example.auta.models.classes.Rent;
import com.example.auta.services.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/Rent")
public class RentController {

    @Autowired
    private RentService rentService;

    @PostMapping ("/rentlist")
    public Map<UUID, Rent> getRents() {return rentService.getRents();}

    @PostMapping ("/updatecomment/{uuid}")
    public boolean updateRentComment(@PathVariable UUID uuid, @RequestBody String comment){
        return rentService.updateRentComment(uuid,comment);
    }

    @PostMapping("/updateemployee/{uuid}/{empid}")
    public boolean updateRentEmployee(@PathVariable UUID uuid,UUID empid){
        return rentService.updateRentEmployee(uuid,empid);
    }



}
