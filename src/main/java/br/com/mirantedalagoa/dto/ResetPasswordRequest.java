package br.com.mirantedalagoa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(
    @NotBlank @Email String email,
    @NotBlank @Size(min = 5, max = 5) String code,
    @NotBlank @Size(min = 6) String newPassword
) {}
