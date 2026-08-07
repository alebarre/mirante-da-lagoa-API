package br.com.mirantedalagoa.controller;

import br.com.mirantedalagoa.dto.CompromissoDTO;
import br.com.mirantedalagoa.service.CompromissoService;
import br.com.mirantedalagoa.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/compromissos")
public class CompromissoController extends GenericCrudController<CompromissoDTO, CompromissoDTO> {

    @Autowired
    private CompromissoService service;

    @Override
    protected CrudService<CompromissoDTO, CompromissoDTO> getService() {
        return service;
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA','FUNCIONARIO')")
    public ResponseEntity<List<CompromissoDTO>> list() {
        return super.list();
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA','FUNCIONARIO')")
    public ResponseEntity<CompromissoDTO> get(@PathVariable UUID id) {
        return super.get(id);
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA','FUNCIONARIO')")
    public ResponseEntity<CompromissoDTO> create(@RequestBody CompromissoDTO dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA','FUNCIONARIO')")
    public ResponseEntity<CompromissoDTO> update(@PathVariable UUID id, @RequestBody CompromissoDTO dto) {
        return super.update(id, dto);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA','FUNCIONARIO')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return super.delete(id);
    }
}