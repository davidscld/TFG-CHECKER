/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Clases.GestorBD;
import Clases.Intercambiadora;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author 6001231
 */
public class VistasUsersController implements Initializable {

    private final String USER = "Residencia", PASS = "Ledesma";
    private Intercambiadora intercambiadora = new Intercambiadora();
    private GestorBD gestorBD = new GestorBD();
    @FXML
    private Pane panelFirma;

    @FXML
    private JFXTextField etCodigoEmpleadoVistaFirma;

    @FXML
    private JFXButton btnFirmar;

    @FXML
    private JFXButton btnCargarVistaAdmin;

    @FXML
    private Pane panelAdministrador;

    @FXML
    private JFXTextField etCodigoAdmin;

    @FXML
    private JFXPasswordField etPassAdmin;

    @FXML
    private JFXButton btnAccederAdmin;

    @FXML
    private JFXButton btnVolverVistaFirma;

    @FXML
    void accederOpcionesAdministrador(ActionEvent event) throws IOException {
        if (etCodigoAdmin.getText().equals(USER) && etPassAdmin.getText().equals(PASS)) {
            limpiarCampos();
            intercambiadora.cargarVistaAdministrador();
        }
    }

    @FXML
    void cargarVistaContraria(ActionEvent event) {
        if (event.getSource() == btnCargarVistaAdmin) {
            panelAdministrador.toFront();
        } else {
            panelFirma.toFront();
        }
    }

    @FXML
    void realizarFirmaUsuario(ActionEvent event) {
        int codigoEmpFirma = Integer.parseInt(etCodigoEmpleadoVistaFirma.getText());

        if (gestorBD.realizarFirma(codigoEmpFirma)) {
            limpiarCampos();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void limpiarCampos() {
        etPassAdmin.setText("");
        etCodigoEmpleadoVistaFirma.setText("");
        etCodigoAdmin.setText("");
    }

}
