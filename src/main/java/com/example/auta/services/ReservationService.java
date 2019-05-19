package com.example.auta.services;

import com.example.auta.domain.entities.CustomerEntity;
import com.example.auta.domain.entities.ReservationEntity;
import com.example.auta.domain.repositories.CustomerRepository;
import com.example.auta.domain.repositories.ReservationRepository;
import com.example.auta.models.classes.Car;
import com.example.auta.models.classes.Customer;
import com.example.auta.models.classes.Reservation;
import com.example.auta.models.enums.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final CarService carService;
    private final BranchService branchService;

    public UUID addReservation(UUID customerUUID, Reservation reservation) {
        CustomerEntity customerEntity = customerRepository
                .findById(customerUUID)
                .orElseThrow(EntityNotFoundException::new);
        Customer customer = customerService.readCustomer(customerEntity);
        reservation.setCustomer(customer);
        return reservationRepository.saveAndFlush(map(reservation)).getId();
    }

    public boolean removeReservation(UUID reservationUUID) throws EntityNotFoundException {
        ReservationEntity reservation = reservationRepository
                .findById(reservationUUID)
                .orElseThrow(EntityNotFoundException::new);
        reservationRepository.delete(reservation);
        return !reservationRepository.findById(reservationUUID).isPresent();

    }

    public Reservation read(ReservationEntity reservation) {
        return map(reservation);
    }


    public ReservationEntity getOrCreateReservationEntity(Reservation reservation) {
        Optional<ReservationEntity> reservationEntity = reservationRepository
                .findReservationEntityByCustomerEqualsAndCarEqualsAndRentalStartDateEquals(customerService
                        .getOrCreateCustomerEntity(reservation.getCustomer()),carService
                        .getOrCreateCarEntity(reservation.getCar()),reservation
                        .getRentalStartDate());
        return reservationEntity.orElse(reservationRepository.saveAndFlush(map(reservation)));
    }
    private Customer customer;
    private Car car;
    private LocalDate rentalStartDate;

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
        reservationEntities.forEach(e -> reservations.put(e.getId(), read(e)));
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
                .reservationStatus(source.getReservationStatus())
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
                .reservationStatus(source.getReservationStatus())
                .build();
    }

    public boolean updateReservation(UUID reservationUUID, Reservation reservation) {
        try {
            ReservationEntity updateReservationEntity = reservationRepository
                    .findById(reservationUUID)
                    .orElseThrow(EntityNotFoundException::new);
            if (reservation.getCar() != null) {
                updateReservationEntity.setCar(carService.getOrCreateCarEntity(reservation.getCar()));
            }
            if (reservation.getRentalStartDate() != null) {
                updateReservationEntity.setRentalStartDate(reservation.getRentalStartDate());
            }
            if (reservation.getRentalEndDate() != null) {
                updateReservationEntity.setRentalEndDate(reservation.getRentalEndDate());
            }
            if (reservation.getRentalBranch() != null) {
                updateReservationEntity.setRentalBranch(branchService.getOrCreateBranchEntity(reservation.getRentalBranch()));
            }
            if (reservation.getReturnBranch() != null) {
                updateReservationEntity.setReturnBranch(branchService.getOrCreateBranchEntity(reservation.getRentalBranch()));
            }
            reservationRepository.saveAndFlush(updateReservationEntity);
            return true;
        } catch (EntityNotFoundException ex) {
            return false;
        }
    }


    public boolean cancelReservation(UUID reservationUUID) {
        try {
            ReservationEntity reservationEntity = reservationRepository
                    .findById(reservationUUID)
                    .orElseThrow(EntityNotFoundException::new);
            reservationEntity.setReservationStatus(ReservationStatus.CANCELED);
            reservationRepository.saveAndFlush(reservationEntity);
            return true;
        } catch (EntityNotFoundException ex) {
            return false;
        }
    }

}
