/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.interfaces;

import com.example.Contact.ContactEmail;
import com.example.Contact.ContactNumero;
import com.example.Contact.Contatto;
import com.example.Exceptions.InvalidEmailException;
import com.example.Exceptions.InvalidNumberException;
import com.example.Flag;
import com.example.Rubrica;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.parameter.TelephoneType;
import ezvcard.property.Email;
import ezvcard.property.Telephone;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import javafx.collections.ObservableList;

/**
 *
 * @author silve
 */
public interface InterfaceRubrica {
     void addContatto(Contatto contatto);
     void removeContatto(Contatto contatto) ;
     ObservableList<Contatto> getListaOsservabile();
     Set<Contatto> getContatti();
     Rubrica ricercaContatti(String stringa);
     Flag isPresent(Contatto c);
     String toString();
     void salvaVCF(String filename) throws IOException;
     Rubrica leggiVCF(String filename) throws IOException,InvalidEmailException,InvalidNumberException;
    
}
