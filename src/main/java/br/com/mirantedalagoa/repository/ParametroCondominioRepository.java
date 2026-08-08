package br.com.mirantedalagoa.repository;

import br.com.mirantedalagoa.model.ParametroCondominio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParametroCondominioRepository extends JpaRepository<ParametroCondominio, UUID> {
    Optional<ParametroCondominio> findByChave(String chave);
}
