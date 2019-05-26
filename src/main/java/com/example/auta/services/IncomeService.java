package com.example.auta.services;

import com.example.auta.domain.entities.BranchEntity;
import com.example.auta.domain.entities.CompanyEntity;
import com.example.auta.domain.entities.ReservationEntity;
import com.example.auta.domain.entities.ReturnEntity;
import com.example.auta.domain.repositories.BranchRepository;
import com.example.auta.domain.repositories.CompanyRepository;
import com.example.auta.domain.repositories.ReservationRepository;
import com.example.auta.domain.repositories.ReturnRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class IncomeService {


    private final CompanyRepository companyRepository;
    private final ReservationRepository reservationRepository;
    private final ReturnRepository returnRepository;
    private final BranchRepository branchRepository;


    public BigDecimal getCompanyIncome(UUID companyUUID) throws Exception {
        CompanyEntity companyEntity = companyRepository
                .findById(companyUUID)
                .orElseThrow(EntityNotFoundException::new);
        Set<ReservationEntity> reservationEntitySet = reservationRepository
                .findReservationEntityByRentalBranch_Company(companyEntity);
        Set<ReturnEntity> returnEntitySet = returnRepository
                .findReturnEntitiesByReservation_RentalBranch_Company(companyEntity);

        return calculateIncome(reservationEntitySet, returnEntitySet);
    }

    public BigDecimal getBranchIncome(UUID branchUUID) throws Exception {
        BranchEntity branchEntity = branchRepository
                .findById(branchUUID)
                .orElseThrow(Exception::new);
        Set<ReservationEntity> reservationEntitySet = reservationRepository
                .findReservationEntityByRentalBranch(branchEntity);
        Set<ReturnEntity> returnEntitySet = returnRepository
                .findReturnEntitiesByReservation_RentalBranch(branchEntity);

        return calculateIncome(reservationEntitySet, returnEntitySet);
    }

    private BigDecimal calculateIncome(Set<ReservationEntity> reservationEntitySet, Set<ReturnEntity> returnEntitySet) throws Exception {
        BigDecimal incomeFromReservation = reservationEntitySet
                .stream()
                .map(e -> e.getTotalPrice())
                .reduce(BigDecimal::add)
                .orElseThrow(Exception::new);
        BigDecimal incomeFromReturn = returnEntitySet
                .stream()
                .map(e -> e.getExtraPayment())
                .reduce(BigDecimal::add)
                .orElseThrow(Exception::new);
        return incomeFromReservation.add(incomeFromReturn);
    }
}
