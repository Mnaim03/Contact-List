package com.example.Contact;

import com.example.Exceptions.InvalidEmailException;
import com.example.Exceptions.InvalidNumberException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import com.example.interfaces.CheckerNumber;

/**
 * La classe <code>Contatto</code> rappresenta un contatto telefonico e email.
 * Un contatto è identificato dal suo nome e cognome e può avere una lista di numeri di telefono(da 0 a 3) e indirizzi email(da 0 a 3) associati.
 * <p>
 * La classe implementa l'interfaccia <code>Comparable<Contatto></code>, consentendo di ordinare i contatti in base al loro cognome(nome in caso di cognomi uguali).
 * </p>
 *
 * @see ContactNumero per la gestione dei numeri di telefono.
 * @see ContactEmail per la gestione delle email.
 * @see GestoreContatti per la gestione delle operazioni di aggiunta e validazione dei numeri e delle email.
 */
public class Contatto implements Comparable<Contatto>{
    private String nome;
    private String cognome;
    private List<ContactNumero> numeriDiTelefono;
    private List<ContactEmail> emails;

    /**
     * Crea un nuovo contatto con nome e cognome specificati
     * @param nome il nome del contatto
     * @param cognome il cognome del contatto
     * @post Crea un nuovo oggetto Contatto con i valori di nome e cognome.
     * La lista dei numeri di telefono e delle email è inizializzata come vuota.
     *
     */
    public Contatto(String nome, String cognome ) {
        this.nome = nome;
        this.cognome = cognome;
        this.numeriDiTelefono = new ArrayList<>();
        this.emails = new ArrayList<>();
    }



    /**
     * Restituisce il nome del contatto
     * @return il nome del contatto
     */
    public String getNome() { return nome; }

    /**
     * Restituisce il cognome del contatto
     * @return il cognome del contatto
     */
    public String getCognome() { return cognome; }

    /**
     * Restituisce la lista dei numeri di telefono del contatto
     * @return i numeri di telefono del contatto
     */
    public List<ContactNumero> getNumeriDiTelefono() { return numeriDiTelefono; }

    /**
     * Restituisce la lista delle email del contatto
     * @return le emails del contatto
     */
    public List<ContactEmail> getEmail() { return emails; }



    /**
     *Imposta il nome del contatto
     * @param nome il nome da inserire per il contatto
     * @pre il nome non può essere nullo o vuoto
     * @post il nome del contatto è aggiornato con il valore fornito
     */
    public void setNome(String nome){
        this.nome=nome;
    }

    /**
     * Imposta il cognome del contatto
     * @param cognome il cognome da inserire per il contatto
     * @pre il cognome non può essere nullo o vuoto
     */
    public void setCognome(String cognome){
        this.cognome=cognome;
    }


    /**
     * Aggiunge un numero di telefono alla lista dei numeri già associati al contatto
     * Questo metodo delega la logica di aggiunta del numero alla classe GestoreContatti.
     * La validazione del numero viene eseguita da GestoreContatti, e in caso
     * di errore viene lanciata un'eccezione.
     * @param numero il numero di telefono da aggiungere alla lista dei numeri già presenti per quel contatto
     * @throws InvalidNumberException
     * @pre il parametro numero non può essere null
     * @post viene aggiunto il nuovo numero alla lista dei numeri associati a quel contatto se non ci sono errori
     * @see InvalidNumberException per più informazioni sulla eccezione sollevata
     */
    public void addNumero(ContactNumero numero) throws InvalidNumberException{
        GestoreContatti.addNumero(numeriDiTelefono,numero);
    }

    /**
     * Aggiunge una nuova email alla lista delle email del contatto.
     *
     * Questo metodo delega la logica di aggiunta della nuova email alla classe GestoreContatti.
     * La validazione dell'email viene eseguita da GestoreContatti, e in caso di errore viene lanciata un'eccezione.
     *
     * @param email L'email da aggiungere alla lista delle email del contatto.
     * @throws InvalidEmailException Se l'email non è valida o se il contatto ha già raggiunto il numero massimo di email.
     * @pre Il parametro email non deve essere nullo.
     * @post L'email viene aggiunta alla lista delle email del contatto se non ci sono errori.
     * @see GestoreContatti#addEmail(List, ContactEmail) Per maggiori dettagli sulla logica di validazione dell'email.
     */
    public void addEmail(ContactEmail email) throws InvalidEmailException{
        GestoreContatti.addEmail(this.emails,email);

    }

    /**
     * Confronta questo contatto con un altro contatto in base a nome, cognome, numeri di telefono ed email.
     *
     * Il confronto avviene nei seguenti passaggi:
     * 1. Viene confrontato il cognome. Se i cognomi sono diversi, viene restituito il risultato del confronto.
     * 2. Se i cognomi sono uguali, viene confrontato il nome. Se i nomi sono diversi, viene restituito il risultato del confronto.
     * 3. Se sia il cognome che il nome sono uguali, viene confrontato il numero di numeri di telefono. Se il numero di numeri di telefono è uguale,
     *    si procede con il confronto delle email.
     * 4. Se anche il numero di numeri di telefono e email sono uguali, i due contatti vengono considerati uguali.
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

        if (this.numeriDiTelefono.size() == altro.numeriDiTelefono.size()) {
            boolean condNumeri=this.numeriDiTelefono.containsAll(altro.numeriDiTelefono);
            if(condNumeri) {
                if (this.emails.size() == altro.emails.size()){
                    boolean condEmail=this.emails.containsAll(altro.emails);
                    if (condEmail) return 0;
                }
            }
        }
        return 1;
    }
    /**
     * Restituisce una rappresentazione testuale del contatto.
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
