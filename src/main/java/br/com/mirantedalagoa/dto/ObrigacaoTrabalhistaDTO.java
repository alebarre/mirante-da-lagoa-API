package br.com.mirantedalagoa.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ObrigacaoTrabalhistaDTO(
    UUID id,
    String name,
    String description,
    String periodicity,
    LocalDate dueDate,
    LocalDate completedAt,
    String responsible,
    String status,
    String notes
) {}
