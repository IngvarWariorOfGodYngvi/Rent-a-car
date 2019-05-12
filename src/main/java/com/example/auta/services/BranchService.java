package com.example.auta.services;import com.example.auta.domain.entities.BranchEntity;import com.example.auta.domain.repositories.BranchRepository;import com.example.auta.models.classes.Branch;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;import java.util.UUID;@Servicepublic class BranchService {    public final BranchRepository branchRepository;    @Autowired    public BranchService(BranchRepository branchRepo){        this.branchRepository = branchRepo;    }    public BranchEntity saveBranch (Branch branch){        BranchEntity branchEntity = branchRepository.saveAndFlush(map(branch));        return branchEntity;    }    public Branch readBranch (BranchEntity branch){        return map(branch);    }    private BranchEntity map (Branch branch){        return BranchEntity.builder()                .address(branch.getAddress())                .cars(branch.getCars())                .employees(branch.getEmployees())                .build();    }    private Branch map (BranchEntity branch){        return Branch.builder()                .address(branch.getAddress())                .cars(branch.getCars())                .employees(branch.getEmployees())                .build();    }}