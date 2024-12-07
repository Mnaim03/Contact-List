/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package it.gruppo27.interfaces;

import it.gruppo27.Models.Contact.Contatto;
import it.gruppo27.Exceptions.InvalidEmailException;
import it.gruppo27.Exceptions.InvalidNumberException;
import it.gruppo27.Models.Rubrica;

import java.io.IOException;
import java.util.Set;
import javafx.collections.ObservableList;

/**
 * L'interfaccia <code>InterfaceRubrica</code> definisce un contratto per le classi che implementano
 * una rubrica di contatti. Le classi che implementano questa interfaccia devono fornire i metodi necessari
 * per gestire e manipolare i contatti, inclusi l'aggiunta, la rimozione, la ricerca e la gestione dei dati
 * di contatto, nonché l'esportazione e l'importazione dei dati in formato VCF (vCard).
 * <p>
 * L'interfaccia è progettata per consentire operazioni di gestione dei contatti, come la gestione dei
 * contatti di una rubrica, la ricerca di contatti e la persistenza dei dati tramite il formato vCard.
 * </p>
 *
 * @author Vincenzo Ragone
 * @see Contatto Per informazioni sul tipo di contatti gestiti dalla rubrica.
 * @see Rubrica Per la classe che implementa la rubrica.
 */

public interface InterfaceRubrica {
     /**
      * Aggiunge un nuovo contatto alla rubrica.
      * <p>
      * Il metodo inserisce un contatto nella rubrica, aggiornando la lista dei contatti.
      * </p>
      *
      * @param contatto Il contatto da aggiungere alla rubrica.
      * @pre Il contatto non può essere nullo.
      * @post Il contatto viene aggiunto alla rubrica.
      */
     void addContatto(Contatto contatto);


     /**
      * Rimuove un contatto dalla rubrica.
      * <p>
      * Il metodo elimina il contatto dalla rubrica, aggiornando la lista dei contatti.
      * </p>
      *
      * @param contatto Il contatto da rimuovere dalla rubrica.
      * @pre Il contatto deve essere presente nella rubrica.
      * @post Il contatto viene rimosso dalla rubrica.
      */
     void removeContatto(Contatto contatto) ;



     /**
      * Restituisce una lista osservabile di contatti.
      * <p>
      * Il metodo consente di ottenere una lista dei contatti che può essere osservata per eventuali modifiche.
      * </p>
      * @return Una lista osservabile di contatti.
      */
     ObservableList<Contatto> getListaOsservabile();


     /**
      * Restituisce un set di contatti nella rubrica.
      * <p>
      * Il metodo restituisce un insieme di contatti senza duplicati.
      * </p>
      *
      * @return Un set di contatti.
      */
     Set<Contatto> getContatti();



     /**
      * Esegue una ricerca nella rubrica per trovare contatti che corrispondono alla stringa di ricerca(sotto
      * stringa del nome o del cognome o intero nome e cognome).
      * <p>
      * Il metodo consente di cercare i contatti nella rubrica in base alla stringa passata come parametro.
      * </p>
      *
      * @param stringa la stringa in base a cui filtrare i contatti
      * @return Una nuova rubrica contenente i contatti che corrispondono alla ricerca.
      */
     Rubrica ricercaContatti(String stringa);




     boolean isPresent(Contatto c);




     /**
      * Rimuove tutti i contatti dalla rubrica.
      * <p>
      * Il metodo elimina tutti i contatti dalla rubrica.
      * </p>
      * @pre c'è almeno un contatto in rubrica
      * @post La rubrica è vuota dopo l'esecuzione del metodo.
      */
     public void deleteAll();



     /**
      * Restituisce una rappresentazione testuale della rubrica.
      * <p>
      * Il metodo fornisce una rappresentazione della rubrica, che include i contatti attuali e le informazioni associate.
      * </p>
      *
      * @return Una stringa che rappresenta la rubrica.
      */
     String toString();



     /**
      * Esporta i contatti della rubrica in un file .vcf (vCard).
      * <p>
      * Questo metodo consente di esportare tutti i contatti presenti nella rubrica in un file
      * con formato VCF (vCard). Il formato VCF è uno standard utilizzato per memorizzare informazioni
      * sui contatti, come nome, numero di telefono, email e altre informazioni pertinenti.
      * </p>
      *
      * @param filename Il percorso del file in cui verranno salvati i contatti in formato .vcf.
      *                 Il file verrà creato nella posizione specificata.
      * @pre La rubrica non è vuota
      * @post Viene creato un nuovo file .vcf contenente i dati della rubrica nella posizione specificata
      * @throws IOException Se si verifica un errore durante la scrittura del file,
      *                     ad esempio se la destinazione non è accessibile o se si verifica un errore di I/O.
      * @see Rubrica Per maggiori dettagli su come la rubrica gestisce i contatti.
      *
      */
     void salvaVCF(String filename) throws IOException;

     /**
      * Legge un file .vcf (vCard) e carica i contatti al suo interno nella rubrica.
      * <p>
      * Questo metodo esamina un file .vcf che contiene informazioni sui contatti e le carica all'interno
      * della rubrica. Ogni contatto viene analizzato per estrarre i dati come nome, cognome, numeri di telefono
      * ed email. In caso di errori nel file, vengono lanciate eccezioni specifiche per segnalarli.
      * </p>
      *
      * @param filename Il percorso del file .vcf da leggere. Il file deve essere nel formato vCard standard.
      * @return Una nuova rubrica che contiene i contatti letti dal file VCF.
      * @throws IOException Se si verifica un errore durante la lettura del file, come un file inesistente o accessibile.
      * @throws InvalidEmailException Se una o più email nel file non sono valide.
      * @throws InvalidNumberException Se uno o più numeri di telefono nel file non sono validi.
      *
      * @pre Il file specificato deve esistere e deve essere accessibile in lettura.
      * @pre Il file deve essere formattato correttamente come file VCF.
      * @post Se il file è letto correttamente, i contatti vengono aggiunti alla rubrica.
      * @post Se ci sono errori nei dati (email o numeri non validi), viene sollevata l'eccezione corrispondente.
      * @see InvalidEmailException Per informazioni sulle eccezioni relative alla validità delle email.
      * @see InvalidNumberException Per informazioni sulle eccezioni relative alla validità dei numeri di telefono.
      */
     Rubrica leggiVCF(String filename) throws IOException,InvalidEmailException,InvalidNumberException;

}

