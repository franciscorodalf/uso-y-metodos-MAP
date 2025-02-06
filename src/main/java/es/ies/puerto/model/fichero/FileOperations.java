package main.java.es.ies.puerto.model.fichero;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import main.java.es.ies.puerto.model.Empleado;
import main.java.es.ies.puerto.model.Operations;

public class FileOperations extends MapOperation implements Operations {

    File fichero;
    String nombreFichero = "empleados.txt";
    String path = "src/main/resources/empleados.txt";

    public FileOperations() {
        // fichero = new File(path);

        try {
            URL source = getClass().getClassLoader().getResource(nombreFichero);
            fichero = new File(source.toURI());
            if (!fichero.exists() || !fichero.isFile()) {
                throw new IllegalArgumentException("El recurso no es de tipo fichero " + path);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean create(Empleado empleado) {
        if (empleado == null || empleado.getIdentificador() == null || empleado.getIdentificador().isEmpty()) {
            return false;
        }
        Map<String, Empleado> empleados = readFile(fichero);
        if (empleados.containsValue(empleado)) {
            return false;
        }
        empleados.putIfAbsent(empleado.getIdentificador(), empleado);
        return updateFile(empleados, fichero);
    }

    @Override
    public Empleado read(String identificador) {
        if (identificador == null || identificador.isEmpty()) {
            return null;
        }
        Map<String, Empleado> empleados = readFile(fichero);
        return empleados.get(identificador);
    }

    @Override
    public Empleado read(Empleado empleado) {
        if (empleado == null || empleado.getIdentificador() == null || empleado.getIdentificador().isEmpty()) {
            return null;
        }
        return read(empleado.getIdentificador());
    }

    @Override
    public boolean update(Empleado empleado) {
        if (empleado == null || empleado.getIdentificador() == null) {
            return false;
        }

        Map<String, Empleado> empleados = readFile(fichero);

        if (!empleados.containsKey(empleado.getIdentificador())) {
            return false;
        }
        if (empleados.replace(empleado.getIdentificador(), empleado) != null) {
            return updateFile(empleados, fichero);
        }
        return updateFile(empleados, fichero);
    }

    @Override
    public boolean delete(String identificador) {
        if (identificador == null || identificador.isEmpty()) {
            return false;
        }

        Map<String, Empleado> empleados = readFile(fichero);

        if (!empleados.containsKey(identificador)) {
            return false;
        }

        empleados.remove(identificador);

        return updateFile(empleados, fichero);
    }

    @Override
    public Map<String, Empleado> empleadosPorPuesto(String puesto) {
        if (puesto == null) {
            return null;
        }

        Map<String, Empleado> empleados = new TreeMap<>();

        Iterator<Empleado> iterator = empleados.values().iterator();
        while (iterator.hasNext()) {
            Empleado empleadoBuscado = iterator.next();
            if (!empleadoBuscado.getPuesto().equals(puesto)) {
                iterator.remove();
            }
        }

        return empleados;

    }

    @Override
    public Map<String, Empleado> empleadosPorEdad(String fechaInicio, String fechaFin) {
        String fechaInicial = fechaInicio;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate inicio = LocalDate.parse(fechaInicial, formato);
        String fechaFinal = fechaFin;
        LocalDate fin = LocalDate.parse(fechaFinal, formato);
        Map<String, Empleado> empleados = readFile(fichero);
        if (empleados == null) {
            return null;
        }

        Iterator<Empleado> iterator = empleados.values().iterator();
        while (iterator.hasNext()) {
            Empleado empleadoBuscado = iterator.next();
            LocalDate fechaBuscada = empleadoBuscado.getFechaNacimientoDate();
            if (fechaBuscada.isAfter(inicio) || fechaBuscada.isBefore(fin)) {
                iterator.remove();
            }
        }
        return empleados;
    }
}
