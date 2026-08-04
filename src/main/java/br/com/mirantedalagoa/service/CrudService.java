package br.com.mirantedalagoa.service;

import java.util.List;
import java.util.UUID;

public interface CrudService<DTO, REQ> {
    List<DTO> listAll();
    DTO findById(UUID id);
    DTO create(REQ dto);
    DTO update(UUID id, REQ dto);
    void delete(UUID id);
}
