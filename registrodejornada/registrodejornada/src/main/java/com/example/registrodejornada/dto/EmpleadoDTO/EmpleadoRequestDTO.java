package com.example.registrodejornada.dto.EmpleadoDTO;

import com.example.registrodejornada.validation.SalarioDentroDePresupuesto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@SalarioDentroDePresupuesto(message = "{empleadoRequestDTO.salarioDentroDePresupuesto}")
public record EmpleadoRequestDTO(

        @NotBlank(message = "{empleadoRequestDTO.nombreCompleto.notBlank}")
        String nombreCompleto,

        @NotBlank(message = "{empleadoRequestDTO.cargo.notBlank}")
        String cargo,

        @NotNull(message = "{empleadoRequestDTO.salario.notNull}")
        @Positive(message = "{empleadoRequestDTO.salario.positive}")
        BigDecimal salario,

        Long departamentoId
) {
}
