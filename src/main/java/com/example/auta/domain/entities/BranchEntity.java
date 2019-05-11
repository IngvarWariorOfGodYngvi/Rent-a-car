package com.example.auta.domain.entities;import lombok.AllArgsConstructor;import lombok.Builder;import lombok.Data;import lombok.NoArgsConstructor;import org.hibernate.annotations.GenericGenerator;import javax.persistence.Entity;import javax.persistence.GeneratedValue;import javax.persistence.Id;import javax.persistence.OneToMany;import java.util.List;import java.util.UUID;@Entity@Data@NoArgsConstructor@Builder@AllArgsConstructorpublic class BranchEntity {    @Id    @GeneratedValue(generator = "uuid")    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUID")    private UUID id;    private String address;    @OneToMany(mappedBy="branch")    private List<EmployeeEntity> employees;    @OneToMany(mappedBy="branch")    private List<CarEntity> cars;}