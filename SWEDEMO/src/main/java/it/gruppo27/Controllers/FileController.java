package it.gruppo27.Controllers;

import it.gruppo27.Exceptions.InvalidEmailException;
import it.gruppo27.Exceptions.InvalidNumberException;
import it.gruppo27.Managers.AlertManager;
import it.gruppo27.interfaces.InterfaceRubrica;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;

public class FileController {
    private final InterfaceRubrica rubrica;

    public FileController(InterfaceRubrica rubrica) {

        this.rubrica = rubrica;
    }

    public void saveFile() {
        try {
            rubrica.salvaVCF("LaMiaRubrica.vcf");
            AlertManager.showAlert("Success", "File Saved", "Contacts saved successfully");
        } catch (IOException ex) {
            AlertManager.showAlert("Error", "Save Failed", ex.getMessage());
        }
    }

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
