package com.mycompany.casopractica1;

import com.mycompany.casopractica1.domain.Reunion;
import com.mycompany.casopractica1.domain.Sala;
import com.mycompany.casopractica1.service.ReunionService;
import com.mycompany.casopractica1.service.SalaService;
import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CasoPractica1Application {

    public static void main(String[] args) {
        SpringApplication.run(CasoPractica1Application.class, args);
    }

    @Bean
    CommandLineRunner cargarDatosIniciales(SalaService salaService, ReunionService reunionService) {
        return args -> {
            // Evita duplicar si reiniciás con DevTools
            if (!salaService.listar().isEmpty() || !reunionService.listar().isEmpty()) {
                return;
            }

            // 1) Sala Tamarindo, capacidad 10
            Sala s1 = new Sala();
            s1.setNombre("Sala Tamarindo");
            s1.setCapacidad(10);
            s1 = salaService.guardar(s1); // devuelve con ID

            // 2) Reunión: revisión de apps, 28/02/2026 13:00, asociada a esa sala
            Reunion r1 = new Reunion();
            r1.setTitulo("Revisión de apps");
            r1.setFecha(LocalDate.of(2026, 2, 28));
            r1.setHora("13:00");
            r1.setSalaId(s1.getId());

            reunionService.guardar(r1);
        };
    }
}