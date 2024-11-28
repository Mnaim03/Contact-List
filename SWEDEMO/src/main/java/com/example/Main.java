package com.example;
public class Main {
    public static void main(String[] args)throws Exception{

        Rubrica rubrica = new Rubrica();

        Contatto c1 = new Contatto("momo","naim");
        Contatto c2 = new Contatto("enzo","ragone");
        c1.addNumero("111111");
         c1.addNumero("111121");
        c2.addNumero("3333333");
         c2.addNumero("3333332");
        c1.addEmail("momo@naim");
        c2.addEmail("enzo@ragone");

        rubrica.addContatto(c2);
        rubrica.addContatto(c1);
        rubrica.salvaVCF("vCardFile.vcf");
        Rubrica tmp = new Rubrica();
        tmp= Rubrica.leggiVCF("vCardFile.vcf");
        System.out.println(tmp);



    }
}
