package com.example.auta.controllers;import com.example.auta.domain.entities.BranchEntity;import com.example.auta.domain.entities.CompanyEntity;import com.example.auta.domain.entities.EmployeeEntity;import com.example.auta.domain.repositories.BranchRepository;import com.example.auta.domain.repositories.CompanyRepository;import com.example.auta.domain.repositories.EmployeeRepository;import com.example.auta.models.classes.Employee;import com.example.auta.services.BranchService;import javassist.NotFoundException;import org.junit.After;import org.junit.Before;import org.junit.Test;import org.junit.runner.RunWith;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.boot.test.context.SpringBootTest;import org.springframework.boot.test.web.client.TestRestTemplate;import org.springframework.http.HttpStatus;import org.springframework.http.ResponseEntity;import org.springframework.test.context.junit4.SpringRunner;import javax.persistence.EntityNotFoundException;import java.util.Optional;import java.util.UUID;import static org.junit.Assert.*;@RunWith(SpringRunner.class)@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)public class EmployeeControllerTest {    @Autowired    private TestRestTemplate testRestTemplate;    @Autowired    private CompanyRepository companyRepository;    @Autowired    private BranchRepository branchRepository;    @Autowired    private BranchService branchService;    @Autowired    private EmployeeRepository employeeRepository;    @Before    public void setUp() {        CompanyEntity company = CompanyEntity.builder().name("WiesiekCar").build();        BranchEntity branch = BranchEntity.builder().address("ul. Cebulowa 34, 04-123 Warszawa").build();        company.getBranches().add(branch);        companyRepository.saveAndFlush(company);    }    @After    public void tearDown() throws Exception {        companyRepository.deleteAll();    }    @Test    public void employees() {    }    @Test    public void addEmployee() {        Optional<BranchEntity> branchEntity = branchRepository.findBranchEntityByAddressEquals("ul. Cebulowa 34, 04-123 Warszawa");        Employee employee = Employee                .builder()                .surname("Kwiatkowski")                .branch(branchService.readBranch(branchEntity.orElseThrow(EntityNotFoundException::new)))                .build();        ResponseEntity<String> re = testRestTemplate.postForEntity("/employee/add",employee,String.class);        assertEquals(HttpStatus.OK,re.getStatusCode());        String employeeUUID = re.getBody().replace("\"", "");        assertTrue(employeeRepository.findById(UUID.fromString(employeeUUID)).isPresent());    }    @Test    public void removeEmployee() {        Optional<BranchEntity> branchEntity = branchRepository.findBranchEntityByAddressEquals("ul. Cebulowa 34, 04-123 Warszawa");        EmployeeEntity employee = EmployeeEntity                .builder()                .surname("Kwiatkowski")                .branch(branchEntity.orElseThrow(EntityNotFoundException::new))                .build();        employee = employeeRepository.saveAndFlush(employee);        testRestTemplate.delete(String.format("/employee/remove/%s", employee.getId()));        assertTrue(!employeeRepository.findById(employee.getId()).isPresent());    }    @Test    public void edit() {    }}