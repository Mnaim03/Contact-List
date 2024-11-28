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

public class Rubrica {
    private TreeSet<Contatto> contatti;

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

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer("La mia rubrica: "+"\n");
        for(Contatto c : contatti){
            sb.append(c.toString()+"\n");
        }
        return sb.toString();
    }



    /*public void salvaVCard(String nomefile) throws IOException {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(nomefile))) {
            //Scorro i contatti nella rubrica
            for (Contatto tmp : contatti) {
                bw.write("BEGIN:VCARD");
                bw.newLine();
                bw.write("VERSION:2.1");
                bw.newLine();
                //Inizio dettagli del contatto
                bw.write("FN:" + tmp.getNome() + " " + tmp.getCognome());
                bw.newLine();
                // Aggiungi i numeri di telefono del contatto
                for (String numero : tmp.getNumeroTelefono()) {
                    bw.write("TEL;TYPE=CELL:" + numero);
                    bw.newLine();
                }
                // Aggiungi gli indirizzi email del contatto
                for (String email : tmp.getEmail()) {
                    bw.write("EMAIL;TYPE=INTERNET:" + email);
                    bw.newLine();
                }
                bw.write("END:VCARD");
                bw.newLine();
                bw.newLine();
            }
        }
    }*/

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
    
    public static Rubrica leggiVCF(String filename) throws IOException{
        
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


