/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Exceptions;


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