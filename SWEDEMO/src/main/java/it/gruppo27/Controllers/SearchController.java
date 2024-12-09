package it.gruppo27.Controllers;

/**
 * @file SearchController.java
 * @brief Gestisce la ricerca e i preferiti nei contatti della rubrica.
 */

import it.gruppo27.Models.Contact.Contatto;
import it.gruppo27.interfaces.InterfaceRubrica;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

/**
 * @class SearchController
 * @brief Classe che gestisce la barra di ricerca e i preferiti della rubrica.
 * @invariant La rubrica può essere null
 */
public class SearchController {
    private final TextField searchBarField;
    private final Button favoritesButton;
    private final InterfaceRubrica rubrica;


    /**
     * @brief Costruttore della classe SearchController.
     *
     * @param[in] searchBarField Campo di testo per la ricerca.
     * @param[in] favoritesButton Pulsante per visualizzare i preferiti.
     * @param[in] rubrica Oggetto che implementa l'interfaccia InterfaceRubrica.
     *
     * @pre searchBarField != null, favoritesButton != null, rubrica != null
     * @post L'oggetto SearchController è inizializzato correttamente.
     */
    public SearchController(TextField searchBarField, Button favoritesButton, InterfaceRubrica rubrica) {
        this.searchBarField = searchBarField;
        this.favoritesButton = favoritesButton;
        this.rubrica = rubrica;
    }

    /**
     * @brief Inizializza il comportamento della barra di ricerca,permettendo di cercare i contatti in base alla sottostringa digitata
     *
     * See also: getContatti()
     *
     * @see ricercaContatti()
     *
     * @pre La rubrica deve contenere contatti.
     * @post In base al contenuto della barra di ricerca , viene aggiornata la lista dei contatti
     */
    public void initSearchBar() {
        searchBarField.textProperty().addListener((observable, oldValue, newValue) -> {
            rubrica.getListaOsservabile().setAll(
                    rubrica.ricercaContatti(newValue).getContatti()
            );
        });

        searchBarField.setOnMouseClicked(event -> {
            favoritesButton.setText("Favourites");
            rubrica.getListaOsservabile().setAll(
                    rubrica.ricercaContatti(searchBarField.getText()).getContatti()
            );
        });
    }

    /**
     * @brief Gestisce il clic sul pulsante dei preferiti.
     *
     *
     *
     *
     * @pre La rubrica deve contenere contatti.
     * @post Mostra i contatti preferiti o tutti i contatti, a seconda dello stato del pulsante.
     */
    public void favoritesClick() {
        if (favoritesButton.getText().equals("Favourites")) {
            favoritesButton.setText("See all contacts");
            List<Contatto> favoriteContacts = new ArrayList<>();
            for (Contatto c : rubrica.getContatti()) {
                if (c.getFavourite()) {
                    favoriteContacts.add(c);
                }
            }
            rubrica.getListaOsservabile().setAll(favoriteContacts);
        } else {
            favoritesButton.setText("Favourites");
            rubrica.getListaOsservabile().setAll(rubrica.getContatti());
        }
    }
}