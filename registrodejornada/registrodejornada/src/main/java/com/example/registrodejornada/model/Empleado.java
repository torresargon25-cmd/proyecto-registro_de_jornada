package com.example.registrodejornada.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empleado {
    @Id @GeneratedValue
    private Long id;

    private String nombreCompleto;
    private String cargo;
    private BigDecimal salario;


    @ManyToOne
    private Departamento departamento;

}
