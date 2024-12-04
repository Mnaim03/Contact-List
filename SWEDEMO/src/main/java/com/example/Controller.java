package com.example;

import com.example.Contact.ContactEmail;
import com.example.Contact.ContactNumero;
import com.example.Contact.Contatto;
import com.example.Exceptions.DuplicatedContactException;
import com.example.Exceptions.DuplicatedNumberException;
import com.example.Exceptions.InvalidEmailException;
import com.example.Exceptions.InvalidNumberException;
import com.example.Managers.AlertManager;
import com.example.Managers.ContactViewManager;
import com.example.Managers.FileManager;
import com.example.interfaces.InterfaceRubrica;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class Controller implements Initializable {
    private ContactViewManager contactManager;
    private FileManager fileManager;
    private AlertManager alertManager;
    
    @FXML
    private TextField searchBarField;
    @FXML
    private TableView<Contatto> tableView;
    @FXML
    private TableColumn<Contatto,String> nomeClm;
    @FXML
    private TableColumn<Contatto,String> surnameClm;
    @FXML
    private TextField nameField, surnameField, phone1Field, phone2Field, phone3Field, email1Field, email2Field, email3Field;
    @FXML
    private VBox datiVBox;
    @FXML
    private Button applyButton, deleteButton, saveButton, favouriteButton;
    @FXML
    private CheckBox favouriteCheckBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        InterfaceRubrica rubrica = new Rubrica();
        contactManager = new ContactViewManager(rubrica);
        fileManager = new FileManager(contactManager, rubrica);
        alertManager = new AlertManager();

        tableView.setItems(contactManager.getObservableList());
        nomeClm.setCellValueFactory(new PropertyValueFactory("nome"));
        surnameClm.setCellValueFactory(new PropertyValueFactory("cognome"));
        initSearchBar();
        initBindings();
        initTableViewDoubleClick();
        datiVBox.setVisible(false);
        applyButton.setVisible(false); 
    }
//OK
    private void initSearchBar() {
        searchBarField.textProperty().addListener((observable, oldValue, newValue) -> {
            Rubrica filteredRubrica = contactManager.getRubrica().ricercaContatti(newValue);
            contactManager.getObservableList().setAll(filteredRubrica.getContatti());
        });
    }
//OK
    private void initTableViewDoubleClick() {
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Contatto selectedContact = tableView.getSelectionModel().getSelectedItem();
                if (selectedContact != null) {
                    populateDetailView(selectedContact);
                    datiVBox.setVisible(true);
                    applyButton.setVisible(true);
                }
            }
        });
    }
//OK
    private void populateDetailView(Contatto contatto) {
        datiVBox.setVisible(true);
        applyButton.setVisible(true);
        deleteButton.setVisible(true);
        nameField.setText(contatto.getNome());
        surnameField.setText(contatto.getCognome());
        // Popola campi telefonici ed email

        phone1Field.setText(contatto.getNumeriDiTelefono().size() > 0 ? contatto.getNumeriDiTelefono().get(0).getAssociatedNumber() : "");
        phone2Field.setText(contatto.getNumeriDiTelefono().size() > 1 ? contatto.getNumeriDiTelefono().get(1).getAssociatedNumber() : "");
        phone3Field.setText(contatto.getNumeriDiTelefono().size() > 2 ? contatto.getNumeriDiTelefono().get(2).getAssociatedNumber() : "");

        email1Field.setText(contatto.getEmail().size() > 0 ? contatto.getEmail().get(0).getAssociatedEmail() : "");
        email2Field.setText(contatto.getEmail().size() > 1 ? contatto.getEmail().get(1).getAssociatedEmail() : "");
        email3Field.setText(contatto.getEmail().size() > 2 ? contatto.getEmail().get(2).getAssociatedEmail() : "");
    }
    

    //Pulsante Save
    @FXML //OK
    private void addLista(ActionEvent event) {

        applyButton.setVisible(false);
        deleteButton.setVisible(false);
        Contatto contattoDigitato = new Contatto(nameField.getText(), surnameField.getText());
        //Controllo sui dati del contatto
        try {
            aggiungiCellAlContattoDigitato(contattoDigitato);
        } catch (InvalidNumberException ex) {
            alertManager.showAlert("Error", "Invalid Number", "You have have inserted an invalid number.");
            return;
        } catch (DuplicatedNumberException ex) {
            alertManager.showAlert("Error","Duplicated Contact","You can't have the same contact two times.");
            return;
        }

        try {
            aggiungiEmailsAlContattoDigitato(contattoDigitato);
        } catch (InvalidEmailException ex) {
            alertManager.showAlert("Error", "Invalid Email", "You have have inserted an invalid email.");
            return;
        }
//Controllo che il contatto non sia duplicato 
        try {
            contactManager.addContact(contattoDigitato);
        }catch(DuplicatedContactException ex){
            alertManager.showAlert("Error","Duplicated Contact","You can't have the same contact two times.");
            return;
        }
        alertManager.showAlert("Success","Operation Success","New Contact is correctly created.");
        clearAllFields();
        datiVBox.setVisible(false);
    }

