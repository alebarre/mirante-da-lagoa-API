package br.com.mirantedalagoa.controller;

import br.com.mirantedalagoa.dto.ObrigacaoTrabalhistaDTO;
import br.com.mirantedalagoa.service.CrudService;
import br.com.mirantedalagoa.service.ObrigacaoTrabalhistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/obrigacoes")
public class ObrigacaoTrabalhistaController extends GenericCrudController<ObrigacaoTrabalhistaDTO, ObrigacaoTrabalhistaDTO> {

    @Autowired
    private ObrigacaoTrabalhistaService service;

    @Override
    protected CrudService<ObrigacaoTrabalhistaDTO, ObrigacaoTrabalhistaDTO> getService() {
        return service;
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO')")
    public ResponseEntity<List<ObrigacaoTrabalhistaDTO>> list() {
        return super.list();
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO')")
    public ResponseEntity<ObrigacaoTrabalhistaDTO> get(@PathVariable UUID id) {
        return super.get(id);
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO')")
    public ResponseEntity<ObrigacaoTrabalhistaDTO> create(@RequestBody ObrigacaoTrabalhistaDTO dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO')")
    public ResponseEntity<ObrigacaoTrabalhistaDTO> update(@PathVariable UUID id, @RequestBody ObrigacaoTrabalhistaDTO dto) {
        return super.update(id, dto);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return super.delete(id);
    }
}