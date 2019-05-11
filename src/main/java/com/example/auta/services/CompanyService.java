package com.example.auta.services;

import com.example.auta.domain.entities.CompanyEntity;
import com.example.auta.domain.repositories.CompanyRepositories;
import com.example.auta.models.classes.Company;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CompanyService {

    private final CompanyRepositories companyRepositories;

    @Autowired
    public CompanyService(CompanyRepositories company) {
        this.companyRepositories = company;
    }

    public UUID addCompany(Company company) {
        return companyRepositories.save(map(company)).getId();
    }

    private Company map(CompanyEntity c) {
        return new Company().builder()
                .name(c.getName())
                .domain(c.getDomain())
                .address(c.getAddress())
                .owner(c.getOwner())
                .logotype(c.getLogotype())
                .branches(c.getBranches())
                .build();
    }

    private CompanyEntity map(Company c) {
        CompanyEntity cc = new CompanyEntity();

        return cc.builder()
                .name(c.getName())
                .domain(c.getDomain())
                .address(c.getAddress())
                .owner(c.getOwner())
                .logotype(c.getLogotype())
                .branches(c.getBranches())
                .build();
    }
}
