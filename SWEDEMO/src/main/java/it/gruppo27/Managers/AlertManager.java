package it.gruppo27.Managers;
/** @file AlertManager.java
  * @brief File per la gestione degli alert , per far sapere l'esito di un'operazione o per chiedere una conferma all'utente      
*/
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.Optional;


public class AlertManager {

    /**
     * @brief Mostra un alert , per rendere noto all'utente l'esito di un'operazione
     * @param title titolo dell'alert
     * @param header intestazione dell'alert
     * @param content contenuto dell'alert
     * @post Sullo schermo appare un alert , con titolo , intestazione e contenuto che cambiano a seconda 
     * dell'esito e del tipo di operazione
     *
     */ 
    public static void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    /**
     * @brief Mostra un alert per chiedere conferma all'utente per avviare l'operazione selezionata
     * @param title titolo dell'alert
     * @param header intestazione dell'alert
     * @param content contenuto dell'alert
     * @post Sullo schermo appare un alert di conferma. Se l'utente seleziona "OK" l'operazione selezionata viene
     * svolta , se l'utente seleziona "Annulla" , l'operazione selezionata viene annullata.
     */ 
    public static boolean showConfirmation(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && (result.get() == ButtonType.OK);
    }

}
