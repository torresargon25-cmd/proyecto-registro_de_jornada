package com.example.registrodejornada.repo;

import com.example.registrodejornada.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoRepository extends JpaRepository <Departamento, Long> {
}
