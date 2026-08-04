package br.com.mirantedalagoa.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CompromissoDTO(
    UUID id,
    String title,
    String description,
    LocalDateTime scheduledAt,
    String location,
    String responsible,
    String status
) {}
