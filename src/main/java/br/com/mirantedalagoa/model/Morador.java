package br.com.mirantedalagoa.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "moradores")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Morador {
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

    @Column(nullable = false)
    private String block;

    @Column(nullable = false)
    private String apartment;

    private String parkingSpot;
    private String pets;
    private boolean owner = true;
    private LocalDate moveInDate;
    private LocalDate moveOutDate;
    private String emergencyContact;
    private String notes;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    private Instant updatedAt;
}
