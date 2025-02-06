package main.java.es.ies.puerto.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Persona {
    String nombre;
    String identificador;
    String fechaNacimiento;

    public Persona() {
    }

    public Persona(String identificador) {
        this.identificador = identificador;
    }

    public Persona(String identificador, String nombre, String fechaNacimiento) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Getters
     * 
     * @return
     */

    public String getNombre() {
        return this.nombre;
    }

    public String getIdentificador() {
        return this.identificador;
    }

    public String getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Persona)) {
            return false;
        }
        Persona persona = (Persona) o;
        return Objects.equals(identificador, persona.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return getNombre() + ", " +
                getIdentificador() + ", " +
                getFechaNacimiento();
    }

    public int getEdad() {
        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaActual = LocalDate.now();
            LocalDate fechaNacimientoParse = LocalDate.parse(fechaNacimiento, formato);
            return Period.between(fechaActual, fechaNacimientoParse).getYears();
        } catch (Exception e) {
            throw new IllegalArgumentException("El formato de la fecha es incorrecto");
        }
    }

    public LocalDate getFechaNacimientoDate() {
        String fecha = getFechaNacimiento();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNacimiento = LocalDate.parse(fecha, formato);
        return fechaNacimiento;
    }

}
