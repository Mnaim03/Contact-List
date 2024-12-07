package it.gruppo27.Controllers;
/**
 * @file BindingController.java
 * @brief Controller per la gestione dei binding tra i componenti della scena e la gestione degli eventi.
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
 * 
 * @see ContactFormController per la gestione del modulo dei contatti  
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
     * @param nameField Campo di testo per il nome.
     * @param surnameField Campo di testo per il cognome.
     * @param phoneFields Array di campi di testo per i numeri di telefono.
     * @param emailFields Array di campi di testo per gli indirizzi email.
     * @param saveButton Pulsante per salvare un contatto.
     * @param applyButton Pulsante per applicare modifiche.
     * @param favoritesButton Pulsante per filtrare i preferiti.
     * @param searchBarField Campo di ricerca.
     * @param view Tabella per i contatti.
     * @param contactFormController Controller per il modulo dei contatti.
     * @param datiVBox Contenitore dei dettagli del contatto.
     * @param favouriteCheckBox CheckBox per contatti preferiti.
     * @param descriptionField Campo di testo per la descrizione.
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
    * @brief Inizializza i binding che coinvolgono i componenti saveButton , applyButton , FavouritesButton
    * e il gestore dell'evento per il componente tableView
    * @post tutti i binding e i gestori saranno inizializzati , dunque i componenti reagiranno in base a come l'utente
    * interagisce con l'applicazione
    */ 
    public void initBindings() {
        initSaveButtonBinding();
        initApplyButtonBinding();
        initFavoritesButtonBinding();
        initTableView();
        
        
    }
    /**
     * @brief Inizializza il binding per la visibilità del pulsante di salvataggio.
     * @pre saveButton,nameField e surnameField non possono essere null 
     * @post Il pulsante di salvataggio sarà visibile solo se nessun contatto è selezionato (dunque l'utente sta creando 
     * un nuovo contatto) e i campi nome o cognome contengono testo.
     */
    private void initSaveButtonBinding() {
        BooleanBinding visible = tableView.getSelectionModel().selectedItemProperty().isNull()
                .and(nameField.textProperty().isNotEmpty().or(surnameField.textProperty().isNotEmpty()));

        saveButton.visibleProperty().bind(visible);

    }
    /**
     * @brief Inizializza il binding per l'abilitazione del pulsante di applicazione.
     * @pre nameField,surnameField,phoneFields , emailFields , descriptionField , favouriteCheckBox non devono essere null
     * @post Il pulsante sarà abilitato se almeno tra nameField e surnameField contiene testo
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
     * @pre searchBarField e favoritesButtonBinding non sono null 
     * @post Il pulsante sarà abilitato solo se il campo di ricerca non contiene testo.
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
     * tableView,datiVBox,contactFormController e applyButton non possono essere null.
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
