package selfttest.model;

import java.util.Date;

public class Empleado {
    
    private int id;
    private String nombre;
    private int edad;
    Date fecharegistro;
    
    
    public Empleado() {
        super();
    }


    public Empleado(int id, String nombre, int edad, Date fecharegistro) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.fecharegistro = fecharegistro;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getEdad() {
        return edad;
    }

    public void setFecharegistro(Date fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    public Date getFecharegistro() {
        return fecharegistro;
    }


}
