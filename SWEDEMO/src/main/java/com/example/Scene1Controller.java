package com.example;

import com.example.Contatto;
import com.example.InterfaceRubrica;
import com.example.Rubrica;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
         initBindings();
        rubrica = new Rubrica(); //contenente la rubrica totale
            
        tableView.setItems(rubrica.getListaOsservabile());
        nomeClm.setCellValueFactory(new PropertyValueFactory("nome"));
        surnameClm.setCellValueFactory(new PropertyValueFactory("cognome"));
        //Sezione searchBarField
        searchBarField.textProperty().addListener((observable, oldValue, newValue) -> {
        // Aggiorna la lista in base alla ricerca
        updateSearchResults(newValue);
        
    });
       
    }
    
    
    private void updateSearchResults(String query){
        if(query==null){
        rubrica.getListaOsservabile().addAll(rubrica.getContatti());
        }
        Rubrica rubricaNuova = rubrica.ricercaContatti(query);
        rubrica.getListaOsservabile().setAll(rubricaNuova.getContatti());
       
        
    }

   

    @FXML
    /***
     * Aggiunge , alla pressione del pulsante new , il contatto digitato nella rubrica.
     * Sfruttando isPresent() , verifica se il contatto digitato è già presente nella rubrica.In tal caso viene mostrato un messaggio di errore
     * altrimenti , viene aggiunto il contatto , notificato da un alert con messaggio di conferma di inserimento del contatto 
     
     */
    private void addLista(ActionEvent event) {
        Contatto contattoDigitato = new Contatto(nameField.getText(),surnameField.getText());
        aggiungiCellAlContattoDigitato(contattoDigitato);
        aggiungiEmailsAlContattoDigitato(contattoDigitato);
        if(isPresent(contattoDigitato)==1){
        showAlert(1);
        return; 
        }
        rubrica.addContatto(contattoDigitato);
        rubrica.getListaOsservabile().setAll(rubrica.getContatti()); //Aggiorna la lista ad ogni inserimento col treeSet , per mantenere l'ordine lessicografico
        showAlert(0);
    }
    private void aggiungiCellAlContattoDigitato(Contatto c){
     if (phone1Field.getText() != null && !phone1Field.getText().isEmpty()) {
        c.addNumero(phone1Field.getText());
     } 
    if (phone2Field.getText() != null && !phone2Field.getText().isEmpty()) {
        c.addNumero(phone2Field.getText());
    }
    if (phone3Field.getText() != null && !phone3Field.getText().isEmpty()) {
        c.addNumero(phone3Field.getText());
    }
    }
    
    
    private void aggiungiEmailsAlContattoDigitato(Contatto c){
    
        if (email1Field.getText() != null && !email1Field.getText().isEmpty()) {
        c.addNumero(phone1Field.getText());
     } 
    if (email2Field.getText() != null && !email2Field.getText().isEmpty()) {
        c.addNumero(email2Field.getText());
    }
    if (email3Field.getText() != null && !email3Field.getText().isEmpty()) {
        c.addNumero(email3Field.getText());
    }
    
    }
    

    @FXML
    private void deleteLista(ActionEvent event) {
        Contatto daRimuovere = tableView.getSelectionModel().getSelectedItem();
        rubrica.removeContatto(daRimuovere);
        rubrica.getListaOsservabile().setAll(rubrica.getContatti()); //Aggiorna la lista ad ogni rimozione
    }
    
    
    
    /**
     * Verifica se il contatto digitato è già presente nella rubrica.
     * Restituisce 1 se già presente , 0 altrimenti 
    
    */
    private int isPresent(Contatto c){
        for(Contatto tmp : rubrica.getContatti()){
        if(tmp.compareTo(c)==0) return 1; 
        }
        return 0; 
        
        
    }
    /****
     * @brief: Funzione che , ricevuto in input un flag(che se 1 indica che il contatto digitato è già presente, se 0 viceversa), mostra un alert
     * customizzato a seconda dello stato del flag passato. Se il flag è 0 , il contatto digitato non è già presente in rubrica , dunque si procede con il 
     * messaggio di successo 
    
    */
    private void showAlert(int flag) {
    // Creazione di un alert di tipo informazione
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Warning!");
    if(flag==1){
    alert.setHeaderText("Contatto già presente!");
    alert.setContentText("Esiste già un contatto "+nameField.getText()+" "+surnameField.getText()+ "con le informazioni che hai digitato");
    }else{
    
    alert.setHeaderText("Operazione completata!");
    alert.setContentText("Contatto"+" " +nameField.getText()+" "+surnameField.getText()+" aggiunto con successo !");
    }
        
    // Mostra l'alert e attendi la chiusura
    alert.showAndWait();
}
    
    private void initBindings(){
    BooleanBinding abilitato = nameField.textProperty().isNotEmpty().or(surnameField.textProperty().isNotEmpty());
    saveButton.disableProperty().bind(abilitato.not());
    
    }
}
