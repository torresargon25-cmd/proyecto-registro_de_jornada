package com.example.registrodejornada.validation;

import com.example.registrodejornada.dto.EmpleadoDTO.EmpleadoRequestDTO;
import com.example.registrodejornada.repo.DepartamentoRepository;
import com.example.registrodejornada.repo.EmpleadoRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class SalarioDentroDePresupuestoValidator
        implements ConstraintValidator<SalarioDentroDePresupuesto, EmpleadoRequestDTO> {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public boolean isValid(EmpleadoRequestDTO dto, ConstraintValidatorContext context) {

        if (dto == null || dto.salario() == null || dto.departamentoId() == null) {
            return true;
        }

        return departamentoRepository.findById(dto.departamentoId())
                .map(depto -> {
                    if (depto.getPresupuesto() == null) {
                        return true;
                    }
                    BigDecimal suma = empleadoRepository.sumaSalarios(depto.getId())
                            .orElse(BigDecimal.ZERO);
                    return suma.add(dto.salario()).compareTo(depto.getPresupuesto()) <= 0;
                })
                .orElse(true);
    }
}
