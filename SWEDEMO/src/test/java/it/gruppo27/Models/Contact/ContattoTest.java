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
    //Verifico il primo numero
    void getNumeriDiTelefonoTest() {
        assertEquals(new ContactNumero("1234567890"), c.getNumeriDiTelefono().get(0));
    }

    @Test
    //Verifico la prima email
    void getEmailTest() {
        assertEquals(new ContactEmail("mail@mail.com"), c.getEmail().get(0));
    }

    @Test
    void getDescrizioneTest() {
        assertEquals("descrizione", c.getDescrizione());
    }

    @Test
    void getFavouriteTest() {
        assertFalse(c.getFavourite());
    }


    @Test
    void setNomeTest() {
        c.setNome("nome2");
        assertEquals("nome2", c.getNome());
    }

    @Test
    void setCognomeTest() {
        c.setCognome("cognome2");
        assertEquals("cognome2", c.getCognome());
    }

    @Test
    void addNumeroTest() throws InvalidNumberException {
        c.addNumero(new ContactNumero("0987654321"));
        assertEquals(2 , c.getNumeriDiTelefono().size());
    }

    @Test
    void addEmailTest() throws InvalidEmailException {
        c.addEmail(new ContactEmail("mail2@mail.com"));
        assertEquals(2 , c.getEmail().size());
    }

    @Test
    void setDescrizioneTest() {
        c.setDescrizione("descrizione2");
        assertEquals("descrizione2", c.getDescrizione());
    }
    @Test
    void setFavoritiTest() {
        c.setFavoriti(true);
        assertEquals(true, c.getFavourite());
    }


    @Test
    void compareToContattiUgualiTest() throws Exception{
        Contatto contattoUguale = new Contatto("nome","cognome","descrizione",false);
        contattoUguale.addEmail(new ContactEmail("mail@mail.com"));
        contattoUguale.addNumero(new ContactNumero("1234567890"));
        assertTrue(c.compareTo(contattoUguale)==0);
    }

    @Test
    void compareToCognomeInizialeATest(){
        Contatto contattoConCognomeCheIniziaPerA = new Contatto( "nome" , "aaaaa" , "descrizione" , false);
        assertTrue(c.compareTo(contattoConCognomeCheIniziaPerA) > 0); //check se c segue c3
    }

    @Test
    void compareToCognomeInizialeZTest(){
        Contatto contattoConCognomeCheIniziaPerZ = new Contatto("nome" , "z" , "descrizione" , false);
        assertTrue(c.compareTo(contattoConCognomeCheIniziaPerZ) < 0); //check se c precede c3
    }

    @Test
    void compareToCognomiUgualiInizialeNomeA(){
        Contatto contattoStessoCognomeNomeCheIniziaPerA = new Contatto( "a" , "cognome" , "descrizione" , false);
        assertTrue(c.compareTo(contattoStessoCognomeNomeCheIniziaPerA) > 0); //check se c segue c5,con cognome uguale , confrontandoli per nome
    }

    @Test
    void compareToCognomiUgualiInizialeNomeZ(){

        Contatto contattoStessoCognomeNomeCheIniziaPerZ = new Contatto( "z" , "cognome" , "descrizione" , false);
        assertTrue(c.compareTo(contattoStessoCognomeNomeCheIniziaPerZ) < 0);


    }



}