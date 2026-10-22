package com.example.registrodejornada.dto.EmpleadoDTO;

import com.example.registrodejornada.validation.SalarioDentroDePresupuesto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(name = "EmpleadoRequestDTO", description = "Datos para crear o editar un empleado")
@SalarioDentroDePresupuesto(message = "{empleadoRequestDTO.salarioDentroDePresupuesto}")
public record EmpleadoRequestDTO(
        @Schema(description = "Nombre completo del empleado", example = "Ana López")
        @NotBlank(message = "{empleadoRequestDTO.nombreCompleto.notBlank}")
        String nombreCompleto,

        @Schema(description = "Cargo del empleado", example = "Desarrolladora")
        @NotBlank(message = "{empleadoRequestDTO.cargo.notBlank}")
        String cargo,

        @Schema(description = "Salario del empleado", example = "2500")
        @NotNull(message = "{empleadoRequestDTO.salario.notNull}")
        @Positive(message = "{empleadoRequestDTO.salario.positive}")
        BigDecimal salario,

        @Schema(description = "Id del departamento (opcional)", example = "1")
        Long departamentoId
) {
}
