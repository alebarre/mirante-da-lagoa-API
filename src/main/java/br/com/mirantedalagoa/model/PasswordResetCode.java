package br.com.mirantedalagoa.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "password_reset_codes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PasswordResetCode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 5)
    private String code;

    @Column(nullable = false)
    private Instant expiresAt;

    @Builder.Default
    private boolean used = false;

    @Builder.Default
    private Instant createdAt = Instant.now();
}
