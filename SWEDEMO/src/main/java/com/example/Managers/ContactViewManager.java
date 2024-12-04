package com.example.Managers;

import com.example.Contact.ContactEmail;
import com.example.Contact.ContactNumero;
import com.example.Contact.Contatto;
import com.example.Exceptions.DuplicatedNumberException;
import com.example.Exceptions.InvalidEmailException;
import com.example.Exceptions.InvalidNumberException;
import com.example.Exceptions.DuplicatedContactException; 
import com.example.Flag;
import com.example.interfaces.InterfaceRubrica;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

public class ContactViewManager {
    private InterfaceRubrica rubrica;

    public ContactViewManager(InterfaceRubrica rubrica) {
        this.rubrica = rubrica;
    }

    public ObservableList<Contatto> getObservableList() {
        return rubrica.getListaOsservabile();
    }

    public InterfaceRubrica getRubrica(){
        return rubrica;
    }

    public void addContact(Contatto contatto) throws DuplicatedContactException {
        if (rubrica.isPresent(contatto) == Flag.CONTACT_EXISTS) {
            throw new DuplicatedContactException("Contatto esistente");
            
        }
        rubrica.addContatto(contatto);
        updateObservableList();
    }

    public void removeContact(Contatto contatto) {
        rubrica.removeContatto(contatto);
        updateObservableList();
    }

    
    public void modificaContatto(Contatto contatto,String nome , String cognome,TextField[]numeriTextFields , TextField [] mailsTextFields){
    contatto.setNome(nome);
    contatto.setCognome(cognome);
     List<ContactNumero> numeri = contatto.getNumeriDiTelefono();
        numeri.clear(); // Pulisci i vecchi numeri
        
        for (TextField field : numeriTextFields) {
            if (field.getText() != null && !field.getText().trim().isEmpty()) {
                numeri.add(new ContactNumero(field.getText().trim()));
            }
        }

        // Aggiorna email
        List<ContactEmail> emails = contatto.getEmail();
        emails.clear(); // Pulisci le vecchie email

        for (TextField field : mailsTextFields) {
            if (field.getText() != null && !field.getText().trim().isEmpty()) {
                emails.add(new ContactEmail(field.getText().trim()));
            }
        }
        updateObservableList(); //Rifletti le modifiche anche nella tabella
    }
    
    
    
    public void clearAll() {
        rubrica.deleteAll();
        updateObservableList();
    }

    public void updateObservableList() {
        rubrica.getListaOsservabile().setAll(rubrica.getContatti());
    }


    }
