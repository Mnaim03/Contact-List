package it.gruppo27.Controllers;

/**
 * @file FileController.java
 */

import it.gruppo27.Exceptions.InvalidEmailException;
import it.gruppo27.Exceptions.InvalidNumberException;
import it.gruppo27.Managers.AlertManager;
import it.gruppo27.interfaces.InterfaceRubrica;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;

/**
 * @class FileController
 * @brief Classe che gestisce operazioni sui file quali salvataggio e caricamento della rubrica su file .vcf .
 */
public class FileController {
    private final InterfaceRubrica rubrica;

    /**
     * @brief Costruttore della classe FileController.
     * @param[in] rubrica Oggetto che implementa l'interfaccia InterfaceRubrica.
     *
     * 
     * @post Viene creata una nuova istanza di FileController. 
     */
    public FileController(InterfaceRubrica rubrica) {
        this.rubrica = rubrica;
    }

    /**
     * @brief Salva i contatti della rubrica in un file VCF.
     *
     * @post Viene creato un file .vcf  che contiene tutti i contatti della rubrica presenti al momento del salvataggio.
     */
    public void saveFile() {
        try {
            rubrica.salvaVCF("LaMiaRubrica.vcf");
            AlertManager.showAlert("Success", "File Saved", "Contacts saved successfully");
        } catch (IOException ex) {
            AlertManager.showAlert("Error", "Save Failed", ex.getMessage());
        }
    }

    /**
     * Carica i contatti da un file VCF selezionato dall'utente.
     * La rubrica precedente verrà sovrascritta dalla rubrica caricata
     *
     * @pre L'utente deve selezionare un file VCF il cui formato sia valido.
     * @post La rubrica contiene tutti i contatti del file caricato. 
     *
     */
    public void uploadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona un file");
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                String filePath = file.getAbsolutePath(); // Get the selected file's path
                InterfaceRubrica letta = rubrica.leggiVCF(filePath); // Use the file path
                rubrica.getContatti().clear(); //pulisci contatti
                rubrica.getContatti().addAll(letta.getContatti()); //aggiorno struttura dati
                rubrica.getListaOsservabile().setAll(letta.getContatti());
                AlertManager.showAlert("Success", "File Loaded", "Contacts loaded successfully");
            } catch (IOException | InvalidEmailException | InvalidNumberException e) {
                AlertManager.showAlert("Error", "Loading Failed", e.getMessage());
            }
        }
    }
}
