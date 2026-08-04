package br.com.mirantedalagoa.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "eventos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private LocalDateTime startAt;

    private LocalDateTime endAt;
    private String location; // salão de festas, piscina, área comum
    private String organizer;
    private String status; // AGENDADO, CONFIRMADO, CANCELADO, REALIZADO
    private boolean restrictedToResidents = false;
    private Integer maxParticipants;
    private String notes;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    private Instant updatedAt;
}
