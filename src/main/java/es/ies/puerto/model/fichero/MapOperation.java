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

abstract public class MapOperation  {

    //File fichero;
    //String path = "C:\\Users\\Francisco\\Documents\\GitHub\\uso-y-metodos-java\\src\\main\\resources\\empleados.txt";

    protected Map<String, Empleado> readFile(File file) {
        Map<String, Empleado> empleados = new TreeMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(", ");
                if(datos.length == 5) {
                    Empleado empleado = new Empleado(datos[0], datos[1], datos[2], Double.parseDouble(datos[3]), datos[4]);
                    empleados.putIfAbsent(empleado.getIdentificador(), empleado);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return empleados;
    }


    protected boolean updateFile(Map<String, Empleado> empleados, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Empleado empleado : empleados.values()) {
                writer.write(empleado.toString());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error al actualizar el fichero: " + e.getMessage());
        }
        return false;
    }

}
