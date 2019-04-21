/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import com.itextpdf.text.BadElementException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.Blob;

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
            System.out.println(fecha);
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

    private void cargarArrayListNumerosTrabajadores() {

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

    public boolean comprobarSiExisteCodEmpleado(int codigoComprobar) {
        if (listadoNumerosTrabajadores == null) {
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

                sentenciaSQL = "UPDATE " + NOMBRE_TABLA_HORARIOS + " SET realizado =  1 WHERE numeroEmpleado = " + codEmpFirma + " AND fecha = DATE(NOW());";

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
        } else {
            return RESULTADO_KO;
        }

    }

    public void crearPDFs() throws FileNotFoundException, DocumentException, BadElementException, IOException {
        String horaInicio, horaFin, fecha;
        Image imagenFirma;
        comprobarSiExisteCodEmpleado(0);
        Collections.sort(listadoNumerosTrabajadores);

        for (int i = 0; i < listadoNumerosTrabajadores.size(); i++) {
            Document pdf = new Document();
            FileOutputStream ficheroPdf = new FileOutputStream("C:/Users/dvdsa/Desktop/PDFGenerados/firmas" + listadoNumerosTrabajadores.get(i) + ".pdf");
            PdfWriter.getInstance(pdf, ficheroPdf).setInitialLeading(20);
            pdf.open();

            imagenFirma = conseguirImagenFirma(listadoNumerosTrabajadores.get(i));
            imagenFirma.scaleToFit(50, 50);
            
            try {
                conexion = DriverManager.getConnection(url, usuario, contrasenia);  //SIEMPRE IGUAL
                st = conexion.createStatement();    //SIEMPRE IGUAL

                //Cambiamos la sentenncia que vamos a ejecutar, este caso sera un UPDATE            
                sentenciaSQL = "SELECT fecha, horaInicio, horaFin FROM " + NOMBRE_TABLA_HORARIOS + " WHERE numeroEmpleado = " + listadoNumerosTrabajadores.get(i) + " AND realizado IS NOT NULL;";
                resultadoSelect = st.executeQuery(sentenciaSQL);
                while (resultadoSelect.next()) {
                    horaInicio = resultadoSelect.getString(2);
                    fecha = resultadoSelect.getString(1);
                    horaFin = resultadoSelect.getString(3);
                    pdf.add(imagenFirma);
                    pdf.add(new Paragraph("Fecha "+fecha+ " Hora inicio "+horaInicio+ " Hora fin "+horaFin));
                }//while "hay mas datos"           
                st.close(); //cerramos
                conexion.close();   //cerramos

            } catch (SQLException ex) {
                Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public ArrayList<Trabajador> rellenarListadoTrabajadores() {
        ArrayList<Trabajador> arrayListDatosTrabajadores = new ArrayList<>();
        try {
            conexion = DriverManager.getConnection(url, usuario, contrasenia);  //SIEMPRE IGUAL
            st = conexion.createStatement();    //SIEMPRE IGUAL

            //Cambiamos la sentenncia que vamos a ejecutar, este caso sera un UPDATE            
            sentenciaSQL = "SELECT nombre, apellidos, numeroEmpleado FROM " + NOMBRE_TABLA_TRBAJADORES + ";";
            resultadoSelect = st.executeQuery(sentenciaSQL);
            while (resultadoSelect.next()) {
                Trabajador t = new Trabajador(resultadoSelect.getString(1), resultadoSelect.getString(2), Integer.parseInt(resultadoSelect.getString(3)));
                arrayListDatosTrabajadores.add(t);

            }//while "hay mas datos"            
            st.close(); //cerramos
            conexion.close();   //cerramos

        } catch (SQLException ex) {
            Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arrayListDatosTrabajadores;
    }

    public ArrayList<Horarios> rellenarListadoHorariosTrabajador(int codigoUsuarioBuscarHorarios) {
        ArrayList<Horarios> arrayListHorariosTrabajador = new ArrayList<>();
        Horarios horario;

        try {
            conexion = DriverManager.getConnection(url, usuario, contrasenia);  //SIEMPRE IGUAL
            st = conexion.createStatement();    //SIEMPRE IGUAL

            //Cambiamos la sentenncia que vamos a ejecutar, este caso sera un UPDATE            
            sentenciaSQL = "SELECT fecha, horaInicio, horaFin FROM " + NOMBRE_TABLA_HORARIOS + " WHERE numeroEmpleado = " + codigoUsuarioBuscarHorarios + " AND realizado IS NULL;";
            resultadoSelect = st.executeQuery(sentenciaSQL);
            while (resultadoSelect.next()) {
                horario = new Horarios(resultadoSelect.getString(2), resultadoSelect.getString(3), resultadoSelect.getString(1));
                arrayListHorariosTrabajador.add(horario);

            }//while "hay mas datos"            
            st.close(); //cerramos
            conexion.close();   //cerramos

        } catch (SQLException ex) {
            Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arrayListHorariosTrabajador;

    }

    private Image conseguirImagenFirma(Integer codEmpleado) throws BadElementException, IOException {
        byte[] bytesFirma = null;
        Image firma;
        try {
            conexion = DriverManager.getConnection(url, usuario, contrasenia);  //SIEMPRE IGUAL
            st = conexion.createStatement();    //SIEMPRE IGUAL

            //Cambiamos la sentenncia que vamos a ejecutar, este caso sera un UPDATE            
            sentenciaSQL = "SELECT firma FROM " + NOMBRE_TABLA_TRBAJADORES + " WHERE numeroEmpleado = " + codEmpleado + ";";
            resultadoSelect = st.executeQuery(sentenciaSQL);
            
            //bytesFirma
                    Blob c = resultadoSelect.getBlob(1);//.getBytes(1, (int) resultadoSelect.getBlob(1).length());
                    System.out.println(c);
            st.close(); //cerramos
            conexion.close();   //cerramos

        } catch (SQLException ex) {
            Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        firma = Image.getInstance(bytesFirma);
        System.out.println(firma);
        return firma;
    }
}
