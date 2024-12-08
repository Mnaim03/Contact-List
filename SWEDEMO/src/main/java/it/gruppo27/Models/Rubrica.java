package it.gruppo27.Models;
import ezvcard.property.Note;
import it.gruppo27.Models.Contact.ContactEmail;
import it.gruppo27.Models.Contact.ContactNumero;
import it.gruppo27.Models.Contact.Contatto;
import it.gruppo27.Exceptions.InvalidEmailException;
import it.gruppo27.Exceptions.InvalidNumberException;
import it.gruppo27.interfaces.InterfaceRubrica;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.property.Telephone;
import ezvcard.parameter.TelephoneType;
import ezvcard.property.Email;

import java.util.List;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Rubrica implements InterfaceRubrica{
    private Set<Contatto> contatti;
    private ObservableList<Contatto> listContatti;
    
    public Rubrica() {
        this.contatti = new TreeSet<>();
        listContatti = FXCollections.observableArrayList();
    }
    
/**
 * Aggiunge un nuovo contatto alla rubrica. COntemporaneamente tiene aggiornata la lista e il treeSet
 */
    public void addContatto(Contatto contatto) {
        contatti.add(contatto);
    }
/**
 * @brief rimuove un contatto dalla rubrica 
 * @param contatto 
 */
    public void removeContatto(Contatto contatto) {
        contatti.remove(contatto);
    }
    
    public ObservableList<Contatto> getListaOsservabile(){
    return listContatti; 
    }

    /**
     * @brief metodo getter
     * @return il set dei contatti della rubrica al momento della chiamata
     */
    public Set<Contatto> getContatti(){
        return contatti;
    }

    /**
     * @brief Ricerca il contatto in base a un sottostringa immessa in input
     * @param stringa la sottostringa del nome o del cognome digitata
     * @return la nuova rubrica contenente tutti i contatti corrispondenti alla ricerca
     */
    public Rubrica ricercaContatti(String stringa){
    Rubrica restituita = new Rubrica(); 
    String nomecognome; 
    String cognomenome; 
    for(Contatto tmp : contatti){
        nomecognome = tmp.getNome()+" "+tmp.getCognome(); 
        cognomenome = tmp.getCognome()+" "+tmp.getNome();
        System.out.println(nomecognome);
        if(nomecognome.contains(stringa) || cognomenome.contains(stringa)){
        restituita.addContatto(tmp);
        }
    } 
    return restituita; 
    }


    public boolean isPresent(Contatto c){
        for(Contatto tmp : contatti){
        if(tmp.compareTo(c)==0) return true;
        }
        return false;
        
        
    }

    public void deleteAll(){
        contatti.clear();
        listContatti.clear();
    }
    

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer("La mia rubrica: "+"\n");
        for(Contatto c : contatti){
            sb.append(c.toString()+"\n");
        }
        return sb.toString();
    }


    public void salvaVCF(String filename) throws IOException {
        try(FileWriter fw = new FileWriter(filename)){
        for(Contatto contatto : contatti){
        VCard card = new VCard();
        card.setFormattedName(contatto.getNome()+" "+contatto.getCognome());
        aggiungiNumeriDiTelefonoAVCard(contatto,card);
        if(contatto.getFavourite()) card.addNote("FAVORITO");
        aggiungiEmailsAVCard(contatto,card);
        //Salva la descrizione se c'è
        if (contatto.getDescrizione() != null && !contatto.getDescrizione().isEmpty()) {
                card.addExtendedProperty("X-DESCRIPTION", contatto.getDescrizione());
            }
        Ezvcard.write(card).go(fw);
        }
        }
    }

    private void aggiungiNumeriDiTelefonoAVCard(Contatto c,VCard card){
        for(ContactNumero numero : c.getNumeriDiTelefono()){
       Telephone telefono = new Telephone(numero.getAssociatedNumber());
       if(numero.isNumeroDiCasa()) telefono.getTypes().add(TelephoneType.HOME);
       else{
       telefono.getTypes().add(TelephoneType.CELL);
       }
       card.addTelephoneNumber(telefono);
        }
    }



    /**
     * Metodo invocato in fase di creazione di una vCard. Aggiunge tutte le email  del contatto alla vCard
    */
    private void aggiungiEmailsAVCard(Contatto c , VCard card){
        for(ContactEmail tmp : c.getEmail()){
            Email vCardEmail = new Email(tmp.getAssociatedEmail());
            card.addEmail(vCardEmail);
        }


    }
    
    
    
    
    
    //Lettura da file .vcf contenente le VCard 

    public Rubrica leggiVCF(String filename) throws IOException, InvalidEmailException, InvalidNumberException {
    Rubrica r = new Rubrica();
    try (FileReader fr = new FileReader(filename)) {
        List<VCard> vcards = Ezvcard.parse(fr).all();
        Contatto c = null;
        for (VCard card : vcards) {
            // Handle name parsing safely
            String fullName = card.getFormattedName().getValue();

            String nome;
            try {
                nome = card.getFormattedName().getValue().split(" ")[0];
            } catch (ArrayIndexOutOfBoundsException ex) {
                nome = "";
            }
            String descrizione ="";
;
            if (card.getExtendedProperty("X-DESCRIPTION") != null) {
                descrizione = card.getExtendedProperty("X-DESCRIPTION").getValue();
            }
            //Lo split se non trova niente lancia eccezione.

            String cognome;
            try {
                cognome = card.getFormattedName().getValue().split(" ")[1];
            } catch (ArrayIndexOutOfBoundsException ex) {
                cognome = "";
            }


            c = new Contatto(nome, cognome, descrizione, false);

            // Add phone numbers
            for (Telephone tmp : card.getTelephoneNumbers()) {
                c.addNumero(new ContactNumero(tmp.getText()));
            }

            // Add emails
            for (Email em : card.getEmails()) {
                c.addEmail(new ContactEmail(em.getValue()));
            }
            //Verifico se è tra i favoriti il contatto
            for (Note nota : card.getNotes()) {
                if (nota.getValue().equals("FAVORITO")) c.setFavoriti(true);
            }
        }

        r.addContatto(c);
    }

        return r;
    }

}
   



