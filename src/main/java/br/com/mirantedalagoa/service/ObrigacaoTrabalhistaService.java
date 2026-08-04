package br.com.mirantedalagoa.service;

import br.com.mirantedalagoa.dto.ObrigacaoTrabalhistaDTO;
import br.com.mirantedalagoa.model.ObrigacaoTrabalhista;
import br.com.mirantedalagoa.repository.ObrigacaoTrabalhistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class ObrigacaoTrabalhistaService implements CrudService<ObrigacaoTrabalhistaDTO, ObrigacaoTrabalhistaDTO> {

    @Autowired
    private ObrigacaoTrabalhistaRepository repository;

    @Override
    public List<ObrigacaoTrabalhistaDTO> listAll() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public ObrigacaoTrabalhistaDTO findById(UUID id) {
        return toDTO(repository.findById(id).orElseThrow(() -> new RuntimeException("Obrigação não encontrada")));
    }

    @Override
    @Transactional
    public ObrigacaoTrabalhistaDTO create(ObrigacaoTrabalhistaDTO dto) {
        ObrigacaoTrabalhista entity = toEntity(dto);
        entity.setCreatedAt(Instant.now());
        return toDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public ObrigacaoTrabalhistaDTO update(UUID id, ObrigacaoTrabalhistaDTO dto) {
        ObrigacaoTrabalhista existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Obrigação não encontrada"));
        ObrigacaoTrabalhista updated = toEntity(dto);
        updated.setId(existing.getId());
        updated.setCreatedAt(existing.getCreatedAt());
        updated.setUpdatedAt(Instant.now());
        return toDTO(repository.save(updated));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    private ObrigacaoTrabalhistaDTO toDTO(ObrigacaoTrabalhista e) {
        return new ObrigacaoTrabalhistaDTO(e.getId(), e.getName(), e.getDescription(), e.getPeriodicity(),
            e.getDueDate(), e.getCompletedAt(), e.getResponsible(), e.getStatus(), e.getNotes());
    }

    private ObrigacaoTrabalhista toEntity(ObrigacaoTrabalhistaDTO dto) {
        return ObrigacaoTrabalhista.builder()
            .id(dto.id())
            .name(dto.name())
            .description(dto.description())
            .periodicity(dto.periodicity())
            .dueDate(dto.dueDate())
            .completedAt(dto.completedAt())
            .responsible(dto.responsible())
            .status(dto.status())
            .notes(dto.notes())
            .build();
    }
}
