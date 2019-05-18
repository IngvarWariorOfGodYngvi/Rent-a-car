package com.example.auta.services;

import com.example.auta.domain.entities.BranchEntity;
import com.example.auta.domain.entities.CompanyEntity;
import com.example.auta.domain.entities.CustomerEntity;
import com.example.auta.domain.repositories.BranchRepository;
import com.example.auta.domain.repositories.CompanyRepository;
import com.example.auta.domain.repositories.CustomerRepository;
import com.example.auta.models.classes.Branch;
import com.example.auta.models.classes.Company;
import com.example.auta.models.classes.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final BranchRepository branchRepository;
    private final BranchService branchService;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    @Autowired
    public CompanyService(CompanyRepository companyRepo,
                          BranchRepository branchRepo,
                          BranchService branchServ,
                          CustomerRepository customerRepo,
                          CustomerService customerServ){
        this.companyRepository = companyRepo;
        this.branchRepository = branchRepo;
        this.branchService = branchServ;
        this.customerRepository = customerRepo;
        this.customerService = customerServ;
    }

    public Map<UUID, Company> getCompanies() {

        Map<UUID,Company> map = new HashMap<>();
        companyRepository.findAll().forEach(
                element -> map.put(element.getId(),map(element)));
        return map;
    }

    public UUID addCompany(Company company) {
        return companyRepository.save(map(company)).getId();
    }

    public boolean updateCompany(UUID uuid, Company company) {
        try{
            CompanyEntity updateCompanyEntity = companyRepository.findById(uuid).orElseThrow(EntityNotFoundException::new);
            if (company.getName() != null){
                updateCompanyEntity.setName(company.getName());
            }
            if (company.getOwner() != null){
                updateCompanyEntity.setOwner(company.getOwner());
            }
            if (company.getAddress() != null){
                updateCompanyEntity.setAddress(company.getAddress());
            }
            if (company.getBranches() != null){
                updateCompanyEntity.getBranches().clear();
                updateCompanyEntity.getBranches().addAll(company.getBranches()
                                                                 .stream()
                                                                 .map(branchService::getOrCreateBranchEntity)
                                                                 .collect(Collectors.toList()));
            }
            if (company.getDomain() != null){
                updateCompanyEntity.setDomain(company.getDomain());
            }
            if (company.getLogotype() != null){
                updateCompanyEntity.setLogotype(company.getLogotype());
            }
            companyRepository.save(updateCompanyEntity);
            return true;
        } catch (EntityNotFoundException ex){
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
        company.getBranches().size();
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
        return CompanyEntity.builder()
                .name(c.getName())
                .domain(c.getDomain())
                .address(c.getAddress())
                .owner(c.getOwner())
                .logotype(c.getLogotype())
                .branches(c.getBranches()
                                  .stream()
                                  .map(branchService::getOrCreateBranchEntity)
                                  .collect(Collectors.toList()))
                .build();
    }

    private Company map(CompanyEntity c) {
        return Company.builder()
                .name(c.getName())
                .domain(c.getDomain())
                .address(c.getAddress())
                .owner(c.getOwner())
                .logotype(c.getLogotype())
                .branches(c.getBranches()
                                  .stream()
                                  .map(branchService::read)
                                  .collect(Collectors.toList()))
                .build();
    }

    public UUID addBranch(UUID companyUUID, Branch branch) throws EntityNotFoundException {
        CompanyEntity company = companyRepository
                .findById(companyUUID)
                .orElseThrow(EntityNotFoundException::new);
        BranchEntity branchEntity = branchService.getOrCreateBranchEntity(branch);
        company.getBranches().add(branchEntity);
        companyRepository.saveAndFlush(company);
        return branchEntity.getId();
    }

    public Map<UUID, Customer> getCustomers(UUID branchUUID){
        BranchEntity branchEntity = branchRepository
                .findById(branchUUID)
                .orElseThrow(EntityNotFoundException::new);
        Set<CustomerEntity> customers = customerRepository.findCustomerEntitiesByBranch(branchEntity);
        Map<UUID, Customer> customerMap = new HashMap<>();
        customers.forEach(e->customerMap.put(e.getId(), customerService.readCustomer(e)));
        return customerMap;
    }
}
