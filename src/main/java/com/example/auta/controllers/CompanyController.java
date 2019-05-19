package com.example.auta.controllers;


import com.example.auta.models.classes.Branch;
import com.example.auta.models.classes.Company;
import com.example.auta.models.classes.Customer;
import com.example.auta.models.classes.Employee;
import com.example.auta.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/company")
public class CompanyController {


    @Autowired
    private CompanyService companyService;

    //          C   O   M   P   A   N   Y
    @GetMapping("/list")
    public Map<UUID, Company> listCompanies() {
        return companyService.getCompanies();
    }

    @PostMapping("/add")
    public UUID addCompany(@RequestBody Company company) {
        return companyService.addCompany(company);
    }

    @PostMapping("/{companyUUID}/update")
    public boolean updateCompany(@PathVariable UUID companyUUID,
                                 @RequestBody Company company) {
        return companyService.updateCompany(companyUUID, company);
    }

    @DeleteMapping("/{uuid}/delete")
    public boolean deleteCompany(@PathVariable UUID uuid) {
        return companyService.deleteCompany(uuid);
    }

    //          B   R   A   N   C   H
    @PostMapping("{companyUUID}/addbranch")
    public UUID addBranch(@PathVariable UUID companyUUID,
                          @RequestBody Branch branch) throws EntityNotFoundException {
        return companyService.addBranch(companyUUID, branch);
    }

    @PostMapping("/{branchUUID}/updatebranch}")
    public boolean updateBranch(@PathVariable UUID branchUUID,
                                @RequestBody Branch branch) {
        return companyService.updateBranch(branchUUID, branch);
    }

    @DeleteMapping("{companyUUID}/deletebranch/{branchUUID}")
    public boolean deleteBranch(@PathVariable UUID companyUUID,
                                @PathVariable UUID branchUUID) throws Exception {
        return companyService.deleteBranch(companyUUID, branchUUID);
    }

    @GetMapping("/{companyUUID}/customers")
    public Map<UUID, Customer> getCustomers(@PathVariable UUID companyUUID){return companyService.getCustomers(companyUUID);}

    @GetMapping("/{companyUUID}/employees")
    public Map<UUID, Employee> getEmployees(@PathVariable UUID companyUUID){
        return companyService.getEmployees(companyUUID);
    }

}