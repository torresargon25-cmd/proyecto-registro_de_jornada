package com.example.registrodejornada.controller;

import com.example.registrodejornada.dto.EmpleadoDTO.EmpleadoListDTO;
import com.example.registrodejornada.dto.EmpleadoDTO.EmpleadoRequestDTO;
import com.example.registrodejornada.dto.EmpleadoDTO.EmpleadoResponseDTO;
import com.example.registrodejornada.service.EmpleadoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<EmpleadoListDTO>> getAllEmployees() {
        return ResponseEntity.ok(
                empleadoService.getAllEmployees().stream()
                        .map(EmpleadoListDTO::of)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> getEmployeeById(
            @PathVariable @Min(value = 1) Long id) {
        return ResponseEntity.ok(
                EmpleadoResponseDTO.of(empleadoService.getEmployeeById(id)));
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponseDTO> addEmployee(
            @Valid @RequestBody EmpleadoRequestDTO dto) {
        return ResponseEntity.status(201)
                .body(EmpleadoResponseDTO.of(empleadoService.addEmployee(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> updateEmployee(
            @PathVariable Long id, @Valid @RequestBody EmpleadoRequestDTO dto) {
        return ResponseEntity.ok(
                EmpleadoResponseDTO.of(empleadoService.updateEmployee(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        empleadoService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
