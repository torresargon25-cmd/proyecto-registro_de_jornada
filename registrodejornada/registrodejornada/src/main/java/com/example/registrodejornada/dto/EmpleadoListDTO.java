package com.example.registrodejornada.dto;

import com.example.registrodejornada.model.Empleado;

public record EmpleadoListDTO(



        Long id,
        String nombreCompleto,
        String cargo,
        String nombreDepartamento
) {
    public static EmpleadoListDTO of(Empleado e){
        if (e == null) return null;
        return new EmpleadoListDTO(
                e.getId(),
                e.getNombreCompleto(),
                e.getCargo(),
                e.getDepartamento() != null ? e.getDepartamento().getNombre() : null
        );
    }
}
