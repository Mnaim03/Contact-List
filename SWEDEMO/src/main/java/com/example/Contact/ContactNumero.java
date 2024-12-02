/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Contact;

import com.example.Exceptions.InvalidNumberException;
import com.example.interfaces.CheckerNumber;
import java.util.Arrays;
import java.util.List;

/**
 * La classe <code>ContactNumero</code> rappresenta un numero  associato a un contatto.
 * <p>
 * Ogni istanza di <code>ContactNumero</code> contiene un singolo numero come String che può essere verificato
 * per la sua validità tramite il metodo <code>isValidNumber()</code>, che controlla se il numero
 * soddisfa il formato standard di un numero telefonico
 * </p>
 * <p>
 * La classe implementa l'interfaccia <code>CheckerNumber</code>, che definisce il metodo
 * <code>isValidNumber()</code> per la verifica del formato dell'email.
 * </p>
 *
 */

public class ContactNumero implements CheckerNumber {
    private String numeroTelefono;

    /**
     * Costruisce un nuovo oggetto <code>ContactNumero</code> con il numero di telefono fornito.
     *
     * <p> Inizializza l'oggetto <code>ContactNumero</code> con il numero di telefono passato come parametro.
     * Il numero rappresenta un contatto telefonico associato a un contatto nel sistema.</p>
     *
     * @param numeroTelefono Il numero di telefono da associare al contatto. Non deve essere <code>null</code> o vuoto.
     *
     *
     */
    public ContactNumero(String numeroTelefono){
        this.numeroTelefono = numeroTelefono;
    }

    /**
     * Verifica la validità del numero di telefono associato a questo oggetto <code>ContactNumero</code>.
     * <p>
     * Questo metodo controlla che il numero di telefono rispetti il formato standard, considerando una lunghezza
     * compresa tra 7 e 15 caratteri e permettendo numeri, trattini e spazi. Il numero può opzionalmente iniziare con il simbolo "+".
     * La validità del numero viene verificata tramite un'espressione regolare.
     * </p>
     *
     * @return <code>true</code> se il numero di telefono è valido (rispetta il formato specificato),
     *         <code>false</code> altrimenti.
     */
    @Override
    public boolean isValidNumber(){
        return numeroTelefono.matches("\\+?[0-9\\- ]{7,15}");
    }

    /**
     * Restituisce il numero di telefono associato al ContactNumero
     * @return il numero di telefono in formato String
     */
    public String getAssociatedNumber(){
        return numeroTelefono;
    }

    /**
     * Verifica se un numero è "di casa" controllando che inizi per alcuni prefissi 089 ecc
     * @return <code>true</code> se il numero è "di casa" <code>false</code> altrimenti
     */
    public boolean isNumeroDiCasa() {
        // Lista dei prefissi per i numeri di casa
        List<String> prefissiCasa = Arrays.asList("06", "02", "051", "055","089"); // Esempio: Roma, Milano, Bologna, Firenze

        // Controlla se il numero inizia con uno dei prefissi
        for (String prefisso : prefissiCasa) {
            if (numeroTelefono.startsWith(prefisso)) {
                return true;
            }
        }

        return false;
    }



}
