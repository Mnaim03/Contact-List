public class MainTest {
    public static void main(String[] args) {

        Rubrica rubrica = new Rubrica();

        Contatto c1 = new Contatto("momo","naim");
        c1.addNumero("6");
        c1.addNumero("7");

        Contatto c2 = new Contatto("momo","naim");
        c2.addNumero("7");
        c2.addNumero("7");

        rubrica.addContatto(  c1  );
        rubrica.addContatto(  c2  );

        rubrica.stampaContatti();

    }
}