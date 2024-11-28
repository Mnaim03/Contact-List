import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeSet;
<<<<<<< HEAD
import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.property.Telephone;
import ezvcard.property.Email;

import java.io.File;
import java.io.IOException;
=======
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.NoSuchElementException;
import java.util.Scanner;
>>>>>>> 7089997bc47d38536a4526ea0cf05bd0acd2ed65

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
                bw.write("VERSION:3.0");
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

    public void salvaVCF(String filename){


    }
<<<<<<< HEAD


=======
    public static Rubrica leggiVCard(String nomefile) throws FileNotFoundException{
        try(Scanner sc = new Scanner(new BufferedReader(new FileReader(nomefile)))){
            Rubrica r = new Rubrica();
            
                sc.useDelimiter("[\n]");

                while(sc.hasNext()){
                    
                    String linea=sc.next();
                    while(!linea.startsWith("FN")){
                        linea=sc.next();
                       
                    }
                    String nome_cognome[] = linea.substring(3).split(" ");
                    Contatto c = new Contatto(nome_cognome[0],nome_cognome[1]);
                        linea=sc.next();
                    while(linea.startsWith("TEL")&&!linea.startsWith("EMAIL")&&!linea.startsWith("END")){
                        System.out.println(linea);
                        c.addNumero(linea.substring(4));
                        linea=sc.next();
                    }
                        
                    while(linea.startsWith("EMAIL")&&!linea.startsWith("END")){
                        System.out.println(linea);
                        c.addEmail(linea.substring(6));
                        linea=sc.next();
                    }
                        System.out.println(linea);
                         
                        r.addContatto(c);
                        
                        linea=sc.next(); //vcf aggiunge un \n dopo ogni riga dunque dopo END segnala ancora un elemento
                    
                }
                        
                
                return r;
        }
        catch(NoSuchElementException ex){
            System.out.println("Eccezione");
                }
        return null ; 
    }
>>>>>>> 7089997bc47d38536a4526ea0cf05bd0acd2ed65
}


