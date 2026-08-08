package br.com.mirantedalagoa.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "funcionario_ocorrencias")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FuncionarioOcorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    @Column(nullable = false)
    private String tipo;

    private LocalDate data;

    @Column(length = 2000)
    private String descricao;

    private String anexo;

    @Builder.Default
    @Column(nullable = false)
    private Instant createdAt = Instant.now();
}
