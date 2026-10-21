package com.example.registrodejornada.dto.DepartamentoDTO;

import com.example.registrodejornada.model.Departamento;


public record DepartamentoListDTO(
        Long id,
        String nombre
) {
    public static DepartamentoListDTO of (Departamento d){
        if (d == null) return null;
        return new DepartamentoListDTO(
                d.getId(),
                d.getNombre()
        );
    }
}
