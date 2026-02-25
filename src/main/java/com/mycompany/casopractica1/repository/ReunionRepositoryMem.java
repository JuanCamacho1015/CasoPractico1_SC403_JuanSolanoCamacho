package com.mycompany.casopractica1.repository;

import com.mycompany.casopractica1.domain.Reunion;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReunionRepositoryMem implements ReunionRepository {

    private final Map<Long, Reunion> data = new LinkedHashMap<>();
    private final AtomicLong seq = new AtomicLong(0);

    @Override
    public List<Reunion> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Optional<Reunion> findById(Long id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Reunion save(Reunion reunion) {
        if (reunion.getId() == null) {
            reunion.setId(seq.incrementAndGet());
        }
        data.put(reunion.getId(), reunion);
        return reunion;
    }

    @Override
    public void deleteById(Long id) {
        data.remove(id);
    }

    @Override
    public void deleteBySalaId(Long salaId) {
        data.values().removeIf(r -> Objects.equals(r.getSalaId(), salaId));
    }
}
