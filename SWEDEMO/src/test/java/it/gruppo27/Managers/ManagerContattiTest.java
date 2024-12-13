package it.gruppo27.Managers;

import it.gruppo27.Exceptions.InvalidEmailException;
import it.gruppo27.Exceptions.InvalidNumberException;
import it.gruppo27.Models.Contact.ContactEmail;
import it.gruppo27.Models.Contact.ContactNumero;
import it.gruppo27.Models.Contact.Contatto;
import it.gruppo27.Models.Rubrica;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

// ID 3
class ManagerContattiTest {
    Contatto c;
    
    @BeforeEach
    void setUp() throws Exception {
        c = new Contatto("nome","cognome","descizione",false);
        c.addNumero(new ContactNumero("1234567890"));
        c.addEmail(new ContactEmail("mail@mail.com"));
    }

// ID = 3.1.1
    @Test
    void addNumeroSingoloNumeroValidoTest(){
        try {
            ManagerContatti.addNumero(c.getNumeriDiTelefono(), new ContactNumero("3312124357"));
        }catch(InvalidNumberException ex){}

        assertEquals(2,c.getNumeriDiTelefono().size());
        System.out.println("Test ID = 3.1.1 SUPERATO");
    }

// ID = 3.1.3
    @Test
    void addNumeroSuperamentoLimiteTreTest(){
        try{
            ManagerContatti.addNumero(c.getNumeriDiTelefono(), new ContactNumero("0987654321"));
            ManagerContatti.addNumero(c.getNumeriDiTelefono(), new ContactNumero("1987654321"));
            ManagerContatti.addNumero(c.getNumeriDiTelefono(), new ContactNumero("2987654321"));
        }catch(InvalidNumberException ex){
            assertEquals(3,c.getNumeriDiTelefono().size());
            System.out.println("Test ID = 3.1.3 SUPERATO");
        }

    }

// ID = 3.1.2
    @Test
    void addNumeroSingoloDuplicatoTest(){
        try{
            ManagerContatti.addNumero(c.getNumeriDiTelefono(),new ContactNumero("1234567890"));
        }catch(InvalidNumberException ex){
            //Viene messo qui l'assert per verificare che venga lanciata l'eccezione
            assertEquals(1,c.getNumeriDiTelefono().size());
            System.out.println("Test ID = 3.1.2 SUPERATO");
        }
    }

// ID = 3.2.1
    @Test
    void addEmailSingolaValidaTest(){
            try {
                ManagerContatti.addEmail(c.getEmail(), new ContactEmail("mailValida@gmail.com"));
            }catch(InvalidEmailException ex){}

            assertEquals(2,c.getEmail().size());
        System.out.println("Test ID = 3.2.1 SUPERATO");
        }
//ID = 3.2.2
    @Test
    void addEmailSingolaDuplicataTest(){
        try{
            ManagerContatti.addEmail(c.getEmail(),new ContactEmail("mail@mail.com"));
        }catch(InvalidEmailException ex){
            assertEquals(1,c.getEmail().size());
            System.out.println("Test ID = 3.2.2 SUPERATO");
        }


    }

//ID = 3.2.3
    @Test
    void addEmailSuperamentoLimiteTre(){
        try{
            ManagerContatti.addEmail(c.getEmail(), new ContactEmail("mail1@mail.com"));
            ManagerContatti.addEmail(c.getEmail(), new ContactEmail("mail2@mail.com"));
            ManagerContatti.addEmail(c.getEmail(), new ContactEmail("mail3@mail.com"));
        }catch(InvalidEmailException ex){
            assertEquals(3,c.getEmail().size());
            System.out.println("Test ID = 3.2.3 SUPERATO");
        }
    }


    }







