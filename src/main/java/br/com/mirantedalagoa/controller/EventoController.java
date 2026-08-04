package br.com.mirantedalagoa.controller;

import br.com.mirantedalagoa.dto.EventoDTO;
import br.com.mirantedalagoa.service.CrudService;
import br.com.mirantedalagoa.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eventos")
@PreAuthorize("hasAnyRole('ADMIN','SINDICO','PORTARIA','MORADOR')")
public class EventoController extends GenericCrudController<EventoDTO, EventoDTO> {

    @Autowired
    private EventoService service;

    @Override
    protected CrudService<EventoDTO, EventoDTO> getService() {
        return service;
    }
}
