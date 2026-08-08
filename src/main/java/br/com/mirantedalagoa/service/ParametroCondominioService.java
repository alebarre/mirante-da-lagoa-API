package br.com.mirantedalagoa.service;

import br.com.mirantedalagoa.dto.ParametroCondominioDTO;
import br.com.mirantedalagoa.model.ParametroCondominio;
import br.com.mirantedalagoa.repository.ParametroCondominioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParametroCondominioService implements CrudService<ParametroCondominioDTO, ParametroCondominioDTO> {

    @Autowired
    private ParametroCondominioRepository repository;

    public static final String CATEGORIA_FOLHA = "FOLHA_PAGAMENTO";

    @Override
    public List<ParametroCondominioDTO> listAll() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public ParametroCondominioDTO findById(UUID id) {
        return toDTO(repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Parametro nao encontrado")));
    }

    public Optional<BigDecimal> findNumericByChave(String chave) {
        return repository.findByChave(chave).map(ParametroCondominio::getValorNumerico);
    }

    public Map<String, BigDecimal> findPercentuaisFolha() {
        return repository.findAll().stream()
            .filter(p -> CATEGORIA_FOLHA.equals(p.getCategoria()))
            .filter(p -> p.getValorNumerico() != null)
            .collect(Collectors.toMap(ParametroCondominio::getChave, ParametroCondominio::getValorNumerico));
    }

    @Override
    @Transactional
    public ParametroCondominioDTO create(ParametroCondominioDTO dto) {
        ParametroCondominio entity = toEntity(dto);
        entity.setAtualizadoEm(Instant.now());
        return toDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public ParametroCondominioDTO update(UUID id, ParametroCondominioDTO dto) {
        ParametroCondominio existing = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Parametro nao encontrado"));
        existing.setCategoria(dto.categoria());
        existing.setChave(dto.chave());
        existing.setDescricao(dto.descricao());
        existing.setValorNumerico(dto.valorNumerico());
        existing.setValorTexto(dto.valorTexto());
        existing.setAtualizadoEm(Instant.now());
        return toDTO(repository.save(existing));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    private ParametroCondominio toEntity(ParametroCondominioDTO dto) {
        ParametroCondominio entity = new ParametroCondominio();
        entity.setCategoria(dto.categoria());
        entity.setChave(dto.chave());
        entity.setDescricao(dto.descricao());
        entity.setValorNumerico(dto.valorNumerico());
        entity.setValorTexto(dto.valorTexto());
        return entity;
    }

    private ParametroCondominioDTO toDTO(ParametroCondominio e) {
        return new ParametroCondominioDTO(
            e.getId(), e.getCategoria(), e.getChave(), e.getDescricao(),
            e.getValorNumerico(), e.getValorTexto()
        );
    }
}
