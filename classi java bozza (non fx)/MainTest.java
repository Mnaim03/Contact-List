import input.AnagraficaStudenti;

public class MainTest {
    public static void main(String[] args)throws Exception{

        Rubrica rubrica = new Rubrica();

        Contatto c1 = new Contatto("momo","naim");
        Contatto c2 = new Contatto("enzo","ragone");
        c1.addNumero("1");

        c2.addNumero("3");

        rubrica.addContatto(c2);
        rubrica.addContatto(c1);
        System.out.println(rubrica);
        rubrica.salvaVCard("vCardFile.vcf");
        Rubrica r = Rubrica.leggiVCard("vCardFile.vcf");
        System.out.println("prova leggiVCard:\n"+ r );


    }
}