//OK
    private void aggiungiCellAlContattoDigitato(Contatto c) throws InvalidNumberException, DuplicatedNumberException {
        // Verifica se il campo phone1Field non è vuoto
        TextField[] phoneFields = {phone1Field, phone2Field, phone3Field};

        for (TextField field : phoneFields) {
            if (field.getText() != null && !field.getText().trim().isEmpty()) {
                ContactNumero numero = new ContactNumero(field.getText().trim());
                if (!numero.isValidNumber()) throw new InvalidNumberException("Numero non valido");
                if (c.getNumeriDiTelefono().contains(numero)) throw new DuplicatedNumberException("Numero Duplicato");
                c.addNumero(numero);
            }
        }
    }

//OK
    private void aggiungiEmailsAlContattoDigitato(Contatto c) throws InvalidEmailException {
        TextField[] emailFields = {email1Field, email2Field, email3Field};
        for (TextField field : emailFields) {
            if (field.getText() != null && !field.getText().trim().isEmpty()) {
                ContactEmail email = new ContactEmail(field.getText().trim());
                if (!email.isValidEmail()) throw new InvalidEmailException("Email non valida");
                c.addEmail(email);
            }
        }
    }


    @FXML //OK
     private void deleteLista(ActionEvent event) {
        Contatto selectedContact = tableView.getSelectionModel().getSelectedItem();
        if (selectedContact != null && alertManager.showConfirmation("Conferma", "Elimina Contatto", "Vuoi eliminare il contatto?")) {
            contactManager.removeContact(selectedContact);
        }
        //Pulisci le fields dopo l'eliminazione
        clearAllFields();
    }

    @FXML //OK
    private void deleteAll(ActionEvent event) {
        contactManager.clearAll();
    }


    @FXML //OK
    private void saveFile(ActionEvent event) {
        try {
            fileManager.saveContacts("LaMiaRubrica.vcf");
        } catch (IOException ex) {
            return;
        }
    }

    @FXML
    private void eseguiModifica(ActionEvent event) {

        Contatto contattoDaModificare = tableView.getSelectionModel().getSelectedItem();
        if (contattoDaModificare == null) {
            new AlertManager().showAlert("Error","No Modification Detected","");
            return;
        } else if ( (!phone1Field.getText().isEmpty() && !(new ContactNumero(phone1Field.getText()).isValidNumber())) ||
                (!phone2Field.getText().isEmpty() && !(new ContactNumero(phone2Field.getText()).isValidNumber())) ||
                (!phone3Field.getText().isEmpty() && !(new ContactNumero(phone3Field.getText()).isValidNumber())) ){

            new AlertManager().showAlert("Error","Wrong phone number","Try again");
            return;
        }else if ( (!email1Field.getText().isEmpty() && !(new ContactEmail(email1Field.getText()).isValidEmail())) ||
                (!email2Field.getText().isEmpty() && !(new ContactEmail(email2Field.getText()).isValidEmail())) ||
                (!email3Field.getText().isEmpty() && !(new ContactEmail(email3Field.getText()).isValidEmail())) ){

            new AlertManager().showAlert("Error","Wrong email number","Try again");
            return;
        }

        TextField [] numeri = {phone1Field,phone2Field,phone3Field};
        TextField [] emails = {email1Field,email2Field,email3Field};
        contactManager.modificaContatto(contattoDaModificare, nameField.getText(), surnameField.getText(), numeri, emails);
        alertManager.showAlert("Sucess","Content modified","");
        contactManager.updateObservableList(); //Aggiorna la lista dopo la modifica 
        //Aggiorna la view 
        datiVBox.setVisible(false);
        applyButton.setVisible(false);
        tableView.getSelectionModel().clearSelection();  // Deseleziona il contatto
    }

    //OK
   @FXML
