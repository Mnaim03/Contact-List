/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.gruppo27.Exceptions;

/**
 * La classe <code>InvalidEmailException</code> è un'eccezione controllata personalizzata che estende la classe <code>Exception</code>.
 * Questa eccezione viene utilizzata per segnalare errori relativi a un numero non valido, per esempio quando si inseriscono dei caratteri
 * invece che dei numeri , oppure quando la stringa numerica non è abbastanza lunga per essere considerata un numero di telefono valido
 *
 * <p>
 * Un esempio di quando questa eccezione potrebbe essere sollevata include:
 * - L'aggiunta di un numero già presente nella lista dei numeri di un contatto.
 * - L'inserimento di un numero che non rispetta il formato corretto
 * </p>
 *
 * <p>
 * La classe estende la classe <code>Exception</code>, il che significa che è una <i>checked exception</i>, ovvero deve essere
 * gestita esplicitamente nel codice tramite un blocco <code>try-catch</code> o essere dichiarata nel metodo con <code>throws</code>.
 * </p>
 *
 *
 */
public class InvalidNumberException extends Exception {

    /**
     * Costruttore della classe <code>InvalidNumberException</code>.
     *
     * @param msg il messaggio di errore che descrive la causa dell'eccezione.
     * @post Viene creato un oggetto <code>InvalidNumberException</code> con il messaggio specificato.
     */
    public InvalidNumberException(String msg){
        super(msg);
    }

}