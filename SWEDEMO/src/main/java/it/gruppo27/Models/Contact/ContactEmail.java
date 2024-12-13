/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.gruppo27.Models.Contact;

import it.gruppo27.interfaces.CheckerEmail;

/**
 * La classe <code>ContactEmail</code> rappresenta una email associata a un contatto.
 * <p>
 * Ogni istanza di <code>ContactEmail</code> contiene una singola email come String che può essere verificata
 * per la sua validità tramite il metodo <code>isValidEmail()</code>, che controlla se l'email
 * soddisfa il formato standard di un'email.
 * </p>
 * <p>
 * La classe implementa l'interfaccia <code>CheckerEmail</code>, che definisce il metodo
 * <code>isValidEmail()</code> per la verifica del formato dell'email.
 * </p>
 *
 */
public class ContactEmail implements CheckerEmail{
    private String email ;

    /**
     * Costruisce un nuovo oggetto <code>ContactEmail</code> con l'indirizzo email fornito.
     *
     * <p>Questo costruttore inizializza l'oggetto <code>ContactEmail</code> con l'indirizzo email passato come parametro.
     * L'email rappresenta l'indirizzo associato al contatto, che sarà utilizzato per operazioni successive come la
     * verifica del formato tramite il metodo <code>isValidEmail()</code>.</p>
     *
     * @param email L'indirizzo email da associare al contatto.
     *
     */
    public ContactEmail(String email){
        this.email=email;
    }

    /**
     * Ottiene la email come stringa associata al contatto
     * @return l'email come formato String
     */
    public String getAssociatedEmail(){
        return email;
    }

    /**
     * Verifica la validità dell'indirizzo email associato a questo oggetto <code>ContactEmail</code>.
     * <p>
     * Questo metodo controlla se l'indirizzo email soddisfa il formato standard di una email,
     * verificando la presenza del carattere "@" e dell'estensione (ad esempio, ".com", ".it", ecc.).
     * Viene utilizzata una espressione regolare per effettuare il controllo.
     * </p>
     *
     * @return <code>true</code> se l'indirizzo email è valido, <code>false</code> altrimenti.
     */
    @Override
    public boolean isValidEmail(){
        return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    /**
     * Stabilisce la relazione di uguaglianza tra due oggetti di tipo ContactEmail
     * @param obj
     * @return <code>true</code> se i due oggetti sono uguali , <code>false</code> altrimenti
     */
    public boolean equals(Object obj){
        if(obj==null) return false;
        if(obj==this) return true;
        if(!(obj instanceof ContactEmail)) return false;
        ContactEmail email =(ContactEmail) obj;
        return this.email.equals(email.email);
    }
}
