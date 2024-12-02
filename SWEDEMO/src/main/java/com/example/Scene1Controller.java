package com.example;

import com.example.Contact.ContactEmail;
import com.example.Contact.ContactNumero;
import com.example.Contact.Contatto;
import com.example.Exceptions.InvalidEmailException;
import com.example.Exceptions.InvalidNumberException;
import com.example.Rubrica;
import com.example.interfaces.InterfaceRubrica;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
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
    private VBox datiVBox;
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

    private InterfaceRubrica rubrica;  

    private ObservableList<Contatto>contatti;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         initBindings();
         datiVBox.setVisible(false);
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
                    datiVBox.setVisible(true);
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
        
    }

   

    @FXML
    /***
     * Aggiunge , alla pressione del pulsante new , il contatto digitato nella rubrica.
     * Sfruttando isPresent() , verifica se il contatto digitato è già presente nella rubrica.In tal caso viene mostrato un messaggio di errore
     * altrimenti , viene aggiunto il contatto , notificato da un alert con messaggio di conferma di inserimento del contatto 
     
     */
    private void addLista(ActionEvent event) {
        applyButton.setVisible(false);
        deleteButton.setVisible(false);
        Contatto contattoDigitato = new Contatto(nameField.getText(),surnameField.getText());
        
        try {
            aggiungiCellAlContattoDigitato(contattoDigitato);
        } catch (InvalidNumberException ex) {
            showAlert(Flag.INVALID_NUMBER);
            return;
        }
        try {
            aggiungiEmailsAlContattoDigitato(contattoDigitato);
        } catch (InvalidEmailException ex) {
            showAlert(Flag.INVALID_EMAIL);
            return;
        }
        if(rubrica.isPresent(contattoDigitato)==Flag.CONTACT_EXISTS){
        showAlert(Flag.CONTACT_EXISTS);
        return; 
        }
        rubrica.addContatto(contattoDigitato);
        rubrica.getListaOsservabile().setAll(rubrica.getContatti()); //Aggiorna la lista ad ogni inserimento col treeSet , per mantenere l'ordine lessicografico
        showAlert(Flag.AGGIUNTO);
        clearAllFields();
        datiVBox.setVisible(false);
    }
        
    
    /**
     * Metodo privato , usato dal metodo addLista , per aggiungere i numeri di cellulari digitati al nuovo contatto in fase di creazione
     * @param c 
     */
    private void aggiungiCellAlContattoDigitato(Contatto c) throws InvalidNumberException {
    // Verifica se il campo phone1Field non è vuoto
  TextField [] phoneFields ={phone1Field,phone2Field,phone3Field};
  for(TextField field: phoneFields){
  if(field.getText() != null && !field.getText().trim().isEmpty()){
      ContactNumero numero = new ContactNumero(field.getText().trim());
      if(!numero.isValidNumber()) throw new InvalidNumberException("Numero non valido");
      c.addNumero(numero);

    }
    }
    }

    
   private void aggiungiEmailsAlContattoDigitato(Contatto c) throws InvalidEmailException {
    TextField[] emailFields = {email1Field, email2Field, email3Field};
    for (TextField field : emailFields) {
        if (field.getText() != null && !field.getText().trim().isEmpty()) {
            ContactEmail email = new ContactEmail(field.getText().trim());
            if(!email.isValidEmail()) throw new InvalidEmailException("Email non valida");
            c.addEmail(email);
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
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Conferma Operazione");
        alert.setHeaderText("Sei sicuro di voler procedere?");
        alert.setContentText("Clicca OK per confermare, oppure Annulla per annullare l'operazione.");

        // Mostra l'alert e attendi la risposta dell'utente
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
           rubrica.removeContatto(daRimuovere);
            rubrica.getListaOsservabile().setAll(rubrica.getContatti()); //Aggiorna la lista ad ogni rimozione
            showAlert(Flag.ELIMINATO);
            tableView.getSelectionModel().clearSelection();
            clearAllFields();
            datiVBox.setVisible(false);
        } else {
            // L'utente ha cliccato Annulla o ha chiuso la finestra
           
            showAlert(Flag.OPERAZIONE_ANNULLATA);
            return;
        }
                
        
    }
    
    
    
    /**
     * Verifica se il contatto digitato è già presente nella rubrica.
     * Restituisce 1 se già presente , 0 altrimenti 
    
    */
    
    /****
     * @brief: Funzione che , ricevuto in input un flag(che se 1 indica che il contatto digitato è già presente, se 0 viceversa), mostra un alert
     * customizzato a seconda dello stato del flag passato. Se il flag è 0 , il contatto digitato non è già presente in rubrica , dunque si procede con il 
     * messaggio di successo 
    
    */
    private void showAlert(Flag flag) {
    // Creazione di un alert di tipo informazione
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Warning!");
    if(flag==flag.CONTACT_EXISTS){
    alert.setHeaderText("Contatto già presente!");
    alert.setContentText("Esiste già un contatto "+nameField.getText()+" "+surnameField.getText()+ "con le informazioni che hai digitato");
    }
    else if(flag==flag.AGGIUNTO){
    alert.setHeaderText("Operazione completata!");
    alert.setContentText("Contatto"+" " +nameField.getText()+" "+surnameField.getText()+" aggiunto con successo !");
    }
    
    else if(flag==flag.ELIMINATO) {
    alert.setHeaderText("Operazione completata!");
    alert.setContentText("Contatto rimosso con successo !");
    }
    
    else if(flag==flag.MODIFICATO) {
    alert.setHeaderText("Operazione completata!");
    alert.setContentText("Contatto modificato con successo !");
    }
    
    else if(flag==flag.INVALID_EMAIL) {
    alert.setHeaderText("Errore!");
    alert.setContentText("Email non valida!");
    }
    else if(flag==flag.INVALID_NUMBER) {
    alert.setHeaderText("Errore!");
    alert.setContentText("Numero non valido!");
    }
    else if(flag==flag.OPERAZIONE_ANNULLATA){
    alert.setHeaderText("Operazione annullata!");
    alert.setContentText("Contatto non eliminato!");
    }
    
    
    
    else
    {
        alert.setContentText("La mail digitata non è corretta");
    }
        
    // Mostra l'alert e attendi la chiusura
    alert.showAndWait();
}
    
    private void initBindings(){

    BooleanBinding unione = Bindings.createBooleanBinding(()-> (nameField.getText().isEmpty() && surnameField.getText().isEmpty()) ||
                                                               tableView.getSelectionModel().selectedItemProperty().isNotNull().getValue(),
                                                               tableView.getSelectionModel().selectedItemProperty(), nameField.textProperty(), surnameField.textProperty());

    saveButton.visibleProperty().bind(unione.not());
    
    
      BooleanBinding apply = Bindings.createBooleanBinding( () -> verificaCambiamenti(),nameField.textProperty() , surnameField.textProperty(),
                                     phone1Field.textProperty(),phone2Field.textProperty(),phone3Field.textProperty(),email1Field.textProperty(),email2Field.textProperty(),
                                                                    email3Field.textProperty());
      applyButton.disableProperty().bind(apply.not());

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

    private void apriFinestraDettagli(Contatto contatto) {
    datiVBox.setVisible(true);
    applyButton.setVisible(true);
    deleteButton.setVisible(true);
    nameField.setText(contatto.getNome());
    surnameField.setText(contatto.getCognome());
    
    phone1Field.setText(contatto.getNumeriDiTelefono().size() > 0 ? contatto.getNumeriDiTelefono().get(0).getAssociatedNumber() : "");
    phone2Field.setText(contatto.getNumeriDiTelefono().size() > 1 ? contatto.getNumeriDiTelefono().get(1).getAssociatedNumber() : "");
    phone3Field.setText(contatto.getNumeriDiTelefono().size() > 2 ? contatto.getNumeriDiTelefono().get(2).getAssociatedNumber() : "");

    email1Field.setText(contatto.getEmail().size() > 0 ? contatto.getEmail().get(0).getAssociatedEmail() : "");
    email2Field.setText(contatto.getEmail().size() > 1 ? contatto.getEmail().get(1).getAssociatedEmail() : "");
    email3Field.setText(contatto.getEmail().size() > 2 ? contatto.getEmail().get(2).getAssociatedEmail() : "");

    // Puoi gestire altri campi qui
}


    @FXML
    private void activeSave(ActionEvent event) {
        datiVBox.setVisible(true);
        applyButton.setVisible(false);
        deleteButton.setVisible(false);
        clearAllFields();
        tableView.getSelectionModel().clearSelection();
    }

    @FXML
    private void eseguiModifica(ActionEvent event) {
        
        Contatto daModificare = tableView.getSelectionModel().getSelectedItem();
        if (daModificare == null) {
            showAlert(Flag.OPERAZIONE_ANNULLATA);
            return;
        }

        // Aggiorna nome e cognome
        daModificare.setNome(nameField.getText());
        daModificare.setCognome(surnameField.getText());

        // Aggiorna numeri di telefono
        List<ContactNumero> numeri = daModificare.getNumeriDiTelefono();
        numeri.clear(); // Pulisci i vecchi numeri

        TextField[] phoneFields = {phone1Field, phone2Field, phone3Field};
        for (TextField field : phoneFields) {
            if (field.getText() != null && !field.getText().trim().isEmpty()) {
                numeri.add(new ContactNumero(field.getText().trim()));
            }
        }

        // Aggiorna email
        List<ContactEmail> emails = daModificare.getEmail();
        emails.clear(); // Pulisci le vecchie email

        TextField[] emailFields = {email1Field, email2Field, email3Field};
        for (TextField field : emailFields) {
            if (field.getText() != null && !field.getText().trim().isEmpty()) {
                emails.add(new ContactEmail(field.getText().trim()));
            }
        }

        // Aggiorna la tabella con la lista modificata
        applyButton.setVisible(false);
        showAlert(Flag.MODIFICATO);
        updateSearchResults(searchBarField.getText());
        datiVBox.setVisible(false);
        
        
    }

        

   private boolean verificaCambiamenti() {
    Contatto contattoSelezionato = tableView.getSelectionModel().getSelectedItem();

    if (contattoSelezionato == null) return false;

    // Verifica cambiamenti nel nome e cognome
    boolean isChangedNome = !contattoSelezionato.getNome().equals(nameField.getText());
    boolean isChangedCognome = !contattoSelezionato.getCognome().equals(surnameField.getText());

    // Verifica cambiamenti nei numeri di telefono
    List<ContactNumero> numeri = contattoSelezionato.getNumeriDiTelefono();
    boolean isChangedNumeri = 
        (numeri.size() > 0 && !phone1Field.getText().equals(numeri.get(0).getAssociatedNumber())) ||
        (numeri.size() > 1 && !phone2Field.getText().equals(numeri.get(1).getAssociatedNumber())) ||
        (numeri.size() > 2 && !phone3Field.getText().equals(numeri.get(2).getAssociatedNumber())) ||
        (numeri.size() < 3 && (!phone1Field.getText().isEmpty() || !phone2Field.getText().isEmpty() || !phone3Field.getText().isEmpty()));

    // Verifica cambiamenti nelle email
    List<ContactEmail> emails = contattoSelezionato.getEmail();
    boolean isChangedEmails =
        (emails.size() > 0 && !email1Field.getText().equals(emails.get(0).getAssociatedEmail())) ||
        (emails.size() > 1 && !email2Field.getText().equals(emails.get(1).getAssociatedEmail())) ||
        (emails.size() > 2 && !email3Field.getText().equals(emails.get(2).getAssociatedEmail())) ||
        (emails.size() < 3 && (!email1Field.getText().isEmpty() || !email2Field.getText().isEmpty() || !email3Field.getText().isEmpty()));

    // Restituisci true se ci sono cambiamenti
    return isChangedNome || isChangedCognome || isChangedNumeri || isChangedEmails;
}
}

    
    

