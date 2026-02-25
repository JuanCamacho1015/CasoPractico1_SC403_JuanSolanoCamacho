package com.mycompany.casopractica1.repository;
import com.mycompany.casopractica1.domain.Sala;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class SalaRepositoryMem implements SalaRepository {

    private final Map<Long, Sala> data = new LinkedHashMap<>();
    private final AtomicLong seq = new AtomicLong(0);

    @Override
    public List<Sala> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Optional<Sala> findById(Long id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Sala save(Sala sala) {
        if (sala.getId() == null) {
            sala.setId(seq.incrementAndGet());
        }
        data.put(sala.getId(), sala);
        return sala;
    }

    @Override
    public void deleteById(Long id) {
        data.remove(id);
    }
}
