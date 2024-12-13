package it.gruppo27.Models.Contact;
/**
 * @file Contatto.java
 */
import it.gruppo27.Exceptions.InvalidEmailException;
import it.gruppo27.Exceptions.InvalidNumberException;
import it.gruppo27.Managers.ManagerContatti;

import java.util.ArrayList;
import java.util.List;

/**

 * @class Contatto
 * @brief  Ogni oggetto della classe Contatto
 * è identificato dal suo nome e cognome e può avere una lista di numeri di telefono(da 0 a 3)
 * e indirizzi email(da 0 a 3) e una descrizione associati, inoltre è possibile aggiungere il contatto tra i preferiti.
 * 
 * La classe implementa l'interfaccia Comparable, consentendo di ordinare i contatti in base al loro cognome
 * (nome in caso di cognomi uguali). Se due contatti hanno nome , cognome ,i 3 numeri di telefono identici e le 3 mail uguali
 * verranno considerati uguali , e dunque uno dei due verrà considerato doppione .
 * 
 * 
 * @see ContactNumero per la gestione dei numeri di telefono.
 * @see ContactEmail per la gestione delle email.
 * @see ManagerContatti per la gestione delle operazioni di aggiunta e validazione dei numeri e delle email.
 */
public class Contatto implements Comparable<Contatto>{
    private String nome;
    private String cognome;
    private List<ContactNumero> numeriDiTelefono;
    private List<ContactEmail> emails;
    private String descrizione;
    private boolean favoriti;

    /**
     * @brief Crea un nuovo contatto con nome e cognome specificati
     * @param nome il nome del contatto
     * @param cognome il cognome del contatto
     * @post Crea un nuovo oggetto Contatto con i valori di nome e cognome.
     * La lista dei numeri di telefono e delle email è inizializzata come vuota.
     *
     */
    public Contatto(String nome, String cognome, String descrizione, boolean favoriti ) {
        this.nome = nome;
        this.cognome = cognome;
        this.numeriDiTelefono = new ArrayList<>();
        this.emails = new ArrayList<>();
        this.descrizione = descrizione;
        this.favoriti = favoriti;
    }

    /**
     * @brief Restituisce il nome del contatto
     * @return il nome del contatto
     */
    public String getNome() { return nome; }

    /**
     * @brief Restituisce il cognome del contatto
     * @return il cognome del contatto
     */
    public String getCognome() { return cognome; }

    /**
     * @brief Restituisce la lista dei numeri di telefono del contatto
     * @return i numeri di telefono del contatto
     */
    public List<ContactNumero> getNumeriDiTelefono() { return numeriDiTelefono; }

    /**
     * @brief Restituisce la lista delle email del contatto
     * @return le emails del contatto
     */
    public List<ContactEmail> getEmail() { return emails; }
    
    /**
     * @brief Restituisce la descrizione del contatto
     * @return descrizione del contatto
     */
    public String getDescrizione() { return descrizione; }
    
    /**
     * @brief Fornisce informazioni sulla presenza del contatto nella lista dei preferiti o meno
     *
     * @return <code>true</code> se il contatto è tra i preferiti , <code>false</code>  altrimenti
     */
    public boolean getFavourite() { return favoriti; }

    /**
     * @brief Setta il nome del contatto
     * @param nome il nome da inserire per il contatto
     * @post il nome del contatto è aggiornato con il valore fornito
     */
    public void setNome(String nome){
        this.nome=nome;
    }

    /**
     * @brief Imposta il cognome del contatto
     * @param cognome il cognome da inserire per il contatto
     * @post il cognome del contatto è aggiornato con il valore fornito
     */
    public void setCognome(String cognome){
        this.cognome=cognome;
    }


    /**
     * @brief Aggiunge un numero di telefono alla lista dei numeri già associati al contatto
     *  Questo metodo delega la logica di aggiunta del numero alla classe GestoreContatti.
     * La validazione del numero viene eseguita da GestoreContatti, e in caso
     * di errore viene lanciata un'eccezione.
     * @param numero il numero di telefono da aggiungere alla lista dei numeri già presenti per quel contatto
     * @throws InvalidNumberException Se il numero non è valido o se il contatto ha già raggiunto il numero massimo di email.
     * @post viene aggiunto il nuovo numero alla lista dei numeri associati a quel contatto se non ci sono errori
     * @see InvalidNumberException per più informazioni sull'eccezione sollevata
     */
    public void addNumero(ContactNumero numero) throws InvalidNumberException{
        ManagerContatti.addNumero(numeriDiTelefono,numero);
    }

