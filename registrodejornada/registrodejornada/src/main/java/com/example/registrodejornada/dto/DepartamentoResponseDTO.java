package com.example.registrodejornada.dto;

import com.example.registrodejornada.model.Departamento;

import java.math.BigDecimal;
import java.util.List;

public record DepartamentoResponseDTO(

       Long id,
       String nombre,
       BigDecimal presupuesto,
       List<EmpleadoListDTO> listaEmpleados
) {
    public static DepartamentoResponseDTO of(Departamento d){
        if (d == null) return null;
        return new DepartamentoResponseDTO(
                d.getId(),
                d.getNombre(),
                d.getPresupuesto(),
                d.getEmpleados().stream().map(EmpleadoListDTO::of).toList()

        );
    }
}
