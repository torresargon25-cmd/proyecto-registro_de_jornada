package com.example.registrodejornada.repo;

import com.example.registrodejornada.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository <Empleado, Long> {

    @Query("select sum(e.salario) from Empleado e where e.departamento.id = :departamentoId")
    Optional<BigDecimal> sumaSalarios(@Param("departamentoId") Long departamentoId);

}
