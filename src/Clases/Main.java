/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author 6001231
 */
public class Main extends Application {

    /**
     * @param args the command line arguments
     */
    public static Stage ventana;
    Intercambiadora intercambiadora;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ventana=stage;
        ventana.setResizable(false);
        intercambiadora = new Intercambiadora();
        intercambiadora.cargarVistaAdministrador();
       
        
    }
    
   

}
