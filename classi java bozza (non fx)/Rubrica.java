import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeSet;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.property.Telephone;
import ezvcard.property.Email;

import java.io.File;
import java.io.IOException;

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

    public void salvaVCF(String filename){


    }


}


