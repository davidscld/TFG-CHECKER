/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 *
 * @author dvdsa
 */
public class Constantes  {
    public final Parent vistaFirma = FXMLLoader.load(getClass().getResource("/Vistas/VistaFirma.fxml"));

    public Constantes() throws IOException{
    }
    
    
}
