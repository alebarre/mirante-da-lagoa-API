package br.com.mirantedalagoa.controller;

import br.com.mirantedalagoa.dto.MoradorDTO;
import br.com.mirantedalagoa.service.CrudService;
import br.com.mirantedalagoa.service.MoradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/moradores")
public class MoradorController extends GenericCrudController<MoradorDTO, MoradorDTO> {

    @Autowired
    private MoradorService service;

    @Override
    protected CrudService<MoradorDTO, MoradorDTO> getService() {
        return service;
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA')")
    public ResponseEntity<List<MoradorDTO>> list() {
        return super.list();
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA')")
    public ResponseEntity<MoradorDTO> get(@PathVariable UUID id) {
        return super.get(id);
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA')")
    public ResponseEntity<MoradorDTO> create(@RequestBody MoradorDTO dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA')")
    public ResponseEntity<MoradorDTO> update(@PathVariable UUID id, @RequestBody MoradorDTO dto) {
        return super.update(id, dto);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return super.delete(id);
    }
}