package com.example;

import com.example.Contact.ContactEmail;
import com.example.Contact.ContactNumero;
import com.example.Contact.Contatto;
import com.example.Exceptions.DuplicatedNumberException;
import com.example.Exceptions.InvalidEmailException;
import com.example.Exceptions.InvalidNumberException;
import com.example.Rubrica;
import com.example.interfaces.InterfaceRubrica;

import java.awt.event.MouseEvent;
import java.io.File;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import static com.example.Flag.NUMERO_DUPLICATO;

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
    private TextField descriptionField;
    @FXML
    private Button saveButton;
    @FXML
    private Button applyButton;
    @FXML
    private Button deleteButton;

    private InterfaceRubrica rubrica;
    @FXML
    private Button deleteAllButton;
    

    /**
     * @brief function that runs at the start of the algorithm
     *
     * See also: initBinding(), updateSearchResults(), apriFinestraDettagli()
     *
     * @param[in] url first parameter of type URL
     * @param[in] rb second parameter of type ResourceBundle
     * @see initBinding()
     * @see updateSearchResults()
     * @see apriFinestraDettagli()
     */
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

    /**
     * @brief function that updates the search result
     *
     * See also: ricercaContatti()
     *
     * @param[in] query parameter of type String
     * @see ricercaContatti()
     */
    private void updateSearchResults(String query){
        if(query==null){
            rubrica.getListaOsservabile().addAll(rubrica.getContatti());
        }
        Rubrica rubricaNuova = rubrica.ricercaContatti(query);
        rubrica.getListaOsservabile().setAll(rubricaNuova.getContatti());
        
    }


    @FXML
    private void addLista(ActionEvent event) {
        applyButton.setVisible(false);
        deleteButton.setVisible(false);
        Contatto contattoDigitato = new Contatto(nameField.getText(),surnameField.getText());
        
        try {
            aggiungiCellAlContattoDigitato(contattoDigitato);
        } catch (InvalidNumberException ex) {
            showAlert(Flag.INVALID_NUMBER);
            return;
        }catch(DuplicatedNumberException ex){
            showAlert(NUMERO_DUPLICATO);
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

    /*
     * commento
     * */
    private void aggiungiCellAlContattoDigitato(Contatto c) throws InvalidNumberException,DuplicatedNumberException {
        // Verifica se il campo phone1Field non è vuoto
        TextField [] phoneFields ={phone1Field,phone2Field,phone3Field};

        for(TextField field: phoneFields){
            if(field.getText() != null && !field.getText().trim().isEmpty()){
                ContactNumero numero = new ContactNumero(field.getText().trim());
                if(!numero.isValidNumber()) throw new InvalidNumberException("Numero non valido");
                if(c.getNumeriDiTelefono().contains(numero)) throw new DuplicatedNumberException("Numero Duplicato");
                c.addNumero(numero);
            }
        }
    }

    /*
     * commento
     * */
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

    /*
     * commento
     * */
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

    /*
     * commento
     * */
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

    /*
     * commento
     * */
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
        else if(flag==flag.ELIMINAZIONE_RUBRICA){
            alert.setHeaderText("Operazione completata!");
            alert.setContentText("Rubrica eliminata con successo !");
        }
        else if(flag==flag.FILE_NULL){
            alert.setHeaderText("Operazione annullata!");
            alert.setContentText("Operazione annullata");
        }
        else if(flag==flag.ERRORE_CARICAMENTO_FILE){
            alert.setHeaderText("Errore!");
            alert.setContentText("Errore nel caricamento del file!");
        }
        else if(flag==flag.ERRORE_SALVATAGGIO_FILE){
            alert.setHeaderText("Errore!");
            alert.setContentText("Errore nel salvataggio del file!");
        }
        else if(flag==flag.INVALID_EMAIL){
        alert.setContentText("La mail digitata non è corretta");
        }
        else if(flag==flag.FILE_SALVATAGGIO){
        alert.setHeaderText("Operazione completata!");
        alert.setContentText("Salvataggio file avvenuto con successo!");
        }
        else if(flag==NUMERO_DUPLICATO){
            alert.setHeaderText("ATTENZIONE");
            alert.setContentText("Hai già salvato il numero una volta , non puoi avere più numeri uguali");
        }
        // Mostra l'alert e attendi la chiusura
        alert.showAndWait();
    }

    /*
     * commento
     * */
    private void initBindings(){
        BooleanBinding saveButtonBinding = Bindings.createBooleanBinding(()-> (nameField.getText().isEmpty() && surnameField.getText().isEmpty()) ||
                                                                    tableView.getSelectionModel().selectedItemProperty().isNotNull().getValue(),
                                                                    tableView.getSelectionModel().selectedItemProperty(),
                                                                    nameField.textProperty(),
                                                                    surnameField.textProperty());
        saveButton.visibleProperty().bind(saveButtonBinding.not());
    
    
        BooleanBinding applyButtonBinding = Bindings.createBooleanBinding( () -> verificaCambiamenti(),nameField.textProperty() , surnameField.textProperty(),
                                                                    phone1Field.textProperty(),phone2Field.textProperty(),
                                                                    phone3Field.textProperty(),email1Field.textProperty(),
                                                                    email2Field.textProperty(), email3Field.textProperty());
        applyButton.disableProperty().bind(applyButtonBinding.not());
    }

    /*
     * commento
     * */
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
    }

    /*
     * commento
     * */
    @FXML
    private void activeSave(ActionEvent event) {
        datiVBox.setVisible(true);
        applyButton.setVisible(false);
        deleteButton.setVisible(false);
        clearAllFields();
        tableView.getSelectionModel().clearSelection();
    }

    /*
     * commento
     * */
    @FXML
    private void eseguiModifica(ActionEvent event) {

        Contatto daModificare = tableView.getSelectionModel().getSelectedItem();
        if (daModificare == null) {
            showAlert(Flag.OPERAZIONE_ANNULLATA);
            return;
        } else if ( (!phone1Field.getText().isEmpty() && !(new ContactNumero(phone1Field.getText()).isValidNumber())) ||
                    (!phone2Field.getText().isEmpty() && !(new ContactNumero(phone2Field.getText()).isValidNumber())) ||
                    (!phone3Field.getText().isEmpty() && !(new ContactNumero(phone3Field.getText()).isValidNumber())) ){
            showAlert(Flag.INVALID_NUMBER);
            showAlert(Flag.OPERAZIONE_ANNULLATA);
            return;
        }else if ( (!email1Field.getText().isEmpty() && !(new ContactEmail(email1Field.getText()).isValidEmail())) ||
                   (!email2Field.getText().isEmpty() && !(new ContactEmail(email2Field.getText()).isValidEmail())) ||
                   (!email3Field.getText().isEmpty() && !(new ContactEmail(email3Field.getText()).isValidEmail())) ){
            showAlert(Flag.INVALID_EMAIL);
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

    /*
     * commento
     * */
   private boolean verificaCambiamenti() {
    Contatto contattoSelezionato = tableView.getSelectionModel().getSelectedItem();

    if (contattoSelezionato == null) return false;

    // Verifica cambiamenti nel nome e cognome
    boolean isChangedNome = !contattoSelezionato.getNome().equals(nameField.getText());
    boolean isChangedCognome = !contattoSelezionato.getCognome().equals(surnameField.getText());

    // Verifica cambiamenti nei numeri di telefono
    List<ContactNumero> numeri = contattoSelezionato.getNumeriDiTelefono();
    boolean isChangedNumeri =
        ( (numeri.size() > 0 && ( !phone1Field.getText().equals(numeri.get(0).getAssociatedNumber()) || !phone2Field.getText().isEmpty() || !phone3Field.getText().isEmpty())) ||
        (numeri.size() > 1 && ( !phone1Field.getText().equals(numeri.get(0).getAssociatedNumber()) || !phone2Field.getText().equals(numeri.get(1).getAssociatedNumber()) || !phone3Field.getText().isEmpty() ) ) ||
        (numeri.size() > 2 && ( !phone1Field.getText().equals(numeri.get(0).getAssociatedNumber()) || !phone2Field.getText().equals(numeri.get(1).getAssociatedNumber())  ||!phone3Field.getText().equals(numeri.get(2).getAssociatedNumber())) )  ||
        (numeri.isEmpty() && (!phone1Field.getText().isEmpty() || !phone2Field.getText().isEmpty() || !phone3Field.getText().isEmpty()))  );

    // Verifica cambiamenti nelle email
    List<ContactEmail> emails = contattoSelezionato.getEmail();
    boolean isChangedEmails =
        ( (emails.size() > 0 && (!email1Field.getText().equals(emails.get(0).getAssociatedEmail()) || !email2Field.getText().isEmpty() || !email3Field.getText().isEmpty() )) ||
        (emails.size() > 1 && ( !email1Field.getText().equals(emails.get(0).getAssociatedEmail()) || !email2Field.getText().equals(emails.get(1).getAssociatedEmail()) || !email3Field.getText().isEmpty())) ||
        (emails.size() > 2 && ( !email1Field.getText().equals(emails.get(0).getAssociatedEmail()) || !email2Field.getText().equals(emails.get(1).getAssociatedEmail()) || !email3Field.getText().equals(emails.get(2).getAssociatedEmail())) ) ||
        (emails.isEmpty() && (!email1Field.getText().isEmpty() || !email2Field.getText().isEmpty() || !email3Field.getText().isEmpty())) );

    // Restituisci true se ci sono cambiamenti
    return isChangedNome || isChangedCognome || isChangedNumeri || isChangedEmails;
    }

    @FXML
    private void deleteAll(ActionEvent event){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Conferma Operazione");
        alert.setHeaderText("Sei sicuro di voler procedere (Eliminazione rubrica)?");
        alert.setContentText("Clicca OK per confermare, oppure Annulla per annullare l'operazione.");

        // Mostra l'alert e attendi la risposta dell'utente
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
           rubrica.deleteAll();
            showAlert(Flag.ELIMINAZIONE_RUBRICA);
            clearAllFields();
            datiVBox.setVisible(false);
        } else {
            // L'utente ha cliccato Annulla o ha chiuso la finestra
           
            showAlert(Flag.OPERAZIONE_ANNULLATA);
            return;
        }

    }

    @FXML
    private void uploadFile(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Seleziona un file vcf");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("VCF Files", "*.vcf"));
        Stage stage = (Stage) datiVBox.getScene().getWindow();
        File selectedFile = fc.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                // Additional check to ensure the file exists and is readable
                if (!selectedFile.exists() || !selectedFile.canRead()) {
                    showAlert(Flag.ERRORE_CARICAMENTO_FILE);
                    return;
                }

                // Clear existing contacts before loading
                rubrica.deleteAll();

                // Use absolute path to ensure fresh file read
                rubrica.getContatti().addAll(rubrica.leggiVCF(selectedFile.getAbsolutePath()).getContatti());
                rubrica.getListaOsservabile().setAll(rubrica.getContatti());
            } catch (IOException e) {
                showAlert(Flag.ERRORE_CARICAMENTO_FILE);
            } catch (InvalidEmailException e) {
                showAlert(Flag.INVALID_EMAIL);
            } catch (InvalidNumberException e) {
                showAlert(Flag.INVALID_NUMBER);
            }
        } else {
            showAlert(Flag.FILE_NULL);
        }
    }
        
        @FXML
        private void saveFile(ActionEvent event) throws IOException{
            try{
                rubrica.salvaVCF("contatti.vcf");
            }
            catch(IOException ex){
                showAlert(Flag.ERRORE_SALVATAGGIO_FILE);
            }
            showAlert(Flag.FILE_SALVATAGGIO);
        }
        
}
    


    
    

