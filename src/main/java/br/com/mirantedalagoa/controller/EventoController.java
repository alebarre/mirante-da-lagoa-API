package br.com.mirantedalagoa.controller;

import br.com.mirantedalagoa.dto.EventoDTO;
import br.com.mirantedalagoa.service.CrudService;
import br.com.mirantedalagoa.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/eventos")
public class EventoController extends GenericCrudController<EventoDTO, EventoDTO> {

    @Autowired
    private EventoService service;

    @Override
    protected CrudService<EventoDTO, EventoDTO> getService() {
        return service;
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA','FUNCIONARIO','MORADOR')")
    public ResponseEntity<List<EventoDTO>> list() {
        return super.list();
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA','FUNCIONARIO','MORADOR')")
    public ResponseEntity<EventoDTO> get(@PathVariable UUID id) {
        return super.get(id);
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA','FUNCIONARIO')")
    public ResponseEntity<EventoDTO> create(@RequestBody EventoDTO dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA','FUNCIONARIO')")
    public ResponseEntity<EventoDTO> update(@PathVariable UUID id, @RequestBody EventoDTO dto) {
        return super.update(id, dto);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA','FUNCIONARIO')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return super.delete(id);
    }
}