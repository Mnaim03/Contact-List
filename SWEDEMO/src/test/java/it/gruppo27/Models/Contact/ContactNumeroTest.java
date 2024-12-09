package it.gruppo27.Models.Contact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactNumeroTest {
    ContactNumero num;

    @BeforeEach
    void setUp() {
        num = new ContactNumero("3898537006");
    }

    @Test
    void isValidNumber() {
        assertTrue(num.isValidNumber() == true);
    }

    @Test
    void getAssociatedNumber() {
        assertEquals("3898537006", num.getAssociatedNumber());
    }

    @Test
    void isNumeroDiCasa() {
        assertFalse(num.isNumeroDiCasa() == true);
    }

    @Test
    void testEquals() {
        ContactNumero num2 = new ContactNumero("3898537006");
        assertTrue(num.equals(num2));
    }
}