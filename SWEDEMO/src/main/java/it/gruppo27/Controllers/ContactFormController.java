/**
 * @file ContactFormController.java
 * @brief Classe che gestisce il modulo per l'aggiunta, modifica e rimozione dei contatti.
 */

package it.gruppo27.Controllers;
import it.gruppo27.Models.Contact.ContactEmail;
import it.gruppo27.Models.Contact.ContactNumero;
import it.gruppo27.Models.Contact.Contatto;
import it.gruppo27.Exceptions.InvalidEmailException;
import it.gruppo27.Exceptions.InvalidNumberException;
import it.gruppo27.Managers.AlertManager;
import it.gruppo27.interfaces.InterfaceRubrica;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.util.List;

/**
 * @class ContactFormController
 * @brief Controller per gestire il modulo dei contatti nella rubrica.
 *
 * @details Consente di aggiungere, modificare o rimuovere contatti, gestendo sia i dati di input sia la validazione.
 */
public class ContactFormController {
    private final VBox datiVBox;
    private final TextField nameField;
    private final TextField surnameField;
    private final TextField[] phoneFields;
    private final TextField[] emailFields;
    private final TextField descriptionField;
    private final CheckBox favouriteCheckBox;
    private final Button applyButton;
    private final Button deleteButton;
    private final InterfaceRubrica rubrica;
    private Contatto selectedContact;
    private final TableView tableView;
    private TableViewController tableViewController;

    /**
     * @brief Costruttore della classe ContactFormController.
     *
     * @param[in] datiVBox Contenitore del modulo di contatto.
     * @param[in] nameField Campo di testo per il nome.
     * @param[in] surnameField Campo di testo per il cognome.
     * @param[in] phoneFields Campi di testo per i numeri di telefono.
     * @param[in] emailFields Campi di testo per gli indirizzi email.
     * @param[in] descriptionField Campo di testo per la descrizione.
     * @param[in] favouriteCheckBox Checkbox per indicare i contatti preferiti.
     * @param[in] applyButton Pulsante per applicare modifiche.
     * @param[in] deleteButton Pulsante per eliminare un contatto.
     * @param[in] rubrica Interfaccia della rubrica.
     * @param[in] tableViewController Controller per la tabella dei contatti.
     * @param[in] tableView Tabella per visualizzare i contatti.
     *
     * @post Il modulo di contatto è stato inizializzato.
     */
    public ContactFormController(VBox datiVBox, TextField nameField, TextField surnameField,
                                 TextField[] phoneFields, TextField[] emailFields,
                                 TextField descriptionField, CheckBox favouriteCheckBox,
                                 Button applyButton, Button deleteButton,
                                 InterfaceRubrica rubrica,TableViewController tableViewController,TableView tableView) {
        this.datiVBox = datiVBox;
        this.nameField = nameField;
        this.surnameField = surnameField;
        this.phoneFields = phoneFields;
        this.emailFields = emailFields;
        this.descriptionField = descriptionField;
        this.favouriteCheckBox = favouriteCheckBox;
        this.applyButton = applyButton;
        this.deleteButton = deleteButton;
        this.rubrica = rubrica;
        this.tableViewController=tableViewController;
        this.tableView=tableView;
        this.initialize(tableView);
    }

    /**
     * @brief Aggiunge un nuovo contatto alla rubrica.
     *
     * @pre I campi di input devono contenere dati validi. Almeno uno tra il campo Nome e il campo Cognome deve essere
     * compilato
     * @post Un nuovo contatto è stato aggiunto alla rubrica.
     */
    public void addLista() {
        try {
            Contatto newContact = new Contatto(
                    nameField.getText(),
                    surnameField.getText(),
                    descriptionField.getText(),
                    favouriteCheckBox.isSelected()
            );

            aggiungiCellAlContattoDigitato(newContact);
            aggiungiEmailsAlContattoDigitato(newContact);

            if (rubrica.isPresent(newContact)) {
                AlertManager.showAlert("Error", "Duplicate Contact", "Contact already exists");
                return;
            }

            rubrica.addContatto(newContact);
            rubrica.getListaOsservabile().setAll(rubrica.getContatti());
            AlertManager.showAlert("Success","Contact Added","New contact added to Addressbook");
            clearAllFields();
            datiVBox.setVisible(false);
            

        } catch (InvalidNumberException e) {
            AlertManager.showAlert("Error", "Invalid Number", e.getMessage());
        } catch (InvalidEmailException e) {
            AlertManager.showAlert("Error", "Invalid Email", e.getMessage());
        }
    }

