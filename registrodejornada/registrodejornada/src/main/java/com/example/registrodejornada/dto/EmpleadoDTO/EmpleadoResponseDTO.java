package com.example.registrodejornada.dto.EmpleadoDTO;

import com.example.registrodejornada.model.Empleado;

import java.math.BigDecimal;

public record EmpleadoResponseDTO(
        Long id,
        String nombreCompleto,
        String cargo,
        BigDecimal salario,
        String nombreDepartamento
) {
    public static EmpleadoResponseDTO of(Empleado e) {
        if (e == null) return null;
        return new EmpleadoResponseDTO(
                e.getId(),
                e.getNombreCompleto(),
                e.getCargo(),
                e.getSalario(),
                e.getDepartamento() != null ? e.getDepartamento().getNombre() : null
        );

    }




}
