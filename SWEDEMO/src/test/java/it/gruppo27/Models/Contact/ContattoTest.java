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

    // ID = 1.1
    @Test
    void getNome() {
        assertEquals("nome", c.getNome());
        System.out.println("Test ID = 1.1 SUPERATO");
    }

    // ID = 1.2
    @Test
    void getCognome() {
        assertEquals("cognome", c.getCognome());
        System.out.println("Test ID = 1.2 SUPERATO");
    }

    // ID = 1.3
    @Test
    //Verifico il primo numero
    void getNumeriDiTelefonoTest() {
        assertEquals(new ContactNumero("1234567890"), c.getNumeriDiTelefono().get(0));
        System.out.println("Test ID = 1.3 SUPERATO");
    }

    // ID = 1.4
    @Test
    //Verifico la prima email
    void getEmailTest() {
        assertEquals(new ContactEmail("mail@mail.com"), c.getEmail().get(0));
        System.out.println("Test ID = 1.4 SUPERATO");
    }

    // ID = 1.5
    @Test
    void getDescrizioneTest() {
        assertEquals("descrizione", c.getDescrizione());
        System.out.println("Test ID = 1.5 SUPERATO");
    }

    // ID = 1.6
    @Test
    void getFavouriteTest() {
        assertFalse(c.getFavourite());
        System.out.println("Test ID = 1.6 SUPERATO");
    }

    //ID = 1.7
    @Test
    void setNomeTest() {
        c.setNome("nome2");
        assertEquals("nome2", c.getNome());
        System.out.println("Test ID = 1.7 SUPERATO");
    }

    // ID = 1.8
    @Test
    void setCognomeTest() {
        c.setCognome("cognome2");
        assertEquals("cognome2", c.getCognome());
        System.out.println("Test ID = 1.8 SUPERATO");
    }

    // ID = 1.9
    @Test
    void addNumeroTest() throws InvalidNumberException {
        c.addNumero(new ContactNumero("0987654321"));
        assertEquals(2 , c.getNumeriDiTelefono().size());
        System.out.println("Test ID = 1.9 SUPERATO");
    }

    // ID = 1.10
    @Test
    void addEmailTest() throws InvalidEmailException {
        c.addEmail(new ContactEmail("mail2@mail.com"));
        assertEquals(2 , c.getEmail().size());
        System.out.println("Test ID = 1.10 SUPERATO");
    }

    // ID = 1.11
    @Test
    void setDescrizioneTest() {
        c.setDescrizione("descrizione2");
        assertEquals("descrizione2", c.getDescrizione());
        System.out.println("Test ID = 1.11 SUPERATO");
    }

    // ID = 1.12
    @Test
    void setFavoritiTest() {
        c.setFavoriti(true);
        assertEquals(true, c.getFavourite());
        System.out.println("Test ID = 1.12 SUPERATO");
    }


    // ID = 1.13.1
    @Test
    void compareToContattiUgualiTest() throws Exception{
        Contatto contattoUguale = new Contatto("nome","cognome","descrizione",false);
        contattoUguale.addEmail(new ContactEmail("mail@mail.com"));
        contattoUguale.addNumero(new ContactNumero("1234567890"));
        assertTrue(c.compareTo(contattoUguale)==0);
        System.out.println("Test ID = 1.13.1 SUPERATO");
    }

    // ID = 1.13.2
    @Test
    void compareToCognomeInizialeATest(){
        Contatto contattoConCognomeCheIniziaPerA = new Contatto( "nome" , "aaaaa" , "descrizione" , false);
        assertTrue(c.compareTo(contattoConCognomeCheIniziaPerA) > 0); //check se c segue c3
        System.out.println("Test ID = 1.13.2 SUPERATO");
    }

    // ID = 1.13.3
    @Test
    void compareToCognomeInizialeZTest(){
        Contatto contattoConCognomeCheIniziaPerZ = new Contatto("nome" , "z" , "descrizione" , false);
        assertTrue(c.compareTo(contattoConCognomeCheIniziaPerZ) < 0); //check se c precede c3
        System.out.println("Test ID = 1.13.3 SUPERATO");
    }

    // ID = 1.13.4
    @Test
    void compareToCognomiUgualiInizialeNomeA(){
        Contatto contattoStessoCognomeNomeCheIniziaPerA = new Contatto( "a" , "cognome" , "descrizione" , false);
        assertTrue(c.compareTo(contattoStessoCognomeNomeCheIniziaPerA) > 0); //check se c segue c5,con cognome uguale , confrontandoli per nome
        System.out.println("Test ID = 1.13.4 SUPERATO");
    }

    // ID = 1.13.5
    @Test
    void compareToCognomiUgualiInizialeNomeZ(){

        Contatto contattoStessoCognomeNomeCheIniziaPerZ = new Contatto( "z" , "cognome" , "descrizione" , false);
        assertTrue(c.compareTo(contattoStessoCognomeNomeCheIniziaPerZ) < 0);
        System.out.println("Test ID = 1.13.5 SUPERATO");

    }



}