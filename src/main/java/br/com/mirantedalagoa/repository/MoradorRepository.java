package br.com.mirantedalagoa.repository;

import br.com.mirantedalagoa.model.Morador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MoradorRepository extends JpaRepository<Morador, UUID> {
}
