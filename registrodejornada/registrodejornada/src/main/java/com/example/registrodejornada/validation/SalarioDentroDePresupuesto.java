package com.example.registrodejornada.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SalarioDentroDePresupuestoValidator.class)
@Documented
public @interface SalarioDentroDePresupuesto {

    String message() default "La suma de salarios del departamento superaría su presupuesto";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}