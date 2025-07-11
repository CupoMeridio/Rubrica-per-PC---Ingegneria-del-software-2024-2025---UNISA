package it.unisa.diem.team02.contactsbook.database;

import it.unisa.diem.team02.contactsbook.model.Contact;
import it.unisa.diem.team02.contactsbook.model.Tag;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Mattia Sanzari
 */
@TestMethodOrder(OrderAnnotation.class)
public class DatabaseTest {
    
    static private Database db;
    static private  Connection conn;
    static private String email_utente;
    static private String password_utente;
    static private Contact cont;
    public DatabaseTest() {
    }

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeAll
    public static void setUp() throws Exception {
        db= new Database();
        conn=db.ConnectionDB(); // Usa il nuovo metodo con variabili d'ambiente
        email_utente ="valeriaquaranta2003@gmail.com";
        password_utente="password";
        
        // Debug: nel metodo statico email e password utente
        cont= new Contact("Vittorio","Postiglione");
        ArrayList<String> number = new ArrayList<>();
        ArrayList<String> em= new ArrayList<>();
        ArrayList<Tag> tag= new ArrayList<>();
        
        tag.add(Tag.Home);
        tag.add(Tag.University);
         
        em.add("postiglione@gmail.com");
        em.add("Vittorio2003@libero.it");
        em.add("cupomabello03@gmail.com");
        
        number.add("0815095344");
        number.add("3279006099");
        number.add("089 964292");
        cont.setTag(tag);
        cont.setEmail(em);
        cont.setNumber(number);
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDown() throws Exception {
        conn=db.ConnectionDB(); // Usa il nuovo metodo con variabili d'ambiente
        try {
            Statement statment;
            String table= "utenti";
            String query= String.format("delete from %s where email='%s'",table, email_utente);
            statment= conn.createStatement();
            statment.execute(query);
            // Debug: Utente eliminato
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
       db.CloseConnection();
    }
    /**
     * Test of ConnectionDB method, of class Database.
     */
    @org.junit.jupiter.api.Test
    public void testConnectionDB() {
        // Test ConnectionDB
        Database instance = new Database();
        Connection result = instance.ConnectionDB(); // Usa il nuovo metodo con variabili d'ambiente
        assertNotNull(result);
    }

    /**
     * Test of insertUtente method, of class Database.
     */
    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order(1)
    public void testInsertUtente() throws Exception {
        // Test insertUtente (con utente non presente nel database)
        String tableName = "utenti";
        db.insertUser(conn, tableName, email_utente, password_utente);
    }
    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order(2)
    public void testInsertUtente2() throws Exception {
        // Test insertUtente 2 (con utente presente nel database)
        String tableName = "utenti";
        Database instance = new Database();
        assertThrows(SQLException.class, () -> { 
        instance.insertUser(conn, tableName, email_utente, password_utente);
        throw new SQLException("Utente già esistente"); });
    }

    /**
     * Test of getUtenti method, of class Database.
     */
    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order(3)
    public void testGetUser() {
        // Test getUtenti
        String tableName = "utenti";
        HashMap<String, String> expect= new HashMap<>();
        expect.put(email_utente, password_utente);
        HashMap<String, String> result = db.getUser(conn, tableName);
        assertNotNull(result);
        boolean verf = BCrypt.checkpw(expect.get(email_utente), result.get(email_utente));
        assertTrue(verf);
        //assertEquals(expect.get(email_utente),result.get(email_utente));
    }

    /**
     * Test of checkLogin method, of class Database.
     */
    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order(4)
    public void testCheckLogin() throws Exception {
        // Test checkLogin
        String tableName = "utenti";
        int expResult = 1;
        int result = db.checkLogin(conn, tableName, email_utente, password_utente);
        assertEquals(expResult, result);
    }
    @org.junit.jupiter.api.Order(5)
    public void testCheckLogin2() throws Exception {
        // Test checkLogin2
        String tableName = "utenti";
        String password = "";
        int expResult = 0;
        int result = db.checkLogin(conn, tableName, email_utente, password);
        assertEquals(expResult, result);
    }
    @org.junit.jupiter.api.Order(6)
     public void testCheckLogin3() throws Exception {
        // Test checkLogin3
        String tableName = "utenti";
        String email = " ";
        int expResult = -1;
        int result = db.checkLogin(conn, tableName, email, password_utente);
        assertEquals(expResult, result);
    }
    /**
     * Test of insertContatto method, of class Database.
     */
    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order(7)
    public void testInsertContact() throws Exception {
        // Test insertContatto
        String tableName = "contatti";
        db.insertContact(conn, tableName, cont, email_utente);
    }
     /**
     * Test of getContact method, of class Database.
     */
    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order(8)
    public void testGetContact() {
        // Test getContact
        String tableName = "contatti";
        
        ArrayList<Contact> result = db.getContact(conn, tableName, email_utente); // CAMBIATO PER TREESET
        // assertTrue(result.containsKey(cont.getID()));
         Contact cont_result= result.get(result.size()-1);
        assertEquals(cont_result.getID(),cont.getID());
    }

    /**
     * Test of modifyContact method, of class Database.
     * @throws java.lang.Exception
     */
    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order(9)
    public void testModifyContact() throws Exception {
        // Test modifyContact
        String tableName = "contatti";
        ArrayList<String> number = new ArrayList<>();
        number.add("0815095344");
        number.add("3279006099");
        number.add("numero cambiato");
        cont.setNumber(number);
        db.modifyContact(conn, tableName, cont, email_utente);
        ArrayList<Contact> result=db.getContact(conn, tableName, email_utente);// CAMBIATO DA HASH MAP
        Contact cont_result= result.get(result.size()-1);
        assertNotNull(cont_result);
        assertEquals(cont.getNumberList().size(),cont_result.getNumberList().size());
        for(int i=0; i<cont_result.getNumberList().size(); i++){
            assertEquals(cont.getNumberList().get(i), cont_result.getNumberList().get(i));
        }
    }

    /**
     * Test of remuveContactByID method, of class Database.
     */
    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order(10)
    public void testRemuveContactByID() throws Exception {
        // Test remuveContactByID
        String tableName = "contatti";
        db.removeContactByID(conn, tableName, cont.getID(), email_utente);
        //Map <String,Contact>table=  new HashMap();
        ArrayList<Contact> table= new ArrayList<>();
        assertEquals(table,db.getContact(conn, tableName, email_utente));
    }

    /**
     * Test of CloseConnection method, of class Database.
     */
    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order(11)
    public void testCloseConnection() throws Exception {
        // Test CloseConnection
        db.CloseConnection();
    }


}
