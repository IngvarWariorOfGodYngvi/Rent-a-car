package com.example.auta.controllers;

import com.example.auta.domain.entities.BranchEntity;
import com.example.auta.domain.entities.CarEntity;
import com.example.auta.domain.entities.CompanyEntity;
import com.example.auta.domain.repositories.BranchRepository;
import com.example.auta.domain.repositories.CarRepository;
import com.example.auta.domain.repositories.CompanyRepository;
import com.example.auta.models.classes.Car;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BranchControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BranchRepository branchRepository;


    @Autowired
    private CompanyRepository companyRepository;
    private BranchEntity branchEntity;


    @Before
    public void setUp(){
        CompanyEntity company = CompanyEntity.builder().name("WiesiekCar").build();
        BranchEntity branch = BranchEntity.builder().address("ul. Cebulowa 34, 04-123 Warszawa").build();
        company.getBranches().add(branch);
        company = companyRepository.saveAndFlush(company);
        branchEntity = company.getBranches().get(0);
    }

    @After
    public void tearDown() {
        carRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @Test
    public void addCar() {
        Car car = Car.builder().make("FIAT").build();
        ResponseEntity<String> re = testRestTemplate.postForEntity(
                String.format("/branch/%s/addcar",branchEntity.getId().toString()),
                car, String.class);
        assertEquals(HttpStatus.OK, re.getStatusCode());
        String carUUID = re.getBody().replace("\"", "");
        assertTrue(carRepository.findById(UUID.fromString(carUUID)).isPresent());
    }

    @Test
    public void deleteCar() {
        CarEntity car = CarEntity.builder().make("FIAT").build();
        CarEntity carEntity = carRepository.saveAndFlush(car);
        branchEntity.getCars().add(carEntity);
        branchEntity = branchRepository.saveAndFlush(branchEntity);
        testRestTemplate.delete(String.format("/branch/%s/deletecar/%s",
                                              branchEntity.getId().toString(),
                                              carEntity.getId().toString()));
        assertTrue(!carRepository.findById(carEntity.getId()).isPresent());
    }
}