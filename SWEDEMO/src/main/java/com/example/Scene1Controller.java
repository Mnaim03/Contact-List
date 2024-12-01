package com.example;

import com.example.Contact.ContactEmail;
import com.example.Contact.ContactNumero;
import com.example.Contact.Contatto;
import com.example.Exceptions.InvalidEmailException;
import com.example.Exceptions.InvalidNumberException;
import com.example.Rubrica;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;

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
        
        
        //Quando fai doppio click su un contatto della tabella , apre una nuova schermata contente i dettagli del contatto cliccato
          tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Doppio clic sulla riga
                Contatto contattoSelezionato = tableView.getSelectionModel().getSelectedItem();
                if (contattoSelezionato != null) {
                    apriFinestraDettagli(contattoSelezionato);
                }
            }
        });
       
    }
    
    
    private void updateSearchResults(String query){
        if(query==null){
        rubrica.getListaOsservabile().addAll(rubrica.getContatti());
        }
        Rubrica rubricaNuova = rubrica.ricercaContatti(query);
        rubrica.getListaOsservabile().setAll(rubricaNuova.getContatti());
        FXCollections.sort(rubrica.getListaOsservabile(),new Comparator<Contatto>(){
                public int compare(Contatto c1,Contatto c2){
                    int confrontoCognome = c1.getCognome().compareToIgnoreCase(c2.getCognome());
                    if (confrontoCognome != 0) {
                        return confrontoCognome;
                    }
                    // Se i cognomi sono uguali, confronta per nome
                    int confrontoNome = c1.getNome().compareToIgnoreCase(c2.getNome());
                    if (confrontoNome != 0) {
                        return confrontoNome;
                    }

                    // Se anche i nomi sono uguali, usa il numero di telefono come discriminante
                    // I nuemri di telefono uguali possono trovarsi in posizioni diverse

                    if (c1.getNumeriDiTelefono().size() == c2.getNumeriDiTelefono().size()) {
                        if(c1.getNumeriDiTelefono().containsAll(c2.getNumeriDiTelefono()) ) return 0;
                    }



        return 1;
                    
                }
    });
        
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
        clearAllFields();
    }
        
    
    /**
     * Metodo privato , usato dal metodo addLista , per aggiungere i numeri di cellulari digitati al nuovo contatto in fase di creazione
     * @param c 
     */
    private void aggiungiCellAlContattoDigitato(Contatto c) {
        // Verifica se il campo phone1Field non è vuoto
        TextField [] phoneFields ={phone1Field,phone2Field,phone3Field};
        for(TextField field: phoneFields){
            if(!field.getText().equals("")){
                ContactNumero numero = new ContactNumero(field.getText().trim());
                try{
                    c.addNumero(numero);
                    }catch(InvalidNumberException ex){}
                                        
     }
    }
  }

    
   private void aggiungiEmailsAlContattoDigitato(Contatto c) {
    TextField[] emailFields = {email1Field, email2Field, email3Field};
    
    for (TextField field : emailFields) {
        if (!field.getText().equals("")) {
            ContactEmail email = new ContactEmail(field.getText().trim());
            try{
            c.addEmail(email);
            }catch(InvalidEmailException ex){
            
            }
        }
    }
}

    private void clearAllFields(){
      nameField.clear();
      surnameField.clear();
      phone1Field.clear();
      phone2Field.clear();
      phone3Field.clear();
      email1Field.clear();
      email2Field.clear();
      email3Field.clear();
      descriptionField.clear();
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
    }else if(flag==0){
    
    alert.setHeaderText("Operazione completata!");
    alert.setContentText("Contatto"+" " +nameField.getText()+" "+surnameField.getText()+" aggiunto con successo !");
    }else
    {
        alert.setContentText("La mail digitata non è corretta");
    }
        
    // Mostra l'alert e attendi la chiusura
    alert.showAndWait();
}
    
    private void initBindings(){

    BooleanBinding unione = Bindings.createBooleanBinding(()->nameField.getText().equals("") &&
                                                            surnameField.getText().equals("") &&
                                                            (tableView.getSelectionModel().selectedItemProperty() == null),
                                                            nameField.textProperty(), surnameField.textProperty(), tableView.getSelectionModel().selectedItemProperty());

    saveButton.disableProperty().bind(unione);
    }
    
    
    
    /***
     * @brief apre una nuova finestra , contenente i dettagli del contatto selezionato
     * @param[in] contatto , il contatto su cui si è fatto doppio click sulla table view 


    private void apriFinestraDettagli(Contatto contatto) {
        try {
            // Carica il file FXML della finestra di dettaglio
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/scenaVisualizzaSingoloContatto.fxml"));
            Parent root = loader.load();

            // Ottieni il controller della finestra di dettaglio
            ControllerSingleContact controller = loader.getController();

            // Passa il contatto selezionato al controller della finestra di dettaglio
            controller.setContatto(contatto);

            // Crea una nuova finestra
            Stage stage = new Stage();
            stage.setTitle("Dettagli Contatto");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     *
     */

    private void apriFinestraDettagli(Contatto contatto){
        //carico i campi del contatto
        nameField.setText(contatto.getNome());
        surnameField.setText(contatto.getCognome());
        phone1Field.setText(contatto.getNumeriDiTelefono().get(0).getAssociatedNumber());
        phone2Field.setText(contatto.getNumeriDiTelefono().get(1).getAssociatedNumber());
        phone3Field.setText(contatto.getNumeriDiTelefono().get(2).getAssociatedNumber());
        email1Field.setText(contatto.getEmail().get(0).getAssociatedEmail());
        email2Field.setText(contatto.getEmail().get(1).getAssociatedEmail());
        email3Field.setText(contatto.getEmail().get(2).getAssociatedEmail());
        //descriptionField.setText(contatto.getDescrizione());



    }

    @FXML
    private void activeSave(ActionEvent event) {
        clearAllFields();
        tableView.getSelectionModel().clearSelection();
    }

}
