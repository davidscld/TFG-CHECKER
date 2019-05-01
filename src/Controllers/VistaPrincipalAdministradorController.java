/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Clases.GestorBD;
import Clases.Horarios;
import Clases.Intercambiadora;
import Clases.Trabajador;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * FXML Controller class
 * Contraldor de todas las vistas que componen la parte del administrador
 * @author dvdsa
 */
public class VistaPrincipalAdministradorController implements Initializable {

    private Intercambiadora intercambiadora = new Intercambiadora();
    private GestorBD gestorBD = new GestorBD();
    private FileInputStream imagenFirma;
    private ArrayList<Trabajador> datosTrabajadores = new ArrayList<>();
    private ArrayList<Horarios> datosHorariosPorTrabajador = new ArrayList<>();
    private int tamImagenFirma;
    @FXML
    private JFXButton btnEmpleados;

    @FXML
    private JFXButton btnHorarios;

    @FXML
    private JFXButton btnPDF;

    @FXML
    private JFXButton btnSalir;

    @FXML
    private VBox menuPersonal;

    @FXML
    private JFXButton btnAltaPersonal;

    @FXML
    private JFXButton btnBajaPersonal;

    @FXML
    private JFXButton btnListarPersonal;

    @FXML
    private VBox menuHorarios;

    @FXML
    private JFXButton btnAniadirHorario;

    @FXML
    private JFXButton btnEliminarHorarioMenu;

    @FXML
    private JFXButton btnListarHorarios;

    @FXML
    private Pane panelVacioPDF;

    @FXML
    private ScrollPane vistaListadoEmpleados;

    @FXML
    private Pane vistaHorariosPorEmpleado;

    @FXML
    private JFXTextField etCodigoEmpleadoVerHorarios;

    @FXML
    private JFXButton btnVerHorarios;

    @FXML
    private Pane vistaNuevoEmpleado;

    @FXML
    private Pane panelGenerarPDF;

    @FXML
    private JFXButton btnGuardarNuevoEmpleado;

    @FXML
    private JFXTextField etRutaPDF;

    @FXML
    private JFXTextField etNombreEmpleado;

    @FXML
    private JFXTextField etApellidosEmpleado;

    @FXML
    private JFXButton btnBuscarImagenFirma;

    @FXML
    private Pane vistaEliminarHorario;

    @FXML
    private JFXTextField etCodigoEmpleadoEliminarHorario;

    @FXML
    private JFXDatePicker dpFechaEliminarHorario;

    @FXML
    private JFXButton btnEliminarHorario;

    @FXML
    private Pane vistaBajaEmpleado;

    @FXML
    private JFXTextField etCodigoEmpleadoEliminar;

    @FXML
    private JFXButton btnBajaEmpleado;

    @FXML
    private Pane vistaNuevoHorario;

    @FXML
    private JFXTextField etCodigoEmpleadoNuevoHorario;

    @FXML
    private JFXDatePicker dpFechaNuevoHorario;

    @FXML
    private JFXTimePicker tpHoraFin;

    @FXML
    private JFXTimePicker tpHoraInicio;

    @FXML
    private JFXButton btnGuardarNuevoHorario;

    @FXML
    private GridPane cuadriculaListadoPersonal;

    @FXML
    private GridPane gvListadoHorariosEmpleado;

    @FXML
    private Label lbCodEmpVerHorarios;

