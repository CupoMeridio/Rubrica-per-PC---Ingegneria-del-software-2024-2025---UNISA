package it.unisa.diem.team02.contactsbook.database;

import it.unisa.diem.team02.contactsbook.model.Contact;
import it.unisa.diem.team02.contactsbook.model.Tag;
import it.unisa.diem.team02.contactsbook.model.User;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;
import io.github.cdimascio.dotenv.Dotenv;
import it.unisa.diem.team02.contactsbook.ui.utils.AlertUtils;
import javafx.application.Platform;
/**
 * @brief
 * Classe che implementa tutti i metodi per comunicare con il database
 * @author team02
 */
public class Database  {
    static public Connection connection; ///Variabile statica che memorizza lo stato della connessione con il database
    static public User user; ///Variabile statica che memorizza l'utente attualemnet collegato
    private static Dotenv dotenv; ///Variabile statica per gestire le variabili d'ambiente
    
    static {
        // Inizializza dotenv per caricare le variabili d'ambiente dal file .env
        try {
            dotenv = Dotenv.configure().ignoreIfMissing().load();
        } catch (Exception e) {
            Logger.getLogger(Database.class.getName()).log(Level.WARNING, "File .env non trovato, utilizzo variabili d'ambiente di sistema", e);
            dotenv = null;
        }
    }
    
    /**
     * @brief Stabilisce una connessione al database PostgreSQL utilizzando le variabili d'ambiente.
     * 
     * @pre Il file .env deve contenere le variabili DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PASSWORD, DB_SSL
     * @post la connessione viene stabilita utilizzando le credenziali dal file .env
     * 
     * @return Oggetto Connection se la connessione è riuscita, altrimenti null.
     */
    public Connection ConnectionDB() {
        try {
            Class.forName("org.postgresql.Driver");
            
            // Recupera le variabili d'ambiente dal file .env
            String host = getEnvVar("DB_HOST");
            String port = getEnvVar("DB_PORT");
            String dbname = getEnvVar("DB_NAME");
            String user = getEnvVar("DB_USER");
            String password = getEnvVar("DB_PASSWORD");
            String ssl = getEnvVar("DB_SSL");
            
            if (host == null || port == null || dbname == null || user == null || password == null) {
                throw new SQLException("Variabili d'ambiente del database mancanti nel file .env");
            }
            
            String url = String.format("jdbc:postgresql://%s:%s/%s?ssl=%s&user=%s&password=%s", 
                                     host, port, dbname, ssl, user, password);
            connection = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Errore di connessione al database", ex);
        }
        return connection;
    }
    
    /**
     * @brief Recupera una variabile d'ambiente dal file .env o dalle variabili di sistema.
     * 
     * @param key Nome della variabile d'ambiente
     * @return Valore della variabile d'ambiente o null se non trovata
     */
    private String getEnvVar(String key) {
        if (dotenv != null) {
            return dotenv.get(key);
        }
        return System.getenv(key);
    }


    
    
    /**
    * 
    * @brief Inserisce un nuovo utente nella tabella specificata.
    * 
    * 
    * @pre conn!= null,tableName deve esistere nel database connesso, l' email inserita deve esser valida e diversa da altre email nel database, la password non può essere vuota o nulla
    * @post viene salvato nella tableName l' email associata allapassword criptata
    * 
    * 
    * @param conn Oggetto Connection per interagire con il database.
    * @param tableName Nome della tabella in cui inserire l'utente.
    * @param email Email dell'utente.
    * @param password Password dell'utente (verrà criptata  prima dell'inserimento).
    * 
    */

