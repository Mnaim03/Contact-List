public class MainTest {
    public static void main(String[] args) {

        Rubrica rubrica = new Rubrica();

        Contatto c1 = new Contatto("momo","naim");
        Contatto c2 = new Contatto("enzo","ragone");
        c1.addNumero("1");
        c2.addNumero("2");
        c2.addNumero("3");

        rubrica.addContatto(c1);
        rubrica.addContatto(c2);

        rubrica.salvaVcard("vCardFile.vcf");
        

    }
}