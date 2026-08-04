package br.com.mirantedalagoa.dto;

import br.com.mirantedalagoa.model.Role;

public record AuthResponse(
    String accessToken,
    String refreshToken,
    String tokenType,
    Long expiresIn,
    String email,
    String fullName,
    Role role
) {}
