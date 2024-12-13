/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.gruppo27.Exceptions;

/**
 * @brief La classe <code>InvalidEmailException</code> è un'eccezione controllata personalizzata che estende la classe <code>Exception</code>.
 * Questa eccezione viene utilizzata per segnalare errori relativi a un'email non valida, come quando un'email non soddisfa i
 * criteri di validità definiti dal sistema.
 *
 *
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

