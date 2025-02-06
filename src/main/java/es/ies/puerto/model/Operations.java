package main.java.es.ies.puerto.model;

import java.util.Map;

public interface Operations {
    boolean create(Empleado empleado);

    Empleado read(String identificador);

    Empleado read(Empleado empleado);

    boolean update(Empleado empleado);

    boolean delete(String identificador);

    Map<String,Empleado> empleadosPorPuesto(String puesto);

    Map<String,Empleado> empleadosPorEdad(String fechaInicio, String fechaFin);
}
