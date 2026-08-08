package br.com.mirantedalagoa.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "parametros_condominio")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ParametroCondominio {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false, unique = true)
    private String chave;

    private BigDecimal valorNumerico;

    private String valorTexto;

    private String descricao;

    @Builder.Default
    @Column(nullable = false)
    private Instant atualizadoEm = Instant.now();
}
