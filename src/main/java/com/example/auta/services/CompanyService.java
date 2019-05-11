package com.example.auta.services;

import com.example.auta.domain.entities.CompanyEntity;
import com.example.auta.domain.repositories.CompanyRepositories;
import com.example.auta.models.classes.Branch;
import com.example.auta.models.classes.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public String updateCompany(Company company) {
        return null;
    }

    public String deleteBranch(Branch branch) {
        return null;
    }

    public boolean deleteCompany(UUID uuid) {

            if (companyRepositories.existsById(uuid)){
                companyRepositories.deleteById(uuid);
                return true;
            } else {
                return false;
            }

    }
}
