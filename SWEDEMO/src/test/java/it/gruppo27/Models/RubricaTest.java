package it.gruppo27.Models;

import it.gruppo27.Exceptions.InvalidNumberException;
import it.gruppo27.Models.Contact.ContactEmail;
import it.gruppo27.Models.Contact.ContactNumero;
import it.gruppo27.Models.Contact.Contatto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class RubricaTest {
    Rubrica r;

    @BeforeEach
    void setUp() throws Exception {
        Contatto c = new Contatto("nome","cognome","descizione",false);
        c.addEmail( new ContactEmail("mail@mail.com"));
        c.addNumero( new ContactNumero("1234567890"));

        r = new Rubrica();
        r.addContatto(c);
    }

    @Test
    void addContatto() {
        Contatto c2 = new Contatto("nome2","cognome2","descizione",false);
        r.addContatto(c2);
        assertEquals(2, r.getContatti().size());
    }

    @Test
    void removeContatto() {
        //rimuovo contatto inesistente
        r.removeContatto( new Contatto("nome2","cognome2","descizione",false));
        assertEquals(1, r.getContatti().size());
    }

    @Test
    void getListaOsservabile() throws Exception {
        assertNotNull(r.getListaOsservabile());
    }

    @Test
    void getContatti() throws Exception {
        assertNotNull(r.getContatti());
    }

    @Test
    void ricercaContatti() {
        assertNotNull(r.ricercaContatti("nome"));
    }

    @Test
    void isPresent() {
        Contatto c2 = new Contatto("nome2","cognome2","descizione",false);
        assertFalse(r.isPresent(c2));
    }

    @Test
    void deleteAll() {
        r.deleteAll();
        assertEquals(0, r.getContatti().size());
    }

    @Test
    void salvaVCF() {
    }

    @Test
    void aggiungiNumeriDiTelefonoAVCard() {
    }

    @Test
    void aggiungiEmailsAVCard() {
    }

    @Test
    void leggiVCF() {
    }
}