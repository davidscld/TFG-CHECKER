/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Clases.Constantes;
import Clases.Logica;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author 6001231
 */
public class ControladorVistasIniciales implements Initializable {

    
    
    private Logica logica;
    @FXML
    private JFXTextField etUsuario;

    @FXML
    private JFXButton btnFirmar;

    @FXML
    private Label lbResultadoComprobacion;

    @FXML
    private JFXButton btnCargarVistaAdmin;

    
    
    public ControladorVistasIniciales(Logica aThis) throws IOException {
        this.logica = aThis;
        
    }

    @FXML
    void cargarVistaAdmin(ActionEvent event) throws IOException {

    }

    public ControladorVistasIniciales() {
    }

    @FXML
    void comprobarFirma(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
