package it.gruppo27.Models;

/**
 * @file Rubrica.java
 * @brief File contenente la classe Rubrica per la gestione di una rubrica dei contatti
 * <p>
 * Questo file definisce la classe Rubrica, che implementa un sistema di gestione
 * dei contatti con funzionalità di aggiunta, rimozione, ricerca e esportazione.
 * @see Contatto
 * @see ContactEmail
 * @see ContactNumero
 */

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

/**
 * @class Rubrica
 * @brief Rappresenta una Rubruca o anche detta address book
 *
 * Questa classe gestisce un una collezzione di Contatti e operazione si quest'ultimi, come aggiunta
 * rimozione, ricerca e esportazione di contatti
 *
 * @note Fa uso di TreeSet per ordinamento efficente dei Contatti e una ObservableList per l'aggiornamento della UI
 */
public class Rubrica implements InterfaceRubrica{
    private Set<Contatto> contatti;
    private ObservableList<Contatto> listContatti;

    /**
     * @brief Costruttore della Classe Rubrica. Iniziallizza la rubrica con un TreeSet e una Observable List
     *
     * @post i contatti inizializzati sono TreeSet vuoti
     * @post la listContatti inizializzata è una ObservableList vuota
     */
    public Rubrica() {
        this.contatti = new TreeSet<>();
        listContatti = FXCollections.observableArrayList();
    }

    /**
     * @brief Aggiunge un nuovo contatto alla Rubrica.
     * Il Contatto viene sia aggiunto al TreeSet che all'ObservableList.
     *
     * @param[in] contatto che deve essere aggiunto
     *
     * @pre contatto non deve essere null
     * @post il contatto è stato aggiunto alla Rubrica
     * @post La dimensione della rubrica incrementa di 1
     */
    public void addContatto(Contatto contatto) {
        contatti.add(contatto);
    }

    /**
     * @brief Rimuove un contatto dalla Rubrica.
     * Il Contatto viene rimosso dal TreeSet e dall'ObservableList.
     *
     * @param[in] contatto che deve essere rimosso
     *
     * @pre contatto non deve essere null
     * @post il contatto è stato rimosso alla Rubrica
     * @post La dimensione della rubrica idecrementa di 1
     */
    public void removeContatto(Contatto contatto) {
        contatti.remove(contatto);
    }

    /**
     * @brief ritorna l'ObservableList dei contatti
     *
     * @return ObservableList contentenente tutti i contatti
     */
    public ObservableList<Contatto> getListaOsservabile(){
    return listContatti; 
    }

    /**
     * @brief Ritorna il Set dei Contatti
     *
     * @return Set contenente tutti i contti
     */
    public Set<Contatto> getContatti(){
        return contatti;
    }

    /**
     * @brief Data una sottostringa restituisce la Rubrica contenente i contatti
     * avetenti la sottostringa nel nome e/o cognome
     *
     * @param[in] stringa da cercre in Rubrica
     * @return Rubrica contente i esclusivamente i contatti avete al loro interno la sottostringa associata
     *
     * @pre stringa non null
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

    /**
     * @brief Verifica la presenza o assenza di un contatto
     *
     * @param[in] c contatto da verificare
     * @return booleano che indica la presenza o assenza di un contatto
     *
     * @pre c non deve essere null
     * @post Ritorna true se la presenza è confermata, altrimenti ritorna false
     */
    public boolean isPresent(Contatto c){
        for(Contatto tmp : contatti){
        if(tmp.compareTo(c)==0) return true;
        }
        return false;
        
        
    }

    /**
     * @brief Rimuove tutti i contatti dalla Rubrica
     *
     * @post contatti and listContatti si svuotano
     */
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

    /**
     * @brief Salve i contatti in un file VCF.
     * importa tutti i contatti in un formato VCF (Virtual Contact File).
     *
     * @param[in] filename indicante il percorso del file VCF
     * @throws IOException in caso di errore nella scrittura
     *
     * @pre filename deve essere un perscorso valido
     * @post tutti i contatti sono correttamente salvati
     */
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

    /**
     * @brief Aggiungi i numeri telefonoci a una VCard
     *
     * @param[in] c contatto con cui associare i numeri telefonici
     * @param card VCard a cui assorcare i numeri telefico
     *
     * @pre c non deve essere null
     * @pre card non deve essere null
     * @post i numeri telefonici sono stati associati/salvati correttamente
     */
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
     * @brief Aggiungi i le email a una VCard
     *
     * @param[in] c contatto con cui associare le email
     * @param card VCard a cui assorcare le emial
     *
     * @pre c non deve essere null
     * @pre card non deve essere null
     * @post le email sono state associate/salvate correttamente
     */
    private void aggiungiEmailsAVCard(Contatto c , VCard card){
        for(ContactEmail tmp : c.getEmail()){
            Email vCardEmail = new Email(tmp.getAssociatedEmail());
            card.addEmail(vCardEmail);
        }


    }

    /**
     * @brief legge i contatti in un file VCF.
     * Esporta tutti i contatti da un formato VCF (Virtual Contact File).
     *
     * @param[in] filename indicante il percorso del file VCF
     * @throws IOException in caso di errore nella lettura
     * @return Rubrica che contiene i contatti letti
     * @throws InvalidEmailException in caso viene lette un'email invalida
     * @throws InvalidNumberException in caso viene letto un numero telefonico invalido
     *
     * @pre filename deve essere un perscorso valido
     * @post tutti i contatti sono stati correttamenti letti
     */
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
   



