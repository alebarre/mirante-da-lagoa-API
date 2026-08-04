package br.com.mirantedalagoa.controller;

import br.com.mirantedalagoa.dto.ObrigacaoTrabalhistaDTO;
import br.com.mirantedalagoa.service.CrudService;
import br.com.mirantedalagoa.service.ObrigacaoTrabalhistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/obrigacoes")
@PreAuthorize("hasAnyRole('ADMIN','SINDICO')")
public class ObrigacaoTrabalhistaController extends GenericCrudController<ObrigacaoTrabalhistaDTO, ObrigacaoTrabalhistaDTO> {

    @Autowired
    private ObrigacaoTrabalhistaService service;

    @Override
    protected CrudService<ObrigacaoTrabalhistaDTO, ObrigacaoTrabalhistaDTO> getService() {
        return service;
    }
}
