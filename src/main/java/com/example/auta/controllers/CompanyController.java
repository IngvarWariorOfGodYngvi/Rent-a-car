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

    //edit Company
    @PostMapping("/update/{uuid}")
    public String updateCompany(@PathVariable UUID uuid, @RequestBody Company company) {
        return null;
    }

    //delete Branch
    @PostMapping("/delete/{uuid}")
    public String deletebranch(@PathVariable UUID uuid, @RequestBody Branch branch) {
        return null;
    }

    //add Branch
    @PostMapping("/addbranch/{uuid}")
    public String addBranch(@PathVariable UUID uuid, @RequestBody Company company) {
        return null;
    }

    //delete Company
    @PostMapping("/deletecompany/{uuid}")
    public String deleteCompany(@PathVariable UUID uuid, @RequestBody Company company) {
        return null;
    }

}