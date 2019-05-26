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

import static java.time.temporal.ChronoUnit.DAYS;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
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

        reservation.setTotalPrice
                (new BigDecimal(1 + DAYS.between(reservation.getRentalStartDate(),
                        reservation.getRentalEndDate()))
                        .multiply(reservation.getCar().getDailyPrice()));
        if (!reservation.getRentalBranch().equals(reservation.getReturnBranch())) {
            reservation.setTotalPrice(reservation.getTotalPrice().add(new BigDecimal(50)));
        }
        ReservationEntity reservationEntity = map(reservation);
        reservationEntity = reservationRepository.saveAndFlush(reservationEntity);
        customerEntity.getReservations().add(reservationEntity);
        customerRepository.save(customerEntity);
        return reservationEntity.getId();
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
        return Optional.ofNullable(reservation).
                map(e -> reservationRepository
                        .findReservationEntityByCustomerEqualsAndCarEqualsAndRentalStartDateEquals(
                                customerService.getOrCreateCustomerEntity(e.getCustomer()),
                                carService.getOrCreateCarEntity(e.getCar()),
                                e.getRentalStartDate())
                        .orElse(reservationRepository.saveAndFlush(map(e))))
                .orElse(null);
    }

    public Map<UUID, Reservation> getReservation(UUID customerUUID) throws EntityNotFoundException {
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
            if (DAYS.between(LocalDate.now(), reservationEntity.getRentalStartDate()) < 2) {
                reservationEntity.setTotalPrice(reservationEntity.getTotalPrice().multiply(new BigDecimal(0.2)));
            } else {
                reservationEntity.setTotalPrice(new BigDecimal(0));
            }
            reservationEntity.setReservationStatus(ReservationStatus.CANCELED);
            reservationRepository.saveAndFlush(reservationEntity);
            return true;
        } catch (EntityNotFoundException ex) {
            return false;
        }
    }

}
