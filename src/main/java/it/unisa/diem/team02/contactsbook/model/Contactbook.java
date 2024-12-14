package it.unisa.diem.team02.contactsbook.model;

import it.unisa.diem.team02.contactsbook.database.Database;
import it.unisa.diem.team02.contactsbook.ui.controllers.AddViewController;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @brief Classe che modella una rubrica.
 * 
 * Contiene una lista di contatti.
 * 
 * @author team02
 */
public class Contactbook {
    private ObservableList<Contact> contacts;

    
    /**
     * @brief Crea un nuovo oggetto Contactbook inizializzando la lista di contatti.
     * 
     * @post La lista di contatti è inizializzata.
     */
    public Contactbook(){
        contacts=FXCollections.observableArrayList();

    }

    /**
    * @brief Restituisce la lista dei contatti della rubrica.
    *
    * @pre La variabile d'istanza contacts deve essere stata inizializzata correttamente.
    * @post Si ottiene un riferimento alla lista di contatti della rubrica.
    *
    * @return Una lista osservabile contenente tutti i contatti della rubrica.
    */
    public ObservableList<Contact> getContacts() {
        return contacts;
    }
    
    /**
     * @brief Rimuove, se presente, il contatto dalla lista dei contatti.
     * 
     * @param c Il contatto da rimuovere dalla rubrica.
     * 
     * @pre La variabile d'istanza contacts deve essere stata inizializzata correttamente.
     * @post Il contatto non è più presente nella lista.
     * 
     * 
     */
    public void delete(Contact c){
        contacts.remove(c);
    }


    
    
 
    /**
     * @brief Aggiunge il contatto passato come parametro alla lista dei contatti.
     * 
     * @param c Il contatto da aggiungere alla rubrica.
     * 
     * @pre La variabile d'istanza contacts deve essere stata inizializzata correttamente.
     * @post Il contatto viene aggiunto alla lista.
     * 
     */
    public void add(Contact c){
        contacts.add(c);
    }
    
    /**
     * @brief Il metodo verifica se un contatto è presente nella rubrica.
     * 
     * @param c Il contatto da cercare.
     * @return true se il contatto è presente in rubrica, false altrimenti.
     * 
     * @pre La variabile d'istanza contacts deve essere stata inizializzata correttamente.
     * @post Si ottiene un valore booleano che indica se il contatto specificato è presente o meno
     * in rubrica.
     */
    public boolean contains(Contact c){
        return contacts.contains(c);
    }
    
    /**
     * @brief Il metodo verifica se un contatto è presente nella rubrica, a meno dell'istanza
     * passata come secondo parametro. 
     * 
     * @param newC È il contatto da cercare.
     * @param oldC È l'istanza del contatto da ignorare nella ricerca.
     * @return true se la condizione è verificata, false altrimenti.
     */
    public boolean contains(Contact newC, Contact oldC){
        for (Contact c: contacts){
            if (c!=oldC && c.equals(newC))
                return true;
        }
        return false;
    }
        
    /**
     * Inizializza la lista osservabile con i contatti presenti nel database/file locale.
     */
    public void initializeList(){
        
    }
    
