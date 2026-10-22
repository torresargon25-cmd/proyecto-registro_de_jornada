package com.example.registrodejornada.controller;

import com.example.registrodejornada.dto.FichajeDTO.FichajeRequestDTO;
import com.example.registrodejornada.dto.FichajeDTO.FichajeResponseDTO;
import com.example.registrodejornada.enums.TipoFichaje;
import com.example.registrodejornada.service.FichajeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/empleados/{empleadoId}")
@Tag(name = "Fichajes", description = "Registro de entradas y salidas de los empleados")
public class FichajeController {

    private final FichajeService fichajeService;

    @Operation(
            summary = "Obtener el historial de fichajes de un empleado",
            description = "Devuelve todos los fichajes del empleado, del más reciente al más antiguo. Si no ha fichado nunca, devuelve una lista vacía"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Historial de fichajes",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = FichajeResponseDTO.class)),
                            examples = @ExampleObject(
                                    name = "Historial de fichajes",
                                    summary = "Una entrada y una salida",
                                    value = """
                                            [
                                              {
                                                "id": 2,
                                                "momento": "2026-10-22T15:00:00",
                                                "tipo": "SALIDA",
                                                "nombreEmpleado": "Ana López"
                                              },
                                              {
                                                "id": 1,
                                                "momento": "2026-10-22T08:00:00",
                                                "tipo": "ENTRADA",
                                                "nombreEmpleado": "Ana López"
                                              }
                                            ]
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
                                              "instance": "/api/v1/empleados/99/fichajes",
                                              "status": 404,
                                              "title": "Recurso no encontrado",
                                              "type": "https://example.com/errors/not-found"
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping("/fichajes")
    public ResponseEntity<List<FichajeResponseDTO>> listadoFichajes(@PathVariable Long empleadoId) {
        return ResponseEntity.ok(
                fichajeService.getFichajes(empleadoId).stream()
                        .map(FichajeResponseDTO::of)
                        .toList()
        );
    }

    @Operation(
            summary = "Registrar una entrada o una salida",
            description = "Sin body, alterna automáticamente entre ENTRADA y SALIDA (el primer fichaje es siempre ENTRADA). Con body {\"tipo\": \"ENTRADA\"}, solo se registra si es el tipo que toca; si no, responde 409"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Fichaje registrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FichajeResponseDTO.class),
                            examples = @ExampleObject(
                                    name = "Fichaje registrado",
                                    summary = "Primera entrada de un empleado",
                                    value = """
                                            {
                                              "id": 1,
                                              "momento": "2026-10-22T08:00:00",
                                              "tipo": "ENTRADA",
                                              "nombreEmpleado": "Ana López"
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
                                              "instance": "/api/v1/empleados/99/fichar",
                                              "status": 404,
                                              "title": "Recurso no encontrado",
                                              "type": "https://example.com/errors/not-found"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "El tipo pedido no es el que corresponde",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 409",
                                    summary = "ENTRADA repetida",
                                    value = """
                                            {
                                              "detail": "El último fichaje del empleado ya fue ENTRADA. Toca registrar SALIDA",
                                              "instance": "/api/v1/empleados/1/fichar",
                                              "status": 409,
                                              "title": "Fichaje duplicado",
                                              "type": "https://example.com/errors/conflict"
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping("/fichar")
    public ResponseEntity<FichajeResponseDTO> fichar(
            @PathVariable Long empleadoId,
            @RequestBody(required = false) FichajeRequestDTO dto) {

        TipoFichaje tipo = dto == null ? null : dto.tipo();
        return ResponseEntity.status(201)
                .body(FichajeResponseDTO.of(fichajeService.fichar(empleadoId, tipo)));
    }
}
