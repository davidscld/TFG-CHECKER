/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author 6001231
 */
public class Logica {

    public Scene scene;
    private Stage stage;
    private final Parent vistaFirma = FXMLLoader.load(getClass().getResource("/Vistas/VistaFirma.fxml"));
    private final Parent vistaAdmin = FXMLLoader.load(getClass().getResource("/Vistas/VistaAdmin.fxml"));

    Logica(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        cargarVistaInicial();

    }

    private void cargarVistaInicial() {
        scene = new Scene(vistaFirma);
        stage.setScene(scene);
        stage.show();
    }

}
