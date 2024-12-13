package it.gruppo27.Controllers;

/**
 * @file SearchController.java
 */

import it.gruppo27.Models.Contact.Contatto;
import it.gruppo27.interfaces.InterfaceRubrica;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe SearchController gestisce l'interfaccia di ricerca e la visualizzazione
 * dei contatti in una rubrica, permettendo di filtrare i contatti per nome,cognome , o per una sottostringa di questi
 * o di visualizzare
 * solo i contatti preferiti.
 */

public class SearchController {
    private final TextField searchBarField;
    private final Button favoritesButton;
    private final InterfaceRubrica rubrica;


    /**
     *  Costruttore della classe SearchController.
     *
     * @param[in] searchBarField Campo di testo per la ricerca.
     * @param[in] favoritesButton Pulsante per visualizzare i preferiti.
     * @param[in] rubrica Oggetto che implementa l'interfaccia InterfaceRubrica.
     *
     * @post Viene creata una nuova istanza di SearchController .
     */
    public SearchController(TextField searchBarField, Button favoritesButton, InterfaceRubrica rubrica) {
        this.searchBarField = searchBarField;
        this.favoritesButton = favoritesButton;
        this.rubrica = rubrica;
    }

    /**
     *  Inizializza il comportamento della barra di ricerca,permettendo di cercare i contatti in
     * base alla sottostringa digitata.Ad ogni cambiamento nella barra di ricerca , che sia l'aggiunta di un nuovo
     * carattere alla query di ricerca oppure l'eliminazione di un carattere, viene effettuato l'aggiornamento
     * della lista osservabile con i nuovi risultati corrispondenti alla ricerca. Si mantiene quindi sempre coerente
     * lo stato della rubrica con quello della lista osservabile
     *
     *
     *
     *
     *
     * @see rubrica.ricercaContatti() , per l'algoritmo implementato per la ricerca dei contatti
     *
     * @post Viene aggiornata la tabella ad ogni cambiamento della query inserita per la ricerca
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
     * Alterna la visualizzazione dei contatti tra tutti i contatti e i contatti preferiti.
     *
     * Questo metodo viene attivato da un clic sul pulsante "favoritesButton". A seconda
     * del testo attuale del pulsante, esegue una delle seguenti azioni:
     * 1. Filtra e visualizza solo i contatti preferiti, aggiornando il testo del pulsante
     *    a "See all contacts".
     * 2. Ripristina l'intera lista dei contatti, aggiornando il testo del pulsante a "Favourites".
     *
     * Funzionalità:
     * - Se il testo del pulsante è "Favourites", il metodo:
     *   - Cambia il testo del pulsante in "See all contacts".
     *   - Crea una lista temporanea di contatti preferiti (usando il metodo `getFavourite` di ogni contatto).
     *   - Aggiorna la lista osservabile di contatti nella rubrica con i contatti preferiti.
     * - Altrimenti, il metodo:
     *   - Cambia il testo del pulsante in "Favourites".
     *   - Ripristina la lista osservabile di contatti nella rubrica con l'intera lista di contatti.
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