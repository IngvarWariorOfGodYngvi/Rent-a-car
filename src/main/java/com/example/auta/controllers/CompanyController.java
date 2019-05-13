package com.example.auta.controllers;


import com.example.auta.models.classes.Branch;
import com.example.auta.models.classes.Company;
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

    @PostMapping("/addcompany")
    public UUID addCompany(@RequestBody Company company) {
        return companyService.addCompany(company);
    }

    @PostMapping("/{uuid}/updatecompany")
    public boolean updateCompany(@PathVariable UUID uuid,
                                 @RequestBody Company company) {
        return companyService.updateCompany(uuid, company);
    }

    @GetMapping("/listcompanies")
    public Map<UUID, Company> listCompanies(){
        return companyService.getCompanies();
    }

    @DeleteMapping("{companyUUID}/deletebranch/{branchUUID}")
    public boolean deleteBranch(@PathVariable UUID companyUUID,
                                @PathVariable UUID branchUUID) throws Exception {
        return companyService.deleteBranch(companyUUID,branchUUID);
    }

    @PostMapping("{companyUUID}/addbranch")
    public UUID addBranch(@PathVariable UUID companyUUID,
                          @RequestBody Branch branch) throws EntityNotFoundException {
        return companyService.addBranch(companyUUID, branch);
    }

    @DeleteMapping("/deletecompany/{uuid}")
    public boolean deleteCompany(@PathVariable UUID uuid) {
        return companyService.deleteCompany(uuid);
    }

}