    /**
     * Lanza un selecctor de archivos para que se pueda buscar la imagen de la
     * firma correspondiente al empleado que se quiere dar de alta Tiene un
     * filtro para que solo se puedan elegir elementos -jpg,png-
     *
     * @param event
     * @throws FileNotFoundException
     */
    @FXML
    void buscarImagenFirma(ActionEvent event) throws FileNotFoundException {
        int seleccion;
        File image;
        String rutaImagen;
        JFileChooser buscadorFirmas = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("jpg", "png", "JPG", "PNG");//Ponemos un filtro para que solo pueda escoger estos tipos de archivos
        buscadorFirmas.setFileFilter(filtro);
        seleccion = buscadorFirmas.showOpenDialog(buscadorFirmas);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            rutaImagen = buscadorFirmas.getSelectedFile().getAbsolutePath();
            image = new File(rutaImagen);
            tamImagenFirma = (int) image.length();//Obtengo el tamaño de la imagen
            imagenFirma = new FileInputStream(image);
        }

    }

    /**
     * Cambia el submenu central dependiendo de la opcion que hayamos elegido en
     * el panel lateral izquierdo.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void cargarMenuCentral(ActionEvent event) throws IOException {
        if (event.getSource() == btnEmpleados) {
            menuPersonal.toFront();
        } else if (event.getSource() == btnHorarios) {
            menuHorarios.toFront();
        } else if (event.getSource() == btnPDF) {
            panelVacioPDF.toFront();
            panelGenerarPDF.toFront();
        } else {
            intercambiadora.cargarVistaInicial();
        }
    }

    /**
     * Alternar entre los diferentes paneles que componen el submenu de horarios
     *
     * @param event
     */
    @FXML
    void cargarVistasHorarios(ActionEvent event) {
        limpiarCampos();
        if (event.getSource() == btnAniadirHorario) {
            vistaNuevoHorario.toFront();

        } else if (event.getSource() == btnEliminarHorarioMenu) {
            vistaEliminarHorario.toFront();
        } else if (event.getSource() == btnListarHorarios) {
            vistaHorariosPorEmpleado.toFront();
        }
    }

    /**
     * Alternar entre los diferentes paneles que componen el submenu de personal
     *
     * @param event
     */
    @FXML
    void cargarVistasPersonal(ActionEvent event) {
        limpiarCampos();
        if (event.getSource() == btnAltaPersonal) {
            vistaNuevoEmpleado.toFront();

        } else if (event.getSource() == btnBajaPersonal) {
            vistaBajaEmpleado.toFront();
        } else if (event.getSource() == btnListarPersonal) {
            rellenarVistaListado();
            vistaListadoEmpleados.toFront();
        }
    }

    /**
     * Al acceder al pande Eliminar empleado, pulsando el boton de eliminar se
     * recogen los datos necesarios y si se consigue eliminar el empleado los
     * campos rellenados se vacian
     *
     * @param event
     */
    @FXML
    void eliminarEmpleado(ActionEvent event) {

        if (gestorBD.darBajaTrabajador(Integer.parseInt(etCodigoEmpleadoEliminar.getText()))) {//Si se ha eliminado bien el empleado
            limpiarCampos();
        }
    }

    /**
     * Se ejecuta al pulsar el boton para eliminar el horario, si se consigue
     * eliminar se limpian los campos rellenos de este panel
     *
     * @param event
     */
    @FXML
    void eliminarHorario(ActionEvent event) {

        if (gestorBD.eliminarHorarioTrabajador(Integer.parseInt(etCodigoEmpleadoEliminarHorario.getText()), java.sql.Date.valueOf(dpFechaEliminarHorario.getValue()))) {
            limpiarCampos();
        }

    }

    /**
     * Se ejecuta al pulsar el boton para dar de alta a un nuevo empleado, si se
     * consigue dar de alta se eliminan los campos rellenos
     *
     * @param event
     * @throws SQLException
     */
    @FXML
    void guardarNuevoEmpleado(ActionEvent event) throws SQLException {
        String nombre = etNombreEmpleado.getText();
        String apellidos = etApellidosEmpleado.getText();
        if (nombre != null && apellidos != null) {
            if (gestorBD.darAltaTrabajador(nombre, apellidos, imagenFirma, tamImagenFirma)) {
                limpiarCampos();
            }
        }

    }

    /**
     * Se ejecuta al dar un nuevo horario a un trabajador, si se puede realizar
     * se eliminan los campos rellenos
     *
     * @param event
     */
    @FXML
    void guardarNuevoHorario(ActionEvent event) {
        String codigo = etCodigoEmpleadoNuevoHorario.getText();
        java.sql.Time horaInicio = java.sql.Time.valueOf(tpHoraInicio.getValue());
        java.sql.Time horaFin = java.sql.Time.valueOf(tpHoraFin.getValue());
        java.sql.Date fecha = java.sql.Date.valueOf(dpFechaNuevoHorario.getValue());
        if (codigo != null && horaInicio != null && horaFin != null && fecha != null) {//Si ningun dato es nulo se procede a realizar
            if (gestorBD.nuevoHorarioTrabajador(Integer.parseInt(codigo), horaInicio, horaFin, fecha)) {
                limpiarCampos();
            }
        }

    }

    /**
     * Al pulsar el boton del submenu generara los pdf necesarios.
     *
     * @param event
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws DocumentException
     * @throws BadElementException
     * @throws IOException
     */
    @FXML
    void generarPDF(ActionEvent event) throws SQLException, FileNotFoundException, DocumentException, BadElementException, IOException {
        gestorBD.crearPDFs(etRutaPDF.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * Se encarga de limpiar todos los campos
     */
    private void limpiarCampos() {
        etApellidosEmpleado.setText("");
        etCodigoEmpleadoEliminar.setText("");
        etCodigoEmpleadoEliminarHorario.setText("");
        etCodigoEmpleadoNuevoHorario.setText("");
        etNombreEmpleado.setText("");
        etCodigoEmpleadoVerHorarios.setText("");
        tpHoraFin.getEditor().clear();
        tpHoraInicio.getEditor().clear();
        dpFechaEliminarHorario.getEditor().clear();
        dpFechaNuevoHorario.getEditor().clear();
        etRutaPDF.setText("");

    }

    /**
     * Realiza una consulta a la base de datos y con los resultados devueltos
     * rellena una vista con todos los horarios que le corresponden a un
     * empleado buscando por el codigo de empleado
     *
     * @param event
     */
    @FXML
    void verHorariosPorEmpleado(ActionEvent event) {
        int codigoUsuarioBuscarHorarios = Integer.parseInt(etCodigoEmpleadoVerHorarios.getText());

        gvListadoHorariosEmpleado.getChildren().removeAll(gvListadoHorariosEmpleado.getChildren());

        datosHorariosPorTrabajador.clear();
        datosHorariosPorTrabajador = gestorBD.rellenarListadoHorariosTrabajador(codigoUsuarioBuscarHorarios);
        if (!datosHorariosPorTrabajador.isEmpty()) {
            Label etiquetaFecha = new Label("FECHA");
            Label etiquetaHorarios = new Label("HORARIOS");
            gvListadoHorariosEmpleado.add(etiquetaFecha, 0, 0);
            gvListadoHorariosEmpleado.add(etiquetaHorarios, 1, 0);
            for (int i = 0; i < datosHorariosPorTrabajador.size(); i++) {
                //Muestra la fecha del horario
                Label nombre = new Label(datosHorariosPorTrabajador.get(i).getFecha());
                gvListadoHorariosEmpleado.add(nombre, 0, (i + 1));
                //Muestra los horarios correspondientes a una fecha
                Label codigo = new Label(datosHorariosPorTrabajador.get(i).getHoraInicio() + " -- " + datosHorariosPorTrabajador.get(i).getHoraFin());
                gvListadoHorariosEmpleado.add(codigo, 1, (i + 1));
            }
            lbCodEmpVerHorarios.setText("" + codigoUsuarioBuscarHorarios);
            limpiarCampos();
        } else {
            lbCodEmpVerHorarios.setText("");
        }

    }

    /**
     * Rellena la vista del listado de los empleados con la cosulta realizada
     * para obtener todos los empleados de la base de datos, mostrara primero
     * apellidos por orden alfabetico luego el nombre y por otro lado su codigo
     * de empleado.
     */
    private void rellenarVistaListado() {

        cuadriculaListadoPersonal.getChildren().removeAll(cuadriculaListadoPersonal.getChildren());//Limpia todos los elemetos de la cuadricula
        Label etiquetaNombre = new Label("APELLIDOS NOMBRE");
        etiquetaNombre.setStyle("-fx-font-weight: bold");
        Label etiquetaCodigo = new Label("CODIGO");
        etiquetaCodigo.setStyle("-fx-font-weight: bold");
        cuadriculaListadoPersonal.add(etiquetaNombre, 0, 0);
        cuadriculaListadoPersonal.add(etiquetaCodigo, 1, 0);

        datosTrabajadores = gestorBD.rellenarListadoTrabajadores();
        Collections.sort(datosTrabajadores);
        for (int i = 0; i < datosTrabajadores.size(); i++) {
            //Pone los apellidos y nombre del empleado
            Label nombre = new Label(datosTrabajadores.get(i).getApellidos() + " - " + datosTrabajadores.get(i).getNombre());
            cuadriculaListadoPersonal.add(nombre, 0, (i + 1));
            //Pone el codigo del empleado junto al apellidos y nombre
            Label codigo = new Label("" + datosTrabajadores.get(i).getNumeroEmpleado());
            cuadriculaListadoPersonal.add(codigo, 1, (i + 1));
        }

    }

}
