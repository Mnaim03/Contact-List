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
    void getAssociatedEmail() {
        assertEquals("mail@mail.com", e.getAssociatedEmail());
    }

    @Test
    void isValidEmail() {
        assertTrue(e.isValidEmail());
        ContactEmail e1 = new ContactEmail("mailmail.com");
        assertFalse(e1.isValidEmail());
    }
}