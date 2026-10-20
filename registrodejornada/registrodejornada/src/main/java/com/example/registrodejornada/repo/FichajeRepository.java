package com.example.registrodejornada.repo;

import com.example.registrodejornada.model.Fichaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FichajeRepository extends JpaRepository <Fichaje, Long> {
    
}
