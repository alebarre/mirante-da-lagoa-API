package br.com.mirantedalagoa.controller;

import br.com.mirantedalagoa.dto.FuncionarioDTO;
import br.com.mirantedalagoa.service.CrudService;
import br.com.mirantedalagoa.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/funcionarios")
@PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA')")
public class FuncionarioController extends GenericCrudController<FuncionarioDTO, FuncionarioDTO> {

    @Autowired
    private FuncionarioService service;

    @Override
    protected CrudService<FuncionarioDTO, FuncionarioDTO> getService() {
        return service;
    }
}
