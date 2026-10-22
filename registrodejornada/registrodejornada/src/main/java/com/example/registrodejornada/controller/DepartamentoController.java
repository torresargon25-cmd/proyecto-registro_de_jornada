package com.example.registrodejornada.controller;

import com.example.registrodejornada.dto.DepartamentoDTO.DepartamentoListDTO;
import com.example.registrodejornada.dto.DepartamentoDTO.DepartamentoRequestDTO;
import com.example.registrodejornada.dto.DepartamentoDTO.DepartamentoResponseDTO;
import com.example.registrodejornada.service.DepartamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/departamentos")
@Validated
@Tag(name = "Departamentos", description = "Gestión de los departamentos de la empresa")
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    @Operation(
            summary = "Obtener todos los departamentos",
            description = "Devuelve el listado de departamentos con su id y su nombre"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de departamentos",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DepartamentoListDTO.class)),
                            examples = @ExampleObject(
                                    name = "Listado de departamentos",
                                    summary = "Ejemplo con dos departamentos",
                                    value = """
                                            [
                                              { "id": 1, "nombre": "Informática" },
                                              { "id": 2, "nombre": "Recursos Humanos" }
                                            ]
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No hay departamentos",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 404",
                                    summary = "No existe ningún departamento",
                                    value = """
                                            {
                                              "detail": "Departamento no encontrado",
                                              "instance": "/api/v1/departamentos",
                                              "status": 404,
                                              "title": "Recurso no encontrado",
                                              "type": "https://example.com/errors/not-found"
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<DepartamentoListDTO>> getAllDepartments() {
        return ResponseEntity.ok(
                departamentoService.getAllDepartments().stream()
                        .map(DepartamentoListDTO::of)
                        .toList()
        );
    }

    @Operation(
            summary = "Obtener un departamento por su id",
            description = "Devuelve el detalle de un departamento, incluida la lista de sus empleados"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Departamento encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DepartamentoResponseDTO.class),
                            examples = @ExampleObject(
                                    name = "Detalle de departamento",
                                    summary = "Departamento con un empleado",
                                    value = """
                                            {
                                              "id": 1,
                                              "nombre": "Informática",
                                              "presupuesto": 10000,
                                              "listaEmpleados": [
                                                {
                                                  "id": 1,
                                                  "nombreCompleto": "Ana López",
                                                  "cargo": "Desarrolladora",
                                                  "nombreDepartamento": "Informática"
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "El departamento no existe",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 404",
                                    summary = "Departamento no encontrado",
                                    value = """
                                            {
                                              "detail": "Departamento no encontrado con el id 99",
                                              "instance": "/api/v1/departamentos/99",
                                              "status": 404,
                                              "title": "Recurso no encontrado",
                                              "type": "https://example.com/errors/not-found"
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoResponseDTO> getDepartmentById(
            @PathVariable @Min(value = 1) Long id) {
        return ResponseEntity.ok(
                DepartamentoResponseDTO.of(departamentoService.getDepartmentById(id)));
    }

    @Operation(
            summary = "Crear un departamento",
            description = "Crea un departamento nuevo. El nombre debe ser único y el presupuesto no puede ser negativo"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Departamento creado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DepartamentoResponseDTO.class),
                            examples = @ExampleObject(
                                    name = "Departamento creado",
                                    summary = "Respuesta tras crear un departamento",
                                    value = """
                                            {
                                              "id": 3,
                                              "nombre": "Marketing",
                                              "presupuesto": 8000,
                                              "listaEmpleados": []
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada no válidos",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 400",
                                    summary = "Nombre vacío",
                                    value = """
                                            {
                                              "detail": "Error de validación",
                                              "instance": "/api/v1/departamentos",
                                              "status": 400,
                                              "title": "Bad Request",
                                              "invalid-params": [
                                                {
                                                  "object": "departamentoRequestDTO",
                                                  "message": "El nombre del departamento es obligatorio",
                                                  "field": "nombre",
                                                  "rejectedValue": ""
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<DepartamentoResponseDTO> addDepartment(
            @Valid @RequestBody DepartamentoRequestDTO departamento) {
        return ResponseEntity.status(201)
                .body(DepartamentoResponseDTO.of(departamentoService.addDepartment(departamento)));
    }

    @Operation(
            summary = "Editar un departamento",
            description = "Modifica el nombre y el presupuesto de un departamento existente"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Departamento modificado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DepartamentoResponseDTO.class),
                            examples = @ExampleObject(
                                    name = "Departamento modificado",
                                    summary = "Respuesta tras editar",
                                    value = """
                                            {
                                              "id": 1,
                                              "nombre": "Informática y Sistemas",
                                              "presupuesto": 12000,
                                              "listaEmpleados": []
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada no válidos",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 400",
                                    summary = "Presupuesto ausente",
                                    value = """
                                            {
                                              "detail": "Error de validación",
                                              "instance": "/api/v1/departamentos/1",
                                              "status": 400,
                                              "title": "Bad Request",
                                              "invalid-params": [
                                                {
                                                  "object": "departamentoRequestDTO",
                                                  "message": "El presupuesto es obligatorio",
                                                  "field": "presupuesto"
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "El departamento no existe",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 404",
                                    summary = "Departamento no encontrado",
                                    value = """
                                            {
                                              "detail": "Departamento no encontrado con el id 99",
                                              "instance": "/api/v1/departamentos/99",
                                              "status": 404,
                                              "title": "Recurso no encontrado",
                                              "type": "https://example.com/errors/not-found"
                                            }
                                            """
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoResponseDTO> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartamentoRequestDTO departamento) {
        return ResponseEntity.ok(
                DepartamentoResponseDTO.of(departamentoService.updateDepartament(id, departamento)));
    }

    @Operation(
            summary = "Borrar un departamento",
            description = "Elimina un departamento. Si el id no existe, no hace nada"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Departamento borrado, sin contenido"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departamentoService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