    public void insertUser(Connection conn, String tableName, String email, String password) {
        String query = String.format("INSERT INTO %s (email, password) VALUES (?, ?);", tableName);
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            pstmt.setString(2, hashPassword(password));
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Errore nell'inserimento dell'utente", ex);
            if (ex.getMessage().contains("duplicate key") || ex.getMessage().contains("already exists")) {
                Platform.runLater(() -> AlertUtils.showError("Errore Registrazione", "Un account con questa email esiste già."));
            } else {
                Platform.runLater(() -> AlertUtils.showDatabaseError());
            }
        }
    }
    
    /**
    * @brief Recupera tutti gli utenti dal database.
    * 
    * @pre conn!= null, tableName deve esistere nel database connesso
    * @post viene restituita una Hashmap con chiave email e valore la password cryptata
    * 
    * 
    * @param conn Oggetto Connection per interagire con il database.
    * @param tableName Nome della tabella da cui recuperare gli utenti.
    */

  
    public HashMap<String, String> getUser(Connection conn, String tableName) {
        HashMap<String, String> users = new HashMap<>();
        String query = String.format("SELECT * FROM %s", tableName);
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                users.put(rs.getString("email"), rs.getString("password"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Errore nel recupero degli utenti", ex);
        }
        return users;
    }
    
    
    
    /**
    * @brief Verifica le credenziali di login di un utente.
    * 
    * 
    * @pre conn!= null, tableName deve esistere nel database connesso, l' email inserita deve esser valida, la password non può essere vuota o nulla
    * @post viene restituito un intero uguale a 1 se le credenziali sono corrette, 0 se la password è errata, -1 se l'email non esiste
    * 
    * 
    * @param conn Oggetto Connection per interagire con il database.
    * @param tableName Nome della tabella contenente gli utenti.
    * @param email Email dell'utente.
    * @param password Password inserita dall'utente.
    * @return 1 se le credenziali sono corrette, 0 se la password è errata, -1 se l'email non esiste
    */

    
    public int checkLogin(Connection conn, String tableName, String email, String password) {
        String query = String.format("SELECT password FROM %s WHERE email = ?", tableName);
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("password");
                    return checkPassword(password, hashedPassword) ? 1 : 0;
                }
                return -1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Errore nel controllo del login", ex);
            return -1;
        }
    }
    
    /**
    * 
    * @brief Cripta la password passata dall' utente 
    * 
    * @pre  la password non può essere vuota o nulla
    * @post viene criptata la password inserita
    * 
    * @param password è la password da criptare
    * @return  Una stringa criptata
    */

    private static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    /**
    * @brief Verifica corrispondenza tra password criptata e password in chiaro
    * 
    * @pre  la password!= null
    * @post restituisce il risultato del confronto tra le 2 stringhe
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
    * @brief Recupera i contatti associati a un utente specifico.
    * 
    * @pre  la password!= null,tableName deve esistere nel database connesso, l' email inserita deve esser valida e deve essere di un utente registrato
    * @post restituisce una TreeMap con chiave ID del Contact e come valore il Contact
    * 
    * 
    * @param conn Oggetto Connection per interagire con il database.
    * @param tableName Nome della tabella contenente i contatti.
    * @param email Email dell'utente di cui recuperare i contatti.
    * @return TreeMap contenente i contatti dell'utente.
    */

    
    public ArrayList<Contact> getContact(Connection conn, String tableName, String email) {
        ArrayList<Contact> contacts = new ArrayList<>();
        String query = String.format("SELECT * FROM %s WHERE email = ?", tableName);
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    contacts.add(createContact(rs.getString("name"), rs.getString("surname"), rs.getString("number"),
                            rs.getString("tag"), rs.getString("email_contact"), rs.getString("id")));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Errore nel recupero dei contatti", ex);
        }
        return contacts;
    }
    
    /**
    * @brief Crea un contatto.
    * 
    * @pre  ID non nullo
    * @post crea un contatto che 
    * 
    * @param name nome del contatto.
    * @param surname cognome del contatto.
    * @param numeri stringa contenete i numeri del contatto con dei separatori.
    * @param tag stringa contenete i tag del contatto con dei separatori.
    * @param em_cont stringa contenete l' emails del contatto con dei separatori.
    * @param ID contiene l' identificativo univoco del contatto
    * @return Contact contenente con tutti i parametri passati.
    */

   private Contact createContact(String name, String surname, String numbers, String tags, String emails, String ID) {
        Contact contact = new Contact(name, surname, ID);
        contact.setTag(parseDelimited(tags, Tag::valueOf));
        contact.setNumber(parseDelimited(numbers, s -> s));
        contact.setEmail(parseDelimited(emails, s -> s));
        return contact;
    }
   
   
   private <T> ArrayList<T> parseDelimited(String input, java.util.function.Function<String, T> mapper) {
        ArrayList<T> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(input)) {
            scanner.useDelimiter(";");
            while (scanner.hasNext()) {
                list.add(mapper.apply(scanner.next()));
            }
        }
        return list;
    }
    
    /**
    *  
    * @brief Inserisce il contatto associato ad un utente specifico.
    * 
    * 
    * @pre  conn!= null,tableName deve esistere nel database connesso, cont!= null, email_Utente deve essere valida e registrata nel DataBase 
    * @post inserisce il contatto con tutti i rispettivi attributi nella tabella che ha come chiave primaria composta l' ID e l' email_Utente
    * 
    * 
    * 
    * @param contact contatto da aggiungere
    * @param conn Oggetto Connection per interagire con il database.
    * @param tableName Nome della tabella contenente i contatti.
    * @param userEmail Email dell'utente che ha fatto il login.
    */

    
    public void insertContact(Connection conn, String tableName, Contact contact, String userEmail) {
        String query = String.format("INSERT INTO %s (email, name, surname, number, tag, email_contact, id) VALUES (?, ?, ?, ?, ?, ?, ?);", tableName);
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userEmail);
            pstmt.setString(2, contact.getName());
            pstmt.setString(3, contact.getSurname());
            pstmt.setString(4, formatList(contact.getNumberList()));
            pstmt.setString(5, formatList(contact.getTagList(), Tag::name));
            pstmt.setString(6, formatList(contact.getEmailList()));
            pstmt.setString(7, contact.getID());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Errore nell'inserimento del contatto", ex);
        }
    }
    
    /**
    * @brief Concatena i valori di una lista in una stringa formattata.
    * 
    * @pre  s non può essere vuota o nulla  
    * @post crea una stringa con un separatore 
    * @invariant  i valori di s preesistenti non vengono modificati 
    * 
    * 
    * Questo metodo prende in ingresso una lista di stringhe e restituisce 
    * una stringa in cui tutti gli elementi della lista sono concatenati, 
    * separati dal carattere `;`.
    * @param s ArrayList da formattare.
    * @return Una stringa contenente gli elementi della lista concatenati e separati da `;`. Se la lista è vuota, restituisce una stringa vuota.
    */

    private String formattaOut(ArrayList<String> s) {
        
        String formattata="";
        for(String i: s){
          
            formattata=formattata+i+";";
        }
        return formattata;
    }
    
    /**
    * @brief Modifica il contatto associato a un utente specifico nel Database.
    * 
    * @pre  conn!= null,tableName deve esistere nel database connesso, cont!= null, il contatto deve essere già presente e deve esser gia associato a email_Utente, email_Utente deve essere valida e registrata nel DataBase 
    * @post aggiorna la tabella tableName modificando i campi cambiati di cont
    * 
    * Modifica un contatto se è gia presente ed associato a email_Utente, in caso contrario aggiungerà il contatto come nuovo
    * 
    * @param conn Oggetto Connection per interagire con il database.
    * @param tableName Nome della tabella contenente i contatti.
    * @param contact il contatto da modificare nel Database
    * @param userEmail Email dell'utente che ha fatto il login.
    */

    public void modifyContact(Connection conn, String tableName, Contact contact, String userEmail) {
        String query = String.format("UPDATE %s SET name = ?, surname = ?, number = ?, tag = ?, email_contact = ? WHERE email = ? AND id = ?", tableName);
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, contact.getName());
            pstmt.setString(2, contact.getSurname());
            pstmt.setString(3, formatList(contact.getNumberList()));
            pstmt.setString(4, formatList(contact.getTagList(), Tag::name));
            pstmt.setString(5, formatList(contact.getEmailList()));
            pstmt.setString(6, userEmail);
            pstmt.setString(7, contact.getID());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Errore nella modifica del contatto", ex);
        }
    }
    
   /**
    * @brief Rimuove un record dalla tabella specificata utilizzando l'ID.
    * Questo metodo esegue un'operazione di eliminazione (`DELETE`) su una tabella 
    * di un database, rimuovendo il record che corrisponde all'ID specificato.
    * 
    * @pre  conn!= null,tableName deve esistere nel database connesso, ID non deve essere vuoto o nullo e deve esser associato ad un contatto ,email deve essere valida e registrata nel DataBase 
    * @post viene eliminato il contatto associato all' ID e all' email 
    * 
    * Rimuove un record dalla tabella specificata utilizzando l'ID, se l' ID non è associato a nessun contatto di email la tabella non verra modificata
    * 
    * @param conn Oggetto Connection per interagire con il database.
    * @param tableName Nome della tabella contenente i contatti.
    * @param ID il contatto da modificare nel Database
    * @param email l' email del utente registrato
    */
    public void removeContactByID(Connection conn, String tableName, String ID, String email) {
        String query = String.format("DELETE FROM %s WHERE id = ? AND email = ?", tableName);
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, ID);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Errore nella rimozione del contatto", ex);
        }
    }
    
    /**
    * @brief Chiude la connessione col database
    * @pre  conn!= null
    * @post la connessione col Database viene chiusa
    */

    public void CloseConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Errore nella chiusura della connessione", ex);
            }
        }
    }
    
    /**
    * 

    * @brief Conta il numero di righe della tabella tableName associata al Database
    * @pre  conn!= null, tableName!=null e deve appartenere al Database
    * @post vengono contate il numero di righe della tabella  
    * 
    * @param conn Oggetto Connection per interagire con il database.
    * @param tableName nome della tabella dal quale prendere le informazioni
    * @return viene restituito il numero di righe della tabella 
    */
    public int getNumberContact(Connection conn, String tableName) {
        String query = String.format("SELECT COUNT(*) AS rowcount FROM %s", tableName);
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("rowcount");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Errore nel conteggio dei contatti", ex);
        }
        return 0;
    }
    
    private String formatList(ArrayList<String> list) {
        return String.join(";", list);
    }

    private <T> String formatList(ArrayList<T> list, java.util.function.Function<T, String> mapper) {
        ArrayList<String> mapped = new ArrayList<>();
        for (T item : list) {
            mapped.add(mapper.apply(item));
        }
        return String.join(";", mapped);
    }
}
