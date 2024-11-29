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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private TableView<Contatto> tableView;
    @FXML
    private TableColumn<Contatto,String> nomeClm;
    @FXML
    private TableColumn<Contatto,String> surnameClm;
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
    
    private Rubrica rubrica;
    
    private ObservableList<Contatto>contatti;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        rubrica = new Rubrica(); //contenente la rubrica totale
        
        contatti = FXCollections.observableArrayList(); //non posso istanziare con parola chiave new
        tableView.setItems(contatti);
        nomeClm.setCellValueFactory(new PropertyValueFactory("nome"));
    }
    
    public void aggiornaLista(InterfaceRubrica rubrica){
        
        contatti.clear();
        for(Contatto c : rubrica.getContatti()) contatti.add(c); //caricata l'observableList
    
    }

    @FXML
    private void addLista(ActionEvent event) {
        contatti.add(new Contatto(nameField.getText(),surnameField.getText()));
    }
}
