package br.com.mirantedalagoa.controller;

import br.com.mirantedalagoa.dto.MoradorDTO;
import br.com.mirantedalagoa.service.CrudService;
import br.com.mirantedalagoa.service.MoradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/moradores")
@PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA')")
public class MoradorController extends GenericCrudController<MoradorDTO, MoradorDTO> {

    @Autowired
    private MoradorService service;

    @Override
    protected CrudService<MoradorDTO, MoradorDTO> getService() {
        return service;
    }
}
