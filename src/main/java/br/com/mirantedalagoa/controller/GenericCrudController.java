package br.com.mirantedalagoa.controller;

import br.com.mirantedalagoa.service.CrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public abstract class GenericCrudController<DTO, REQ> {

    protected abstract CrudService<DTO, REQ> getService();

    @GetMapping
    public ResponseEntity<List<DTO>> list() {
        return ResponseEntity.ok(getService().listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTO> get(@PathVariable UUID id) {
        return ResponseEntity.ok(getService().findById(id));
    }

    @PostMapping
    public ResponseEntity<DTO> create(@RequestBody REQ dto) {
        return ResponseEntity.ok(getService().create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DTO> update(@PathVariable UUID id, @RequestBody REQ dto) {
        return ResponseEntity.ok(getService().update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        getService().delete(id);
        return ResponseEntity.noContent().build();
    }
}
