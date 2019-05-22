package com.example.auta.controllers;import com.example.auta.domain.entities.CustomerEntity;import com.example.auta.domain.entities.ReservationEntity;import com.example.auta.domain.repositories.CustomerRepository;import com.example.auta.domain.repositories.ReservationRepository;import com.example.auta.models.classes.Reservation;import com.example.auta.models.enums.ReservationStatus;import org.junit.After;import org.junit.Before;import org.junit.Test;import org.junit.runner.RunWith;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.boot.test.context.SpringBootTest;import org.springframework.boot.test.web.client.TestRestTemplate;import org.springframework.http.HttpStatus;import org.springframework.http.ResponseEntity;import org.springframework.test.context.junit4.SpringRunner;import javax.persistence.EntityExistsException;import java.time.LocalDate;import java.util.UUID;import static org.junit.Assert.*;@RunWith(SpringRunner.class)@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)public class ReservationControllerTest {    @Autowired    private TestRestTemplate testRestTemplate;    @Autowired    private CustomerRepository customerRepository;    @Autowired    private ReservationRepository reservationRepository;    private CustomerEntity customerEntity;    @Before    public void setUp() {        customerEntity = customerRepository.saveAndFlush(                CustomerEntity.builder().surname("Rabczewska").build());    }    @After    public void tearDown() {        reservationRepository.deleteAll();        customerRepository.deleteAll();    }    @Test    public void addReservation() {        Reservation reservation = Reservation.builder().reservationStatus(ReservationStatus.BOOKED).build();        ResponseEntity<String> re = testRestTemplate.postForEntity(                String.format("/reservation/add?customerUUID=%s", customerEntity.getId()),                reservation, String.class);        assertEquals(HttpStatus.OK, re.getStatusCode());        assertTrue(reservationRepository.findById(UUID.fromString(                re.getBody().replace("\"",""))).isPresent());    }    @Test    public void removeReservation() {        ReservationEntity reservationEntity = reservationRepository.saveAndFlush(                ReservationEntity.builder().reservationStatus(ReservationStatus.CANCELED).build());        testRestTemplate.delete(String.format("/reservation/%s/remove", reservationEntity.getId()));        assertTrue(!reservationRepository.findById(reservationEntity.getId()).isPresent());    }    @Test    public void updateReservation() {        ReservationEntity reservationEntity = reservationRepository.saveAndFlush(                ReservationEntity.builder().rentalStartDate(LocalDate.of(2019,7,1)).build());        Reservation reservation = Reservation.builder()                .rentalStartDate(LocalDate.of(2019,8,2)).build();        ResponseEntity<String> re = testRestTemplate.postForEntity(                String.format("/reservation/%s/update", reservationEntity.getId()),                reservation, String.class);        assertEquals(reservation.getRentalStartDate(),                     reservationRepository                             .findById(reservationEntity.getId())                             .orElseThrow(EntityExistsException::new)                             .getRentalStartDate());    }    @Test    public void cancelReservation() {        ReservationEntity reservationEntity = reservationRepository.saveAndFlush(                ReservationEntity.builder().reservationStatus(ReservationStatus.BOOKED).build());        ResponseEntity<String> re = testRestTemplate.getForEntity(                String.format("/reservation/%s/cancel", reservationEntity.getId()),                String.class);        assertEquals(HttpStatus.OK, re.getStatusCode());        assertEquals(ReservationStatus.CANCELED,                     reservationRepository                             .findById(reservationEntity.getId())                             .orElseThrow(EntityExistsException::new)                             .getReservationStatus());    }}