package br.com.mirantedalagoa.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventoDTO(
    UUID id,
    String title,
    String description,
    LocalDateTime startAt,
    LocalDateTime endAt,
    String location,
    String organizer,
    String status,
    boolean restrictedToResidents,
    Integer maxParticipants,
    String notes
) {}