    /**
     * @brief Aggiunge numeri telefonici al contatto digitato.
     *
     * @param[in] contatto
     *
     * @pre Riceve un contatto valido
     * @post Il Contatto ha correttamente ricevuto i numeri telefonoci
     */
    private void aggiungiCellAlContattoDigitato(Contatto contatto) throws InvalidNumberException {
        for (TextField field : phoneFields) {
            if (!field.getText().trim().isEmpty()) {
                ContactNumero numero = new ContactNumero(field.getText().trim());
                if (!numero.isValidNumber()) {
                    throw new InvalidNumberException("Invalid phone number format");
                }
                contatto.addNumero(numero);
            }
        }
    }

    /**
     * @brief Aggiunge  emails al contatto digitato.
     *
     * @param[in] contatto
     *
     * @pre Riceve un contatto valido
     * @post Il Contatto ha correttamente ricevuto le email
     */
    private void aggiungiEmailsAlContattoDigitato(Contatto contatto) throws InvalidEmailException {
        for (TextField field : emailFields) {
            if (!field.getText().trim().isEmpty()) {
                ContactEmail email = new ContactEmail(field.getText().trim());
                if (!email.isValidEmail()) {
                    throw new InvalidEmailException("Invalid email format");
                }
                contatto.addEmail(email);
            }
        }
    }

    /**
     * @brief Elimina il contatto selezionato.
     *
     * @pre Un contatto deve essere selezionato nella tabella.
     * @post Il contatto selezionato viene rimosso dalla rubrica.
     */
    public void deleteLista() {
        if (selectedContact != null) {
            Boolean confirm = AlertManager.showConfirmation("Alert", "Are you sure you want to delete this contact?", "");
            if (confirm) {
                rubrica.removeContatto(selectedContact);
                clearAllFields();
                datiVBox.setVisible(false);
                tableViewController.updateView();
                AlertManager.showAlert("Success", "Done", "Contact deleted successfully");
            } else {
                AlertManager.showAlert("Cancelled", "Operation cancelled", "Operation cancelled ");
            }

        }
    }

    /**
     * @brief Aggiorna i dati di un contatto
     *
     * @pre Le informazioni modificate devono essere valide.
     * @post Il contatto modificato è stato aggiornato con le ultime modifiche.
     */
    public void eseguiModifica() {
        if (selectedContact != null) {
            if(verificaCambiamenti()) {
                try {
                    // Update the selected contact's details
                    selectedContact.setNome(nameField.getText());
                    selectedContact.setCognome(surnameField.getText());
                    selectedContact.setDescrizione(descriptionField.getText());
                    selectedContact.setFavoriti(favouriteCheckBox.isSelected());

                    // Update phone numbers
                    selectedContact.getNumeriDiTelefono().clear();
                    aggiungiCellAlContattoDigitato(selectedContact);

                    // Update emails
                    selectedContact.getEmail().clear();
                    aggiungiEmailsAlContattoDigitato(selectedContact);

                    // Refresh the observable list and UI
                    rubrica.getListaOsservabile().set(rubrica.getListaOsservabile().indexOf(selectedContact), selectedContact);
                    AlertManager.showAlert("Success", "Contact Modified", "Contact updated successfully");

                    // Clear fields and hide the form
                    clearAllFields();
                    datiVBox.setVisible(false);
                    applyButton.setVisible(false);
                } catch (InvalidNumberException | InvalidEmailException e) {
                    AlertManager.showAlert("Error", "Modification Failed", e.getMessage());
                }
            }
        }
    }

    /**
     * @brief Ripulisce le caselle testuali.
     *
     * @post Le caselle sono ripulite del testo che contenevano prima.
     */
    public void clearAllFields() {
        nameField.clear();
        surnameField.clear();
        for (TextField field : phoneFields) field.clear();
        for (TextField field : emailFields) field.clear();
        descriptionField.clear();
        favouriteCheckBox.setSelected(false);
    }

