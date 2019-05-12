package com.example.auta.services;

import com.example.auta.domain.entities.BranchEntity;
import com.example.auta.domain.entities.CompanyEntity;
import com.example.auta.domain.repositories.BranchRepository;
import com.example.auta.domain.repositories.CompanyRepository;
import com.example.auta.models.classes.Branch;
import com.example.auta.models.classes.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final BranchRepository branchRepository;
    private final BranchService branchService;

    @Autowired
    public CompanyService(CompanyRepository companyRepo,
                          BranchRepository branchRepo,
                          BranchService branchServ){
        this.companyRepository = companyRepo;
        this.branchRepository = branchRepo;
        this.branchService = branchServ;
    }

    public Map<UUID, Company> getCompany() {

        Map<UUID,Company> map = new HashMap<>();
        companyRepository.findAll().forEach(
                element -> map.put(element.getId(),map(element)));
        return map;
    }

    public UUID addCompany(Company company) {
        return companyRepository.save(map(company)).getId();
    }

    public boolean updateCompany(UUID uuid, Company company) {
        Optional<CompanyEntity> updateCompany = companyRepository.findById(uuid);
        if (updateCompany.isPresent()) {
            updateCompany.get().setName(company.getName());
            updateCompany.get().setOwner(company.getOwner());
            updateCompany.get().setAddress(company.getAddress());
            updateCompany.get().setBranches(company.getBranches());
            updateCompany.get().setDomain(company.getDomain());
            updateCompany.get().setLogotype(company.getLogotype());
            companyRepository.save(updateCompany.get());
            return true;
        } else {
            return false;
        }

    }

    public boolean deleteBranch(UUID companyUUID, UUID branchUUID) throws Exception {
        CompanyEntity company = companyRepository
                .findById(companyUUID)
                .orElseThrow(Exception::new);
        BranchEntity branch = branchRepository
                .findById(branchUUID)
                .orElseThrow(Exception::new);
        if (company.getBranches().remove(branch)){
            companyRepository.save(company);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteCompany(UUID uuid) {

        if (companyRepository.existsById(uuid)) {
            companyRepository.deleteById(uuid);
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

    public UUID addBranch(UUID companyUUID, Branch branch) throws Exception {
        CompanyEntity company = companyRepository
                .findById(companyUUID)
                .orElseThrow(Exception::new);
        BranchEntity branchEntity = branchService.saveBranch(branch);


        return null;
    }
}
