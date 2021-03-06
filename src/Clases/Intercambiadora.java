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

/**
 *Se encarga de ir carrgando las distintas vistas.
 * @author dvdsa
 */
public class Intercambiadora {
/**
 * Carga las vistas iniciales de la aplicacion
 * @throws IOException error entrada/salida
 */
    public void cargarVistaInicial() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Vistas/VistasUsers.fxml"));
        Scene scene = new Scene(root);
        Main.ventana.setScene(scene);
        Main.ventana.show();
    }
/**
 * Carga la vista de administrador
 * @throws IOException error entrada/salida
 */
    public void cargarVistaAdministrador() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Vistas/VistaPrincipalAdministrador.fxml"));
        Scene scene = new Scene(root);
        Main.ventana.setScene(scene);
        Main.ventana.show();
    }
}
