/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import javafx.application.Application;
import javafx.stage.Stage;
import javax.swing.UIManager;

/**
 * Clase principal, donde se incia la aplicacion
 * @author 6001231
 */
public class Main extends Application {

    /**
     */
    public static Stage ventana;
    Intercambiadora intercambiadora;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Inicializa la interfaz visual.
     *
     * @param stage ventana donde se van a mostar los elementos de la aplicacion
     * @throws Exception exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        ventana = stage;
        ventana.setResizable(false);//Deshabilita la redimension de la pantalla
        intercambiadora = new Intercambiadora();
        intercambiadora.cargarVistaInicial();
         

    }

}
