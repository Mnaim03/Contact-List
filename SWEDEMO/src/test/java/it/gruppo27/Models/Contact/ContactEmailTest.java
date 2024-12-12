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

    // ID = 5.1
    @Test
    void getAssociatedEmailTest() {
        assertEquals("mail@mail.com", e.getAssociatedEmail());
        System.out.println("Test ID = 5.1 SUPERATO");
    }

    // ID = 5.2.1
    @Test
    void isValidEmailMailValidaTest() {
        assertTrue(e.isValidEmail());
        System.out.println("Test ID=5.2.1 SUPERATO");
    }

    @Test
        //ID 5.2.2
    void isValidEmailMailNonValidaTest() {
        ContactEmail emailNonValida = new ContactEmail("mailmail.it");
        assertFalse(emailNonValida.isValidEmail());
        System.out.println("Test ID=5.2.2 SUPERATO");

    }

    // ID = 5.3.1
    @Test
    void equalsMailUgualiTest() {
        //Caso email uguali
        ContactEmail emailUguale = new ContactEmail("mail@mail.com");
        assertTrue(emailUguale.equals(e));
        System.out.println("Test ID = 5.3.1 SUPERATO");
    }
    // ID = 5.3.2
    @Test
    void equalsMailDiverseTest(){
        ContactEmail mailDiversa = new ContactEmail("maildiversa@gmail.com");
        assertFalse(mailDiversa.equals(e));
        System.out.println("Test ID = 5.3.2 SUPERATO");
    }
}