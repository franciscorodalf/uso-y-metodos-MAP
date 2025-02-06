package main.java.es.ies.puerto.model;

public class Empleado extends Persona {
    private String puesto;
    private Double salario;

    public Empleado() {
    }

    public Empleado(String identificador, String nombre, String puesto, Double salario, String fechaNacimiento) {
        super(identificador, nombre, fechaNacimiento);
        this.puesto = puesto;
        this.salario = salario;
    }

    /**
     * Getters
     */
    public String getPuesto() {
        return this.puesto;
    }

    public Double getSalario() {
        return this.salario;
    }

    @Override
    public String toString() {
        return getNombre() + "," + getIdentificador() + ", " + getPuesto() + ", " + getSalario() + ", " +
                getFechaNacimiento();
    }

}
