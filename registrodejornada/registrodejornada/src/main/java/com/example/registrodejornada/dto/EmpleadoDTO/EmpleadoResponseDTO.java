package com.example.registrodejornada.dto.EmpleadoDTO;

import com.example.registrodejornada.model.Empleado;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(name = "EmpleadoResponseDTO", description = "Detalle de un empleado")
public record EmpleadoResponseDTO(
        @Schema(description = "Identificador único del empleado", example = "1")
        Long id,
        @Schema(description = "Nombre completo del empleado", example = "Ana López")
        String nombreCompleto,
        @Schema(description = "Cargo del empleado", example = "Desarrolladora")
        String cargo,
        @Schema(description = "Salario del empleado", example = "2500")
        BigDecimal salario,
        @Schema(description = "Nombre del departamento (null si no tiene)", example = "Informática")
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
