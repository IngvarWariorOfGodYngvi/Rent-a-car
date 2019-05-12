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
    public String addBranch(@RequestBody Company company) {
        return null;
    }

    //edit Company
    @PostMapping("/updatecompany/{uuid}")
    public String updateCompany(@PathVariable UUID uuid, @RequestBody Company company) {
        return null;
    }

    //delete Branch
    @PostMapping("/deletebranch")
    public String deletebranch(@PathVariable UUID uuid, @RequestBody Branch branch) {
        return null;
    }

    //add Branch
    @PostMapping("/addbranch")
    public String addBranch(@PathVariable UUID uuid, @RequestBody Company company) {
        return null;
    }

    //delete Company
    @PostMapping("/deletecompany")
    public String deleteCompany(@PathVariable UUID uuid, @RequestBody Company company) {
        return null;
    }

}