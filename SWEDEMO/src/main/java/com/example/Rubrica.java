package com.example;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.property.Telephone;
import ezvcard.parameter.TelephoneType;
import ezvcard.property.Email;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Rubrica implements InterfaceRubrica{
    private Set<Contatto> contatti;

    public Rubrica() {
        this.contatti = new TreeSet<>();
    }

    public void addContatto(Contatto contatto) {
        contatti.add(contatto);
    }

    public void removeContatto(Contatto contatto) {
        contatti.remove(contatto);
    }

    public void stampaContatti() {
        for (Contatto contatto : contatti) {
            System.out.println(contatto);
        }
    }
    public Set<Contatto> getContatti(){
        return contatti;
    }
    
    public Rubrica ricercaContatti(String stringa){
    Rubrica restituita = new Rubrica(); 
    String nomecognome; 
    String cognomenome; 
    for(Contatto tmp : contatti){
        nomecognome = tmp.getNome()+" "+tmp.getCognome(); 
        cognomenome = tmp.getCognome()+" "+tmp.getNome();
        System.out.println(nomecognome);
        if(nomecognome.contains(stringa) || cognomenome.contains(stringa)){
        restituita.addContatto(tmp);
        }
    } 
    return restituita; 
    }
    

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer("La mia rubrica: "+"\n");
        for(Contatto c : contatti){
            sb.append(c.toString()+"\n");
        }
        return sb.toString();
    }


    public void salvaVCF(String filename) throws IOException {
        try(FileWriter fw = new FileWriter(filename)){
        for(Contatto contatto : contatti){
        VCard card = new VCard(); 
        card.setFormattedName(contatto.getNome()+" "+contatto.getCognome());
        aggiungiNumeriDiTelefonoAVCard(contatto,card);
        aggiungiEmailsAVCard(contatto,card);
        Ezvcard.write(card).go(fw);
        }
        }
    }
    
    private void aggiungiNumeriDiTelefonoAVCard(Contatto c,VCard card){
        for(String numero : c.getNumeroTelefono()){
       Telephone telefono = new Telephone(numero);
       telefono.getTypes().add(TelephoneType.CELL);
       card.addTelephoneNumber(telefono);
        }
    }
    
    private void aggiungiEmailsAVCard(Contatto c , VCard card){
        for(String tmp : c.getEmail()){
        Email email = new Email(tmp);
        card.addEmail(email);    
        }
    }
    
    
    //Lettura da file .vcf contenente le VCard 
    
    public Rubrica leggiVCF(String filename) throws IOException{
        
        Rubrica r = new Rubrica();
        try(FileReader fr = new FileReader(filename)){
            List<VCard> vcards = Ezvcard.parse(fr).all();
            for(VCard card : vcards){
            String nome = card.getFormattedName().getValue().split(" ")[0];
            String cognome = card.getFormattedName().getValue().split(" ")[1];
            String [] numeri = new String[card.getTelephoneNumbers().size()];
            int i =0; 
            for(Telephone tmp : card.getTelephoneNumbers()){
            numeri[i] = tmp.getText();
            i++; 
            }
            
            //Emails  
            String [] emails = new String[card.getEmails().size()];
            int j =0; 
            for(Email em : card.getEmails()){
            emails[j]=em.getValue(); 
            j++; 
            }
            Contatto c = new Contatto(nome,cognome);
            for(String numero : numeri){
            c.addNumero(numero);
            }
            
            for(String email : emails){
            c.addEmail(email);
            }
            
            r.addContatto(c);
            }
            
            

            
        }
         return  r;
    }
   
}


