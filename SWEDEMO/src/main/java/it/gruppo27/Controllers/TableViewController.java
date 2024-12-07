package it.gruppo27.Controllers;

import it.gruppo27.Contact.Contatto;
import it.gruppo27.Managers.AlertManager;
import it.gruppo27.interfaces.InterfaceRubrica;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableViewController {
    private final TableView<Contatto> tableView;
    private final TableColumn<Contatto,String> nomeClm;
    private final TableColumn<Contatto,String> surnameClm;
    private final InterfaceRubrica rubrica;

    public TableViewController(TableView<Contatto> tableView,
                               TableColumn<Contatto,String> nomeClm,
                               TableColumn<Contatto,String> surnameClm,
                               InterfaceRubrica rubrica) {
        this.tableView = tableView;
        this.nomeClm = nomeClm;
        this.surnameClm = surnameClm;
        this.rubrica = rubrica;
    }

    public void initializeTable() {
        tableView.setItems(rubrica.getListaOsservabile());
        nomeClm.setCellValueFactory(new PropertyValueFactory<>("nome"));
        surnameClm.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        tableView.setPlaceholder(new Label("Nessun contatto nella tabella"));
    }

    public void deleteAll() {
        if(rubrica.getContatti().size() > 0) {
            Boolean confirm = AlertManager.showConfirmation("Alert", "Are you sure you want to delete All?", "");
            if (confirm) {
                rubrica.deleteAll();
                updateView();
                AlertManager.showAlert("Success", "Done", "Addressbook deleted successfully");
            } else {
                AlertManager.showAlert("Cancelled", "Operation cancelled", "Operation cancelled by user");
            }
        } else {
            AlertManager.showAlert("Error", "No contacts in addressbook", "Add New Contact to Delete All");
        }
    }

    public void updateView() {
        rubrica.getListaOsservabile().setAll(rubrica.getContatti());
    }
}
