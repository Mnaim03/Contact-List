/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.gruppo27.interfaces;

/**
 * L'interfaccia <code>CheckerNumber</code> definisce un contratto per le classi che devono implementare
 * la logica di validazione di un numero di telefono. Ogni classe che implementa questa interfaccia dovrà
 * fornire una propria implementazione del metodo <code>isValidNumber()</code>, che permette di verificare
 * se un numero di telefono è valido.
 * <p>
 * L'interfaccia è destinata ad essere implementata da classi che rappresentano entità che hanno un
 * numero di telefono
 * </p>
 * */
public interface CheckerNumber {
    /**
     * Verifica la validità del numero di telefono.
     * <p>
     * Il metodo deve restituire <code>true</code> se il numero è valido,ovvero se soddisfa i criteri
     * di formato corretti e di lunghezza minima, altrimenti deve restituire <code>false</code>.
     * </p>
     *
     * @return <code>true</code> se il numero è valido, <code>false</code> altrimenti.
     */
    boolean isValidNumber(); 

    
}
