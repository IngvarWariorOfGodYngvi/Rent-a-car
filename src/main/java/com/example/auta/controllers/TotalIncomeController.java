package com.example.auta.controllers;

import com.example.auta.services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/income")
public class TotalIncomeController {

    @Autowired
    private IncomeService incomeService;

    @GetMapping("/gettotalincome")
    public BigDecimal totalIncome(@RequestParam("companyUUID") UUID companyUUID) throws Exception {
        return incomeService.getTotalIncome(companyUUID);
    }
}
