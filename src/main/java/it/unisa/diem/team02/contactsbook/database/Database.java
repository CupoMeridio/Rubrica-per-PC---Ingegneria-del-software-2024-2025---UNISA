
package it.unisa.diem.team02.contactsbook.database;

import it.unisa.diem.team02.contactsbook.model.Contact;
import it.unisa.diem.team02.contactsbook.model.Tag;
import it.unisa.diem.team02.contactsbook.model.User;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;
/**
 * @brief
 * Classe che implementa tutti i metodi per comunicare con il database
 * @author team02
 */
public class Database  {
    static public Connection connection; ///Variabile statica che memorizza lo stato della connesione con il database
    static public User user; ///Variabile statica che memorizza l'utente attualemnet collegato
/**
 * 
 * @brief Stabilisce una connessione al database PostgreSQL.
 * 
 * @pre i parametri passati devono essere quelli di una tabella esistente in un database con un propretario e lacorrispettiva password
 * @post la connessione viene stabilita
 * @invariant dbname, user, password
 * 
 * @param dbname Nome del database a cui connettersi.
 * @param user Nome del possessore del database in pgAdmin.
 * @param password Password per accedere al Database .
 * @return Oggetto Connection se la connessione è riuscita, altrimenti null.
 */
    public Connection ConnectionDB(String dbname, String user, String password) {
        Connection conn=null;
        
       
        try {
            Class.forName("org.postgresql.Driver");
            System.out.print("Driver trovato ");
              conn= DriverManager.getConnection("jdbc:postgresql://rubrica-mattiasanzari2003-19e7.k.aivencloud.com:14305/"+dbname+"?ssl=require&user="+user+"&password="+password); //connessione database online
            //conn= DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname,user,password); //Conessione database offline
            if(conn!=null){
                System.out.println("Connessione Stabilita");
            }else{
                System.out.println("Connessione Fallita");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
           return conn;
    }
    
    
    /**
    * 
    * @brief Inserisce un nuovo utente nella tabella specificata.
    * 
    * 
    * @pre conn!= null,tableName deve esistere nel database connesso, l' email inserita deve esser valida e diversa da altre email nel database, la password non può essere vuota o nulla
    * @post viene salvato nella tableName l' email associata allapassword criptata
    * @invariant conn,email, tableName
    * 
    * 
    * @param conn Oggetto Connection per interagire con il database.
    * @param tableName Nome della tabella in cui inserire l'utente.
    * @param email Email dell'utente.
    * @param password Password dell'utente (verrà criptata  prima dell'inserimento).
    * @throws SQLException Se si verifica un errore durante l'inserimento.
    * 
    */

    public void insertUser(Connection conn, String tableName, String email, String password) throws SQLException{
    
           Statement statment;
           String pass= hashPassword(password);
           String query= String.format("insert into %s(email,password) values('%s','%s');",tableName, email, pass);
           statment= conn.createStatement();
           statment.executeUpdate(query);
           System.out.println("Utente inserito");
           statment.close();
        
    }
    
    /**
    * 
    * @brief Recupera tutti gli utenti dal database.
    * 
    * @pre conn!= null, tableName deve esistere nel database connesso
    * @post viene restituita una Hashmap con chiave email e valore la password cryptata
    * @invariant conn, tableName
    * 
    * 
    * @param conn Oggetto Connection per interagire con il database.
    * @param tableName Nome della tabella da cui recuperare gli utenti.
    * @return HashMap contenente le coppie email-password degli utenti.
    * 
    */

  
    public HashMap<String, String> getUser(Connection conn, String tableName){
        
        Statement statement;
        ResultSet rs= null;
        Map <String,String>table=null;
        String email;
        String password;
        try {
            table =  new HashMap();
            String query= String.format("select * from %s", tableName);
            statement= conn.createStatement();
            rs= statement.executeQuery(query);
            while(rs.next()){
                email=rs.getString("email");
                password=rs.getString("password");
                table.put(email, password);      
            }
            statement.close(); 
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return (HashMap<String, String>) table;
    }
    
    
    
    /**
    * 
    * @brief Verifica le credenziali di login di un utente.
    * 
    * 
    * @pre conn!= null, tableName deve esistere nel database connesso, l' email inserita deve esser valida, la password non può essere vuota o nulla
    * @post viene restituito un intero uguale a 1 se le credenziali sono corrette, 0 se la password è errata, -1 se l'email non esiste
    * @invariant conn, tableName, email, password
    * 
    * 
    * @param conn Oggetto Connection per interagire con il database.
    * @param tableName Nome della tabella contenente gli utenti.
    * @param email Email dell'utente.
    * @param password Password inserita dall'utente.
    * @return 1 se le credenziali sono corrette, 0 se la password è errata, -1 se l'email non esiste
    * @throws SQLException Se si verifica un errore durante l'interrogazione.
    */

    
    public int checkLogin(Connection conn,String tableName, String email, String password) throws SQLException{
    
    ResultSet rs= null;
    int esito;  
            Statement statment;
            String query= String.format("select * from %s where email='%s'", tableName,email);
            statment= conn.createStatement();
            rs=statment.executeQuery(query);
         
            if (!rs.isBeforeFirst()) {
                System.out.println("No data found."); 
                esito= -1;// -1 perchè email non è presente nel db
            }else{
                do{
                    rs.next();
                    String em=rs.getString("email");
                    String hashed = rs.getString("password");
                    System.out.print("\n"+email+"   checkLogin");
                   

                    if( checkPassword( password,hashed)){
                         System.out.print(" \n password Giusta ");
                         esito=1; // email e password sono corette e l' utente entra
                    }else{
                       System.out.print(" \n password Sbagliata ");
                       esito= 0;// email corretta e password sbagliata
                    }
                }while(rs.next());
           }
        statment.close(); 
        return esito;
    }
    
    /**
    * 
    * @brief Cripta la password passata dall' utente 
    * 
    * @pre  la password non può essere vuota o nulla
    * @post viene criptata la password inserita
    * @invariant  password
    * 
    * @param password è la password da criptare
    * @return  Una stringa criptata
    */

    
    private static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    /**
    * 
    * @brief Verifica corrispondenza tra password criptata e password in chiaro
    * 
    * @pre  la password!= null
    * @post restituisce il risultato del confronto tra le 2 stringhe
    * @invariant  password, hashed
    * 
    * 
    * @param password è la password non criptata passata alla funzione 
    * @param hashed è la password criptata passata alla funzione 
    * @return  true se la password è uguale a hashed, se sono diverse false
    */


    private static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
    
    
    
    
    /**
    * 
    * @brief Recupera i contatti associati a un utente specifico.
    * 
    * @pre  la password!= null,tableName deve esistere nel database connesso, l' email inserita deve esser valida e deve essere di un utente registrato
    * @post restituisce una TreeMap con chiave ID del Contact e come valore il Contact
    * @invariant  conn,tableName, email
    * 
    * 
    * @param conn Oggetto Connection per interagire con il database.
    * @param tableName Nome della tabella contenente i contatti.
    * @param email Email dell'utente di cui recuperare i contatti.
    * @return TreeMap contenente i contatti dell'utente.
    */

    
   public TreeMap<String, Contact> getContact(Connection conn, String tableName,String email){
        
        Statement statement;
        ResultSet rs= null;
        Map <String, Contact>table=null;
        try {
            table =  new TreeMap<>();
            String query= String.format("select * from %s where email='%s'", tableName,email);
            statement= conn.createStatement();
            rs= statement.executeQuery(query);
            while(rs.next()){
                String name = rs.getString("name");
                String surname= rs.getString("surname");
                String numeri = rs.getString("number");
                String tag = rs.getString("tag");
                String em_cont = rs.getString("email_contact");
                String ID = rs.getString("id");
                table.put(ID, createContact(name,surname,numeri,tag,em_cont,ID));
            }
            statement.close(); 
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return (TreeMap<String, Contact>) table;
    }
    
    /**
    *  
    * @brief Crea un contatto.
    * 
    * @pre  ID non nullo
    * @post crea un contatto che 
    * @invariant   name, surname, numeri,  tag,  em_cont, ID
    * 
    * @param name nome del contatto.
    * @param surname cognome del contatto.
    * @param numeri stringa contenete i numeri del contatto con dei separatori.
    * @param tag stringa contenete i tag del contatto con dei separatori.
    * @param em_cont stringa contenete l' emails del contatto con dei separatori.
    * @param ID contiene l' identificativo univoco del contatto
    * @return Contact contenente con tutti i parametri passati.
    * 
    */

   private Contact createContact(String name, String surname, String numeri, String tag, String em_cont,String ID) {
        Contact c= new Contact(name,surname, ID); 
        ArrayList<String> n = new ArrayList<>();
        ArrayList<String> e = new ArrayList<>();
        ArrayList<Tag> t = new ArrayList<>();
        
        
            Scanner i = new Scanner(tag);
            i.useDelimiter(";");
             while(i.hasNext()){
                 t.add( Tag.valueOf(i.next()));
            }
             System.out.print(t+" \n");
             c.setTag(t);
             
            i = new Scanner(numeri);
            i.useDelimiter(";");
            while(i.hasNext()){
                 n.add( i.next());
            }
             System.out.print(n+" \n");
             c.setNumber(n);
            
            i = new Scanner(em_cont);
            i.useDelimiter(";");
            while(i.hasNext()){
                 e.add( i.next());
            }
            System.out.print(e+" \n");
             c.setEmail(e);
        
        return c;
    }
    
    /**
    *  
    * @brief Inserisce il contatto associato ad un utente specifico.
    * 
    * 
    * @pre  conn!= null,tableName deve esistere nel database connesso, cont!= null, email_Utente deve essere valida e registrata nel DataBase 
    * @post inserisce il contatto con tutti i rispettivi attributi nella tabella che ha come chiave primaria composta l' ID e l' email_Utente
    * @invariant  conn,tableName,cont,email_Utente
    * 
    * 
    * 
    * @param cont contatto da aggiungere
    * @param conn Oggetto Connection per interagire con il database.
    * @param tableName Nome della tabella contenente i contatti.
    * @param email_Utente Email dell'utente che ha fatto il login.
    * @throws SQLException Se si verifica un errore durante l'interrogazione.
    */

    
     public void insertContact(Connection conn, String tableName, Contact cont, String email_Utente) throws SQLException{
    
           Statement statment;
           String nm=cont.getName();
           String srn=cont.getSurname();
           String ID= String.valueOf(cont.getID());
           
           ArrayList<String> e = cont.getEmailList();
           ArrayList<String> n = cont.getNumberList();
           ArrayList<Tag> t =cont.getTagList();
           
            /*formatto le email*/
           String email= formattaOut(e);
           
            /*formatto i numeri*/
           String number= formattaOut(n);
           
           /*formatto i tag*/
           ArrayList<String> St= new ArrayList<>();
           for (Tag i : t) { 
               St.add(i.name());
           }
           String tag= formattaOut(St);
           
           
           String query;
           query = String.format("insert into %s(email,name,surname,number,tag,email_contact,id) values('%s','%s','%s','%s','%s','%s','%s');",tableName,  email_Utente,nm,srn,number,tag,email,ID);
           statment= conn.createStatement();
           statment.executeUpdate(query);
           System.out.println("Contatto inserito");
           statment.close(); 
        
    }
    
    /**
    *  
    * @brief Concatena i valori di una lista in una stringa formattata.
    * 
    * @pre  s non può essere vuota o nulla  
    * @post crea una stringa con un separatore 
    * @invariant  s
    * 
    * 
    * Questo metodo prende in ingresso una lista di stringhe e restituisce 
    * una stringa in cui tutti gli elementi della lista sono concatenati, 
    * separati dal carattere `;`.
    * @param s ArrayList da formattare.
    * @return Una stringa contenente gli elementi della lista concatenati e separati da `;`. Se la lista è vuota, restituisce una stringa vuota.
    * 
    */

    private String formattaOut(ArrayList<String> s) {
        
        String formattata="";
        for(String i: s){
          
            formattata=formattata+i+";";
        }
        return formattata;
    }
    
    /**
    * 
    * @brief Modifica il contatto associato a un utente specifico nel Database.
    * 
    * @pre  conn!= null,tableName deve esistere nel database connesso, cont!= null, il contatto deve essere già presente e deve esser gia associato a email_Utente, email_Utente deve essere valida e registrata nel DataBase 
    * @post aggiorna la tabella tableName modificando i campi cambiati di cont
    * @invariant  conn,tableName,cont,email_Utente
    * 
    * Modifica un contatto se è gia presente ed associato a email_Utente, in caso contrario aggiungerà il contatto come nuovo
    * 
    * @param conn Oggetto Connection per interagire con il database.
    * @param tableName Nome della tabella contenente i contatti.
    * @param cont il contatto da modificare nel Database
    * @param email_Utente Email dell'utente che ha fatto il login.
    * @throws SQLException Se si verifica un errore durante l'interrogazione.
    * 
    */

    public void modifyContact(Connection conn, String tableName, Contact cont, String email_Utente) throws SQLException{
        
         Statement statment;
           String nm=cont.getName();
           String srn=cont.getSurname();
           String ID= cont.getID();
           
           ArrayList<String> e = cont.getEmailList();
           ArrayList<String> n = cont.getNumberList();
           ArrayList<Tag> t =cont.getTagList();
        
             /*formatto le email*/
           String email= formattaOut(e);
           
            /*formatto i numeri*/
           String number= formattaOut(n);
           
           /*formatto i tag*/
           ArrayList<String> St= new ArrayList<>();
           for (Tag i : t) { 
               St.add(i.name());
           }
           String tag= formattaOut(St);
           
           
      String query = String.format("UPDATE %s SET name='%s', surname='%s', number='%s', tag='%s', email_contact='%s' WHERE email='%s' AND id='%s'", tableName, nm, srn, number, tag, email, email_Utente, ID);
      
      statment= conn.createStatement();
      statment.executeUpdate(query);
      System.out.println("Contatto modificato");
      statment.close(); 
    }
    
   /**
    * 
    * @brief Rimuove un record dalla tabella specificata utilizzando l'ID.
    * Questo metodo esegue un'operazione di eliminazione (`DELETE`) su una tabella 
    * di un database, rimuovendo il record che corrisponde all'ID specificato.
    * 
    * @pre  conn!= null,tableName deve esistere nel database connesso, ID non deve essere vuoto o nullo e deve esser associato ad un contatto ,email deve essere valida e registrata nel DataBase 
    * @post viene eliminato il contatto associato all' ID e all' email 
    * @invariant  conn,tableName,ID,email
    * 
    * Rimuove un record dalla tabella specificata utilizzando l'ID, se l' ID non è associato a nessun contatto di email la tabella non verra modificata
    * 
    * @param conn Oggetto Connection per interagire con il database.
    * @param tableName Nome della tabella contenente i contatti.
    * @param ID il contatto da modificare nel Database
    * @param email l' email del utente registrato
    * @throws SQLException Se si verifica un errore durante l'interrogazione.
    * 
    */
     public void removeContactByID(Connection conn, String tableName, String ID, String email) throws SQLException{
    
       Statement statment;
       
       String query= String.format("delete from %s where ID='%s' AND email='%s'",tableName,ID,email);
       statment= conn.createStatement();
       statment.execute(query);
       System.out.print("\n Dato eliminato per ID");
       statment.close(); 
    }
    /**
    * 
    * @brief Chiude la connessione col database
    * @pre  conn!= null
    * @post la connessione col Database viene chiusa  
    * 
    * 
    * @param conn Oggetto Connection per interagire con il database.
    * 
    * @throws SQLException Se si verifica un errore durante l'interrogazione.
    */

    
    public void CloseConnection(Connection conn) throws SQLException{
        conn.close();
        System.out.print("Chiusura connessione");
    }
    
    /**
    * 

    * @brief Conta il numero di righe della tabella tableName associata al Database
    * @pre  conn!= null, tableName!=null e deve appartenere al Database
    * @post vengono contate il numero di righe della tabella  
    * @invariant conn, tableName
    * 
    * @param conn Oggetto Connection per interagire con il database.
    * @param tableName nome della tabella dal quale prendere le informazioni
    * @return viene restituito il numero di righe della tabella 
    */
    public int getNumberContact(Connection conn, String tableName){
        
        int numero_righe=0;
        try {
            Statement statement = conn.createStatement();
            String query = String.format("SELECT COUNT(*) AS rowcount FROM %s", tableName);//conta il numero di righe nella tabella nome_tabella e assegna il risultato alla colonna rowcount
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) { 
             numero_righe = rs.getInt("rowcount"); 
            System.out.println("Numero di righe: " + numero_righe); 
            }
            statement.close(); 
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }   
         
        return numero_righe;
    }
}
