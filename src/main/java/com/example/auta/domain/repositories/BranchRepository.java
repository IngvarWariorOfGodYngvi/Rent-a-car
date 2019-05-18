package com.example.auta.domain.repositories;import com.example.auta.domain.entities.BranchEntity;import com.example.auta.domain.entities.EmployeeEntity;import org.springframework.data.jpa.repository.EntityGraph;import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.stereotype.Repository;import java.util.Optional;import java.util.UUID;@Repositorypublic interface BranchRepository extends JpaRepository<BranchEntity, UUID> {    @EntityGraph(attributePaths = {"cars", "employees"})    Optional<BranchEntity> findBranchEntityByAddressEquals(String address);}