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


class ManagerContattiTest {
    Contatto c;
    
    @BeforeEach
    void setUp() throws Exception {
        c = new Contatto("nome","cognome","descizione",false);
    }
    @Test
    void addNumeroTest() throws InvalidNumberException{
        
        ManagerContatti.addNumero(c.getNumeriDiTelefono(), new ContactNumero("1234567890"));
        
        assertEquals(1,c.getNumeriDiTelefono().size());
        

        //Caso numero duplicato , la size deve rimanere 1 in quanto non accettiamo numeri duplicati
        try{
        ManagerContatti.addNumero(c.getNumeriDiTelefono(), new ContactNumero("1234567890"));
        }
        catch(InvalidNumberException ex){
        assertEquals(1,c.getNumeriDiTelefono().size());
        }
        assertEquals(1,c.getNumeriDiTelefono().size()); //ripeto controllo nel caso in cui non venga lanciata eccezione
        
        try{
        ManagerContatti.addNumero(c.getNumeriDiTelefono(), new ContactNumero("0987654321"));
        ManagerContatti.addNumero(c.getNumeriDiTelefono(), new ContactNumero("1987654321"));
        ManagerContatti.addNumero(c.getNumeriDiTelefono(), new ContactNumero("2987654321"));
        }
        catch(InvalidNumberException ex){
        assertEquals(3,c.getNumeriDiTelefono().size());
        }
        assertEquals(3,c.getNumeriDiTelefono().size()); //ripeto controllo nel caso in cui non venga lanciata eccezione
        
    }

    @Test
    void addEmailTest() throws InvalidEmailException {


        ManagerContatti.addEmail(c.getEmail(), new ContactEmail("mail@mail.com"));
        
        assertEquals(1,c.getEmail().size());


        //Caso email duplicata, la size deve rimanere 1 in quanto non accettiamo email duplicate
        try{
        ManagerContatti.addEmail(c.getEmail(), new ContactEmail("mail@mail.com"));
        }
        catch(InvalidEmailException ex){
        assertEquals(1,c.getEmail().size());
        System.out.println("Eccezione InvalidNumber lanciata test");
        }

        //Controllo che non mi faccia aggiungere più di 3 emails
        try{
        ManagerContatti.addEmail(c.getEmail(), new ContactEmail("mail1@mail.com"));
        ManagerContatti.addEmail(c.getEmail(), new ContactEmail("mail2@mail.com"));
        ManagerContatti.addEmail(c.getEmail(), new ContactEmail("mail3@mail.com"));
        }
        catch(InvalidEmailException ex){
        assertEquals(3,c.getEmail().size());
        System.out.println("Eccezione InvalidEmail lanciata test");
        }

        
    }
}
