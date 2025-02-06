package main.java.es.ies.puerto.model.fichero;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import main.java.es.ies.puerto.model.Empleado;
import main.java.es.ies.puerto.model.Operations;

public class FileOperations extends MapOperation implements Operations {

    File fichero;
    String path = "C:\\Users\\Francisco\\Documents\\GitHub\\uso-y-metodos-java\\src\\main\\resources\\empleados.txt";

    public FileOperations() {
        fichero = new File(path);
        if (!fichero.exists() || !fichero.isFile()) {
            throw new IllegalArgumentException("El recurso no es de tipo fichero " + path);
        }
    }

    @Override
    public boolean create(Empleado empleado) {
        if (empleado == null || empleado.getIdentificador() == null) {
            return false;
        }
        Map<String, Empleado> empleados = new TreeMap<>();
        if (empleados.containsValue(empleado)) {
            return false;
        }
        return create(empleado.toString(), fichero);
    }

    public static boolean create(String data, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(data);
            writer.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public Empleado read(String identificador) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fichero))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] arrayLine = line.split(",");
                if (arrayLine[0].equals(identificador)) {
                    return new Empleado(arrayLine[0], arrayLine[1], arrayLine[2],
                            Double.parseDouble(arrayLine[3]), arrayLine[4]);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Empleado read(Empleado empleado) {
        if (empleado == null || empleado.getIdentificador() == null) {
            return null;
        }
        return empleado;
    }

    @Override
    public boolean update(Empleado empleado) {
        if (empleado == null || empleado.getIdentificador() == null) {
            return false;
        }

        Map<String, Empleado> empleados = read(fichero);

        if (!empleados.containsKey(empleado.getIdentificador())) {
            return false;
        }

        empleados.replace(empleado.getIdentificador(), empleado);

        return updateFile(empleados, fichero);
    }

    @Override
    public boolean delete(String identificador) {
        if (identificador == null) {
            return false;
        }

        Map<String, Empleado> empleados = read(fichero);

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
        Map<String, Empleado> empleados = read(fichero);
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
