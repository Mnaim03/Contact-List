import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeSet;

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



    public void salvaVCard(String nomefile) throws IOException {
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
    }
    public Rubrica leggiVCard(String nomefile) {
        try(Scanner sc = new Scanner(new BufferedReader(new FileReader(nomefile)))){
            Rubrica r = new Rubrica();
            
                sc.useDelimeter("[\n]");

                while(sc.hasNext()){
                    String FN;
                    while(FN.startsWith("FN")){
                        FN=s.next();
                    }
                    String nome_cognome[] = FN.substring(4).split(" ");
                    
                    Contatto c = new Contatto(nome_cognome[0],nome_cognome[1]);
                    
                }
        }
    }
}
