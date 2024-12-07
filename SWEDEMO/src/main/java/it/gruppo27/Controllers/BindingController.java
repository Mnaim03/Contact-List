package it.gruppo27.Controllers;

import it.gruppo27.Contact.Contatto;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class BindingController {
    private final VBox datiVBox;
    private final TextField nameField;
    private final TextField surnameField;
    private final TextField[] phoneFields;
    private final TextField[] emailFields;
    private final Button saveButton;
    private final Button applyButton;
    private final Button favoritesButton;
    private CheckBox favouriteCheckBox;
    private final TextField searchBarField;
    private TableView<Contatto> tableView;
    private final TextField descriptionField;
    private final ContactFormController contactFormController;

    public BindingController(TextField nameField, TextField surnameField,
                             TextField[] phoneFields, TextField[] emailFields,
                             Button saveButton, Button applyButton,
                             Button favoritesButton, TextField searchBarField,TableView<Contatto> view,ContactFormController contactFormController,
                             VBox datiVBox,CheckBox favouriteCheckBox, TextField descriptionField) {
        this.nameField = nameField;
        this.surnameField = surnameField;
        this.phoneFields = phoneFields;
        this.emailFields = emailFields;
        this.saveButton = saveButton;
        this.applyButton = applyButton;
        tableView=view;
        this.favoritesButton = favoritesButton;
        this.searchBarField = searchBarField;
        this.contactFormController = contactFormController;
        this.datiVBox = datiVBox;
        this.favouriteCheckBox =favouriteCheckBox;
        this.descriptionField = descriptionField;
    }

    public void initBindings() {
        initSaveButtonBinding();
        initApplyButtonBinding();
        initFavoritesButtonBinding();
        initTableView();
        
        
    }

    private void initSaveButtonBinding() {
        BooleanBinding visible = tableView.getSelectionModel().selectedItemProperty().isNull()
                .and(nameField.textProperty().isNotEmpty().or(surnameField.textProperty().isNotEmpty()));

        saveButton.visibleProperty().bind(visible);

    }

    private void initApplyButtonBinding() {
        BooleanBinding applyButtonBinding = Bindings.createBooleanBinding(
                ()-> !(nameField.getText().isEmpty() && surnameField.getText().isEmpty()) && contactFormController.verificaCambiamenti(),
                nameField.textProperty(),
                surnameField.textProperty(),
                phoneFields[0].textProperty(),
                phoneFields[1].textProperty(),
                phoneFields[2].textProperty(),
                emailFields[0].textProperty(),
                emailFields[1].textProperty(),
                emailFields[2].textProperty(),
                descriptionField.textProperty(),
                favouriteCheckBox.selectedProperty()
        );

        applyButton.disableProperty().bind(applyButtonBinding.not());
    }

    private void initFavoritesButtonBinding() {
        BooleanBinding favoritesButtonBinding = Bindings.createBooleanBinding(
                () -> !searchBarField.getText().isEmpty(),
                searchBarField.textProperty()
        );
        favoritesButton.disableProperty().bind(favoritesButtonBinding);
    }
    
    private void initTableView(){
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Contatto selectedContact = tableView.getSelectionModel().getSelectedItem();
                if (selectedContact != null) {
                    contactFormController.populateFields(selectedContact);
                    datiVBox.setVisible(true);
                    applyButton.setVisible(true);
                }
            }
        });
    }
}
