package main.java.es.ies.puerto.model.fichero;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import main.java.es.ies.puerto.model.Empleado;

public class MapOperation  {

    File fichero;
    String path = "C:\\Users\\Francisco\\Documents\\GitHub\\uso-y-metodos-java\\src\\main\\resources\\empleados.txt";

    public boolean updateFile(Map<String,Empleado> empleados, File file) {
        try {
            file.delete();
            file.createNewFile();

        } catch (IOException e) {
            return true;
        }
        for (Empleado empleado : empleados.values()) {
            create(empleado);
        }
        return true;
    }

    public boolean create(Empleado empleado) {
        if (empleado == null || empleado.getIdentificador() == null) {
            return false;
        }
        Map<String,Empleado> empleados = read(fichero);
        if (empleados.containsKey(empleado.getIdentificador())) {
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

    public static Map<String, Empleado> read(File file) {
        Map<String, Empleado> empleados = new TreeMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return empleados;
    }

}
