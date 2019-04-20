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
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.awt.Color;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Timer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * FXML Controller class
 *
 * @author dvdsa
 */
public class VistaPrincipalAdministradorController implements Initializable {

    private Intercambiadora intercambiadora = new Intercambiadora();
    private GestorBD gestorBD = new GestorBD();
    private FileInputStream imagenFirma;
    private ArrayList<Trabajador> datosTrabajadores = new ArrayList<>();
    private ArrayList<Horarios> datosHorariosPorTrabajador = new ArrayList<>();
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
    private JFXButton btnGuardarNuevoEmpleado;

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

    @FXML
    void buscarImagenFirma(ActionEvent event) throws FileNotFoundException {
        int seleccion;
        String rutaImagen;
        JFileChooser buscadorFirmas = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("jpg", "png", "JPG", "PNG");
        buscadorFirmas.setFileFilter(filtro);
        seleccion = buscadorFirmas.showOpenDialog(buscadorFirmas);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            rutaImagen = buscadorFirmas.getSelectedFile().getAbsolutePath();
            imagenFirma = new FileInputStream(rutaImagen);
        }

    }

    @FXML
    void cargarMenuCentral(ActionEvent event) throws IOException {
        if (event.getSource() == btnEmpleados) {
            menuPersonal.toFront();
        } else if (event.getSource() == btnHorarios) {
            menuHorarios.toFront();
        } else if (event.getSource() == btnPDF) {
            panelVacioPDF.toFront();
        } else {
            intercambiadora.cargarVistaInicial();
        }
    }

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

    @FXML
    void eliminarEmpleado(ActionEvent event) {

        if (gestorBD.darBajaTrabajador(Integer.parseInt(etCodigoEmpleadoEliminar.getText()))) {//Si se ha eliminado bien el empleado
            limpiarCampos();
        }
    }

    @FXML
    void eliminarHorario(ActionEvent event) {

        if (gestorBD.eliminarHorarioTrabajador(Integer.parseInt(etCodigoEmpleadoEliminarHorario.getText()), java.sql.Date.valueOf(dpFechaEliminarHorario.getValue()))) {
            limpiarCampos();
        }

    }

    @FXML
    void guardarNuevoEmpleado(ActionEvent event) throws SQLException {

        if (gestorBD.darAltaTrabajador(etNombreEmpleado.getText(), etApellidosEmpleado.getText(), imagenFirma)) {
            limpiarCampos();
        }
    }

    @FXML
    void guardarNuevoHorario(ActionEvent event) {

        if (gestorBD.nuevoHorarioTrabajador(Integer.parseInt(etCodigoEmpleadoNuevoHorario.getText()),
                java.sql.Time.valueOf(tpHoraInicio.getValue()), java.sql.Time.valueOf(tpHoraFin.getValue()),
                java.sql.Date.valueOf(dpFechaNuevoHorario.getValue()))) {
            limpiarCampos();
        }

    }

    @FXML
    void generarPDF(ActionEvent event) throws SQLException, FileNotFoundException {
        gestorBD.crearPDFs();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

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

    }

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

                Label nombre = new Label(datosHorariosPorTrabajador.get(i).getFecha());
                gvListadoHorariosEmpleado.add(nombre, 0, (i + 1));

                Label codigo = new Label(datosHorariosPorTrabajador.get(i).getHoraInicio() + " -- " + datosHorariosPorTrabajador.get(i).getHoraFin());
                gvListadoHorariosEmpleado.add(codigo, 1, (i + 1));
            }
            lbCodEmpVerHorarios.setText("" + codigoUsuarioBuscarHorarios);
            limpiarCampos();
        } else {
            lbCodEmpVerHorarios.setText("");
        }

    }

    private void rellenarVistaListado() {

        cuadriculaListadoPersonal.getChildren().removeAll(cuadriculaListadoPersonal.getChildren());
        Label etiquetaNombre = new Label("APELLIDOS NOMBRE");
        etiquetaNombre.setStyle("-fx-font-weight: bold");
        Label etiquetaCodigo = new Label("CODIGO");
        etiquetaCodigo.setStyle("-fx-font-weight: bold");
        cuadriculaListadoPersonal.add(etiquetaNombre, 0, 0);
        cuadriculaListadoPersonal.add(etiquetaCodigo, 1, 0);

        datosTrabajadores = gestorBD.rellenarListadoTrabajadores();
        Collections.sort(datosTrabajadores);
        for (int i = 0; i < datosTrabajadores.size(); i++) {

            Label nombre = new Label(datosTrabajadores.get(i).getApellidos() + " - " + datosTrabajadores.get(i).getNombre());
            cuadriculaListadoPersonal.add(nombre, 0, (i + 1));

            Label codigo = new Label("" + datosTrabajadores.get(i).getNumeroEmpleado());
            cuadriculaListadoPersonal.add(codigo, 1, (i + 1));
        }

    }

}
