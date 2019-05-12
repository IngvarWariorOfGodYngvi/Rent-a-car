package com.example.auta.domain.entities;import lombok.AllArgsConstructor;import lombok.Builder;import lombok.Data;import lombok.NoArgsConstructor;import org.hibernate.annotations.GenericGenerator;import javax.persistence.Entity;import javax.persistence.GeneratedValue;import javax.persistence.Id;import javax.persistence.OneToMany;import java.util.List;import java.util.UUID;@Entity@Data@NoArgsConstructor@Builder@AllArgsConstructorpublic class CompanyEntity {    @Id    @GeneratedValue(generator = "uuid")    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")    private UUID id;    private String name;    private String domain;    private String address;    private String owner;    private byte[] logotype;    @OneToMany(mappedBy = "company")    private List<BranchEntity> branches;}