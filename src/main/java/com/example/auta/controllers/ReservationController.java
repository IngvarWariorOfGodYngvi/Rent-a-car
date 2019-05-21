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

    @PostMapping("/add")
    public UUID addReservation(@RequestBody Reservation reservation,
                               @RequestParam("customerUUID") UUID customerUUID) {
        return reservationService.addReservation(customerUUID,reservation);
    }

    @DeleteMapping("/{reservationUUID}/remove")
    public boolean removeReservation(@PathVariable UUID reservationUUID) {
        return reservationService.removeReservation(reservationUUID);
    }

    @PostMapping("/{reservationUUID}/update")
    public boolean updateReservation(@PathVariable UUID reservationUUID, Reservation reservation){
        return reservationService.updateReservation(reservationUUID,reservation);
    }

    @PostMapping("/reservationUUID/cancel")
    public boolean cancelReservation(@PathVariable UUID reservationUUID){
        return reservationService.cancelReservation(reservationUUID);
    }



}


