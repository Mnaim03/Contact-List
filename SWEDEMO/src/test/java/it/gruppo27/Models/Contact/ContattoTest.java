package it.gruppo27.Models.Contact;

import it.gruppo27.Exceptions.InvalidEmailException;
import it.gruppo27.Exceptions.InvalidNumberException;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContattoTest {
    Contatto c;

    @BeforeEach
    void setUp() throws Exception {
        c = new Contatto("nome","cognome","descrizione",false);
        c.addEmail( new ContactEmail("mail@mail.com"));
        c.addNumero( new ContactNumero("1234567890"));
    }

    @Test
    void getNome() {
        assertEquals("nome", c.getNome());
    }

    @Test
    void getCognome() {
        assertEquals("cognome", c.getCognome());
    }

    @Test
    void getNumeriDiTelefono() {
        assertEquals(new ContactNumero("1234567890"), c.getNumeriDiTelefono());
    }

    @Test
    void getEmail() {
        assertEquals(new ContactEmail("mail@mail.com"), c.getEmail());
    }

    @Test
    void getDescrizione() {
        assertEquals("descrizione", c.getDescrizione());
    }

    @Test
    void getFavourite() {
        assertFalse(c.getFavourite());
    }


    @Test
    void setNome() {
        c.setNome("nome2");
        assertEquals("nome2", c.getNome());
    }

    @Test
    void setCognome() {
        c.setCognome("cognome2");
        assertEquals("cognome2", c.getCognome());
    }

    @Test
    void addNumero() throws InvalidNumberException {
        c.addNumero(new ContactNumero("0987654321"));
        assertEquals(2 , c.getNumeriDiTelefono().size());
    }

    @Test
    void addEmail() throws InvalidEmailException {
        c.addEmail(new ContactEmail("mail2@mail.com"));
        assertEquals(2 , c.getNumeriDiTelefono().size());
    }

    @Test
    void setDescrizione() {
        c.setDescrizione("descrizione2");
        assertEquals("descrizione2", c.getDescrizione());
    }
    @Test
    void setFavoriti() {
        c.setFavoriti(true);
        assertEquals(true, c.getFavourite());
    }

    @Test

    //NON FUNZIONA
    void compareTo() throws Exception {
        //secondo contatto c2 copia identica di contatto c
        Contatto c2 = new Contatto("nome","cognome","descrizione",false);
        c2.addEmail( new ContactEmail("mail@mail.com"));
        c2.addNumero( new ContactNumero("1234567890"));

        assertTrue(c.compareTo(c2) == 0); //check se c e c2 sono uguali
        
        Contatto c3 = new Contatto( "nome" , "a" , "descrizione" , false);
        assertTrue(c.compareTo(c3) > 0); //check se c segue c3
                
        Contatto c4 = new Contatto( "nome" , "z" , "descrizione" , false);
        assertTrue(c.compareTo(c4) < 0); //check se c precede c3
                
        Contatto c5 = new Contatto( "a" , "cognome" , "descrizione" , false);
        assertTrue(c.compareTo(c5) > 0); //check se c segue c5 , con cognome uguale , confrontandoli per nome
                
        Contatto c6 = new Contatto( "z" , "cognome" , "descrizione" , false);
        assertTrue(c.compareTo(c6) < 0); //check se c precede c6 , con cognome uguale , confrontandoli per nome
    }



}