package com.mycompany.casopractica1.service;

import com.mycompany.casopractica1.domain.Reunion;

import java.util.List;
import java.util.Optional;

public interface ReunionService {
    List<Reunion> listar();
    Optional<Reunion> buscarPorId(Long id);


    Reunion guardar(Reunion reunion);
    Reunion actualizar(Long id, Reunion reunion);
    void eliminar(Long id);
}
