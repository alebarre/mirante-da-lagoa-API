package br.com.mirantedalagoa.service;

import br.com.mirantedalagoa.dto.FuncionarioOcorrenciaDTO;
import br.com.mirantedalagoa.model.Funcionario;
import br.com.mirantedalagoa.model.FuncionarioOcorrencia;
import br.com.mirantedalagoa.repository.FuncionarioOcorrenciaRepository;
import br.com.mirantedalagoa.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class FuncionarioOcorrenciaService {

    @Autowired
    private FuncionarioOcorrenciaRepository repository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public List<FuncionarioOcorrenciaDTO> listByFuncionario(UUID funcionarioId) {
        return repository.findByFuncionarioIdOrderByDataDesc(funcionarioId).stream()
            .map(this::toDTO)
            .toList();
    }

    public FuncionarioOcorrenciaDTO findById(UUID id) {
        return repository.findById(id)
            .map(this::toDTO)
            .orElseThrow(() -> new RuntimeException("Ocorrencia nao encontrada"));
    }

    @Transactional
    public FuncionarioOcorrenciaDTO create(FuncionarioOcorrenciaDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(dto.funcionarioId())
            .orElseThrow(() -> new RuntimeException("Funcionario nao encontrado"));
        FuncionarioOcorrencia entity = toEntity(dto, funcionario);
        entity.setCreatedAt(Instant.now());
        return toDTO(repository.save(entity));
    }

    @Transactional
    public FuncionarioOcorrenciaDTO update(UUID id, FuncionarioOcorrenciaDTO dto) {
        FuncionarioOcorrencia existing = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ocorrencia nao encontrada"));
        existing.setTipo(dto.tipo());
        existing.setData(dto.data());
        existing.setDescricao(dto.descricao());
        existing.setAnexo(dto.anexo());
        return toDTO(repository.save(existing));
    }

    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    private FuncionarioOcorrenciaDTO toDTO(FuncionarioOcorrencia e) {
        return new FuncionarioOcorrenciaDTO(
            e.getId(),
            e.getFuncionario() != null ? e.getFuncionario().getId() : null,
            e.getTipo(),
            e.getData(),
            e.getDescricao(),
            e.getAnexo()
        );
    }

    private FuncionarioOcorrencia toEntity(FuncionarioOcorrenciaDTO dto, Funcionario funcionario) {
        return FuncionarioOcorrencia.builder()
            .funcionario(funcionario)
            .tipo(dto.tipo())
            .data(dto.data())
            .descricao(dto.descricao())
            .anexo(dto.anexo())
            .build();
    }
}
