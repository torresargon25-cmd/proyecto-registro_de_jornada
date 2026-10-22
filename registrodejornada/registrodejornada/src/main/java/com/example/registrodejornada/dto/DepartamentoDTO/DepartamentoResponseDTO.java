package com.example.registrodejornada.dto.DepartamentoDTO;

import com.example.registrodejornada.dto.EmpleadoDTO.EmpleadoListDTO;
import com.example.registrodejornada.model.Departamento;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

@Schema(name = "DepartamentoResponseDTO", description = "Detalle de un departamento con sus empleados")
public record DepartamentoResponseDTO(
        @Schema(description = "Identificador único del departamento", example = "1")
        Long id,
        @Schema(description = "Nombre del departamento", example = "Informática")
        String nombre,
        @Schema(description = "Presupuesto del departamento", example = "10000")
        BigDecimal presupuesto,
        @Schema(description = "Empleados del departamento")
        List<EmpleadoListDTO> listaEmpleados
) {
    public static DepartamentoResponseDTO of(Departamento d){
        if (d == null)return null;
        return new DepartamentoResponseDTO(
                d.getId(),
                d.getNombre(),
                d.getPresupuesto(),
                d.getEmpleados().stream().map(EmpleadoListDTO::of).toList()

        );
    }
}
