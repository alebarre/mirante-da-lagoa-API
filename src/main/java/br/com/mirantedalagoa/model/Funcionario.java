package br.com.mirantedalagoa.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "funcionarios")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String fullName;

    private String cpf;
    private String rg;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private String address;
    private String position;
    private String department;
    private LocalDate hireDate;
    private LocalDate terminationDate;
    private BigDecimal salary;
    private String workRegime; // CLT, PJ, estagiário
    private String bankAccount;
    private String notes;

    @Builder.Default
    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    private Instant updatedAt;
}
