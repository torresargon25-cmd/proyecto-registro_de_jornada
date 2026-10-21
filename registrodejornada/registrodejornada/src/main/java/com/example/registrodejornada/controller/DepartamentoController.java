package com.example.registrodejornada.controller;

import com.example.registrodejornada.dto.DepartamentoDTO.DepartamentoListDTO;
import com.example.registrodejornada.dto.DepartamentoDTO.DepartamentoRequestDTO;
import com.example.registrodejornada.dto.DepartamentoDTO.DepartamentoResponseDTO;
import com.example.registrodejornada.model.Departamento;
import com.example.registrodejornada.repo.DepartamentoRepository;
import com.example.registrodejornada.service.DepartamentoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping ("api/v1/departamentos")
public class DepartamentoController {

    private final DepartamentoService departamentoService;
    private final DepartamentoRepository departamentoRepository;

    @GetMapping
    public ResponseEntity<List<DepartamentoListDTO>> getAllDepartments() {
        return ResponseEntity.ok(
                departamentoService.getAllDepartments().stream()
                        .map(DepartamentoListDTO::of)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoResponseDTO> getDepartmentById(@PathVariable @Min(value = 1) Long id){

        return ResponseEntity.ok(DepartamentoResponseDTO.of(departamentoService.getDepartmentById(id)));

    }
    @PostMapping
    public ResponseEntity <DepartamentoResponseDTO> addDepartment (
            @Valid @RequestBody DepartamentoRequestDTO departamento){
        return ResponseEntity.status(201)
                .body(DepartamentoResponseDTO.of(departamentoService.addDepartment(departamento)));
    }

    @PutMapping ("/{id}")
    public ResponseEntity <DepartamentoResponseDTO> updateDepartment (
            @PathVariable Long id, @RequestBody DepartamentoRequestDTO departamento){
            return ResponseEntity.ok(
                    DepartamentoResponseDTO.of(departamentoService.updateDepartament(id, departamento))
            );
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity <Void> deleteDepartment (@PathVariable Long id) {
        departamentoService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
