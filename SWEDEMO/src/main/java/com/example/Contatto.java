package com.example;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Contatto implements Comparable<Contatto> {
    private String nome;
    private String cognome;
    private List<String> numeroTelefono;
    private List<String> email;

    public Contatto(String nome, String cognome ) {
        this.nome = nome;
        this.cognome = cognome;
        this.numeroTelefono = new ArrayList<>();
        this.email = new ArrayList<>();
    }

    public void addNumero(String numero){
        if(numeroTelefono.contains(numero)) return;
        if(numeroTelefono.size()<3) numeroTelefono.add(numero);
    }

    public void addEmail(String mail){
        if(email.contains(mail)) return;
        if(email.size()<3) email.add(mail);
    }

    public void removeNumero(int index){
        numeroTelefono.remove(numeroTelefono.get(index));
    }
    public void removeEmail(int index){
        email.remove(email.get(index));
    }

    public void modificaNumero(int index,String numero){
        numeroTelefono.set(index,numero);
    }
    public void modificaEmail(int index,String mail){
        email.set(index,mail);
    }

    // Getters
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public List<String> getNumeroTelefono() { return numeroTelefono; }
    public List<String> getEmail() { return email; }

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
            if(this.numeroTelefono.containsAll(altro.numeroTelefono) ) return 0;
        }



        return 1;


    }

    @Override
    public boolean equals(Object o) {
        if(o==null) return false;
        if (this == o) return true;
        if ( getClass() != o.getClass()) return false;
        Contatto that = (Contatto) o;
        return this.numeroTelefono.equals(that.numeroTelefono);  // Solo il numero di telefono determina l'uguaglianza
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("Nome: "+nome+" Cognome: "+cognome+"\n");
        int i = 0;
        int j = 0;
        for (String s : numeroTelefono) {
            i++;
            sb.append(" Numero di telefono " + i +" : " + s);
        }
        sb.append("\n");
        for (String e : email) {
            j++;
            sb.append(" Email " + j +" : " + e);
        }
        return sb.toString();
    }
}
