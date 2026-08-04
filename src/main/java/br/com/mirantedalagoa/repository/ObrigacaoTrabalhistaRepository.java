package br.com.mirantedalagoa.repository;

import br.com.mirantedalagoa.model.ObrigacaoTrabalhista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ObrigacaoTrabalhistaRepository extends JpaRepository<ObrigacaoTrabalhista, UUID> {
}
