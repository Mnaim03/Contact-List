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
    public void salvaVCard(String nomefile) throws IOException{
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
                // Aggiungi i numeri di telefono
                for (String numero : contatto.getNumeroTelefono()) {
                    writer.write("TEL;TYPE=CELL:" + numero);
                    writer.newLine();
                }
                // Aggiungi gli indirizzi email
                for (String email : contatto.getEmail()) {
                    writer.write("EMAIL;TYPE=INTERNET:" + email);
                    writer.newLine();
                }
                writer.write("END:VCARD");
                writer.newLine();
            }
        }
    }
}
