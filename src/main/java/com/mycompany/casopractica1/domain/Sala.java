package com.mycompany.casopractica1.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Sala {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio.")
    private String nombre;

    @Min(value = 1, message = "La capacidad debe ser mayor a 0.")
    private int capacidad;

    public Sala() {}

    public Sala(Long id, String nombre, int capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
}
