package com.example.registrodejornada.dto.FichajeDTO;

import com.example.registrodejornada.enums.TipoFichaje;
import com.example.registrodejornada.model.Fichaje;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "FichajeResponseDTO", description = "Un fichaje de entrada o salida")
public record FichajeResponseDTO(
        @Schema(description = "Identificador único del fichaje", example = "1")
        Long id,
        @Schema(description = "Fecha y hora del fichaje", example = "2026-10-22T08:00:00")
        LocalDateTime momento,
        @Schema(description = "Tipo de fichaje", example = "ENTRADA")
        TipoFichaje tipo,
        @Schema(description = "Nombre del empleado", example = "Ana López")
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
