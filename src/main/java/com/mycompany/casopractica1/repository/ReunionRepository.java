package com.mycompany.casopractica1.repository;

import com.mycompany.casopractica1.domain.Reunion;

import java.util.List;
import java.util.Optional;

public interface ReunionRepository {
    List<Reunion> findAll();
    Optional<Reunion> findById(Long id);
    Reunion save(Reunion reunion);
    void deleteById(Long id);
    void deleteBySalaId(Long salaId);
}
