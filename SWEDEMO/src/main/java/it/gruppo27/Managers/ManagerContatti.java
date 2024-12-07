package it.gruppo27.Managers;


import it.gruppo27.Contact.ContactEmail;
import it.gruppo27.Contact.ContactNumero;
import it.gruppo27.Exceptions.InvalidEmailException;
import it.gruppo27.Exceptions.InvalidNumberException;
import java.util.List;

/**
 * La classe <code>GestoreContatti</code> è responsabile per la gestione dei contatti, in particolare
 * per l'aggiunta di numeri di telefono ed email ai contatti esistenti. Questa classe implementa metodi
 * statici che permettono di aggiungere nuovi numeri e email ai contatti, rispettando i limiti massimi
 * e verificando la validità dei dati.
 *
 * Le principali funzioni di questa classe includono:
 * - Aggiungere un numero di telefono a un contatto, con validazione.
 * - Aggiungere un'email a un contatto, con validazione.
 * - Gestire errori legati alla presenza di numeri o email duplicati o invalidi.
 *
 * <p>
 * La classe utilizza due limiti fissi:
 * - MAX_NUMERI: il numero massimo di numeri di telefono che un contatto può avere.
 * - MAX_EMAIL: il numero massimo di email che un contatto può avere.
 * </p>
 *
 * @see ContactNumero per la rappresentazione di un numero di telefono.
 * @see ContactEmail per la rappresentazione di un'email.
 * @see InvalidNumberException per i dettagli sulle eccezioni sollevate durante la gestione dei numeri di telefono.
 * @see InvalidEmailException per i dettagli sulle eccezioni sollevate durante la gestione delle email.
 */
public class ManagerContatti {
    private static final int MAX_NUMERI = 3;
    private static final int MAX_EMAIL = 3;

    /**
     *  Aggiunge un nuovo numero alla lista dei numeri del contatto passata come parametro
     * @param numeriDelContatto i numeri già associati a quel contatto
     * @param nuovoNumero il nuovo numero da inserire
     * @throws InvalidNumberException eccezione lanciata quando il contatto ha già 3 numeri di telefono oppure il nuovo numero non è valido
     * @pre la lista numeriDelContatto dev'essere una lista valida , non nulla
     * @post il nuovo numero è aggiunto alla lista se non ci sono stati errori , viceversa è lanciata un'eccezione InvalidNumberException
     * @see InvalidNumberException per ulteriori dettagli sull'eccezione sollevata
     */
    public static void addNumero(List<ContactNumero> numeriDelContatto, ContactNumero nuovoNumero) throws InvalidNumberException {
        if (numeriDelContatto.contains(nuovoNumero) || numeriDelContatto.size() >= MAX_NUMERI) {
            throw new InvalidNumberException("Il numero non è valido!");
        }
        numeriDelContatto.add(nuovoNumero);
    }

    /**
     *  Aggiunge una nuova email alla lista delle email del contatto passata come parametro
     * @param emailDelContatto le email già associate al contatto
     * @param nuovaMail la nuova mail da inserire tra quelle del contatto
     * @throws InvalidEmailException eccezione lanciata in caso di email non valida o se il contatto ha già 3 email
     * @pre la lista emailDelContatto dev'essere una lista valida , non nulla
     * @post la nuova email è aggiunta alla lista se non ci sono stati errori , viceversa è sollevata l'eccezione InvalidEmailException
     * @see InvalidEmailException per ulteriori dettagli sull'eccezione sollevata
     */
    public static void addEmail(List<ContactEmail> emailDelContatto, ContactEmail nuovaMail) throws InvalidEmailException {
        if (emailDelContatto.contains(nuovaMail) || emailDelContatto.size() >= MAX_EMAIL) {
            throw new InvalidEmailException("La mail non è valida!");
        }
        emailDelContatto.add(nuovaMail);
    }


}