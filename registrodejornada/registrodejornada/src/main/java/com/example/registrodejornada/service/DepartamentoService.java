package com.example.registrodejornada.service;

import com.example.registrodejornada.error.notfound.DepartamentoNoEncontradaException;
import com.example.registrodejornada.model.Departamento;
import com.example.registrodejornada.repo.DepartamentoRepository;
import com.example.registrodejornada.repo.EmpleadoRepository;
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
                .orElseThrow(() -> new DepartamentoNoEncontradaException());
    }

    public Departamento addDepartment (Long id, Departamento departamento){
        return departamentoRepository.save(departamento);
    }

    public Departamento updateDepartament (Long id, Departamento departamento){
        if (!departamentoRepository.existsById(id)){
            throw new DepartamentoNoEncontradaException();
        }
        departamento.setNombre(departamento.getNombre());
        return departamentoRepository.save(departamento);
    }

    public void deleteDepartment(Long id){
        departamentoRepository.deleteById(id);
    }
}
