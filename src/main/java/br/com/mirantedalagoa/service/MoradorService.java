package br.com.mirantedalagoa.service;

import br.com.mirantedalagoa.dto.MoradorDTO;
import br.com.mirantedalagoa.model.Morador;
import br.com.mirantedalagoa.repository.MoradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class MoradorService implements CrudService<MoradorDTO, MoradorDTO> {

    @Autowired
    private MoradorRepository repository;

    @Override
    public List<MoradorDTO> listAll() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public MoradorDTO findById(UUID id) {
        return toDTO(repository.findById(id).orElseThrow(() -> new RuntimeException("Morador nao encontrado")));
    }

    @Override
    @Transactional
    public MoradorDTO create(MoradorDTO dto) {
        Morador entity = toEntity(dto);
        entity.setCreatedAt(Instant.now());
        return toDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public MoradorDTO update(UUID id, MoradorDTO dto) {
        Morador existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Morador nao encontrado"));
        updateEntity(existing, dto);
        existing.setUpdatedAt(Instant.now());
        return toDTO(repository.save(existing));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    private MoradorDTO toDTO(Morador e) {
        return new MoradorDTO(e.getId(), e.getFullName(), e.getCpf(), e.getRg(), e.getBirthDate(),
            e.getPhone(), e.getEmail(), e.getBlock(), e.getApartment(), e.getParkingSpot(), e.getPets(),
            e.isOwner(), e.getMoveInDate(), e.getMoveOutDate(), e.getEmergencyContact(), e.getNotes());
    }

    private Morador toEntity(MoradorDTO dto) {
        Morador entity = new Morador();
        updateEntity(entity, dto);
        return entity;
    }

    private void updateEntity(Morador entity, MoradorDTO dto) {
        entity.setFullName(dto.fullName());
        entity.setCpf(dto.cpf());
        entity.setRg(dto.rg());
        entity.setBirthDate(dto.birthDate());
        entity.setPhone(dto.phone());
        entity.setEmail(dto.email());
        entity.setBlock(dto.block());
        entity.setApartment(dto.apartment());
        entity.setParkingSpot(dto.parkingSpot());
        entity.setPets(dto.pets());
        entity.setOwner(dto.owner());
        entity.setMoveInDate(dto.moveInDate());
        entity.setMoveOutDate(dto.moveOutDate());
        entity.setEmergencyContact(dto.emergencyContact());
        entity.setNotes(dto.notes());
    }
}