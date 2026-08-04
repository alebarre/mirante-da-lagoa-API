package br.com.mirantedalagoa.repository;

import br.com.mirantedalagoa.model.Compromisso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompromissoRepository extends JpaRepository<Compromisso, UUID> {
}
