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

    // ID = 4.1.1
    @Test
    void isValidNumberNumeroValidoTest() {
        assertTrue(num.isValidNumber());
        System.out.println("Test ID = 4.1.1 SUPERATO");
    }
    @Test
    void isValidNumberNumeroNonValidTest(){
        ContactNumero numeroNonValido = new ContactNumero("12345");
        assertFalse(numeroNonValido.isValidNumber());
        System.out.println("TEST ID = 4.1.2 SUPERATO");
    }

    // ID = 4.2
    @Test
    void getAssociatedNumber() {
        assertEquals("3898537006", num.getAssociatedNumber());
        System.out.println("Test ID = 4.2 SUPERATO");
    }

    // ID = 4.3.1
    @Test
    void isNumeroDiCasaNumeroNonDiCasaTest() {
        assertFalse(num.isNumeroDiCasa());
        System.out.println("Test ID = 4.3.1 SUPERATO");
    }
    @Test
    void isNumeroDiCasaNumeroDiCasaTest(){
        ContactNumero numeroDiCasa = new ContactNumero("089761388");
        assertTrue(numeroDiCasa.isNumeroDiCasa());
        System.out.println("Test ID=4.3.2 SUPERATO");
    }

    // ID = 4.4
    @Test
    void testEqualsNumeriUgualiTest() {
        //Caso numeri uguali
        ContactNumero numeroUgualeANum = new ContactNumero("3898537006");
        assertTrue(num.equals(numeroUgualeANum));
        System.out.println("Test ID = 4.4.1 SUPERATO");
    }


    @Test
    void testEqualsNumeriDiversiTest(){
        ContactNumero numeroDiversoDaNum = new ContactNumero("1234567890");
        assertFalse(num.equals(numeroDiversoDaNum));
        System.out.println("Test ID=4.4.2 SUPERATO");

    }
}