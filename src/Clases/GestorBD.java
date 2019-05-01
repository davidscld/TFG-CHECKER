/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
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
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import javax.swing.JFileChooser;

/**
 * Gestiona todas las conexione y consultas a la base de datos.
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
    private ResultSet resultadoSelect = null;
    private String sentenciaSQL;
    private PreparedStatement state;
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

    /**
     * Recibiendo ambos parametros busca aquel empleado que tenga una fecha
     * asignada a la que nosotros le estamos pasando y elimina dicho horario, no
     * tiene en cuenta la hora, pues solo puede haber un horario por dia
     *
     * @param codigoTrabajador es el codigo por el que voy a eliminar el horario
     * @param fecha en la que voy a eliminar el horario
     * @return consulta ok/ko;
     */
    public boolean eliminarHorarioTrabajador(int codigoTrabajador, Date fecha) {

        try {
            conexion = DriverManager.getConnection(url, usuario, contrasenia);
            st = conexion.createStatement();

            sentenciaSQL = "DELETE FROM " + NOMBRE_TABLA_HORARIOS + " WHERE numeroEmpleado = " + codigoTrabajador + " AND fecha = '" + fecha + "'AND realizado IS NULL;";
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

    /**
     * Recoge los parametros y buscando el trabajador por su codigo en el caso
     * de que este existe, le pone un nuevo rango horario para firmar en un dia
     * en concreto
     *
     * @param codigoTrabajador es el codigo del trabajador al que se le va a asignar un nuevo horario
     * @param horaInicio hora por la que empezara la jornada
     * @param horaFin hora de fin de la jornada
     * @param fecha de la jornada
     * @return consulta ok/ko
     */
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

    /**
     * Recoge los parametros necesarios y da de alta al nuevo empleado, el
     * codigo de empleado se asgina de forma automatica, se genera y se
     * comprueba si este existe dentro de la base de datos, para que no se
     * repita
     *
     * @param nombre del trabajador
     * @param apellido del trabajador
     * @param imagenFirma imagen de la firma del trabajador
     * @param tam tamaño que tiene la imagen de la firma, necesario para guardalo en la BD
     * @return cosulta ok/ko
     * @throws SQLException excepcion de SQL erroneo
     */
    public boolean darAltaTrabajador(String nombre, String apellido, FileInputStream imagenFirma, int tam) throws SQLException {
        int codigoEmpleado;
        do {
            codigoEmpleado = (int) (Math.random() * 1000);

        } while (comprobarSiExisteCodEmpleado(codigoEmpleado));
        listadoNumerosTrabajadores.add(codigoEmpleado);
        try {
            conexion = DriverManager.getConnection(url, usuario, contrasenia);
            state = conexion.prepareStatement("INSERT INTO " + NOMBRE_TABLA_TRBAJADORES + "(nombre, apellidos, numeroEmpleado, firma) VALUES (?,?,?,?)");
            state.setString(1, nombre);
            state.setString(2, apellido);
            state.setInt(3, codigoEmpleado);
            state.setBinaryStream(4, (InputStream) imagenFirma, tam);
            state.executeUpdate();
            state.close();
            conexion.close();

        } catch (SQLException ex) {
            Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    /**
     * Buscando por el codigo de trabajador introducido encuentra el trabajador
     * que le corresponde y elimna todos sus datos de la base.
     *
     * @param codigoTrabajador codigo del trbajador que se va a dar de baja
     * @return consulta ok/ko
     */
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

    /**
     * Realiza una consulta a la base de datos en la que busca todos los codigo
     * de empleado que existen, con el resultado de esta consulta se carga un
     * ArrayList donde estaran dichos codigos.
     */
    private void cargarArrayListNumerosTrabajadores() {

        try {
            conexion = DriverManager.getConnection(url, usuario, contrasenia);
            st = conexion.createStatement();

            sentenciaSQL = "SELECT numeroEmpleado FROM " + NOMBRE_TABLA_TRBAJADORES + ";";
            resultadoSelect = st.executeQuery(sentenciaSQL);
            while (resultadoSelect.next()) {
                listadoNumerosTrabajadores.add(Integer.parseInt(resultadoSelect.getString(1)));
            }
            st.close();
            conexion.close();

        } catch (SQLException ex) {
            Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo utilizado para el momento de dar de alta un empleado, se le pasa
     * un codigo de empleado generado de forma aleatoria y este se busca dentro
     * del ArrayList el cual se encuentra lleno por todos los codigos de
     * empleado existentes en la base de datos, en el caso de que no existe en
     * el ArrayList se retorna true para que se de alta ese numero generado, si
     * se encuentra se retorna false y se vuelve a realizar el mismo proceso.
     *
     * @param codigoComprobar codigo de emepleao que le envio al metodo y que se va a usar para comprobar si existe ya existe
     * @return retorna si el codigo de empleado existe en la BD
     */
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

    /**
     * Realiza una actualizacion de la columna -realizado- poniendolo a 1, en el
     * caso de que la persona que con el codigo de empleado recogido por el
     * metodo, tenga un un horario fijado para el dia que realiza la firma.
     *
     * @param codEmpFirma codigo del empleado que a realizado la firma de que ha realizado su jornada
     * @return retorna si se ha podido realizar la firma del empleado en su horario
     */
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

    /**
     * Se encarga de crear un pdf por cada empleado que constara de su nombre y
     * todos los horarios que haya hecho con su respectiva fecha y firma.
     *
     * @param ruta donde se van a crear los PDF
     * @throws FileNotFoundException excepcion de archivo no encontrado
     * @throws DocumentException excepcion de documento corrupto
     * @throws BadElementException excepcion de elmento erroneo introducido en PDF
     * @throws IOException error de entrada/salida
     * @throws java.sql.SQLException expcecion SQL
     */
    public void crearPDFs(String ruta) throws FileNotFoundException, DocumentException, BadElementException, IOException, SQLException {
        String horaInicio, horaFin, fecha;
        String nombre = null; //ruta = obtenerRuta();

        Image imagenFirma;
        comprobarSiExisteCodEmpleado(0);
        Collections.sort(listadoNumerosTrabajadores);

        for (int i = 0; i < listadoNumerosTrabajadores.size(); i++) {
            if (comprobarSiTrabajadorHorarios(listadoNumerosTrabajadores.get(i))) {//Compuebo si el trabajador ha realizado algun horario ya

                try {
                    conexion = DriverManager.getConnection(url, usuario, contrasenia);
                    st = conexion.createStatement();
                    //Busco el nombre y los apellidos del trabajador por su codigo, para poder ponerlo en el PDF
                    resultadoSelect = st.executeQuery("SELECT nombre, apellidos FROM " + NOMBRE_TABLA_TRBAJADORES + " WHERE numeroEmpleado = " + listadoNumerosTrabajadores.get(i) + " LIMIT 1;");
                    while (resultadoSelect.next()) {
                        nombre = resultadoSelect.getString(1);
                        nombre += " " + resultadoSelect.getString(2);
                    }

                    st.close();
                    conexion.close();

                } catch (SQLException ex) {
                    Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
                }

                Document pdf = new Document();
                FileOutputStream ficheroPdf = new FileOutputStream(ruta + "/" + nombre + " " + listadoNumerosTrabajadores.get(i) + ".pdf");
                PdfWriter.getInstance(pdf, ficheroPdf).setInitialLeading(20);
                pdf.open();

                imagenFirma = conseguirImagenFirma(listadoNumerosTrabajadores.get(i));
                imagenFirma.scaleToFit(50, 50);
                imagenFirma.setAlignment(Chunk.ALIGN_CENTER);//Centro el parrafo
                Paragraph datosEmp = new Paragraph(nombre, FontFactory.getFont("arial",22,Font.BOLD,BaseColor.BLACK));//Doy estilo al parrafo
                
                datosEmp.setAlignment(Paragraph.ALIGN_CENTER);
                datosEmp.setSpacingAfter(10);
                pdf.add(datosEmp);
                pdf.add(new Paragraph(" "));
                try {
                    conexion = DriverManager.getConnection(url, usuario, contrasenia);
                    st = conexion.createStatement();
                    sentenciaSQL = "SELECT fecha, horaInicio, horaFin FROM " + NOMBRE_TABLA_HORARIOS + " WHERE numeroEmpleado = " + listadoNumerosTrabajadores.get(i) + " AND realizado IS NOT NULL;";
                    resultadoSelect = st.executeQuery(sentenciaSQL);
                    while (resultadoSelect.next()) {
                        horaInicio = resultadoSelect.getString(2);
                        fecha = resultadoSelect.getString(1);
                        horaFin = resultadoSelect.getString(3);
                        pdf.addCreationDate();
                        Paragraph horarios = new Paragraph("Fecha " + fecha + " --- Hora inicio " + horaInicio + " --- Hora fin " + horaFin);
                        horarios.setAlignment(Paragraph.ALIGN_CENTER);
                        pdf.add(horarios);
                        pdf.add(imagenFirma);
                    }
                    st.close();
                    conexion.close();

                } catch (SQLException ex) {
                    Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
                }
                pdf.close();
            }
        }
    }

    /**
     * Realiza una consulta para obtener todos los empleados de la base de datos
     * con su nombre apellido y numero de empleado
     *
     * @return ArrayList con objeto de la clase Trabajador, siendo cada uno un
     * trabajador almacenado en la base de datos.
     */
    public ArrayList<Trabajador> rellenarListadoTrabajadores() {
        ArrayList<Trabajador> arrayListDatosTrabajadores = new ArrayList<>();
        try {
            conexion = DriverManager.getConnection(url, usuario, contrasenia);
            st = conexion.createStatement();

            sentenciaSQL = "SELECT nombre, apellidos, numeroEmpleado FROM " + NOMBRE_TABLA_TRBAJADORES + ";";
            resultadoSelect = st.executeQuery(sentenciaSQL);
            while (resultadoSelect.next()) {
                Trabajador t = new Trabajador(resultadoSelect.getString(1), resultadoSelect.getString(2), Integer.parseInt(resultadoSelect.getString(3)));
                arrayListDatosTrabajadores.add(t);

            }
            st.close();
            conexion.close();

        } catch (SQLException ex) {
            Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arrayListDatosTrabajadores;
    }

    /**
     * Metodo utilizado para mostrar todos los horarios que no hayan sido
     * realizados aun por un trabajador, buscando por su codigo.
     *
     * @param codigoUsuarioBuscarHorarios codigo del usuario que vamos a usar para buscar sus horarios sin realizar
     * @return ArrayList con los horarios del empleado elegido
     */
    public ArrayList<Horarios> rellenarListadoHorariosTrabajador(int codigoUsuarioBuscarHorarios) {
        ArrayList<Horarios> arrayListHorariosTrabajador = new ArrayList<>();
        Horarios horario;

        try {
            conexion = DriverManager.getConnection(url, usuario, contrasenia);
            st = conexion.createStatement();

            sentenciaSQL = "SELECT fecha, horaInicio, horaFin FROM " + NOMBRE_TABLA_HORARIOS + " WHERE numeroEmpleado = " + codigoUsuarioBuscarHorarios + " AND realizado IS NULL;";
            resultadoSelect = st.executeQuery(sentenciaSQL);
            while (resultadoSelect.next()) {
                horario = new Horarios(resultadoSelect.getString(2), resultadoSelect.getString(3), resultadoSelect.getString(1));
                arrayListHorariosTrabajador.add(horario);

            }
            st.close();
            conexion.close();

        } catch (SQLException ex) {
            Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arrayListHorariosTrabajador;

    }

    /**
     * Usando el codigo de empleado realiza una consulta para buscar la firma
     * que le corresponde
     *
     * @param codEmpleado codigo del empleado del que vamos a recuperar la firma de la BD
     * @return Imagen de firma
     * @throws BadElementException
     * @throws IOException
     */
    private Image conseguirImagenFirma(Integer codEmpleado) throws BadElementException, IOException {
        byte[] bytesFirma = null;
        Image firma = null;
        Blob imagenBlob;
        try {
            conexion = DriverManager.getConnection(url, usuario, contrasenia);
            st = conexion.createStatement();

            sentenciaSQL = "SELECT firma FROM " + NOMBRE_TABLA_TRBAJADORES + " WHERE numeroEmpleado = " + codEmpleado + ";";
            resultadoSelect = st.executeQuery(sentenciaSQL);
            while (resultadoSelect.next()) {
                imagenBlob = resultadoSelect.getBlob(1);
                bytesFirma = imagenBlob.getBytes(1, (int) imagenBlob.length());//Contrsutye la imagen a partir de los bytes almacenados en la BD
                firma = Image.getInstance(bytesFirma);
            }

            st.close();
            conexion.close();

        } catch (SQLException ex) {
            Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(firma);
        return firma;
    }

    /**
     * Hago una consulta para saber que empleados tienen horarios que ya han
     * sido cumplidos, elijo cualquier valor para la consulta si este valor se
     * me devuelve es que el empleado tiene horarios hechos, si es nulo es que
     * aun no ha hecho nunca ningun horario.
     *
     * @param codEmp codigoo de empleado que vamos a usar para saber si ese empleado tiene horarios creados sin realizar
     * @return
     * @throws SQLException
     */
    private boolean comprobarSiTrabajadorHorarios(int codEmp) throws SQLException {
        String existe = null;
        try {
            conexion = DriverManager.getConnection(url, usuario, contrasenia);
            st = conexion.createStatement();

            sentenciaSQL = "SELECT numeroEmpleado FROM " + NOMBRE_TABLA_HORARIOS + " WHERE numeroEmpleado = " + codEmp + " AND realizado IS NOT NULL LIMIT 1;";
            resultadoSelect = st.executeQuery(sentenciaSQL);
            while (resultadoSelect.next()) {
                existe = resultadoSelect.getString(1);
            }
            st.close();
            conexion.close();

        } catch (SQLException ex) {
            Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return existe != null;
    }

    private String obtenerRuta() {
        
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            
            return ""+chooser.getCurrentDirectory();
        }
        return null;
    }

}
