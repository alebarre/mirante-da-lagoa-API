package br.com.mirantedalagoa.repository;

import br.com.mirantedalagoa.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventoRepository extends JpaRepository<Evento, UUID> {
}
