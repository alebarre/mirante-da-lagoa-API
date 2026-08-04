package br.com.mirantedalagoa.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "obrigacoes_trabalhistas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ObrigacaoTrabalhista {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private String periodicity; // MENSAL, TRIMESTRAL, SEMESTRAL, ANUAL

    private LocalDate dueDate;
    private LocalDate completedAt;
    private String responsible;
    private String status; // PENDENTE, EM_DIA, ATRASADO, CANCELADO
    private String notes;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    private Instant updatedAt;
}
