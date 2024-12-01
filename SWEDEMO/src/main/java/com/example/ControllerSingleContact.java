/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.example;

import com.example.Contact.ContactNumero;
import com.example.Contact.Contatto;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Vincenzo Ragone
 */
public class ControllerSingleContact implements Initializable {

    @FXML
    private Label labelNome;
    @FXML
    private Label labelCognome;
    @FXML
    private Label labelValoreNumero1;
    @FXML
    private Label labelValoreNumero2;
    @FXML
    private Label labelValoreNumero3;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 
    }    
    
    public void setContatto(Contatto contatto){
    setNomeECognome(contatto); 
    setNumeri(contatto);
    }
    
    private void setNomeECognome(Contatto c){
    labelNome.setText(c.getNome());
    labelCognome.setText(c.getCognome());
    }
    
    private void setNumeri(Contatto c){
     List<ContactNumero> numeriInseriti = c.getNumeriDiTelefono();
    //Non ho inserito nessun numero per il contatto
    if(numeriInseriti.isEmpty()) return; 
    
    if(numeriInseriti.size()==1){
    String numeroDaInserire = c.getNumeriDiTelefono().get(0).getAssociatedNumber();
    labelValoreNumero1.setText(numeroDaInserire);
    
    }
   
    
    }
    
    
    
}
