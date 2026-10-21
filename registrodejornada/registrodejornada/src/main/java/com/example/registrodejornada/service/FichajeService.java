package com.example.registrodejornada.service;

import com.example.registrodejornada.enums.TipoFichaje;
import com.example.registrodejornada.model.Empleado;
import com.example.registrodejornada.model.Fichaje;
import com.example.registrodejornada.repo.FichajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FichajeService {

    private final FichajeRepository fichajeRepository;
    private final EmpleadoService empleadoService;

    public Fichaje fichar(Long empleadoId) {

        Empleado empleado = empleadoService.getEmployeeById(empleadoId);


        TipoFichaje tipo = fichajeRepository
                .findFirstByEmpleadoIdOrderByMomentoDesc(empleadoId)
                .map(ultimo -> ultimo.getTipo() == TipoFichaje.ENTRADA
                        ? TipoFichaje.SALIDA
                        : TipoFichaje.ENTRADA)
                .orElse(TipoFichaje.ENTRADA);

        Fichaje fichaje = Fichaje.builder()
                .empleado(empleado)
                .tipo(tipo)
                .momento(LocalDateTime.now())
                .build();

        return fichajeRepository.save(fichaje);
    }
}