    /**
    * @brief Esporta la lista dei contatti della rubrica in un file.
    *
    * Questo metodo consente all'utente di salvare i contatti presenti nella rubrica in un file passato
    * come parametro.
    * La lista dei contatti viene salvata in formato CSV.
    *
    * @pre La rubrica deve essere inizializzata.
    * @post I contatti vengono salvati nel file selezionato dall'utente. Il file viene creato o sovrascritto
    *       con i dati esportati.
    *
    * @param filename Il file in cui salvare il contatto.
    * @throws FileNotFoundException Se il file non viene trovato.
    * @throws IOException Se si verifica un errore durante la scrittura nel file.
    */
    public void saveOnFile(File filename) throws IOException{
        try(PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter(filename)))){
            pw.println("NOME;COGNOME;NUMERO 1; NUMERO 2; NUMERO 3;EMAIL 1;EMAIL 2; EMAIL 3; Tag;");
            for (Contact c : contacts){
                pw.append(c.getName());
                pw.append(';');
                pw.append(c.getSurname());
                pw.append(';');
                
                String[] number=c.getNumber().split("\n");
                if(number.length>=1)
                    pw.append(number[0]);
                pw.append(';');
                if (number.length>=2)
                    pw.append(number[1]);
                pw.append(';');
                 if (number.length>=3)
                    pw.append(number[2]);
                 pw.append(';');
                 
                String[] email=c.getEmail().split("\n");
                if (email.length>=1)
                    pw.append(email[0]);
                pw.append(';');
                if (email.length>=2)
                    pw.append(email[1]);
                pw.append(';');
                 if (email.length>=3)
                    pw.append(email[2]);
                pw.append(';');
                
                String[] tag=c.getTag().split("\n");
                if (tag.length>=1)
                    pw.append(tag[0]);
                pw.append(';');
                if (tag.length>=2)
                    pw.append(tag[1]);
                pw.append(';');
                 if (tag.length>=3)
                    pw.append(tag[2]);
                pw.append(';');
                    
                pw.append('\n');
            }
        }
    }
    
    /**
    * @brief Importa i contatti da un file e li aggiunge alla rubrica.
    * 
    * Questo metodo permette all'utente di aggiungere dei contatti dal file passato come parametro.
    * Ogni riga del file viene letta e i dati vengono utilizzati per creare nuovi oggetti 
    * `Contact`, che vengono successivamente aggiunti alla rubrica. Il formato del file 
    * deve essere conforme alla struttura prevista, con i campi separati da punto e virgola.
    * 
    * @details Il metodo apre un file passato come parametro, legge ogni riga e divide i dati nei 
    * rispettivi campi (nome, cognome, numeri di telefono, e-mail, tag). I contatti creati vengono 
    * aggiunti alla rubrica. Prima di aggiungere un campo (ad esempio numero di telefono) al contatto, 
    * si verifica che questo non sia vuoto.
    * 
    * @pre Il file passato come parametro deve essere un file valido.
    * @post I contatti letti dal file vengono aggiunti alla rubrica.
    * 
    * @throws IOException Se si verifica un errore durante la lettura del file.
    * @throws ClassNotFoundException Se il tipo di dato non è trovato durante il caricamento dei dati.
    */
    public void loadFromFile(File filename) throws IOException{
        
        try(BufferedReader br=new BufferedReader(new FileReader(filename))){
            if (br.readLine() == null) return;
            String line;
            while((line=br.readLine())!= null){
                String campi []=line.split(";",-1);
                Contact c=new Contact(campi[0], campi[1]);
                try {
                    sleep(3000); //diamo il tempo di generare un id univoco valido per il contatto
                } catch (InterruptedException ex) {
                    Logger.getLogger(Contactbook.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (!campi[2].equals(""))
                    c.addNumber(campi[2]);
                if (!campi[3].equals(""))
                    c.addNumber(campi[3]);
                if (!campi[4].equals(""))
                    c.addNumber(campi[4]);

                if (!campi[5].equals(""))
                    c.addEmail(campi[5]);
                if (!campi[6].equals(""))
                    c.addEmail(campi[6]);
                if (!campi[7].equals(""))
                    c.addEmail(campi[7]);
                
                if(!campi[8].equals("")){ //è presente almeno un tag.
                    if(campi[8].equals(Tag.Job.toString())) 
                        c.addTag(Tag.Job); //se il primo tag è Job, è anche l'unico
                    else {
                        if (campi[8].equals(Tag.University.toString())){
                            c.addTag(Tag.University);
                            if (campi[9].equals(Tag.Job.toString())) //l'altro tag, se c'è è Job
                                c.addTag(Tag.Job);
                        } 
                        else{
                            c.addTag(Tag.Home);
                            if (!campi[9].equals("")){
                                if (campi[9].equals(Tag.Job))
                                    c.addTag(Tag.Job);
                                else{
                                    c.addTag(Tag.University);
                                    if (campi[10].equals(Tag.Job))
                                        c.addTag(Tag.Job);
                            }
                        }
                    }
                    }
                }

                contacts.add(c);
            }
        }
    }
}
