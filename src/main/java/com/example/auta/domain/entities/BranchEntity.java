package com.example.auta.domain.entities;import lombok.AllArgsConstructor;import lombok.Builder;import lombok.Data;import lombok.NoArgsConstructor;import org.hibernate.annotations.GenericGenerator;import javax.persistence.*;import java.util.ArrayList;import java.util.List;import java.util.UUID;@Entity@Data@NoArgsConstructor@Builder@AllArgsConstructorpublic class BranchEntity {    @Id    @GeneratedValue(generator = "uuid")    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")    private UUID id;    private String address;    @OneToMany(mappedBy="branch")    private List<EmployeeEntity> employees;    @Builder.Default    @OneToMany    @JoinColumn(name="branch_id")    private List<CarEntity> cars = new ArrayList<>();    @OneToMany(mappedBy = "rentalBranch")    private List<ReservationEntity> rentals;    @OneToMany(mappedBy = "returnBranch")    private List<ReservationEntity> returns;    @ManyToOne    private CompanyEntity company;}