package com.example.auta.controllers;

import com.example.auta.services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/income")
public class TotalIncomeController {

    @Autowired
    private IncomeService incomeService;

    @GetMapping("/company/{companyUUID}")
    public BigDecimal companyIncome(@PathVariable("companyUUID") UUID companyUUID) throws Exception {
        return incomeService.getCompanyIncome(companyUUID);
    }
    @GetMapping("/branch/{branchUUID}")
    public BigDecimal branchIncome(@PathVariable("branchUUID")UUID branchUUID) throws Exception {
        return incomeService.getBranchIncome(branchUUID);
    }
}
