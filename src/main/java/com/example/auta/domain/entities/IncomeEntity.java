package com.example.auta.domain.entities;import lombok.AllArgsConstructor;import lombok.Builder;import lombok.Data;import lombok.NoArgsConstructor;import org.hibernate.annotations.GenericGenerator;import javax.persistence.Entity;import javax.persistence.GeneratedValue;import javax.persistence.Id;import java.math.BigDecimal;import java.util.UUID;@Entity@Data@NoArgsConstructor@Builder@AllArgsConstructorpublic class IncomeEntity {    @Id    @GeneratedValue(generator = "uuid")    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")    private UUID id;    private BigDecimal totalIncome;}