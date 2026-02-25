package com.mycompany.casopractica1.repository;

import com.mycompany.casopractica1.domain.Sala;

import java.util.List;
import java.util.Optional;

public interface SalaRepository {
    List<Sala> findAll();
    Optional<Sala> findById(Long id);
    Sala save(Sala sala);
    void deleteById(Long id);
}
