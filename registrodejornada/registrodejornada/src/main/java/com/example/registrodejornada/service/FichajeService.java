package com.example.registrodejornada.service;

import com.example.registrodejornada.enums.TipoFichaje;
import com.example.registrodejornada.error.conflict.FichajeDuplicadoException;
import com.example.registrodejornada.model.Empleado;
import com.example.registrodejornada.model.Fichaje;
import com.example.registrodejornada.repo.FichajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FichajeService {

    private final FichajeRepository fichajeRepository;
    private final EmpleadoService empleadoService;

    public Fichaje fichar(Long empleadoId, TipoFichaje tipoSolicitado) {

        Empleado empleado = empleadoService.getEmployeeById(empleadoId);

        Optional<Fichaje> ultimo =
                fichajeRepository.findFirstByEmpleadoIdOrderByMomentoDesc(empleadoId);


        TipoFichaje tipoQueToca = ultimo
                .map(u -> u.getTipo() == TipoFichaje.ENTRADA
                        ? TipoFichaje.SALIDA
                        : TipoFichaje.ENTRADA)
                .orElse(TipoFichaje.ENTRADA);


        if (tipoSolicitado != null && tipoSolicitado != tipoQueToca) {
            String motivo = ultimo.isPresent()
                    ? "El último fichaje del empleado ya fue " + tipoSolicitado
                    + ". Toca registrar " + tipoQueToca
                    : "El empleado no tiene fichajes: el primero debe ser ENTRADA";
            throw new FichajeDuplicadoException(motivo);
        }

        Fichaje fichaje = Fichaje.builder()
                .empleado(empleado)
                .tipo(tipoQueToca)
                .momento(LocalDateTime.now())
                .build();

        return fichajeRepository.save(fichaje);
    }
}
