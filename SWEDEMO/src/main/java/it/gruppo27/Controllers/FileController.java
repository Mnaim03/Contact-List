package it.gruppo27.Controllers;

/**
 * @file FileController.java
 * @brief Gestisce il caricamento e il salvataggio dei file della rubrica.
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
 * @brief Classe che gestisce operazioni sui file come salvataggio e caricamento della rubrica su file .vcf .
 */
public class FileController {
    private final InterfaceRubrica rubrica;

    /**
     * @brief Costruttore della classe FileController.
     * @param[in] rubrica Oggetto che implementa l'interfaccia InterfaceRubrica.
     *
     * @pre rubrica non puà essere null
     * @post L'oggetto FileController è inizializzato con una rubrica valida.
     */
    public FileController(InterfaceRubrica rubrica) {
        this.rubrica = rubrica;
    }

    /**
     * @brief Salva i contatti della rubrica in un file VCF.
     *
     * @post Un file VCF è stato creato e contiene tutti i contatti della rubrica presenti al momento del salvataggio.
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
     * @brief Carica i contatti da un file VCF selezionato dall'utente.
     *
     * @pre L'utente deve selezionare un file valido.
     * @post La rubrica contiene tutti i contatti del file caricato.
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
