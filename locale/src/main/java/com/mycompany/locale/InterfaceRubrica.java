/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.locale;

/**
 *
 * @author silve
 */
public interface InterfaceRubrica {
    public void addContatto(Contatto contatto); 
    public void removeContatto(Contatto contatto);
    public Contatto removeContatto(Contatto contatto);
    public void stampaContatti();
    public void salvaVCF(String filename) throws IOException;
    public Rubrica leggiVCF(String filename) throws IOException;
    public Set<Contatto> getContatti();
}
