/**
 * @file Controller.java
 * @brief Classe principale per gestire il flusso dell'applicazione della rubrica.
 */
package it.gruppo27.Controllers;

import it.gruppo27.Models.Contact.Contatto;
import it.gruppo27.Models.Rubrica;
import it.gruppo27.interfaces.InterfaceRubrica;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @class Controller
 * @brief Classe principale che integra le funzioni di gestione della rubrica.
 * @details Gestisce la tabella dei contatti, il modulo di modifica/aggiunta, e altre operazioni come salvataggio e caricamento.
 */
public class Controller implements Initializable {
    @FXML private TableView<Contatto> tableView;
    @FXML private TableColumn<Contatto,String> nomeClm;
    @FXML private TableColumn<Contatto,String> surnameClm;
    @FXML private VBox datiVBox;
    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private TextField phone1Field;
    @FXML private TextField phone2Field;
    @FXML private TextField phone3Field;
    @FXML private TextField email1Field;
    @FXML private TextField email2Field;
    @FXML private TextField email3Field;
    @FXML private TextField descriptionField;
    @FXML private TextField searchBarField;
    @FXML private CheckBox favouriteCheckBox;
    @FXML private Button favoritesButton;
    @FXML private Button applyButton;
    @FXML private Button deleteButton;
    @FXML private Button saveButton;

    private TableViewController tableController;
    private ContactFormController formController;
    private SearchController searchController;
    private FileController fileController;
    private BindingController bindingController;
    private InterfaceRubrica rubrica;

    /**
     * @brief Metodo di inizializzazione chiamato al caricamento del controller.
     *
     * @param[in] url L'URL utilizzato per risolvere i percorsi relativi.
     * @param[in] rb Il bundle di risorse per localizzare il controller.
     *
     * @post I controller secondari sono inizializzati e la vista è pronta.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rubrica = new Rubrica();
        initializeControllers();
        datiVBox.setVisible(false);
    }

    /**
     * @brief Inizializza i controller secondari.
     *
     * @post Tutti i controller secondari sono configurati e funzionanti.
     */
    private void initializeControllers() {
        tableController = new TableViewController(tableView, nomeClm, surnameClm, rubrica);

        TextField[] phoneFields = {phone1Field, phone2Field, phone3Field};
        TextField[] emailFields = {email1Field, email2Field, email3Field};

        formController = new ContactFormController(
                datiVBox, nameField, surnameField,
                phoneFields, emailFields,
                descriptionField, favouriteCheckBox,
                applyButton, deleteButton,
                rubrica,tableController,tableView
        );
        
        

        searchController = new SearchController(searchBarField, favoritesButton, rubrica);
        fileController = new FileController(rubrica);

        bindingController = new BindingController(
                nameField, surnameField,
                phoneFields, emailFields,
                saveButton, applyButton, favoritesButton,
                searchBarField,tableView,formController,datiVBox,favouriteCheckBox, descriptionField
        );

        tableController.initializeTable();
        searchController.initSearchBar();
        bindingController.initBindings();
    }

    /**
     * @brief Aggiunge un nuovo contatto alla rubrica.
     *
     * @param[in] event Evento generato dal clic sul pulsante.
     * @post Un nuovo contatto è stato aggiunto alla rubrica.
     */
    @FXML
    private void addLista(ActionEvent event) {
        formController.addLista();
    }

    /**
     * @brief Elimina il contatto selezionato.
     *
     * @param[in] event Evento generato dal clic sul pulsante.
     * @see formController.deleteLista() per ulteriori dettagli sull'implementazione dell'eliminazione di un contatto
     *
     * @post Il contatto selezionato è stato rimosso.
     */
    @FXML
    private void deleteLista(ActionEvent event) {
        formController.deleteLista();
    }

    /**
     * @brief Elimina tutti i contatti dalla rubrica.
     *
     * @param[in] event Evento generato dal clic sul pulsante
     * @see tableController.deleteAll() per ulteriori dettagli sull'implementazione dell'eliminazione di tutti i contatti
     *
     * @post La rubrica è stata svuotata.
     */
    @FXML
    private void deleteAll(ActionEvent event) {
        tableController.deleteAll();
        datiVBox.setVisible(false);
    }

    /**
     * @brief Salva i contatti su file.
     *
     * @param[in] event Evento generato dal clic sul pulsante.
     * @see fileController.saveFile() per l'implementazione effettiva del salvataggio sul file
     * @post I dati sono stati salvati su file.
     */
    @FXML
    private void saveFile(ActionEvent event) {
        fileController.saveFile();
    }

    /**
     * @brief Carica contatti da un file.
     *
     * @param[in] event Evento generato dal clic sul pulsante.
     *
     * @post I contatti sono stati caricati nella rubrica.
     */
    @FXML
    private void uploadFile(ActionEvent event) {
        fileController.uploadFile();
    }

    /**
     * @brief Modifica un contatto selezionato.
     *
     * @param[in] event Evento generato dal clic sul pulsante.
     *
     * @post I dettagli del contatto selezionato sono stati aggiornati.
     */
    @FXML
    private void eseguiModifica(ActionEvent event) {
        formController.eseguiModifica();
    }

    /**
     * @brief Gestisce il clic sul pulsante dei preferiti.
     *
     * @param[in] event Evento generato dal clic sul pulsante.
     *
     * @post Lo stato della lista dei preferiti è stato aggiornato.
     */
    @FXML
    private void favoritesClick(ActionEvent event) {
        searchController.favoritesClick();
    }

    /**
     * @brief Attiva il modulo di aggiunta di un nuovo contatto.
     *
     * @param[in] event Evento generato dal clic sul pulsante.
     *
     * @post Il modulo di inserimento contatti viene reso visibile , permettendo la creazione di nuovi contatti.
     */
    @FXML
    private void activeSave(ActionEvent event){
        tableView.getSelectionModel().clearSelection(); // Deseleziona eventuali elementi selezionati
        deleteButton.setVisible(false);
        applyButton.setVisible(false);
        datiVBox.setVisible(true);
        formController.clearAllFields();
    }

}




    


    
    

