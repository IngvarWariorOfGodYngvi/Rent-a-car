package com.example.auta.controllers;import com.example.auta.domain.entities.*;import com.example.auta.domain.repositories.BranchRepository;import com.example.auta.domain.repositories.CompanyRepository;import com.example.auta.domain.repositories.EmployeeRepository;import com.example.auta.domain.repositories.ReservationRepository;import com.example.auta.models.classes.Branch;import com.example.auta.models.classes.Company;import com.example.auta.models.classes.Customer;import com.example.auta.models.classes.Employee;import org.junit.After;import org.junit.Before;import org.junit.Test;import org.junit.runner.RunWith;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.boot.test.context.SpringBootTest;import org.springframework.boot.test.web.client.TestRestTemplate;import org.springframework.core.ParameterizedTypeReference;import org.springframework.http.HttpMethod;import org.springframework.http.HttpStatus;import org.springframework.http.ResponseEntity;import org.springframework.test.context.junit4.SpringRunner;import java.util.*;import java.util.stream.Collectors;import static org.junit.Assert.*;@RunWith(SpringRunner.class)@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)public class CompanyControllerTest {    @Autowired    private TestRestTemplate testRestTemplate;    @Autowired    private CompanyRepository companyRepository;    @Autowired    private BranchRepository branchRepository;    @Autowired    private ReservationRepository reservationRepository;    @Autowired    private EmployeeRepository employeeRepository;    private CompanyEntity company;    private UUID companyUUID;    private BranchEntity branch;    @Before    public void setUp() {        CompanyEntity companyEntity = CompanyEntity.builder().name("WiesiekCar").build();        company = companyRepository.saveAndFlush(companyEntity);        companyUUID = company.getId();        branch = BranchEntity.builder().address("ul. Cebulowa 34, 04-123 Warszawa").build();    }    @After    public void tearDown(){        reservationRepository.deleteAll();        branchRepository.deleteAll();        companyRepository.deleteAll();    }    @Test    public void addCompany() {        Company company = Company.builder().name("WiesiekCar").build();        ResponseEntity<String> re = testRestTemplate.postForEntity("/company/add",                                                                   company,                                                                   String.class);        assertEquals(HttpStatus.OK, re.getStatusCode());        String uuid = re.getBody().replace("\"", "");        assertTrue(companyRepository.findById(UUID.fromString(uuid)).isPresent());    }    @Test    public void updateCompany() {        CompanyEntity company = CompanyEntity.builder().name("WiesiekCar").owner("Janusz Wiesław").build();        company = companyRepository.saveAndFlush(company);        String companyUUID = company.getId().toString();        Company updatedCompany = Company.builder().owner("Wiesław Janusz").build();        ResponseEntity<String> re = testRestTemplate                .postForEntity(String.format("/company/%s/update", companyUUID),                               updatedCompany, String.class);        assertEquals(HttpStatus.OK, re.getStatusCode());        company = companyRepository.findById(UUID.fromString(companyUUID)).orElseGet(CompanyEntity::new);        assertEquals(updatedCompany.getOwner(),company.getOwner());    }    @Test    public void listCompanies() {        CompanyEntity company = CompanyEntity.builder().name("JanuszWóz").build();        companyRepository.saveAndFlush(company);        ResponseEntity<Map<UUID,Company>> re = testRestTemplate                .exchange("/company/list",                          HttpMethod.GET,                          null,                          new ParameterizedTypeReference<Map<UUID, Company>>(){});        Map<UUID,Company> map = re.getBody();        assertEquals(HttpStatus.OK, re.getStatusCode());        assertEquals(2, map.size());    }    @Test    public void deleteBranch() {        company.getBranches().add(branch);        company = companyRepository.saveAndFlush(company);        String companyUUID = company.getId().toString();        String branchUUID = company.getBranches().iterator().next().getId().toString();        testRestTemplate.delete(String.format("/company/%s/deletebranch/%s",                                              companyUUID, branchUUID));        assertTrue(!branchRepository.findById(UUID.fromString(branchUUID)).isPresent());    }    @Test    public void addBranch() {        Branch branch = Branch.builder().address("ul. Cebulowa 34, 04-123 Warszawa").build();        ResponseEntity<String> re = testRestTemplate                .postForEntity(String.format("/company/%s/addbranch/",companyUUID.toString()),                               branch, String.class);        assertEquals(HttpStatus.OK, re.getStatusCode());        String branchUUID = re.getBody().replace("\"", "");        assertTrue(branchRepository.findById(UUID.fromString(branchUUID)).isPresent());    }    @Test    public void deleteCompany() {        testRestTemplate.delete(String.format("/company/%s/delete",companyUUID.toString()));        assertTrue(!companyRepository.findById(companyUUID).isPresent());    }    @Test    public void getCustomers() {        company.getBranches().add(branch);        company = companyRepository.saveAndFlush(company);        branch = company.getBranches().iterator().next();        CustomerEntity customer1 = CustomerEntity.builder().forename("Adam").build();        CustomerEntity customer2 = CustomerEntity.builder().forename("Kazimierz").build();        ReservationEntity reservation1 = ReservationEntity.builder().customer(customer1).rentalBranch(branch).build();        ReservationEntity reservation2 = ReservationEntity.builder().customer(customer2).returnBranch(branch).build();        Arrays.asList(reservation1, reservation2).forEach(reservationRepository::saveAndFlush);        ResponseEntity<Map<UUID,Customer>> re = testRestTemplate.exchange(                String.format("/company/%s/customerlist",company.getId()),                HttpMethod.GET,                null,                new ParameterizedTypeReference<Map<UUID, Customer>>(){});        Map<UUID,Customer> customers = re.getBody();        assertEquals(HttpStatus.OK, re.getStatusCode());        assertEquals(2,customers.size());    }    @Test    public void getEmployees() {        List<BranchEntity> branches = new ArrayList<>(                Arrays.asList(branch, BranchEntity.builder().address("ul. Ziemniaczana 54, 41-200 Sosnowiec").build()));        company.getBranches().addAll(branches);        branches = branches.stream().map(branchRepository::saveAndFlush).collect(Collectors.toList());        company = companyRepository.saveAndFlush(company);        EmployeeEntity employee1 = EmployeeEntity.builder().surname("Kwiatkowski").branch(branches.get(0)).build();        EmployeeEntity employee2 = EmployeeEntity.builder().surname("Zielinski").branch(branches.get(1)).build();        Arrays.asList(employee1, employee2).forEach(employeeRepository::saveAndFlush);        ResponseEntity<Map<UUID, Employee>> re = testRestTemplate.exchange(                String.format("/company/%s/employeelist",company.getId()),                HttpMethod.GET,                null,                new ParameterizedTypeReference<Map<UUID, Employee>>(){});        Map<UUID, Employee> employees = re.getBody();        assertEquals(HttpStatus.OK, re.getStatusCode());        assertEquals(2,employees.size());    }}