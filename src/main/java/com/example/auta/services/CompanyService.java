package com.example.auta.services;

import com.example.auta.domain.entities.BranchEntity;
import com.example.auta.domain.entities.CompanyEntity;
import com.example.auta.domain.repositories.BranchRepository;
import com.example.auta.domain.repositories.CompanyRepositories;
import com.example.auta.models.classes.Branch;
import com.example.auta.models.classes.Company;
import com.example.auta.models.classes.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class CompanyService {

    private final CompanyRepositories companyRepositories;
    private final BranchRepository branchRepository;

    @Autowired
    public CompanyService(CompanyRepositories companyRepo, BranchRepository branchRepo){
        this.companyRepositories = companyRepo;
        this.branchRepository = branchRepo;
    }
    public Map<UUID, Company> getCompany() {

        Map<UUID,Company> map = new HashMap<>();
        companyRepositories.findAll().forEach(
                element -> map.put(element.getId(),map(element)));
        return map;
    }

    public UUID addCompany(Company company) {
        return companyRepositories.save(map(company)).getId();
    }

    public boolean updateCompany(UUID uuid, Company company) {
        Optional<CompanyEntity> updateCompany = companyRepositories.findById(uuid);
        if (updateCompany.isPresent()) {
            updateCompany.get().setName(company.getName());
            updateCompany.get().setOwner(company.getOwner());
            updateCompany.get().setAddress(company.getAddress());
            updateCompany.get().setBranches(company.getBranches());
            updateCompany.get().setDomain(company.getDomain());
            updateCompany.get().setLogotype(company.getLogotype());
            companyRepositories.save(updateCompany.get());
            return true;
        } else {
            return false;
        }

    }

    public boolean deleteBranch(UUID companyUUID, UUID branchUUID) throws Exception {
        CompanyEntity company = companyRepositories
                .findById(companyUUID)
                .orElseThrow(Exception::new);
        BranchEntity branch = branchRepository
                .findById(branchUUID)
                .orElseThrow(Exception::new);
        if (company.getBranches().remove(branch)){
            companyRepositories.save(company);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteCompany(UUID uuid) {

        if (companyRepositories.existsById(uuid)) {
            companyRepositories.deleteById(uuid);
            return true;
        } else {
            return false;
        }

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

    public UUID addBranch(Branch branch) {
        return null;
    }
}
