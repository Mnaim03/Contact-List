import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Contatto implements Comparable<Contatto> {
    private String nome;
    private String cognome;
    private ArrayList<String> numeroTelefono;
    private ArrayList<String> email;

    public Contatto(String nome, String cognome ) {
        this.nome = nome;
        this.cognome = cognome;
        this.numeroTelefono = new ArrayList<>();
        this.email = new ArrayList<>();
    }

    public void addNumero(String numero){
        if(numeroTelefono.contains(numero)) return;
        if(numeroTelefono.size()<3) numeroTelefono.add(numero);
    }

    public void addEmail(String mail){
        if(email.contains(mail)) return;
        if(email.size()<3) email.add(mail);
    }

    // Getters
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public ArrayList<String> getNumeroTelefono() { return numeroTelefono; }
    public ArrayList<String> getEmail() { return email; }

    @Override
    public int compareTo(Contatto altro) {
        // Prima confronta per cognome
        int confrontoCognome = this.cognome.compareToIgnoreCase(altro.cognome);
        if (confrontoCognome != 0) {
            return confrontoCognome;
        }
        // Se i cognomi sono uguali, confronta per nome
        int confrontoNome = this.nome.compareToIgnoreCase(altro.nome);
        if (confrontoNome != 0) {
            return confrontoNome;
        }

        // Se anche i nomi sono uguali, usa il numero di telefono come discriminante
        // I nuemri di telefono uguali possono trovarsi in posizioni diverse

        if (this.numeroTelefono.size() == altro.numeroTelefono.size()) {
            if(this.numeroTelefono.containsAll(altro.numeroTelefono) ) return 0;
        }


        //se non si verifaca nessuna
        return 1;


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contatto that = (Contatto) o;
        return Objects.equals(numeroTelefono, that.numeroTelefono);  // Solo il numero di telefono determina l'uguaglianza
    }

    @Override
    public int hashCode() {
        if (numeroTelefono != null) {return Objects.hash(numeroTelefono); } // Solo il numero di telefono nel hashCode}
          return cognome.hashCode();
    }

    @Override
    public String toString() {
        return cognome + " " + nome ;
    }
}