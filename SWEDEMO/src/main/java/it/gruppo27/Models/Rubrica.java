package it.gruppo27.Models;

/**
 * @file Rubrica.java
 * @brief File contenente la classe Rubrica per la gestione di una rubrica dei contatti
 * <p>
 * 
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
 * @brief Classe che implementa un sistema di gestione
 * dei contatti con funzionalità di aggiunta, rimozione, ricerca di contatti dalla rubrica ed
 * esportazione e caricamento di un file .
 * 
 */
public class Rubrica implements InterfaceRubrica{
    private Set<Contatto> contatti;
    /**
     * @note Fa uso di TreeSet per ottenere un ordinamento efficente dei Contatti e non permettere doppioni.
     * Ordinamento e il fatto che due contatti siano considerati doppioni dipende dalla compareTo dell'oggetto Contatto.
     */ 
    private ObservableList<Contatto> listContatti;


    /**
     * @brief Costruttore della Classe Rubrica. Inizializza la rubrica con un TreeSet e una Observable List
     *
     * @post è stata creata una nuova istanza di rubrica.
     * Come struttura dati per accogliere gli oggetto Contatto è stato scelto un TreeSet
     * Come struttura dati per accogliere la lista di contatti in modo da facilitarci per la gestione della view è stata scelta una observableArrayList
     */
    public Rubrica() {
        this.contatti = new TreeSet<>();
        listContatti = FXCollections.observableArrayList();
    }

    /**
     * @brief Aggiunge un nuovo contatto alla Rubrica.
     * Il Contatto viene aggiunto al TreeSet 
     *
     * @param[in] contatto che deve essere aggiunto
     *
     * @pre contatto non deve essere null
     * @post il contatto è stato aggiunto al TreeSet
     * @post La dimensione della rubrica aumenta di 1
     */
    public void addContatto(Contatto contatto) {
        contatti.add(contatto);
    }

    /**
     * @brief Rimuove un contatto dalla Rubrica.
     *
     *
     * @param[in] contatto che deve essere rimosso
     *
     * @pre contatto non deve essere null
     * @post il contatto è stato rimosso alla Rubrica
     * @post La dimensione della rubrica diminuisce di 1
     */
    public void removeContatto(Contatto contatto) {
        contatti.remove(contatto);
    }

    /**
     * @brief ritorna l'ObservableList dei contatti
     *
     * @return ObservableList contenente tutti i contatti
     */
    public ObservableList<Contatto> getListaOsservabile(){
    return listContatti;
    }

    /**
     * @brief Ritorna il Set dei Contatti
     *
     * @return Set contenente tutti i contatti
     */
    public Set<Contatto> getContatti(){
        return contatti;
    }

    /**
     * @brief Data una sottostringa restituisce la Rubrica contenente i contatti
     * aventi la sottostringa nel nome o cognome
     *
     * @param[in] stringa  la query di ricerca , in base alla quale filtrare la rubrica
     * @return Rubrica contenente esclusivamente i contatti aventi al loro interno la sottostringa passata
     *
     * 
     */
    public Rubrica ricercaContatti(String stringa){
    Rubrica restituita = new Rubrica(); 
    String nomecognome; 
    String cognomenome; 
    for(Contatto tmp : contatti){
        nomecognome = tmp.getNome()+" "+tmp.getCognome(); 
        cognomenome = tmp.getCognome()+" "+tmp.getNome();

        if(nomecognome.contains(stringa) || cognomenome.contains(stringa)){
        restituita.addContatto(tmp);
        }
    } 
    return restituita; 
    }

    /**
     * @brief Verifica la presenza o assenza di un contatto all'interno della rubrica
     *
     * @param[in] c contatto di cui verificare la presenza
     * @return <code>true</code> se il contatto è presente ,<code>false</code> altrimenti
     *
     *
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
     * @post contatti e listContatti si svuotano
     */
    public void deleteAll(){
        contatti.clear();
        listContatti.clear();
    }

    /**
     * Metodo che elabora la rappresentazione testuale della rubrica
     * @return String la rappresentazione testuale dello stato della rubrica
     */
    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer("La mia rubrica: "+"\n");
        for(Contatto c : contatti){
            sb.append(c.toString()+"\n");
        }
        return sb.toString();
    }

    /**
     * @brief Salva i contatti in un file VCF.
     * Esporta tutti i contatti in un formato VCF  e li salva all'interno di un file.
     *Il file può essere aperto utilizzando una qualsiasi applicazione di gestione dei contatti che supporta il formato VCF
     *su qualsiasi sistema operativo.
     * @param[in] filename indicante il percorso del file VCF
     * @throws IOException in caso di errore nella scrittura
     *
     * @pre filename deve essere un percorso valido
     * @post tutti i contatti sono correttamente salvati all'interno del file il cui nome è passato come parametro.
     *  che supporta il formato VCF
     * su qualsiasi sistema operativo.
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
     * @brief Aggiungi i numeri di telefono a una VCard
     * Associa i numeri di telefono alla VCard , settando opportunamente il tipo di numero : Cellulare o Casa
     * @param[in] c contatto con cui associare i numeri telefonici
     * @param card VCard a cui associare i numeri telefonici
     *
     * @pre c non deve essere null
     * @pre card non deve essere null
     * @post i numeri telefonici sono stati associati correttamente alla VCard
     */
    private void aggiungiNumeriDiTelefonoAVCard(Contatto c,VCard card){
        for(ContactNumero numero : c.getNumeriDiTelefono()){
       //creo un oggetto di tipo Telephone che abbia come numero quello del contatto
       Telephone telefono = new Telephone(numero.getAssociatedNumber());
       if(numero.isNumeroDiCasa()) telefono.getTypes().add(TelephoneType.HOME); //Setto il tipo CASA
       else{
       telefono.getTypes().add(TelephoneType.CELL); //Setto il tipo CELLULARE
       }
       //Aggiungi il numero di telefono alla card
       card.addTelephoneNumber(telefono);
        }
    }

    /**
     * @brief Aggiungi i le email a una VCard
     *
     * @param[in] c contatto con cui associare le email
     * @param card VCard a cui associare le email
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
        List<VCard> vcards = Ezvcard.parse(fr).all(); //Ottengo la lista di VCard dal file "filename"
        Contatto c = null;
        for (VCard card : vcards) { //Scorriamo tutte le VCard del file letto

            String fullName = card.getFormattedName().getValue(); //Leggo nome e cognome

            String nome;
            try {
                nome = card.getFormattedName().getValue().split(" ")[0]; //Poichè in fase di salvataggio abbiamo scritto nome e cognome separati da uno spazio , possiamo sempre prendere nome e cognome usando la split con lo spazio come carattere di divisione
            } catch (ArrayIndexOutOfBoundsException ex) {
                nome = "";
            }
            String descrizione ="";
;
            if (card.getExtendedProperty("X-DESCRIPTION") != null) { //Se ha il campo descrizione , leggiti la descrizione
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
            for (Note nota : card.getNotes()) { //Se ha come nota che è un contatto tra i favoriti , setta a true la proprietà per favorito
                if (nota.getValue().equals("FAVORITO")) c.setFavoriti(true);
            }

            r.addContatto(c);
        }


    }

        return r;
    }

}
   



