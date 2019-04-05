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
import static Clases.Main.scene;

/**
 *
 * @author 6001231
 */
public class Logica {

    private Stage stage;
    private Parent vistaFirma = FXMLLoader.load(getClass().getResource("/XMLDocuments/vistaFirma.fxml"));
    private Parent vistaAdmin = FXMLLoader.load(getClass().getResource("/XMLDocuments/vistaAdmin.fxml"));

    Logica(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        cargarVistaInicial();
      
    }
    
    private void cargarVistaInicial( ) {
        scene = new Scene(vistaFirma);
        stage.setScene(scene);
        stage.show();
    }

}
