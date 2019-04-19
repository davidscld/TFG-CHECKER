/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dvdsa
 */
public class GestorBD {

    private Connection conexion = null;
    private String nombreBD = "residencia_villa_ledesma";
    private String origenDatos = "//localhost:3306/" + nombreBD;
    private String usuario = "root";
    private String contrasenia = "";
    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql:" + origenDatos;
    private Statement st;
    private ResultSet resultadoSelect;
    private String sentenciaSQL;
    private final String NOMBRE_TABLA_TRBAJADORES = "trabajadores", NOMBRE_TABLA_HORARIOS = "horarios";
    private final boolean RESULTADO_OK = true, RESULTADO_KO = false;
    private int resultadoConsultaSQL;
    private static ArrayList<Integer> listadoNumerosTrabajadores = null;

    public GestorBD() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean eliminarHorarioTrabajador(int codigoTrabajador, Date fecha) {
        try {
            conexion = DriverManager.getConnection(url, usuario, contrasenia);
            st = conexion.createStatement();

            sentenciaSQL = "DELETE FROM " + NOMBRE_TABLA_HORARIOS + " WHERE numeroEmpleado = " + codigoTrabajador + " AND fecha = '" + fecha + "';";

            resultadoConsultaSQL = st.executeUpdate(sentenciaSQL);

            st.close();
            conexion.close();

        } catch (SQLException ex) {
            Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (resultadoConsultaSQL == 1) {
            return RESULTADO_OK;
        } else {
            return RESULTADO_KO;

        }
    }

    public boolean nuevoHorarioTrabajador(int codigoTrabajador, Time horaInicio, Time horaFin, Date fecha) {
        if (comprobarSiExisteCodEmpleado(codigoTrabajador)) {

            try {
                conexion = DriverManager.getConnection(url, usuario, contrasenia);
                st = conexion.createStatement();

                sentenciaSQL = "INSERT INTO " + NOMBRE_TABLA_HORARIOS + "(fecha, horaInicio, horaFin, numeroEmpleado) VALUES ('"
                        + fecha + "', '" + horaInicio + "', '" + horaFin + "', '" + codigoTrabajador + "');";

                resultadoConsultaSQL = st.executeUpdate(sentenciaSQL);

                st.close();
                conexion.close();

            } catch (SQLException ex) {
                Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (resultadoConsultaSQL == 1) {
                return RESULTADO_OK;
            } else {
                return RESULTADO_KO;

            }

        }
        return RESULTADO_KO;
    }

    public boolean darAltaTrabajador(String nombre, String apellido, FileInputStream imagenFirma) throws SQLException {
        int codigoEmpleado;
        do {
            codigoEmpleado = (int) (Math.random() * 1000);

        } while (comprobarSiExisteCodEmpleado(codigoEmpleado));
        listadoNumerosTrabajadores.add(codigoEmpleado);
        try {
            conexion = DriverManager.getConnection(url, usuario, contrasenia);
            st = conexion.createStatement();

            sentenciaSQL = "INSERT INTO " + NOMBRE_TABLA_TRBAJADORES + "(nombre, apellidos, numeroEmpleado, firma) VALUES ('"
                    + nombre + "', '" + apellido + "', '" + codigoEmpleado + "', '" + imagenFirma + "');";

            resultadoConsultaSQL = st.executeUpdate(sentenciaSQL);

            st.close();
            conexion.close();

        } catch (SQLException ex) {
            Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (resultadoConsultaSQL == 1) {
            return RESULTADO_OK;
        } else {
            return RESULTADO_KO;

        }
    }

    public boolean darBajaTrabajador(int codigoTrabajador) {
        try {
            conexion = DriverManager.getConnection(url, usuario, contrasenia);
            st = conexion.createStatement();

            sentenciaSQL = "DELETE FROM " + NOMBRE_TABLA_TRBAJADORES + " WHERE numeroEmpleado = " + codigoTrabajador + ";";

            resultadoConsultaSQL = st.executeUpdate(sentenciaSQL);

            st.close();
            conexion.close();

        } catch (SQLException ex) {
            Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (resultadoConsultaSQL == 1) {
            return RESULTADO_OK;
        } else {
            return RESULTADO_KO;

        }
    }

    public ArrayList<Trabajador> listadoDeTrabajadores() {

        return null;
    }

    public void cargarArrayListNumerosTrabajadores() {

        try {
            conexion = DriverManager.getConnection(url, usuario, contrasenia);  //SIEMPRE IGUAL
            st = conexion.createStatement();    //SIEMPRE IGUAL

            //Cambiamos la sentenncia que vamos a ejecutar, este caso sera un UPDATE            
            sentenciaSQL = "SELECT numeroEmpleado FROM " + NOMBRE_TABLA_TRBAJADORES + ";";
            resultadoSelect = st.executeQuery(sentenciaSQL);
            while (resultadoSelect.next()) {
                listadoNumerosTrabajadores.add(Integer.parseInt(resultadoSelect.getString(1)));
            }//while "hay mas datos"           
            st.close(); //cerramos
            conexion.close();   //cerramos

        } catch (SQLException ex) {
            Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean comprobarSiExisteCodEmpleado(int codigoComprobar) {
        if (listadoDeTrabajadores() == null) {
            listadoNumerosTrabajadores = new ArrayList<>();
            cargarArrayListNumerosTrabajadores();
        }

        for (Integer it : listadoNumerosTrabajadores) {
            if (it == codigoComprobar) {
                return true;
            }
        }
        return false;
    }

    public boolean realizarFirma(int codEmpFirma) {
        if (comprobarSiExisteCodEmpleado(codEmpFirma)) {

            try {
                conexion = DriverManager.getConnection(url, usuario, contrasenia);
                st = conexion.createStatement();

                sentenciaSQL = "UPDATE " + NOMBRE_TABLA_HORARIOS + " SET realizado =  1 WHERE numeroEmpleado = " + codEmpFirma + ";";

                resultadoConsultaSQL = st.executeUpdate(sentenciaSQL);

                st.close();
                conexion.close();

            } catch (SQLException ex) {
                Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (resultadoConsultaSQL == 1) {
                return true;
            } else {
                return false;

            }
        } else {
            return false;
        }

    }

}
