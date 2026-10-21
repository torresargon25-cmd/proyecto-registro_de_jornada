package com.example.registrodejornada.repo;

import com.example.registrodejornada.model.Fichaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FichajeRepository extends JpaRepository <Fichaje, Long> {
    Optional<Fichaje> findFirstByEmpleadoIdOrderByMomentoDesc(Long empleadoId);

    }


