package com.example.auta.domain.entities;import com.example.auta.models.enums.CarStatus;import com.example.auta.models.enums.Suspension;import lombok.AllArgsConstructor;import lombok.Builder;import lombok.Data;import lombok.NoArgsConstructor;import org.hibernate.annotations.GenericGenerator;import javax.persistence.*;import java.math.BigDecimal;import java.util.UUID;@Entity@Data@NoArgsConstructor@Builder@AllArgsConstructorpublic class CarEntity {    @Id    @GeneratedValue(generator = "uuid")    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")    private UUID id;    private String make;    private String model;    private Suspension suspension;    private Integer yearOfProduction;    private String color;    private Integer mileage;    private CarStatus carStatus;    private BigDecimal dailyPrice;    @ManyToOne    private BranchEntity branch;}