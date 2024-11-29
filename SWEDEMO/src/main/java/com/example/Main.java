package com.example;
public class Main {
    public static void main(String[] args)throws Exception{

        Rubrica rubrica = new Rubrica();

        Contatto c1 = new Contatto("momo","naim");
        Contatto c2 = new Contatto("enzo","ragone");
        Contatto c3 = new Contatto("enzo","rossi"); 
        c1.addNumero("111111");
         c1.addNumero("111121");
        c2.addNumero("3333333");
         c2.addNumero("3333332");
        c1.addEmail("momo@naim");
        c2.addEmail("enzo@ragone");
        
        rubrica.addContatto(c2);
        rubrica.addContatto(c1);
        rubrica.addContatto(c3);
        Rubrica rest = rubrica.ricercaContatti("ragone");
        System.out.println(rest);



    }
}
