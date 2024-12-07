package it.gruppo27.Controllers;
import it.gruppo27.Contact.ContactEmail;
import it.gruppo27.Contact.ContactNumero;
import it.gruppo27.Contact.Contatto;
import it.gruppo27.Exceptions.InvalidEmailException;
import it.gruppo27.Exceptions.InvalidNumberException;
import it.gruppo27.Managers.AlertManager;
import it.gruppo27.interfaces.InterfaceRubrica;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import it.gruppo27.Managers.*;

import java.util.List;

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

    public void deleteLista() {
        if (selectedContact != null) {
            Boolean confirm = AlertManager.showConfirmation("Alert", "Are you sure you want to delete this contact?", "");
            if (confirm) {
                rubrica.deleteAll();
                tableViewController.updateView();
                AlertManager.showAlert("Success", "Done", "Contact deleted successfully");
            } else {
                AlertManager.showAlert("Cancelled", "Operation cancelled", "Operation cancelled ");
            }
            rubrica.removeContatto(selectedContact);
            clearAllFields();
            datiVBox.setVisible(false);
            tableViewController.updateView();
        }
    }

    public void eseguiModifica() {
        if (selectedContact != null && verificaCambiamenti()) {
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
        } else {
            AlertManager.showAlert("Error", "No Modification Detected", "No changes were made to the contact.");
        }
    }

    public void clearAllFields() {
        nameField.clear();
        surnameField.clear();
        for (TextField field : phoneFields) field.clear();
        for (TextField field : emailFields) field.clear();
        descriptionField.clear();
        favouriteCheckBox.setSelected(false);
    }

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

    public void initialize(TableView<Contatto> tableView) {
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedContact = newValue;
                populateFields(selectedContact);
            }

        });
    }

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
