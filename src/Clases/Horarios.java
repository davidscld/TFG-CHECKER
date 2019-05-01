/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Date;
import java.sql.Time;

/**
 *  Objeto usado para almacenar los datos de los horarios de cada trabajador
 * @author dvdsa
 */
public class Horarios {
    private String horaInicio, horaFin;
    private String fecha;

    public Horarios(String horaInicio, String horaFin, String fecha) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.fecha = fecha;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public String getFecha() {
        return fecha;
    }
    
    
    
}
