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
 *
 * @author Vincenzo Ragone
 */
public class ContactNumero implements CheckerNumber {
    private String numeroTelefono; 
    
    public ContactNumero(String numeroTelefono){
    this.numeroTelefono = numeroTelefono; 
    }

    //da rivedere
    @Override
    public boolean isValidNumber(){
        return numeroTelefono.matches("\\+?[0-9\\- ]{7,15}");
    }
    
    
    public String getAssociatedNumber(){
    return numeroTelefono;
    }
    
    
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
