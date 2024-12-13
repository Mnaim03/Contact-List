/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.gruppo27.Exceptions;

/**
 * @brief La classe <code>InvalidEmailException</code> è un'eccezione controllata personalizzata che estende la classe <code>Exception</code>.
 * Questa eccezione viene utilizzata per segnalare errori relativi a un numero non valido, per esempio quando si inseriscono dei caratteri
 * invece che dei numeri , oppure quando la stringa numerica non è abbastanza lunga per essere considerata un numero di telefono valido

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