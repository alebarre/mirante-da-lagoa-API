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
        return toDTO(repository.findById(id).orElseThrow(() -> new RuntimeException("Funcionario nao encontrado")));
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
        Funcionario existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Funcionario nao encontrado"));
        updateEntity(existing, dto);
        existing.setUpdatedAt(Instant.now());
        return toDTO(repository.save(existing));
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
        Funcionario entity = new Funcionario();
        updateEntity(entity, dto);
        return entity;
    }

    private void updateEntity(Funcionario entity, FuncionarioDTO dto) {
        entity.setFullName(dto.fullName());
        entity.setCpf(dto.cpf());
        entity.setRg(dto.rg());
        entity.setBirthDate(dto.birthDate());
        entity.setPhone(dto.phone());
        entity.setEmail(dto.email());
        entity.setAddress(dto.address());
        entity.setPosition(dto.position());
        entity.setDepartment(dto.department());
        entity.setHireDate(dto.hireDate());
        entity.setTerminationDate(dto.terminationDate());
        entity.setSalary(dto.salary());
        entity.setWorkRegime(dto.workRegime());
        entity.setBankAccount(dto.bankAccount());
        entity.setNotes(dto.notes());
    }
}