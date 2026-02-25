package com.mycompany.casopractica1.service;

import com.mycompany.casopractica1.domain.Reunion;
import com.mycompany.casopractica1.repository.ReunionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;

@Service
public class ReunionServiceImpl implements ReunionService {

    private final ReunionRepository reunionRepository;
    private final SalaService salaService;

    // Acepta "13:00" y "3:05" y normaliza a "HH:mm"
    private static final DateTimeFormatter INPUT_HORA = new DateTimeFormatterBuilder()
            .appendValue(HOUR_OF_DAY)          
            .appendLiteral(':')
            .appendValue(MINUTE_OF_HOUR, 2)    
            .toFormatter()
            .withResolverStyle(ResolverStyle.STRICT);

    private static final DateTimeFormatter OUT_HORA = DateTimeFormatter.ofPattern("HH:mm");

    public ReunionServiceImpl(ReunionRepository reunionRepository, SalaService salaService) {
        this.reunionRepository = reunionRepository;
        this.salaService = salaService;
    }

    @Override
    public List<Reunion> listar() {
        return reunionRepository.findAll();
    }

    @Override
    public Optional<Reunion> buscarPorId(Long id) {
        return reunionRepository.findById(id);
    }

    @Override
    public Reunion guardar(Reunion reunion) {
        normalizar(reunion);
        validarCampos(reunion);
        validarSalaExiste(reunion.getSalaId());


        if (existeConflicto(reunion, null)) {
            throw new IllegalArgumentException("Conflicto: ya existe una reunión en esa sala para esa fecha y hora.");
        }

        reunion.setId(null);
        return reunionRepository.save(reunion);
    }

    @Override
    public Reunion actualizar(Long id, Reunion reunion) {
        Reunion existente = reunionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reunión no encontrada."));

        normalizar(reunion);
        validarCampos(reunion);
        validarSalaExiste(reunion.getSalaId());


        if (existeConflicto(reunion, id)) {
            throw new IllegalArgumentException("Conflicto: ya existe una reunión en esa sala para esa fecha y hora.");
        }

        existente.setTitulo(reunion.getTitulo());
        existente.setFecha(reunion.getFecha());
        existente.setHora(reunion.getHora());
        existente.setSalaId(reunion.getSalaId());

        return reunionRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        reunionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reunión no encontrada."));
        reunionRepository.deleteById(id);
    }


    private void normalizar(Reunion reunion) {
        if (reunion.getTitulo() != null) reunion.setTitulo(reunion.getTitulo().trim());
        if (reunion.getHora() != null) reunion.setHora(normalizarHora(reunion.getHora()));
    }

    private String normalizarHora(String hora) {
        String h = hora.trim();
        try {
            LocalTime t = LocalTime.parse(h, INPUT_HORA);
            return t.format(OUT_HORA);
        } catch (Exception ex) {
            return h;
        }
    }

    private void validarCampos(Reunion reunion) {
        if (reunion.getTitulo() == null || reunion.getTitulo().isBlank()) {
            throw new IllegalArgumentException("El título es obligatorio.");
        }
        if (reunion.getFecha() == null) {
            throw new IllegalArgumentException("La fecha es obligatoria.");
        }
        if (reunion.getHora() == null || reunion.getHora().isBlank()) {
            throw new IllegalArgumentException("La hora es obligatoria.");
        }
        if (reunion.getSalaId() == null) {
            throw new IllegalArgumentException("Debe seleccionar una sala.");
        }
    }

    private void validarSalaExiste(Long salaId) {
        if (salaService.buscarPorId(salaId).isEmpty()) {
            throw new IllegalArgumentException("La sala seleccionada no existe.");
        }
    }


    private boolean existeConflicto(Reunion candidata, Long excludeId) {
        String horaCand = normalizarHora(candidata.getHora());

        for (Reunion r : reunionRepository.findAll()) {
            if (excludeId != null && Objects.equals(r.getId(), excludeId)) {
                continue;
            }
            boolean mismaSala = Objects.equals(r.getSalaId(), candidata.getSalaId());
            boolean mismaFecha = Objects.equals(r.getFecha(), candidata.getFecha());
            boolean mismaHora = Objects.equals(normalizarHora(r.getHora()), horaCand);

            if (mismaSala && mismaFecha && mismaHora) {
                return true;
            }
        }
        return false;
    }
}