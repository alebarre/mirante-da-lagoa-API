package br.com.mirantedalagoa.controller;

import br.com.mirantedalagoa.dto.FuncionarioOcorrenciaDTO;
import br.com.mirantedalagoa.service.FuncionarioOcorrenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/funcionarios/{funcionarioId}/ocorrencias")
public class FuncionarioOcorrenciaController {

    @Autowired
    private FuncionarioOcorrenciaService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA','FUNCIONARIO')")
    public ResponseEntity<List<FuncionarioOcorrenciaDTO>> list(@PathVariable UUID funcionarioId) {
        return ResponseEntity.ok(service.listByFuncionario(funcionarioId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA','FUNCIONARIO')")
    public ResponseEntity<FuncionarioOcorrenciaDTO> get(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA')")
    public ResponseEntity<FuncionarioOcorrenciaDTO> create(
        @PathVariable UUID funcionarioId,
        @RequestBody FuncionarioOcorrenciaDTO dto
    ) {
        FuncionarioOcorrenciaDTO toSave = new FuncionarioOcorrenciaDTO(
            null, funcionarioId, dto.tipo(), dto.data(), dto.descricao(), dto.anexo()
        );
        return ResponseEntity.ok(service.create(toSave));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA')")
    public ResponseEntity<FuncionarioOcorrenciaDTO> update(
        @PathVariable UUID id,
        @RequestBody FuncionarioOcorrenciaDTO dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
