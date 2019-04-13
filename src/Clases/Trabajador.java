/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author dvdsa
 */
public class Trabajador {
    private String nombre, apellidos;
    private int numeroEmpleado;

    public Trabajador(String nombre, String apellidos, int numeroEmpleado) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public int getNumeroEmpleado() {
        return numeroEmpleado;
    }
    
    
}
