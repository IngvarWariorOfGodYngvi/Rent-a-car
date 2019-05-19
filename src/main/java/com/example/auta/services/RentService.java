package com.example.auta.services;

import com.example.auta.domain.entities.EmployeeEntity;
import com.example.auta.domain.entities.RentEntity;
import com.example.auta.domain.entities.ReservationEntity;
import com.example.auta.domain.repositories.EmployeeRepository;
import com.example.auta.domain.repositories.RentRepository;
import com.example.auta.domain.repositories.ReservationRepository;
import com.example.auta.models.classes.Employee;
import com.example.auta.models.classes.Rent;
import com.example.auta.models.classes.Reservation;
import com.example.auta.models.enums.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RentService {

    private final RentRepository rentRepository;
    private final EmployeeRepository employeeRepository;
    private final ReservationRepository reservationRepository;
    private final EmployeeService employeeService;
    private final ReservationService reservationService;

    public Map<UUID, Rent> getRents() {
        return null;
    }

    public boolean updateRentComment(UUID uuid, String comment) {

        if (!rentRepository.findById(uuid).isPresent() || comment == null) {
            throw new IllegalArgumentException("Wrong argument");
        }
        RentEntity newEntity = rentRepository.findById(uuid).get();
        newEntity.setComment(comment);
        return true;
    }

    public UUID addRent(UUID reservationUUID, UUID employeeUUID, Rent rent) {
        RentEntity rentEntity = rentRepository.saveAndFlush(map(rent));
        ReservationEntity reservationEntity = reservationRepository
                .findById(reservationUUID)
                .orElseThrow(EntityExistsException::new);
        EmployeeEntity employeeEntity = employeeRepository
                .findById(employeeUUID)
                .orElseThrow(EntityExistsException::new);
        rentEntity.setRentalStart(reservationEntity.getRentalStartDate());
        rentEntity.setEmployee(employeeEntity);
        rentEntity.setReservation(reservationEntity);
        reservationEntity.setReservationStatus(ReservationStatus.IN_RENT);
        return rentRepository.saveAndFlush(rentEntity).getId();

    }

    private RentEntity map(Rent rent) {
        return RentEntity.builder()
                .employee(employeeService.getOrCreateEmployeeEntity(rent.getEmployee()))
                .rentalStart(rent.getRentalStart())
                .reservation(reservationService.getOrCreateReservationEntity(rent.getReservation()))
                .comment(rent.getComment())
                .build();

    }

}
