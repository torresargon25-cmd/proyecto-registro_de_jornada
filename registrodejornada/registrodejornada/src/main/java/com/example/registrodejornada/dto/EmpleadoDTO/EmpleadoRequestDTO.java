package com.example.registrodejornada.dto.EmpleadoDTO;

import com.example.registrodejornada.validation.SalarioDentroDePresupuesto;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@SalarioDentroDePresupuesto
public record EmpleadoRequestDTO(

        @NotBlank (message = "No puede ir en blanco")
        String nombreCompleto,
        @NotBlank (message = "No puede ir en blanco")
        String cargo,

        @NotNull (message = "No puede ser nulo")
        @Positive (message = "El salario debe ser mayor a 0 y positivo")
        BigDecimal salario,

        Long departamentoId
) {
}
