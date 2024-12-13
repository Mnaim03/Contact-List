/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.gruppo27.interfaces;



/**
 * @brief L'interfaccia <code>CheckerEmail</code> definisce un contratto per le classi che devono implementare
 * la logica di validazione di un indirizzo email. Ogni classe che implementa questa interfaccia dovrà
 * fornire una propria implementazione del metodo <code>isValidEmail()</code>, che permette di verificare
 * se un indirizzo email è valido.
 * <p>
 * L'interfaccia è destinata ad essere implementata da classi che rappresentano entità che hanno un
 * indirizzo email
 * </p>
 * */
public interface CheckerEmail {

    /**
     * Verifica la validità dell'indirizzo email.
     * <p>
     * Il metodo deve restituire <code>true</code> se l'email è valida, ovvero se soddisfa i criteri
     * di formato corretti, altrimenti deve restituire <code>false</code>.
     * </p>
     *
     * @return <code>true</code> se l'indirizzo email è valido, <code>false</code> altrimenti.
     */
    boolean isValidEmail();
}
