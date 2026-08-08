package br.com.mirantedalagoa.dto;

import java.time.LocalDate;
import java.util.UUID;

public record FuncionarioOcorrenciaDTO(
    UUID id,
    UUID funcionarioId,
    String tipo,
    LocalDate data,
    String descricao,
    String anexo
) {}
