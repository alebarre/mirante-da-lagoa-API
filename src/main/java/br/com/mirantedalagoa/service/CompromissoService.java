package br.com.mirantedalagoa.service;

import br.com.mirantedalagoa.dto.CompromissoDTO;
import br.com.mirantedalagoa.model.Compromisso;
import br.com.mirantedalagoa.repository.CompromissoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class CompromissoService implements CrudService<CompromissoDTO, CompromissoDTO> {

    @Autowired
    private CompromissoRepository repository;

    @Override
    public List<CompromissoDTO> listAll() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public CompromissoDTO findById(UUID id) {
        return toDTO(repository.findById(id).orElseThrow(() -> new RuntimeException("Compromisso nao encontrado")));
    }

    @Override
    @Transactional
    public CompromissoDTO create(CompromissoDTO dto) {
        Compromisso entity = toEntity(dto);
        entity.setCreatedAt(Instant.now());
        return toDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public CompromissoDTO update(UUID id, CompromissoDTO dto) {
        Compromisso existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Compromisso nao encontrado"));
        updateEntity(existing, dto);
        existing.setUpdatedAt(Instant.now());
        return toDTO(repository.save(existing));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    private CompromissoDTO toDTO(Compromisso e) {
        return new CompromissoDTO(e.getId(), e.getTitle(), e.getDescription(), e.getScheduledAt(),
            e.getLocation(), e.getResponsible(), e.getStatus());
    }

    private Compromisso toEntity(CompromissoDTO dto) {
        Compromisso entity = new Compromisso();
        updateEntity(entity, dto);
        return entity;
    }

    private void updateEntity(Compromisso entity, CompromissoDTO dto) {
        entity.setTitle(dto.title());
        entity.setDescription(dto.description());
        entity.setScheduledAt(dto.scheduledAt());
        entity.setLocation(dto.location());
        entity.setResponsible(dto.responsible());
        entity.setStatus(dto.status());
    }
}