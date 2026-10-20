package com.example.registrodejornada.repo;

import com.example.registrodejornada.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository <Empleado, Long> {
}
