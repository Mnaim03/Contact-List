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
        assertTrue(num.isValidNumber());
        //check per numero invalido
        ContactNumero num1 = new ContactNumero("0");
        assertFalse(num1.isValidNumber());
    }

    @Test
    void getAssociatedNumber() {
        assertEquals("3898537006", num.getAssociatedNumber());
    }

    @Test
    void isNumeroDiCasa() {
        assertFalse(num.isNumeroDiCasa());
        ContactNumero num1 = new ContactNumero("089555444");
        assertTrue(num1.isNumeroDiCasa());
    }

    @Test
    void testEquals() {
        //Caso numeri uguali
        ContactNumero numeroUguale = new ContactNumero("3898537006");
        assertTrue(num.equals(numeroUguale));
        //Caso numeri diversi
        ContactNumero numeroDiverso = new ContactNumero("1234567890");
        assertFalse(num.equals(numeroDiverso));
    }
}