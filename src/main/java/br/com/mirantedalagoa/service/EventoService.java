package br.com.mirantedalagoa.service;

import br.com.mirantedalagoa.dto.EventoDTO;
import br.com.mirantedalagoa.model.Evento;
import br.com.mirantedalagoa.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class EventoService implements CrudService<EventoDTO, EventoDTO> {

    @Autowired
    private EventoRepository repository;

    @Override
    public List<EventoDTO> listAll() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public EventoDTO findById(UUID id) {
        return toDTO(repository.findById(id).orElseThrow(() -> new RuntimeException("Evento não encontrado")));
    }

    @Override
    @Transactional
    public EventoDTO create(EventoDTO dto) {
        Evento entity = toEntity(dto);
        entity.setCreatedAt(Instant.now());
        return toDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public EventoDTO update(UUID id, EventoDTO dto) {
        Evento existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Evento não encontrado"));
        Evento updated = toEntity(dto);
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

    private EventoDTO toDTO(Evento e) {
        return new EventoDTO(e.getId(), e.getTitle(), e.getDescription(), e.getStartAt(), e.getEndAt(),
            e.getLocation(), e.getOrganizer(), e.getStatus(), e.isRestrictedToResidents(),
            e.getMaxParticipants(), e.getNotes());
    }

    private Evento toEntity(EventoDTO dto) {
        return Evento.builder()
            .id(dto.id())
            .title(dto.title())
            .description(dto.description())
            .startAt(dto.startAt())
            .endAt(dto.endAt())
            .location(dto.location())
            .organizer(dto.organizer())
            .status(dto.status())
            .restrictedToResidents(dto.restrictedToResidents())
            .maxParticipants(dto.maxParticipants())
            .notes(dto.notes())
            .build();
    }
}
