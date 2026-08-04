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
        return toDTO(repository.findById(id).orElseThrow(() -> new RuntimeException("Morador não encontrado")));
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
        Morador existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Morador não encontrado"));
        Morador updated = toEntity(dto);
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

    private MoradorDTO toDTO(Morador e) {
        return new MoradorDTO(e.getId(), e.getFullName(), e.getCpf(), e.getRg(), e.getBirthDate(),
            e.getPhone(), e.getEmail(), e.getBlock(), e.getApartment(), e.getParkingSpot(), e.getPets(),
            e.isOwner(), e.getMoveInDate(), e.getMoveOutDate(), e.getEmergencyContact(), e.getNotes());
    }

    private Morador toEntity(MoradorDTO dto) {
        return Morador.builder()
            .id(dto.id())
            .fullName(dto.fullName())
            .cpf(dto.cpf())
            .rg(dto.rg())
            .birthDate(dto.birthDate())
            .phone(dto.phone())
            .email(dto.email())
            .block(dto.block())
            .apartment(dto.apartment())
            .parkingSpot(dto.parkingSpot())
            .pets(dto.pets())
            .owner(dto.owner())
            .moveInDate(dto.moveInDate())
            .moveOutDate(dto.moveOutDate())
            .emergencyContact(dto.emergencyContact())
            .notes(dto.notes())
            .build();
    }
}
