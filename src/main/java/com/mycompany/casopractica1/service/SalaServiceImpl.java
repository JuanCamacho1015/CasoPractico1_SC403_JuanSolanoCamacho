package com.mycompany.casopractica1.service;

import com.mycompany.casopractica1.domain.Sala;
import com.mycompany.casopractica1.repository.ReunionRepository;
import com.mycompany.casopractica1.repository.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaServiceImpl implements SalaService {

    private final SalaRepository salaRepository;
    private final ReunionRepository reunionRepository;

    public SalaServiceImpl(SalaRepository salaRepository, ReunionRepository reunionRepository) {
        this.salaRepository = salaRepository;
        this.reunionRepository = reunionRepository;
    }

    @Override
    public List<Sala> listar() {
        return salaRepository.findAll();
    }

    @Override
    public Optional<Sala> buscarPorId(Long id) {
        return salaRepository.findById(id);
    }

    @Override
    public Sala guardar(Sala sala) {

        if (sala.getNombre() != null) {
            sala.setNombre(sala.getNombre().trim());
        }

        if (sala.getNombre() == null || sala.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (sala.getCapacidad() <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0.");
        }


        sala.setId(null);
        return salaRepository.save(sala);
    }

    @Override
    public Sala actualizar(Long id, Sala sala) {
        var existente = salaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sala no encontrada."));

        if (sala.getNombre() != null) {
            sala.setNombre(sala.getNombre().trim());
        }

        if (sala.getNombre() == null || sala.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (sala.getCapacidad() <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0.");
        }

        existente.setNombre(sala.getNombre());
        existente.setCapacidad(sala.getCapacidad());

        return salaRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {

        salaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Sala no encontrada."));

        reunionRepository.deleteBySalaId(id);

        salaRepository.deleteById(id);
    }
}