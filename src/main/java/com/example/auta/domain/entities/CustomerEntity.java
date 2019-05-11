package com.example.auta.domain.entities;import lombok.AllArgsConstructor;import lombok.Builder;import lombok.Data;import lombok.NoArgsConstructor;import org.hibernate.annotations.GenericGenerator;import javax.persistence.Entity;import javax.persistence.GeneratedValue;import javax.persistence.Id;import java.util.UUID;@Entity@Data@NoArgsConstructor@Builder@AllArgsConstructorpublic class CustomerEntity {    @Id    @GeneratedValue(generator = "uuid")    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUID")    private UUID id;    private String forname;    private String lastname;    private String email;    private String address;}