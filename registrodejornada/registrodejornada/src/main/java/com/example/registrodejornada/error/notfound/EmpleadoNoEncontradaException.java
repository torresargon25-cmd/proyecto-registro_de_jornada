package com.example.registrodejornada.error.notfound;

import com.example.registrodejornada.error.EntityNotFoundException;

public class EmpleadoNoEncontradaException extends EntityNotFoundException {

    public EmpleadoNoEncontradaException (){
        super("Empleado no encontrado");
    }

    public EmpleadoNoEncontradaException (Long id){
        super("Empleado no encontrado con el id: " + id);
    }

    public EmpleadoNoEncontradaException (String message){
        super(message);
    }
}
