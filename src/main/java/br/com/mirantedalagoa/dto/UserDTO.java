package br.com.mirantedalagoa.dto;

import br.com.mirantedalagoa.model.Role;
import java.util.UUID;

public record UserDTO(UUID id, String email, String fullName, Role role, boolean active) {}
