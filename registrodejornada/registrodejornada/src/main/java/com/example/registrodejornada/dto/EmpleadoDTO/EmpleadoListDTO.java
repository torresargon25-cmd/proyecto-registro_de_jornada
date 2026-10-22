package com.example.registrodejornada.dto.EmpleadoDTO;

import com.example.registrodejornada.model.Empleado;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "EmpleadoListDTO", description = "Item de una lista de empleados")
public record EmpleadoListDTO(
        @Schema(description = "Identificador único del empleado", example = "1")
        Long id,
        @Schema(description = "Nombre completo del empleado", example = "Ana López")
        String nombreCompleto,
        @Schema(description = "Cargo del empleado", example = "Desarrolladora")
        String cargo,
        @Schema(description = "Nombre del departamento (null si no tiene)", example = "Informática")
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
