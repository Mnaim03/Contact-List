package it.gruppo27.Models;

import it.gruppo27.Exceptions.InvalidNumberException;
import it.gruppo27.Models.Contact.ContactEmail;
import it.gruppo27.Models.Contact.ContactNumero;
import it.gruppo27.Models.Contact.Contatto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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
        r.getListaOsservabile().setAll(r.getContatti());
    }

    @Test
    void addContatto() {
        Contatto c2 = new Contatto("nome2","cognome2","descizione",false);
        r.addContatto(c2);
        assertEquals(2, r.getContatti().size());
        //X controllare gestione corretta duplicati
        r.addContatto(c2);
        assertEquals(2, r.getContatti().size());
    }

    @Test
    void removeContatto() {
        //rimuovo contatto inesistente
        r.removeContatto( new Contatto("nome2","cognome2","descizione",false));
        assertEquals(1, r.getContatti().size());
        //rimuovo un contatto esistente
        Contatto c2 = new Contatto("nome2","cognome2","descizione",false);
        r.removeContatto(c2);
        assertEquals(1 , r.getContatti().size());
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
        assertNotNull(r.ricercaContatti("cognome"));
        assertNotNull(r.ricercaContatti("cognome"+" "+"nome"));
        assertNotNull(r.ricercaContatti("nome"+" "+"cognome"));
    }

    @Test
    void isPresent() {
        //check per contatto non presente in lista
        Contatto c2 = new Contatto("nome2","cognome2","descizione",false);
        assertFalse(r.isPresent(c2));
        //check per contatto presente in lista
        r.addContatto(c2);
        assertTrue(r.isPresent(c2));
    }

    @Test
    void deleteAll() {
        r.deleteAll();
        assertEquals(0, r.getContatti().size());
    }

    @Test
    void salvaVCF() {
        try {
            r.salvaVCF("LaMiaRubrica.vcf");
        }catch(IOException ex){}

        File vcfFile = new File("LaMiaRubrica.vcf");
        //Verifico che il file sia stato creato
        assertTrue(vcfFile.exists());
        String fileContent="";
        try {
             fileContent = new String(Files.readAllBytes(vcfFile.toPath()), StandardCharsets.UTF_8);
        }catch(IOException ex){}
         String expectedContent =
                 "BEGIN:VCARD" +"\n" +
                 "VERSION:3.0" + "\n" +
                 "PRODID:ez-vcard 0.12.1" + "\n" +
                 "FN:nome cognome" +"\n" +
                 "TEL;TYPE=cell:1234567890" + "\n" +
                 "EMAIL:mail@mail.com" + "\n" +
                 "X-DESCRIPTION:descizione" + "\n"+
                 "END:VCARD" +"\n";
        // Normalizzo i separatori di riga per entrambi i contenuti, per evitare che i file siano diversi solo per i separatori di riga diversi
        String normalizedFileContent = fileContent.replace("\r\n", "\n");
        String normalizedExpectedContent = expectedContent.replace("\r\n", "\n");

        assertEquals(normalizedExpectedContent, normalizedFileContent);
        //Pulisco dopo averlo creato
        vcfFile.delete();

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