/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.gruppo27.Exceptions;

/**
 * La classe <code>InvalidEmailException</code> è un'eccezione controllata personalizzata che estende la classe <code>Exception</code>.
 * Questa eccezione viene utilizzata per segnalare errori relativi a un'email non valida, come quando un'email non soddisfa i
 * criteri di validità definiti dal sistema.
 *
 * <p>
 * Un esempio di quando questa eccezione potrebbe essere sollevata include:
 * - L'aggiunta di un'email già presente nella lista delle email di un contatto.
 * - L'inserimento di un'email che non rispetta il formato corretto (ad esempio, mancanza del carattere "@" o di un dominio valido).
 * </p>
 *
 * <p>
 * La classe estende la classe <code>Exception</code>, il che significa che è una <i>checked exception</i>, ovvero deve essere
 * gestita esplicitamente nel codice tramite un blocco <code>try-catch</code> o essere dichiarata nel metodo con <code>throws</code>.
 * </p>
 *
 *
 */
public class InvalidEmailException extends Exception {


    /**
     * Costruttore della classe <code>InvalidEmailException</code>.
     *
     * @param msg il messaggio di errore che descrive la causa dell'eccezione.
     * @post Viene creato un oggetto <code>InvalidEmailException</code> con il messaggio specificato.
     */
    public InvalidEmailException(String msg){
        super(msg);
    }

}

