package com.example.registrodejornada.service;

import com.example.registrodejornada.dto.EmpleadoDTO.EmpleadoRequestDTO;
import com.example.registrodejornada.error.PresupuestoExcedidoException;
import com.example.registrodejornada.error.notfound.EmpleadoNoEncontradaException;
import com.example.registrodejornada.model.Departamento;
import com.example.registrodejornada.model.Empleado;
import com.example.registrodejornada.repo.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final DepartamentoService departamentoService;

    public List<Empleado> getAllEmployees() {
        List<Empleado> result = empleadoRepository.findAll();
        if (result.isEmpty()) {
            throw new EmpleadoNoEncontradaException();
        }
        return result;
    }

    public Empleado getEmployeeById(Long id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new EmpleadoNoEncontradaException(id));
    }

    public Empleado addEmployee(EmpleadoRequestDTO dto) {
        Empleado e = Empleado.builder()
                .nombreCompleto(dto.nombreCompleto())
                .cargo(dto.cargo())
                .salario(dto.salario())
                .departamento(buscarDepartamento(dto.departamentoId()))
                .build();
        return empleadoRepository.save(e);
    }

    public Empleado updateEmployee(Long id, EmpleadoRequestDTO dto) {
        return empleadoRepository.findById(id)
                .map(e -> {
                    e.setNombreCompleto(dto.nombreCompleto());
                    e.setCargo(dto.cargo());
                    e.setSalario(dto.salario());
                    e.setDepartamento(buscarDepartamento(dto.departamentoId()));
                    return empleadoRepository.save(e);
                })
                .orElseThrow(() -> new EmpleadoNoEncontradaException(id));
    }

    public void deleteEmployee(Long id) {
        empleadoRepository.deleteById(id);
    }

    // Si no viene departamentoId, el empleado queda sin departamento.
    // Si viene y no existe, getDepartmentById lanza el 404.
    private Departamento buscarDepartamento(Long departamentoId) {
        return departamentoId == null ? null
                : departamentoService.getDepartmentById(departamentoId);
    }

    public Empleado assignDepartment(Long empleadoId, Long deptoId) {
        Empleado e = getEmployeeById(empleadoId);
        Departamento d = departamentoService.getDepartmentById(deptoId);

        boolean yaEstaAhi = e.getDepartamento() != null
                && e.getDepartamento().getId().equals(d.getId());

        if (!yaEstaAhi && d.getPresupuesto() != null) {
            BigDecimal suma = empleadoRepository.sumaSalarios(d.getId())
                    .orElse(BigDecimal.ZERO);
            if (suma.add(e.getSalario()).compareTo(d.getPresupuesto()) > 0) {
                throw new PresupuestoExcedidoException(
                        "No se puede asignar a " + e.getNombreCompleto() + " al departamento "
                                + d.getNombre() + ": la suma de salarios superaría su presupuesto de "
                                + d.getPresupuesto());
            }
        }

        e.setDepartamento(d);
        return empleadoRepository.save(e);
    }
}