    /**
     * @brief Verifica se ci sono cambiamenti nel modulo del contatto selezionato .
     *
     * @return True se ci sono cambiamenti, false altrimenti.
     * @post Restituisce <code>true</code> se ci sono cambiamenti(tra i numeri di telefono ,emails , e altri dettagli del contatto selezionato), <code>false</code> altrimenti.
     */
    public boolean verificaCambiamenti() {
    
    if (selectedContact == null) return false;
    
    // Verifica cambiamenti nel nome e cognome
    boolean isChangedNome = !selectedContact.getNome().equals(nameField.getText());
    boolean isChangedCognome = !selectedContact.getCognome().equals(surnameField.getText());
    
    // Verifica cambiamenti nei numeri di telefono
    List<ContactNumero> numeri = selectedContact.getNumeriDiTelefono();
       boolean isChangedNumeri =
               ( (numeri.size() == 1 && ( !phoneFields[0].getText().equals(numeri.get(0).getAssociatedNumber()) || !phoneFields[1].getText().isEmpty() || !phoneFields[2].getText().isEmpty())) ||
                       (numeri.size() == 2 && ( !phoneFields[0].getText().equals(numeri.get(0).getAssociatedNumber()) || !phoneFields[1].getText().equals(numeri.get(1).getAssociatedNumber()) || !phoneFields[2].getText().isEmpty() ) ) ||
                       (numeri.size() == 3 && ( !phoneFields[0].getText().equals(numeri.get(0).getAssociatedNumber()) || !phoneFields[1].getText().equals(numeri.get(1).getAssociatedNumber())  ||!phoneFields[2].getText().equals(numeri.get(2).getAssociatedNumber())) )  ||
                       (numeri.isEmpty() && (!phoneFields[0].getText().isEmpty() || !phoneFields[1].getText().isEmpty() || !phoneFields[2].getText().isEmpty()))  );

       // Verifica cambiamenti nelle email
       List<ContactEmail> emails = selectedContact.getEmail();
       boolean isChangedEmails =
               ( (emails.size() == 1 && (!emailFields[0].getText().equals(emails.get(0).getAssociatedEmail()) || !emailFields[1].getText().isEmpty() || !emailFields[2].getText().isEmpty() )) ||
                       (emails.size() == 2 && ( !emailFields[0].getText().equals(emails.get(0).getAssociatedEmail()) || !emailFields[1].getText().equals(emails.get(1).getAssociatedEmail()) || !emailFields[2].getText().isEmpty())) ||
                       (emails.size() == 3 && ( !emailFields[0].getText().equals(emails.get(0).getAssociatedEmail()) || !emailFields[1].getText().equals(emails.get(1).getAssociatedEmail()) || !emailFields[2].getText().equals(emails.get(2).getAssociatedEmail())) ) ||
                       (emails.isEmpty() && (!emailFields[0].getText().isEmpty() || !emailFields[1].getText().isEmpty() || !emailFields[2].getText().isEmpty())) );

    boolean isChangedDescrizione = !selectedContact.getDescrizione().equals(descriptionField.getText());
    boolean isChangedFavourite = !(selectedContact.getFavourite() == favouriteCheckBox.isSelected());
    // Restituisci true se ci sono cambiamenti
    return isChangedNome || isChangedCognome || isChangedNumeri || isChangedEmails || isChangedDescrizione || isChangedFavourite;
}

    /**
     * @brief Inizializza il comportamento da rispettare quando si seleziona un elemento nella tabella.
     *
     * Metodo che ha l'obiettivo di visualizzare il modulo con tutti i campi riempiti con i dettagli del contatto , quando si seleziona quest'ultimo  nella tableView
     *
     * @param[in] tableView Tabella dei contatti.
     * @post I listener sono configurati per la selezione dei contatti.Quando si seleziona un contatto dalla tableView , vengono mostrati i dettagli nel modulo di fianco che compare
     */
    public void initialize(TableView<Contatto> tableView) {
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedContact = newValue;
                populateFields(selectedContact);
            }

        });
    }

    /**
     * @brief Popola i campi del modulo con i dettagli del contatto selezionato.
     *
     * @param[in] contact Il contatto da visualizzare.
     * @post I campi del modulo sono aggiornati con i dati del contatto.
     */
    public void populateFields(Contatto contact) {
        deleteButton.setVisible(true);
        applyButton.setVisible(true);
        nameField.setText(contact.getNome());
        surnameField.setText(contact.getCognome());
        descriptionField.setText(contact.getDescrizione());
        favouriteCheckBox.setSelected(contact.getFavourite());

        // Clear existing phone and email fields
        for (TextField field : phoneFields) field.clear();
        for (TextField field : emailFields) field.clear();

        // Populate phone fields
        List<ContactNumero> numeri = contact.getNumeriDiTelefono();
        for (int i = 0; i < numeri.size() && i < phoneFields.length; i++) {
            phoneFields[i].setText(numeri.get(i).getAssociatedNumber());
        }

        // Populate email fields
        List<ContactEmail> emails = contact.getEmail();
        for (int i = 0; i < emails.size() && i < emailFields.length; i++) {
            emailFields[i].setText(emails.get(i).getAssociatedEmail());
        }
    }

}
