package br.com.mirantedalagoa.repository;

import br.com.mirantedalagoa.model.FuncionarioOcorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FuncionarioOcorrenciaRepository extends JpaRepository<FuncionarioOcorrencia, UUID> {
    List<FuncionarioOcorrencia> findByFuncionarioIdOrderByDataDesc(UUID funcionarioId);
}
