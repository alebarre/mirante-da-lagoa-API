package br.com.mirantedalagoa.controller;

import br.com.mirantedalagoa.dto.CompromissoDTO;
import br.com.mirantedalagoa.service.CompromissoService;
import br.com.mirantedalagoa.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/compromissos")
@PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA','FUNCIONARIO')")
public class CompromissoController extends GenericCrudController<CompromissoDTO, CompromissoDTO> {

    @Autowired
    private CompromissoService service;

    @Override
    protected CrudService<CompromissoDTO, CompromissoDTO> getService() {
        return service;
    }
}
