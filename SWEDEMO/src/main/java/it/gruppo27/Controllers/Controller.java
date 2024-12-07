package it.gruppo27.Controllers;

import it.gruppo27.Contact.Contatto;
import it.gruppo27.Rubrica;
import it.gruppo27.interfaces.InterfaceRubrica;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rubrica = new Rubrica();
        initializeControllers();
        datiVBox.setVisible(false);
    }

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

    @FXML
    private void addLista(ActionEvent event) {
        formController.addLista();
    }

    @FXML
    private void deleteLista(ActionEvent event) {
        formController.deleteLista();
    }

    @FXML
    private void deleteAll(ActionEvent event) {
        tableController.deleteAll();
        datiVBox.setVisible(false);
    }

    @FXML
    private void saveFile(ActionEvent event) {
        fileController.saveFile();
    }

    @FXML
    private void uploadFile(ActionEvent event) {
        fileController.uploadFile();
    }

    @FXML
    private void eseguiModifica(ActionEvent event) {
        formController.eseguiModifica();
    }

    @FXML
    private void favoritesClick(ActionEvent event) {
        searchController.favoritesClick();
    }

    @FXML
    private void activeSave(ActionEvent event){
        tableView.getSelectionModel().clearSelection(); // Deseleziona eventuali elementi selezionati
        deleteButton.setVisible(false);
        applyButton.setVisible(false);
        datiVBox.setVisible(true);
        formController.clearAllFields();
    }

}




    


    
    

