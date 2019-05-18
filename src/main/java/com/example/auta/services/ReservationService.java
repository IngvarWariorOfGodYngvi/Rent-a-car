package com.example.auta.services;

import com.example.auta.domain.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public UUID addReservation(UUID branchUUID) {
        return null;
    }

    public boolean removeReservation(UUID reservationUUID) {
        return false;
    }

    public boolean updateReservation() {
        return false;
    }
}