    /**
     * @brief Aggiunge una nuova email alla lista delle email del contatto.
     *
     * Questo metodo delega la logica di aggiunta della nuova email alla classe GestoreContatti.
     * La validazione dell'email viene eseguita da GestoreContatti, e in caso di errore viene lanciata un'eccezione.
     *
     * @param email L'email da aggiungere alla lista delle email del contatto.
     * @throws InvalidEmailException Se l'email non è valida o se il contatto ha già raggiunto il numero massimo di email.
     * @post L'email viene aggiunta alla lista delle email del contatto se non ci sono errori.
     * @see ManagerContatti#addEmail(List, ContactEmail) Per maggiori dettagli sulla logica di validazione dell'email.
     */
    public void addEmail(ContactEmail email) throws InvalidEmailException{
        ManagerContatti.addEmail(this.emails,email);

    }
    /**
     * @brief Imposta la descrizione del contatto
     * @param descrizione descrizione da inserire per il contatto
     * @post la descrizione del contatto è aggiornata con il valore fornito
     */
    public void setDescrizione(String descrizione){
        this.descrizione=descrizione;
    }
    
    /**
     * @brief Serve per impostare se un contatto deve essere aggiunto ai preferiti 
     * @param favoriti parametro per aggiungere o meno un contatto tra i preferiti 
     * @post l'attributo favoriti è aggiornato con il valore fornito
     */
    public void setFavoriti(boolean favoriti){
        this.favoriti=favoriti;
    }

    /**
     * @brief Confronta questo contatto con un altro contatto in base a nome, cognome, numeri di telefono ed email.
     * Il confronto avviene seguendo quest'ordine:
     * 1. Se i cognomi sono diversi, viene restituito il risultato del confronto.
     * 2. Se i cognomi sono uguali, viene confrontato il nome.
     * 
     * 3. Se i nomi sono diversi, viene restituito il risultato del confronto.
     * 4. Se anche i nomi sono uguali, viene confrontato il numero di numeri di telefono.
     * 
     * 5. Se il numero di numeri di telefono è diverso , viene restituito il risultato del confronto.
     * 6. Se anche il numero di numeri di telefono è uguale , vengono confrontati i numeri di telefono.
     * 
     * 7.Se i numeri di telefono sono diversi , viene restituito il risultato del confronto.
     * 8. Se anche i numeri di telefono sono uguali , viene confrontato il numero di email.
     * 
     * 9. Se il numero di email è diverso , viene restituito il risultato del confronto
     * 10. Se il numero di email sono uguali, vengono confrontate le email.
     * 
     * 11.Se il le email sono diverse , viene restituito il risultato del confronto
     * 12. Se anche le email sono uguali , i due contatti sono considerati uguali
     *
     * @param altro Il contatto con cui confrontare questo contatto.
     * @return Un valore negativo se questo contatto viene prima di `altro`, un valore positivo se viene dopo, e 0 se sono considerati uguali.
     *
     */
    @Override
    public int compareTo(Contatto altro) {

        int confrontoCognome = this.cognome.compareToIgnoreCase(altro.cognome);
        if (confrontoCognome != 0) {
            return confrontoCognome;
        }
        int confrontoNome = this.nome.compareToIgnoreCase(altro.nome);
        if (confrontoNome != 0) {
            return confrontoNome;
        }
//Solo se hanno lo stesso numero di numeri di telefono faccio il controllo per risparmiare risorse
        if (this.numeriDiTelefono.size() == altro.numeriDiTelefono.size()) {
            boolean condNumeri=this.numeriDiTelefono.containsAll(altro.numeriDiTelefono);
            if(condNumeri) {
                if (this.emails.size() == altro.emails.size()){
                    boolean condEmail=this.emails.containsAll(altro.emails);
                    if (condEmail) return 0; //Se sono arrivato fin qui hanno tutto uguale
                }
            }
        }

        return 1;
    }
    /**
     * @brief Restituisce una rappresentazione testuale del contatto.
     *
     * La rappresentazione include:
     * - Il nome e cognome del contatto.
     * - Una lista numerata di numeri di telefono associati al contatto.
     * - Una lista numerata di email associate al contatto.
     *
     * La stringa risultante avrà il seguente formato:
     *
     * Nome: [nome]
     * Cognome: [cognome]
     * Numero di telefono 1: [numero1]
     * Numero di telefono 2: [numero2]
     * ...
     * Email 1: [email1]
     * Email 2: [email2]
     * ...
     *
     * @return Una stringa che rappresenta il contatto con nome, cognome, numeri di telefono ed email.
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("Nome: "+nome+" Cognome: "+cognome+"\n");
        int i = 0;
        int j = 0;
        for (ContactNumero s : numeriDiTelefono) {
            i++;
            sb.append(" Numero di telefono " + i +" : " + s);
        }
        sb.append("\n");
        for (ContactEmail e : emails) {
            j++;
            sb.append(" Email " + j +" : " + e);
        }
        return sb.toString();
    }
}
