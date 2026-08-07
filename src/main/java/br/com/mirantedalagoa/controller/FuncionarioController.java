package br.com.mirantedalagoa.controller;

import br.com.mirantedalagoa.dto.FuncionarioDTO;
import br.com.mirantedalagoa.service.CrudService;
import br.com.mirantedalagoa.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController extends GenericCrudController<FuncionarioDTO, FuncionarioDTO> {

    @Autowired
    private FuncionarioService service;

    @Override
    protected CrudService<FuncionarioDTO, FuncionarioDTO> getService() {
        return service;
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA','FUNCIONARIO')")
    public ResponseEntity<List<FuncionarioDTO>> list() {
        return super.list();
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA','FUNCIONARIO')")
    public ResponseEntity<FuncionarioDTO> get(@PathVariable UUID id) {
        return super.get(id);
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA')")
    public ResponseEntity<FuncionarioDTO> create(@RequestBody FuncionarioDTO dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA')")
    public ResponseEntity<FuncionarioDTO> update(@PathVariable UUID id, @RequestBody FuncionarioDTO dto) {
        return super.update(id, dto);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return super.delete(id);
    }
}