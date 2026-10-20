package com.example.registrodejornada.error.notfound;

import com.example.registrodejornada.error.EntityNotFoundException;

public class DepartamentoNoEncontradaException extends EntityNotFoundException {

    public DepartamentoNoEncontradaException(){
        super("Departamento no encontrado");
    }

    public DepartamentoNoEncontradaException(Long id){
        super("Departamento no encontrado con el id " + id);
    }

    public DepartamentoNoEncontradaException(String message){
        super(message);
    }
}
