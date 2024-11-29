/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package fxml;

import com.example.Contatto;
import com.example.InterfaceRubrica;
import com.example.Rubrica;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author silve
 */
public class Scene1Controller implements Initializable {

    @FXML
    private MenuItem saveFileButton;
    @FXML
    private MenuItem uploadFileButton;
    @FXML
    private MenuItem newContantButton;
    @FXML
    private TextField searchBarField;
    @FXML
    private ListView<Contatto> list;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label phone1Label;
    @FXML
    private TextField phone1Field;
    @FXML
    private Label phone2Label;
    @FXML
    private TextField phone2Field;
    @FXML
    private Label phone3Label;
    @FXML
    private TextField phone3Field;
    @FXML
    private Label emailLabel;
    @FXML
    private Label email1Label;
    @FXML
    private TextField email1Field;
    @FXML
    private Label email2Label;
    @FXML
    private TextField email2Field;
    @FXML
    private Label email3Label;
    @FXML
    private TextField email3Field;
    @FXML
    private Label descriptionLabel;
    @FXML
    private TextArea descriptionField;
    @FXML
    private Button saveButton;
    @FXML
    private Button applyButton;
    @FXML
    private Button deleteButton;
    
    private ObservableList<Contatto>contatti;
    
    private InterfaceRubrica rubrica ; 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        contatti = FXCollections.observableArrayList(); //non posso istanziare con parola chiave new
        list.setItems(contatti);
        list.setCellFactory(param -> new ListCell<Contatto>() { //sottoclasse che estende ListCell
            //param è il contatto che passi 
            @Override
            protected void updateItem(Contatto item, boolean empty) {
                super.updateItem(item, empty); //sennò la lista potrebbe contenere dati obsoleti?
                if (item != null && !empty) {
                    // Mostra solo nome e cognome
                    setText(item.getNome() + " " + item.getCognome());
                } else {
                    setText(null);
                }
            }
        });
    }

    public void aggiornaLista(InterfaceRubrica rubrica){
        this.rubrica = rubrica ; //ho la rubrica caricata coi contatti.
        
        for(Contatto c : this.rubrica.getContatti())
            contatti.add(c); //caricata l'observableList
    }
   
    
}
