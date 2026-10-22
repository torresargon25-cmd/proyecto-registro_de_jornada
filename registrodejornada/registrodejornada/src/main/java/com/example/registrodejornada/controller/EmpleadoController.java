package com.example.registrodejornada.controller;

import com.example.registrodejornada.dto.EmpleadoDTO.EmpleadoListDTO;
import com.example.registrodejornada.dto.EmpleadoDTO.EmpleadoRequestDTO;
import com.example.registrodejornada.dto.EmpleadoDTO.EmpleadoResponseDTO;
import com.example.registrodejornada.service.EmpleadoService;
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
@RequestMapping("/api/v1/empleados")
@Validated
@Tag(name = "Empleados", description = "Gestión de los empleados y su asignación a departamentos")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @Operation(
            summary = "Obtener todos los empleados",
            description = "Devuelve el listado de empleados con el nombre de su departamento"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de empleados",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EmpleadoListDTO.class)),
                            examples = @ExampleObject(
                                    name = "Listado de empleados",
                                    summary = "Ejemplo con dos empleados",
                                    value = """
                                            [
                                              {
                                                "id": 1,
                                                "nombreCompleto": "Ana López",
                                                "cargo": "Desarrolladora",
                                                "nombreDepartamento": "Informática"
                                              },
                                              {
                                                "id": 2,
                                                "nombreCompleto": "Luis Pérez",
                                                "cargo": "Técnico",
                                                "nombreDepartamento": null
                                              }
                                            ]
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No hay empleados",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 404",
                                    summary = "No existe ningún empleado",
                                    value = """
                                            {
                                              "detail": "Empleado no encontrado",
                                              "instance": "/api/v1/empleados",
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
    public ResponseEntity<List<EmpleadoListDTO>> getAllEmployees() {
        return ResponseEntity.ok(
                empleadoService.getAllEmployees().stream()
                        .map(EmpleadoListDTO::of)
                        .toList()
        );
    }

    @Operation(
            summary = "Obtener un empleado por su id",
            description = "Devuelve el detalle de un empleado, con su salario y el nombre de su departamento"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Empleado encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmpleadoResponseDTO.class),
                            examples = @ExampleObject(
                                    name = "Detalle de empleado",
                                    summary = "Empleado con departamento",
                                    value = """
                                            {
                                              "id": 1,
                                              "nombreCompleto": "Ana López",
                                              "cargo": "Desarrolladora",
                                              "salario": 2500,
                                              "nombreDepartamento": "Informática"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "El empleado no existe",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 404",
                                    summary = "Empleado no encontrado",
                                    value = """
                                            {
                                              "detail": "Empleado no encontrado con el id: 99",
                                              "instance": "/api/v1/empleados/99",
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
    public ResponseEntity<EmpleadoResponseDTO> getEmployeeById(
            @PathVariable @Min(value = 1) Long id) {
        return ResponseEntity.ok(
                EmpleadoResponseDTO.of(empleadoService.getEmployeeById(id)));
    }

    @Operation(
            summary = "Crear un empleado",
            description = "Crea un empleado. El departamento es opcional, pero si se indica debe existir y la suma de salarios del departamento no puede superar su presupuesto"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Empleado creado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmpleadoResponseDTO.class),
                            examples = @ExampleObject(
                                    name = "Empleado creado",
                                    summary = "Respuesta tras crear un empleado",
                                    value = """
                                            {
                                              "id": 3,
                                              "nombreCompleto": "Marta Ruiz",
                                              "cargo": "Responsable",
                                              "salario": 2200,
                                              "nombreDepartamento": "Recursos Humanos"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos no válidos o presupuesto del departamento superado",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 400",
                                    summary = "El salario haría superar el presupuesto",
                                    value = """
                                            {
                                              "detail": "Error de validación",
                                              "instance": "/api/v1/empleados",
                                              "status": 400,
                                              "title": "Bad Request",
                                              "invalid-params": [
                                                {
                                                  "object": "empleadoRequestDTO",
                                                  "message": "La suma de salarios del departamento superaría su presupuesto"
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "El departamento indicado no existe",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 404",
                                    summary = "Departamento no encontrado",
                                    value = """
                                            {
                                              "detail": "Departamento no encontrado con el id 99",
                                              "instance": "/api/v1/empleados",
                                              "status": 404,
                                              "title": "Recurso no encontrado",
                                              "type": "https://example.com/errors/not-found"
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<EmpleadoResponseDTO> addEmployee(
            @Valid @RequestBody EmpleadoRequestDTO dto) {
        return ResponseEntity.status(201)
                .body(EmpleadoResponseDTO.of(empleadoService.addEmployee(dto)));
    }

    @Operation(
            summary = "Editar un empleado",
            description = "Modifica los datos de un empleado existente, incluido su departamento"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Empleado modificado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmpleadoResponseDTO.class),
                            examples = @ExampleObject(
                                    name = "Empleado modificado",
                                    summary = "Respuesta tras editar",
                                    value = """
                                            {
                                              "id": 1,
                                              "nombreCompleto": "Ana López Ruiz",
                                              "cargo": "Jefa de equipo",
                                              "salario": 2800,
                                              "nombreDepartamento": "Informática"
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
                                    summary = "Cargo vacío",
                                    value = """
                                            {
                                              "detail": "Error de validación",
                                              "instance": "/api/v1/empleados/1",
                                              "status": 400,
                                              "title": "Bad Request",
                                              "invalid-params": [
                                                {
                                                  "object": "empleadoRequestDTO",
                                                  "message": "El cargo es obligatorio",
                                                  "field": "cargo",
                                                  "rejectedValue": ""
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "El empleado o el departamento no existen",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 404",
                                    summary = "Empleado no encontrado",
                                    value = """
                                            {
                                              "detail": "Empleado no encontrado con el id: 99",
                                              "instance": "/api/v1/empleados/99",
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
    public ResponseEntity<EmpleadoResponseDTO> updateEmployee(
            @PathVariable Long id, @Valid @RequestBody EmpleadoRequestDTO dto) {
        return ResponseEntity.ok(
                EmpleadoResponseDTO.of(empleadoService.updateEmployee(id, dto)));
    }

    @Operation(
            summary = "Borrar un empleado",
            description = "Elimina un empleado. Si el id no existe, no hace nada"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Empleado borrado, sin contenido"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        empleadoService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Asignar un empleado a un departamento",
            description = "Asigna (o reasigna) un empleado a un departamento existente. No lleva body. Falla si la suma de salarios del departamento superaría su presupuesto"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Empleado asignado al departamento",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmpleadoResponseDTO.class),
                            examples = @ExampleObject(
                                    name = "Empleado asignado",
                                    summary = "Empleado con su nuevo departamento",
                                    value = """
                                            {
                                              "id": 2,
                                              "nombreCompleto": "Luis Pérez",
                                              "cargo": "Técnico",
                                              "salario": 1800,
                                              "nombreDepartamento": "Recursos Humanos"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Se superaría el presupuesto del departamento",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 400",
                                    summary = "Presupuesto excedido",
                                    value = """
                                            {
                                              "detail": "No se puede asignar a Luis Pérez al departamento Informática: la suma de salarios superaría su presupuesto de 3000.00",
                                              "instance": "/api/v1/empleados/2/departamento/1",
                                              "status": 400,
                                              "title": "Presupuesto excedido",
                                              "type": "https://example.com/errors/budget-exceeded"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "El empleado o el departamento no existen",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 404",
                                    summary = "Departamento no encontrado",
                                    value = """
                                            {
                                              "detail": "Departamento no encontrado con el id 99",
                                              "instance": "/api/v1/empleados/2/departamento/99",
                                              "status": 404,
                                              "title": "Recurso no encontrado",
                                              "type": "https://example.com/errors/not-found"
                                            }
                                            """
                            )
                    )
            )
    })
    @PutMapping("/{empleadoId}/departamento/{deptoId}")
    public ResponseEntity<EmpleadoResponseDTO> assignDepartment(
            @PathVariable Long empleadoId, @PathVariable Long deptoId) {
        return ResponseEntity.ok(
                EmpleadoResponseDTO.of(empleadoService.assignDepartment(empleadoId, deptoId)));
    }
}
