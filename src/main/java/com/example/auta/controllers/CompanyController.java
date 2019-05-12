package com.example.auta.controllers;


import com.example.auta.models.classes.Branch;
import com.example.auta.models.classes.Company;
import com.example.auta.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/Company")
public class CompanyController {


    @Autowired
    private CompanyService companyService;

    //add Company
    @PostMapping("/addcompany")
    public UUID addCompany(@RequestBody Company company) {
        return companyService.addCompany(company);
    }

    //update Company
    @PostMapping("/updatecompany/{uuid}")
    public boolean updateCompany(@PathVariable UUID uuid, @RequestBody Company company) {
        return companyService.updateCompany(uuid, company);
    }

    //delete Branch
    @DeleteMapping("/deletebranch/{uuid}")
    public boolean deleteBranch(@PathVariable UUID companyUuid, UUID branchUuid) throws Exception {
        return companyService.deleteBranch(companyUuid,branchUuid);
    }

    //add Branch
    @PostMapping("/addbranch")
    public UUID addBranch(@RequestBody Branch branch) {
        return companyService.addBranch(branch);
    }

    //delete Company
    @DeleteMapping("/deletecompany/{uuid}")
    public boolean deleteCompany(@PathVariable UUID uuid) {
        return companyService.deleteCompany(uuid);
    }

}