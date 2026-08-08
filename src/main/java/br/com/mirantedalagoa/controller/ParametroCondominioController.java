package br.com.mirantedalagoa.controller;

import br.com.mirantedalagoa.dto.ParametroCondominioDTO;
import br.com.mirantedalagoa.service.ParametroCondominioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/parametros")
public class ParametroCondominioController extends GenericCrudController<ParametroCondominioDTO, ParametroCondominioDTO> {

    @Autowired
    private ParametroCondominioService service;

    @Override
    protected ParametroCondominioService getService() {
        return service;
    }

    @GetMapping("/folha")
    public ResponseEntity<Map<String, String>> findPercentuaisFolha() {
        Map<String, String> result = service.findPercentuaisFolha().entrySet().stream()
            .collect(java.util.stream.Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toPlainString()));
        return ResponseEntity.ok(result);
    }
}
