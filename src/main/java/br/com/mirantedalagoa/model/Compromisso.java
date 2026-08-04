package br.com.mirantedalagoa.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "compromissos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Compromisso {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private LocalDateTime scheduledAt;

    private String location;
    private String responsible;
    private String status; // AGENDADO, REALIZADO, CANCELADO

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    private Instant updatedAt;
}
