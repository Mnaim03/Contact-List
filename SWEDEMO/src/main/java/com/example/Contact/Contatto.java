package com.example.Contact;

import com.example.Exceptions.InvalidEmailException;
import com.example.Exceptions.InvalidNumberException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import com.example.interfaces.CheckerNumber;

public class Contatto implements Comparable<Contatto>{
    private String nome;
    private String cognome;
    private List<ContactNumero> numeroTelefono;
    private List<ContactEmail> email;
    private static final int MAX_NUMERI = 3;
    private static final int MAX_EMAIL = 3;

    public Contatto(String nome, String cognome ) {
        this.nome = nome;
        this.cognome = cognome;
        this.numeroTelefono = new ArrayList<>();
        this.email = new ArrayList<>();
    }
    public void setNome(String nome){
        this.nome=nome;
    }
    public void setCognome(String cognome){
        this.cognome=cognome;
    }
    
    public void addNumero(ContactNumero numero) throws InvalidNumberException{
        if(numeroTelefono.contains(numero) || numeroTelefono.size()>MAX_NUMERI)
           throw new InvalidNumberException("Il numero non è valido!");
        
       
        numeroTelefono.add(numero);
    }

    public void addEmail(ContactEmail mail) throws InvalidEmailException{
        if(email.contains(mail) || email.size()>MAX_EMAIL)
            throw new InvalidEmailException("La mail non è valida!");
        
        email.add(mail);
    }

    // Getters
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public List<ContactNumero> getNumeriDiTelefono() { return numeroTelefono; }
    public List<ContactEmail> getEmail() { return email; }

    @Override
    public int compareTo(Contatto altro) {
        // Prima confronta per cognome
        int confrontoCognome = this.cognome.compareToIgnoreCase(altro.cognome);
        if (confrontoCognome != 0) {
            return confrontoCognome;
        }
        // Se i cognomi sono uguali, confronta per nome
        int confrontoNome = this.nome.compareToIgnoreCase(altro.nome);
        if (confrontoNome != 0) {
            return confrontoNome;
        }

        // Se anche i nomi sono uguali, usa il numero di telefono come discriminante
        // I nuemri di telefono uguali possono trovarsi in posizioni diverse

        if (this.numeroTelefono.size() == altro.numeroTelefono.size()) {
        boolean condNumeri=this.numeroTelefono.containsAll(altro.numeroTelefono);
        if(condNumeri) {
            if (this.email.size() == altro.email.size()){
                boolean condEmail=this.email.containsAll(altro.email);
                if (condEmail) return 0;
            }
        }
        }
        return 1;


    }

  


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("Nome: "+nome+" Cognome: "+cognome+"\n");
        int i = 0;
        int j = 0;
        for (ContactNumero s : numeroTelefono) {
            i++;
            sb.append(" Numero di telefono " + i +" : " + s);
        }
        sb.append("\n");
        for (ContactEmail e : email) {
            j++;
            sb.append(" Email " + j +" : " + e);
        }
        return sb.toString();
    }
}
