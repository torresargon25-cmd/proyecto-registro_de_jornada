package com.example.registrodejornada.controller;

import com.example.registrodejornada.dto.FichajeDTO.FichajeRequestDTO;
import com.example.registrodejornada.dto.FichajeDTO.FichajeResponseDTO;
import com.example.registrodejornada.enums.TipoFichaje;
import com.example.registrodejornada.service.FichajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/empleados/{empleadoId}")
public class FichajeController {

    private final FichajeService fichajeService;

    @PostMapping("/fichar")
    public ResponseEntity<FichajeResponseDTO> fichar(
            @PathVariable Long empleadoId,
            @RequestBody(required = false) FichajeRequestDTO dto) {

        TipoFichaje tipo = dto == null ? null : dto.tipo();
        return ResponseEntity.status(201)
                .body(FichajeResponseDTO.of(fichajeService.fichar(empleadoId, tipo)));
    }
}
