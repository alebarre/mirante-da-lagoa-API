package br.com.mirantedalagoa.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ParametroCondominioDTO(
    UUID id,
    String categoria,
    String chave,
    String descricao,
    BigDecimal valorNumerico,
    String valorTexto
) {}
