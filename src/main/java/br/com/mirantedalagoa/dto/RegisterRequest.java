package br.com.mirantedalagoa.dto;

import br.com.mirantedalagoa.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import javax.xml.crypto.Data;

public record RegisterRequest(
    @NotBlank String fullName,
    @NotBlank @Email String email,
    @NotBlank String password,
    @NotNull Role role
) {}
