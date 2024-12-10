package it.gruppo27.Models.Contact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactEmailTest {
    ContactEmail e;

    @BeforeEach
    void setUp() {
        e = new ContactEmail("mail@mail.com");
    }

    @Test
    void getAssociatedEmailTest() {
        assertEquals("mail@mail.com", e.getAssociatedEmail());
    }

    @Test
    void isValidEmailTest() {
        assertTrue(e.isValidEmail());
        ContactEmail e1 = new ContactEmail("mailmail.com"); //Email senza @
        assertFalse(e1.isValidEmail());
        ContactEmail emailSenzaEstensione = new ContactEmail("mail@mail"); //Email senza estensione
        assertFalse(emailSenzaEstensione.isValidEmail());
    }

    @Test
    void equalsTest(){
        //Caso email uguali
        ContactEmail emailUguale = new ContactEmail("mail@mail.com");
        assertTrue(emailUguale.equals(e));
        //Caso email diverse
        ContactEmail emailDiversa = new ContactEmail("mail1@mail.com");
        assertFalse(emailDiversa.equals(e));
    }
}