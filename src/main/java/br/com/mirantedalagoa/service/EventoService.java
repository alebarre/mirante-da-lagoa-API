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
        return toDTO(repository.findById(id).orElseThrow(() -> new RuntimeException("Evento nao encontrado")));
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
        Evento existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Evento nao encontrado"));
        updateEntity(existing, dto);
        existing.setUpdatedAt(Instant.now());
        return toDTO(repository.save(existing));
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
        Evento entity = new Evento();
        updateEntity(entity, dto);
        return entity;
    }

    private void updateEntity(Evento entity, EventoDTO dto) {
        entity.setTitle(dto.title());
        entity.setDescription(dto.description());
        entity.setStartAt(dto.startAt());
        entity.setEndAt(dto.endAt());
        entity.setLocation(dto.location());
        entity.setOrganizer(dto.organizer());
        entity.setStatus(dto.status());
        entity.setRestrictedToResidents(dto.restrictedToResidents());
        entity.setMaxParticipants(dto.maxParticipants());
        entity.setNotes(dto.notes());
    }
}