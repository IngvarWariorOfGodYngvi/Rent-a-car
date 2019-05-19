package com.example.auta.services;

import com.example.auta.domain.entities.CustomerEntity;
import com.example.auta.domain.entities.ReservationEntity;
import com.example.auta.domain.repositories.CustomerRepository;
import com.example.auta.domain.repositories.ReservationRepository;
import com.example.auta.models.classes.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final CarService carService;
    private final BranchService branchService;

    public UUID addReservation(Reservation reservation) {
        return reservationRepository.save(map(reservation)).getId();
    }

    public boolean removeReservation(UUID reservationUUID) throws EntityNotFoundException {
        ReservationEntity reservation = reservationRepository
                .findById(reservationUUID)
                .orElseThrow(EntityNotFoundException::new);
        reservationRepository.delete(reservation);
        return !reservationRepository.findById(reservationUUID).isPresent();

    }
    //----------------------------------------------------------------------------
    public boolean updateReservation() {
        return false;
    }
    //----------------------------------------------------------------------------

    public Reservation read(ReservationEntity reservation){
        return map(reservation);
    }

    public Map<UUID, Reservation> getReservation() {
        Map<UUID, Reservation> map = new HashMap<>();
        reservationRepository.findAll().forEach(
                element -> map.put(element.getId(), map(element)));
        return map;
    }

    public Map<UUID, Reservation> getReservations(UUID customerUUID) throws EntityNotFoundException {
        CustomerEntity customer = customerRepository
                .findById(customerUUID)
                .orElseThrow(EntityNotFoundException::new);
        Set<ReservationEntity> reservationEntities = reservationRepository.findAllByCustomer(customer);
        Map<UUID, Reservation> reservations = new HashMap<>();
        reservationEntities.forEach(e->reservations.put(e.getId(), read(e)));
        return reservations;
    }


    private Reservation map(ReservationEntity source) {

        return Reservation.builder()
                .reservationDate(source.getReservationDate())
                .customer(customerService.readCustomer(source.getCustomer()))
                .car(carService.readCar(source.getCar()))
                .rentalStartDate(source.getRentalStartDate())
                .rentalEndDate(source.getRentalEndDate())
                .rentalBranch(branchService.read(source.getRentalBranch()))
                .returnBranch(branchService.read(source.getReturnBranch()))
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
