package com.example.registrodejornada.dto.FichajeDTO;

import com.example.registrodejornada.enums.TipoFichaje;
import com.example.registrodejornada.model.Fichaje;

import java.time.LocalDateTime;

public record FichajeResponseDTO(
        Long id,
        LocalDateTime momento,
        TipoFichaje tipo,
        String nombreEmpleado
) {
    public static FichajeResponseDTO of (Fichaje f) {
        if (f == null)return null;
        return new FichajeResponseDTO(
                f.getId(),
                f.getMomento(),
                f.getTipo(),
                f.getEmpleado() != null ? f.getEmpleado().getNombreCompleto() : null
        );
    }
}
