package com.example.auta.controllers;

import com.example.auta.models.classes.Reservation;
import com.example.auta.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    public ReservationService reservationService;


    @PostMapping("/add/{branchUUID}")
    public UUID addReservation(@PathVariable UUID branchUUID) {
        return reservationService.addReservation(branchUUID);
    }

    @DeleteMapping("/remove/{reservationUUID}")
    public boolean removeReservation(@PathVariable UUID reservationUUID) {
        return reservationService.removeReservation(reservationUUID);
    }

    @PostMapping("/update/{reservationUUID}")
    public boolean updateReservation(@PathVariable UUID reservationUUID, Reservation reservation){
        return reservationService.updateReservation();
    }


}


