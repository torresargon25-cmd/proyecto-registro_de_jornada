package com.example.registrodejornada.service;

import com.example.registrodejornada.dto.DepartamentoDTO.DepartamentoRequestDTO;
import com.example.registrodejornada.error.notfound.DepartamentoNoEncontradaException;
import com.example.registrodejornada.model.Departamento;
import com.example.registrodejornada.repo.DepartamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;

    public List<Departamento> getAllDepartments () {
        List<Departamento> result = departamentoRepository.findAll();
        if (result.isEmpty()) {
            throw new DepartamentoNoEncontradaException();
        }
        return result;
    }

    public Departamento getDepartmentById (Long id){
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new DepartamentoNoEncontradaException(id));
    }

    public Departamento addDepartment (DepartamentoRequestDTO departamento){
        return departamentoRepository.save(departamento.to());
    }

    public Departamento updateDepartament (Long id, DepartamentoRequestDTO departamento) {

        return departamentoRepository.findById(id)
                .map(d -> {
                    //falta la validacion
                    d.setNombre(departamento.nombre());
                    d.setPresupuesto(departamento.presupuesto());
                    return departamentoRepository.save((d));
                })
                .orElseThrow(() -> new DepartamentoNoEncontradaException(id));
    }

    public void deleteDepartment(Long id){
        departamentoRepository.deleteById(id);
    }

    }



