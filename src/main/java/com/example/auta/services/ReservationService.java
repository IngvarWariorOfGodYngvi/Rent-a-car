package com.example.auta.services;

import com.example.auta.domain.entities.ReservationEntity;
import com.example.auta.domain.repositories.CustomerRepository;
import com.example.auta.domain.repositories.ReservationRepository;
import com.example.auta.models.classes.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerService customerService;
    private final CarService carService;
    private final BranchService branchService;
    public UUID addReservation(UUID branchUUID) {
        return null;
    }

    public boolean removeReservation(UUID reservationUUID) {
        return false;
    }

    public boolean updateReservation() {
        return false;
    }

    public Map<UUID, Reservation> getReservation() {
        Map<UUID, Reservation> map = new HashMap<>();
        reservationRepository.findAll().forEach(
                element -> map.put(element.getId(), map(element)));
        return map;
    }

    private Reservation map(ReservationEntity source) {

        return Reservation.builder()
                .reservationDate(source.getReservationDate())
                .customer(customerService.readCustomer(source.getCustomer()))
                .car(carService.readCar(source.getCar()))
                .rentalStartDate(source.getRentalStartDate())
                .rentalEndDate(source.getRentalEndDate())
                .rentalBranch(branchService.readBranch(source.getRentalBranch()))
                .returnBranch(branchService.readBranch(source.getReturnBranch()))
                .totalPrice(source.getTotalPrice())
                .build();
    }


    private ReservationEntity map(Reservation source) {

        return ReservationEntity.builder()
                .reservationDate(source.getReservationDate())
                .customer(customerService.getOrCreateCustomerEntity(source.getCustomer()))
                .car(carService.getOrCreateCarEntity(source.getCar()))
                .rentalStartDate(source.getRentalStartDate())
                .rentalEndDate(source.getRentalEndDate())
                .rentalBranch(branchService.getOrCreateBranchEntity(source.getRentalBranch()))
                .returnBranch(branchService.getOrCreateBranchEntity(source.getReturnBranch()))
                .totalPrice(source.getTotalPrice())
                .build();
    }
}
