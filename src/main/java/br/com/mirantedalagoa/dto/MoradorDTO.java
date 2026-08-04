package br.com.mirantedalagoa.dto;

import java.time.LocalDate;
import java.util.UUID;

public record MoradorDTO(
    UUID id,
    String fullName,
    String cpf,
    String rg,
    LocalDate birthDate,
    String phone,
    String email,
    String block,
    String apartment,
    String parkingSpot,
    String pets,
    boolean owner,
    LocalDate moveInDate,
    LocalDate moveOutDate,
    String emergencyContact,
    String notes
) {}
