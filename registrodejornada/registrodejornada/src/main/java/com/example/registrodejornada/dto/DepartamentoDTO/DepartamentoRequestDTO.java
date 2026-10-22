package com.example.registrodejornada.dto.DepartamentoDTO;

import com.example.registrodejornada.model.Departamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record DepartamentoRequestDTO(
        @NotBlank(message = "{departamentoRequestDTO.nombre.notBlank}")
        String nombre,

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
