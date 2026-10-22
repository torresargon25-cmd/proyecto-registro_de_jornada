package com.example.registrodejornada.dto.DepartamentoDTO;

import com.example.registrodejornada.model.Departamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

@Schema(name = "DepartamentoRequestDTO", description = "Datos para crear o editar un departamento")
public record DepartamentoRequestDTO(
        @Schema(description = "Nombre del departamento (único)", example = "Informática")
        @NotBlank(message = "{departamentoRequestDTO.nombre.notBlank}")
        String nombre,

        @Schema(description = "Presupuesto del departamento", example = "10000")
        @NotNull(message = "{departamentoRequestDTO.presupuesto.notNull}")
        @PositiveOrZero(message = "{departamentoRequestDTO.presupuesto.positiveOrZero}")
        BigDecimal presupuesto
) {
    public Departamento to() {
        return Departamento.builder()
                .nombre(nombre)
                .presupuesto(presupuesto)
                .build();
    }
}
