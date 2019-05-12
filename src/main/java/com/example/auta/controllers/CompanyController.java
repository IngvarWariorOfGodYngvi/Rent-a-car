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
    @PostMapping("/add")
    public UUID addCompany(@RequestBody Company company) {
        return companyService.addCompany(company);
    }

    //update Company
    @PostMapping("/update/{uuid}")
    public boolean updateCompany(@PathVariable UUID uuid, @RequestBody Company company) {
        return companyService.updateCompany(uuid, company);
    }

    //delete Branch
    @DeleteMapping("/delete/{uuid}")
    public String deleteBranch(@PathVariable UUID uuid, @RequestBody Branch branch) {
        return companyService.deleteBranch(branch);
    }

    //add Branch
    @PostMapping("/addbranch/{uuid}")
    public String addBranch(@PathVariable UUID uuid, @RequestBody Company company) {
        return null;
    }

    //delete Company
    @DeleteMapping("/deletecompany/{uuid}")
    public boolean deleteCompany(@PathVariable UUID uuid) {
        return companyService.deleteCompany(uuid);
    }

}