package com.example.registrodejornada.controller;

import com.example.registrodejornada.dto.FichajeDTO.FichajeResponseDTO;
import com.example.registrodejornada.service.FichajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/empleados/{empleadoId}")
public class FichajeController {

    private final FichajeService fichajeService;

    @PostMapping("/fichar")
    public ResponseEntity<FichajeResponseDTO> fichar(@PathVariable Long empleadoId) {
        return ResponseEntity.status(201)
                .body(FichajeResponseDTO.of(fichajeService.fichar(empleadoId)));
    }
}
