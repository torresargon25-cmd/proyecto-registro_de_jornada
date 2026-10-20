package com.example.registrodejornada.model;

import com.example.registrodejornada.enums.TipoFichaje;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fichaje {

    @Id @GeneratedValue
    private Long id;
    private LocalDateTime momento;

    @Enumerated (EnumType.STRING)
    private TipoFichaje tipo;

    @ManyToOne
    private Empleado empleado;



}
