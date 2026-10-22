package com.example.registrodejornada.dto.DepartamentoDTO;

import com.example.registrodejornada.model.Departamento;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "DepartamentoListDTO", description = "Item de una lista de departamentos")
public record DepartamentoListDTO(
        @Schema(description = "Identificador único del departamento", example = "1")
        Long id,
        @Schema(description = "Nombre del departamento", example = "Informática")
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
