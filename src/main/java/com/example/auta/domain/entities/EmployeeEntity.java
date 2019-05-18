package com.example.auta.domain.entities;import com.example.auta.models.enums.Position;import com.fasterxml.jackson.annotation.JsonIgnoreProperties;import lombok.*;import org.hibernate.annotations.GenericGenerator;import javax.persistence.*;import java.util.List;import java.util.UUID;@Entity@Data@NoArgsConstructor@Builder@AllArgsConstructorpublic class EmployeeEntity {    @Id @GeneratedValue(generator = "uuid")    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")    private UUID id;    private String forename;    private String surname;    private Position position;    @ManyToOne @EqualsAndHashCode.Exclude    @ToString.Exclude @JsonIgnoreProperties("employees")    private BranchEntity branch;    @OneToMany(mappedBy = "employee")    private List<RentEntity> rents;    @OneToMany(mappedBy = "employee")    private List<ReturnEntity> returns;}