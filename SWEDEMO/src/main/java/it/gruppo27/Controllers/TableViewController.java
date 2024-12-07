package it.gruppo27.Controllers;
/**
 * @file TableViewController.java
 * @brief Controller per gestire le operazioni sulla table view presente nell'interfaccia grafica.
 */
import it.gruppo27.Models.Contact.Contatto;
import it.gruppo27.Managers.AlertManager;
import it.gruppo27.interfaces.InterfaceRubrica;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * @see InterfaceRubrica per l'interfaccia implementata da Rubrica
 */


public class TableViewController {
    private final TableView<Contatto> tableView;
    private final TableColumn<Contatto,String> nomeClm;
    private final TableColumn<Contatto,String> surnameClm;
    private final InterfaceRubrica rubrica;
    
    /**
     * @brief Costruttore della classe TableViewController.
     * @param tableView Tabella da gestire.
     * @param nomeClm Colonna per il nome.
     * @param surnameClm Colonna per il cognome.
     * @param rubrica Rubrica associata alla tabella.
     */
    public TableViewController(TableView<Contatto> tableView,
                               TableColumn<Contatto,String> nomeClm,
                               TableColumn<Contatto,String> surnameClm,
                               InterfaceRubrica rubrica) {
        this.tableView = tableView;
        this.nomeClm = nomeClm;
        this.surnameClm = surnameClm;
        this.rubrica = rubrica;
    }
    
    /**
     * @brief Inizializza la tabella e imposta i dati.
     *
     * Configura le colonne della tabella e assegna la lista osservabile dei contatti.
     * Imposta anche un messaggio di placeholder se la tabella è vuota.
     * 
     * @pre tableView , nomeClm , surnameClm non possono essere null.
     * @post mostra una tableView vuota, con due colonne ,che cambia in base alle modifiche della lista osservabile dei contatti
     */
    public void initializeTable() {
        tableView.setItems(rubrica.getListaOsservabile());
        nomeClm.setCellValueFactory(new PropertyValueFactory<>("nome"));
        surnameClm.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        tableView.setPlaceholder(new Label("Nessun contatto nella tabella"));
    }
    
    /**
     * @brief Eliminazione di tutti i contatti dalla rubrica.
     *
     * Mostra  un messaggio di conferma per chiedere all'utente se vuole procedere con l'eliminazione dell'intera rubrica.
     * 
     * @pre ci deve essere almeno un contatto salvato all'interno della rubrica
     * @post mostra una tableView vuota.
     */
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
    /**
     * @brief Aggiorna la vista della tabella.
     *
     * Reimposta la lista osservabile legata alla tabella con i contatti correnti presenti nella rubrica.
     * @post all'utente viene mostrata una table view aggiornata in base alle operazioni eseguite.
     */
    public void updateView() {
        rubrica.getListaOsservabile().setAll(rubrica.getContatti());
    }
}
