package com.example.registrodejornada.dto.FichajeDTO;

import com.example.registrodejornada.enums.TipoFichaje;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FichajeRequestDTO", description = "Body opcional al fichar. Si se omite, se alterna solo")
public record FichajeRequestDTO(
        @Schema(description = "Tipo de fichaje que se pide registrar", example = "ENTRADA")
        TipoFichaje tipo
) {
}
