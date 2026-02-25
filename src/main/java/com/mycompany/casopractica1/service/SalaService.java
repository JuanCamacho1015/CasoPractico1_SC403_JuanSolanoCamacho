package com.mycompany.casopractica1.service;

import com.mycompany.casopractica1.domain.Sala;

import java.util.List;
import java.util.Optional;

public interface SalaService {
    List<Sala> listar();
    Optional<Sala> buscarPorId(Long id);


    Sala guardar(Sala sala);
    Sala actualizar(Long id, Sala sala);
    void eliminar(Long id);
}
