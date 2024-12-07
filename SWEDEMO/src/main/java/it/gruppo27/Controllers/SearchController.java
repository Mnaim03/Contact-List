package it.gruppo27.Controllers;

import it.gruppo27.Contact.Contatto;
import it.gruppo27.interfaces.InterfaceRubrica;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class SearchController {
    private final TextField searchBarField;
    private final Button favoritesButton;
    private final InterfaceRubrica rubrica;


    public SearchController(TextField searchBarField, Button favoritesButton, InterfaceRubrica rubrica) {
        this.searchBarField = searchBarField;
        this.favoritesButton = favoritesButton;
        this.rubrica = rubrica;
    }

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