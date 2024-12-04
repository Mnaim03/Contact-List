package com.example.Managers;

import com.example.Contact.Contatto;
import com.example.Exceptions.DuplicatedContactException;
import com.example.Exceptions.DuplicatedNumberException;
import com.example.Exceptions.InvalidEmailException;
import com.example.Exceptions.InvalidNumberException;
import com.example.interfaces.InterfaceRubrica;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class FileManager {
    private final ContactViewManager contactManager;
    private final InterfaceRubrica IRubrica;
    

    public FileManager(ContactViewManager contactManager,InterfaceRubrica IRubrica) {
        this.contactManager = contactManager;
        this.IRubrica = IRubrica;
    }

    public void loadContacts(File file) throws IOException, InvalidNumberException, DuplicatedNumberException, InvalidEmailException{
        if (file == null || !file.canRead()) {
            throw new IOException("Errore nel caricamento del file.");
        }
        contactManager.clearAll();
        Set<Contatto> contacts = IRubrica.leggiVCF(file.getAbsolutePath()).getContatti();
        for (Contatto contatto : contacts) {
            try{
            contactManager.addContact(contatto);
            }catch(DuplicatedContactException ex){}
        }
    }


    public void saveContacts(String filePath) throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            throw new IOException("Percorso del file non valido.");
        }
        AlertManager alert = new AlertManager();
        IRubrica.salvaVCF(filePath);
        alert.showAlert("Salvataggio completato con successo","","Salvataggio completato");
        
    }
}

