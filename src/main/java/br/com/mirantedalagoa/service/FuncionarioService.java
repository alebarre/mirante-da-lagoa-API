package br.com.mirantedalagoa.service;

import br.com.mirantedalagoa.dto.FuncionarioDTO;
import br.com.mirantedalagoa.model.Funcionario;
import br.com.mirantedalagoa.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class FuncionarioService implements CrudService<FuncionarioDTO, FuncionarioDTO> {

    @Autowired
    private FuncionarioRepository repository;

    @Override
    public List<FuncionarioDTO> listAll() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public FuncionarioDTO findById(UUID id) {
        return toDTO(repository.findById(id).orElseThrow(() -> new RuntimeException("Funcionário não encontrado")));
    }

    @Override
    @Transactional
    public FuncionarioDTO create(FuncionarioDTO dto) {
        Funcionario entity = toEntity(dto);
        entity.setCreatedAt(Instant.now());
        return toDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public FuncionarioDTO update(UUID id, FuncionarioDTO dto) {
        Funcionario existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        Funcionario updated = toEntity(dto);
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

    private FuncionarioDTO toDTO(Funcionario e) {
        return new FuncionarioDTO(
            e.getId(), e.getFullName(), e.getCpf(), e.getRg(), e.getBirthDate(), e.getPhone(),
            e.getEmail(), e.getAddress(), e.getPosition(), e.getDepartment(), e.getHireDate(),
            e.getTerminationDate(), e.getSalary(), e.getWorkRegime(), e.getBankAccount(), e.getNotes()
        );
    }

    private Funcionario toEntity(FuncionarioDTO dto) {
        return Funcionario.builder()
            .id(dto.id())
            .fullName(dto.fullName())
            .cpf(dto.cpf())
            .rg(dto.rg())
            .birthDate(dto.birthDate())
            .phone(dto.phone())
            .email(dto.email())
            .address(dto.address())
            .position(dto.position())
            .department(dto.department())
            .hireDate(dto.hireDate())
            .terminationDate(dto.terminationDate())
            .salary(dto.salary())
            .workRegime(dto.workRegime())
            .bankAccount(dto.bankAccount())
            .notes(dto.notes())
            .build();
    }
}
