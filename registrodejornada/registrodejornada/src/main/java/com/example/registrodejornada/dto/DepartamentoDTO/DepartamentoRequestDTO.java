package com.example.registrodejornada.dto.DepartamentoDTO;

import com.example.registrodejornada.model.Departamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record DepartamentoRequestDTO(
        @NotBlank(message = "El nombre es inválido")
        String nombre,

        @NotNull(message = "El presupuesto no puede ser null")
        @PositiveOrZero(message = "El presupuesto no puede ser un número negativo")
        BigDecimal presupuesto
) {
    public Departamento to(){
        return Departamento.builder()
                .nombre(nombre)
                .presupuesto(presupuesto)
                .build();
    }
}
