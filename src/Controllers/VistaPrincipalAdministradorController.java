/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Clases.GestorBD;
import Clases.Intercambiadora;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JFileChooser;

/**
 * FXML Controller class
 *
 * @author dvdsa
 */
public class VistaPrincipalAdministradorController implements Initializable {

    private Intercambiadora intercambiadora = new Intercambiadora();
    private GestorBD gestorBD = new GestorBD();

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
    private Pane vistaListadoEmpleados;

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
    void buscarImagenFirma(ActionEvent event) {
        JFileChooser buscadorFirmas = new JFileChooser();
        int seleccion = buscadorFirmas.showOpenDialog(null);

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
        if (event.getSource() == btnAltaPersonal) {
            vistaNuevoEmpleado.toFront();

        } else if (event.getSource() == btnBajaPersonal) {
            vistaBajaEmpleado.toFront();
        } else if (event.getSource() == btnListarPersonal) {
            vistaListadoEmpleados.toFront();
        }
    }

    @FXML
    void eliminarEmpleado(ActionEvent event) {
        gestorBD.darBajaTrabajador(Integer.parseInt(etCodigoEmpleadoEliminar.getText()));
    }

    @FXML
    void eliminarHorario(ActionEvent event) {
        //gestorBD.eliminarHorarioTrabajador(Integer.parseInt(etCodigoEmpleadoEliminarHorario.toString()), dpFechaEliminarHorario.getValue());
       Date date =  java.sql.Date.valueOf(dpFechaEliminarHorario.getValue());
    }

    @FXML
    void guardarNuevoEmpleado(ActionEvent event) {

    }

    @FXML
    void guardarNuevoHorario(ActionEvent event) {
       
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