private void activeSave(ActionEvent event) {
    tableView.getSelectionModel().clearSelection(); // Deseleziona eventuali elementi selezionati
    deleteButton.setVisible(false); 
    applyButton.setVisible(false); 
    datiVBox.setVisible(true);
    clearAllFields();
}

    
    
    /**
     * Pulisce tutte le textfield
     * Questo metodo viene chiamato nel momento in cui si preme il pulsante "Save"
     * @post tutte le textfield sono vuote 
     */
    private void clearAllFields(){
    nameField.clear();
    surnameField.clear();
    phone1Field.clear();
    phone2Field.clear();
    phone3Field.clear();
    email1Field.clear();
    email2Field.clear();
    email3Field.clear();
    }
    

    @FXML //OK
    private void uploadFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona un file");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                fileManager.loadContacts(file);
                alertManager.showAlert("Successo", "File Caricato", "I contatti sono stati caricati con successo.");
            } catch (Exception e) {
                alertManager.showAlert("Errore", "Errore durante il caricamento.", e.getMessage());
            }
        }
 
    }
    //OK
   private void initBindings(){
    // Bind per il pulsante Save
    BooleanBinding visible = tableView.getSelectionModel().selectedItemProperty().isNull()
                            .and(nameField.textProperty().isNotEmpty().or(surnameField.textProperty().isNotEmpty()));

    saveButton.visibleProperty().bind(visible);

    // Bind per il pulsante Apply
       BooleanBinding applyButtonBinding = Bindings.createBooleanBinding( () -> verificaCambiamenti(),nameField.textProperty() , surnameField.textProperty(),
               phone1Field.textProperty(),phone2Field.textProperty(),
               phone3Field.textProperty(),email1Field.textProperty(),
               email2Field.textProperty(), email3Field.textProperty());
               applyButton.disableProperty().bind(applyButtonBinding.not());
    
    applyButton.disableProperty().bind(applyButtonBinding.not());
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
               ( (numeri.size() == 1 && ( !phone1Field.getText().equals(numeri.get(0).getAssociatedNumber()) || !phone2Field.getText().isEmpty() || !phone3Field.getText().isEmpty())) ||
                       (numeri.size() == 2 && ( !phone1Field.getText().equals(numeri.get(0).getAssociatedNumber()) || !phone2Field.getText().equals(numeri.get(1).getAssociatedNumber()) || !phone3Field.getText().isEmpty() ) ) ||
                       (numeri.size() == 3 && ( !phone1Field.getText().equals(numeri.get(0).getAssociatedNumber()) || !phone2Field.getText().equals(numeri.get(1).getAssociatedNumber())  ||!phone3Field.getText().equals(numeri.get(2).getAssociatedNumber())) )  ||
                       (numeri.isEmpty() && (!phone1Field.getText().isEmpty() || !phone2Field.getText().isEmpty() || !phone3Field.getText().isEmpty()))  );

       // Verifica cambiamenti nelle email
       List<ContactEmail> emails = contattoSelezionato.getEmail();
       boolean isChangedEmails =
               ( (emails.size() == 1 && (!email1Field.getText().equals(emails.get(0).getAssociatedEmail()) || !email2Field.getText().isEmpty() || !email3Field.getText().isEmpty() )) ||
                       (emails.size() == 2 && ( !email1Field.getText().equals(emails.get(0).getAssociatedEmail()) || !email2Field.getText().equals(emails.get(1).getAssociatedEmail()) || !email3Field.getText().isEmpty())) ||
                       (emails.size() == 3 && ( !email1Field.getText().equals(emails.get(0).getAssociatedEmail()) || !email2Field.getText().equals(emails.get(1).getAssociatedEmail()) || !email3Field.getText().equals(emails.get(2).getAssociatedEmail())) ) ||
                       (emails.isEmpty() && (!email1Field.getText().isEmpty() || !email2Field.getText().isEmpty() || !email3Field.getText().isEmpty())) );

    // Restituisci true se ci sono cambiamenti
    return isChangedNome || isChangedCognome || isChangedNumeri || isChangedEmails;
}

}






    


    
    

