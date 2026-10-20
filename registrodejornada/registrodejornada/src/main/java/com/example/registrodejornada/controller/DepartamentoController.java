package com.example.registrodejornada.controller;

import com.example.registrodejornada.dto.DepartamentoResponseDTO;
import com.example.registrodejornada.model.Departamento;
import com.example.registrodejornada.model.Empleado;
import com.example.registrodejornada.service.DepartamentoService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("api/v1/departamentos")
@RequiredArgsConstructor
public class DepartamentoController {

    private final DepartamentoService departamentoService;


    @GetMapping
    public ResponseEntity<List<Departamento>> getAllDepartments (){
        return ResponseEntity.ok(departamentoService.getAllDepartments());
    }

    @GetMapping ("/{id}")
    public ResponseEntity <DepartamentoResponseDTO>(@PathVariable @Min(value = 1))

}
