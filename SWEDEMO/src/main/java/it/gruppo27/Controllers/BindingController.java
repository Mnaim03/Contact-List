package it.gruppo27.Controllers;
/**
 * @file BindingController.java
 * 
 */
import it.gruppo27.Models.Contact.Contatto;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * @class BindingController
 * @brief Classe con l'obiettivo di inizializzare i bindings e gli event listeners utili per la gestione dei componenti nell'UI
 */
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

    /**
     * @brief Costruttore della classe BindingController.
     * @param[in] nameField Campo di testo per il nome.
     * @param[in] surnameField Campo di testo per il cognome.
     * @param[in] phoneFields Array di campi di testo per i numeri di telefono.
     * @param[in] emailFields Array di campi di testo per gli indirizzi email.
     * @param[in] saveButton Pulsante per salvare un contatto.
     * @param[in] applyButton Pulsante per applicare modifiche.
     * @param[in] favoritesButton Pulsante per filtrare i preferiti.
     * @param[in] searchBarField Campo di ricerca.
     * @param[in] view Tabella per i contatti.
     * @param[in] contactFormController Controller per il modulo dei contatti.
     * @param[in] datiVBox Contenitore dei dettagli del contatto.
     * @param[in] favouriteCheckBox CheckBox per contatti preferiti.
     * @param[in] descriptionField Campo di testo per la descrizione.
     *
     * @post viene creata una nuova istanza di BindingController
     */
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

    /**
    * @brief Inizializza i bindings che coinvolgono i componenti saveButton , applyButton , FavouritesButton
    * e il gestore dell'evento per il componente tableView
    * @post tutti i binding e i gestori saranno inizializzati,dunque i componenti reagiranno in base a come l'utente
    * interagisce con l'applicazione , rispettando la logica su cui si basano i bindings
    */ 
    public void initBindings() {
        initSaveButtonBinding();
        initApplyButtonBinding();
        initFavoritesButtonBinding();
        initTableView();
    }

    /**
     * @brief Inizializza il binding che gestisce la visibilità del pulsante di salvataggio.
     * @post Il pulsante di salvataggio sarà visibile solo se nessun contatto è selezionato (dunque l'utente sta creando 
     * un nuovo contatto) e i campi nome o cognome contengono testo(infatti non sarà possibile creare un contatto senza ne nome e cognome).
     */
    private void initSaveButtonBinding() {
        BooleanBinding visible = tableView.getSelectionModel().selectedItemProperty().isNull()
                .and(nameField.textProperty().isNotEmpty().or(surnameField.textProperty().isNotEmpty()));

        saveButton.visibleProperty().bind(visible);

    }

    /**
     * @brief Inizializza il binding per l'abilitazione del pulsante dapply.
     * @post Il pulsante sarà abilitato se almeno uno tra nameField e surnameField contiene testo
     * e sono state apportate modifiche al contatto
     */
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
    
    /**
     * @brief Inizializza il binding per l'abilitazione del pulsante dei preferiti.
     * @post Il pulsante per la visualizzazione dei contatti 
     * preferiti sarà abilitato solo se il campo di ricerca non contiene testo.
     */  
    private void initFavoritesButtonBinding() {
        BooleanBinding favoritesButtonBinding = Bindings.createBooleanBinding(
                () -> !searchBarField.getText().isEmpty(),
                searchBarField.textProperty()
        );
        favoritesButton.disableProperty().bind(favoritesButtonBinding);
    }
    
    /**
     * @brief Configura la tabella per gestire eventi di selezione dei contatti.
     * @pre deve esserci almeno un contatto da poter selezionare all'interno della tabella.
     * @post Quando un contatto all'interno della tabella 
     * viene cliccato due volte, i suoi dettagli vengono mostrati nel modulo.
     */
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
