/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Controllers.ControladorVistasIniciales;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author 6001231  17'13
 */
public class Logica {

    public Scene scene;
    private Stage stage;
    private Parent vistaFirma = FXMLLoader.load(getClass().getResource("/Vistas/VistaFirma.fxml"));
    private Parent vistaAdmin = FXMLLoader.load(getClass().getResource("/Vistas/VistaAdmin.fxml"));

    Logica(Stage primaryStage) throws IOException {
        this.stage = primaryStage; 
        ControladorVistasIniciales v = new ControladorVistasIniciales(this);

    }

    private void cargarVistaInicial(Parent vista) {
        scene = new Scene(vista);
        stage.setScene(scene);
        stage.show();
    }

}
