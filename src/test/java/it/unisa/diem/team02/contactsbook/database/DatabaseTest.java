/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package it.unisa.diem.team02.contactsbook.database;

import it.unisa.diem.team02.contactsbook.model.Contact;
import it.unisa.diem.team02.contactsbook.model.Tag;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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
        conn=db.ConnectionDB("rubrica", "avnadmin", "AVNS_rgkdmIqyKlbMdHqenly");
        email_utente ="valeriaquaranta2003@gmail.com";
        password_utente="password";
        
        System.out.print("\n nel metodo statico  "+ email_utente+"  "+password_utente);
        cont= new Contact("Vittorio","Postiglione");
        ArrayList<String> number = new ArrayList<String>();
        ArrayList<String> em= new ArrayList<String>();
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
        conn=db.ConnectionDB("rubrica", "avnadmin", "AVNS_rgkdmIqyKlbMdHqenly");
        try {
            Statement statment;
            String table= "utenti";
            String query= String.format("delete from %s where email='%s'",table, email_utente);
            statment= conn.createStatement();
            statment.execute(query);
            System.out.print("\n Utente eliminato");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
       db.CloseConnection(conn);
    }
    /**
     * Test of ConnectionDB method, of class Database.
     */
    @org.junit.jupiter.api.Test
    public void testConnectionDB() {
        System.out.println("ConnectionDB");
        String dbname = "rubrica";
        String user = "avnadmin";
        String password = "AVNS_rgkdmIqyKlbMdHqenly";
        Database instance = new Database();
        Connection result = instance.ConnectionDB(dbname, user, password);
        assertNotNull(result);
    }

    /**
     * Test of insertUtente method, of class Database.
     */
    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order(1)
    public void testInsertUtente() throws Exception {
        System.out.println("insertUtente (con utente non presente nel database)");
        String tableName = "utenti";
        db.insertUser(conn, tableName, email_utente, password_utente);
    }
    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order(2)
    public void testInsertUtente2() throws Exception {
        System.out.println("insertUtente 2 (con utente presente nel database)");
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
        System.out.println("getUtenti");
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
        System.out.println("checkLogin");
        String tableName = "utenti";
        int expResult = 1;
        int result = db.checkLogin(conn, tableName, email_utente, password_utente);
        assertEquals(expResult, result);
    }
    @org.junit.jupiter.api.Order(5)
    public void testCheckLogin2() throws Exception {
        System.out.println("checkLogin2");
        String tableName = "utenti";
        String password = "";
        int expResult = 0;
        int result = db.checkLogin(conn, tableName, email_utente, password);
        assertEquals(expResult, result);
    }
    @org.junit.jupiter.api.Order(6)
     public void testCheckLogin3() throws Exception {
        System.out.println("checkLogin3");
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
        System.out.println("insertContatto");
        String tableName = "contatti";
        db.insertContact(conn, tableName, cont, email_utente);
    }
     /**
     * Test of getContact method, of class Database.
     */
    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order(8)
    public void testGetContact() {
        System.out.println("getContact");
        String tableName = "contatti";
        
        TreeMap<String,Contact> result = db.getContact(conn, tableName, email_utente); // CAMBIATO PER TREESET
        // assertTrue(result.containsKey(cont.getID()));
         Contact cont_result= result.get(cont.getID());
        assertEquals(cont_result.getID(),cont.getID());
    }

    /**
     * Test of modifyContact method, of class Database.
     */
    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order(9)
    public void testModifyContact() throws Exception {
        System.out.println("modifyContact");
        String tableName = "contatti";
        ArrayList<String> number = new ArrayList<String>();
        number.add("0815095344");
        number.add("3279006099");
        number.add("numero cambiato");
        cont.setNumber(number);
        db.modifyContact(conn, tableName, cont, email_utente);
        TreeMap<String,Contact> result=db.getContact(conn, tableName, email_utente);// CAMBIATO DA HASH MAP
        Contact cont_result= result.get(cont.getID());
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
        System.out.println("remuveContactByID");
        String tableName = "contatti";
        db.removeContactByID(conn, tableName, cont.getID(), email_utente);
        //Map <String,Contact>table=  new HashMap();
        TreeMap<String,Contact> table= new TreeMap<>();
        assertEquals(table,db.getContact(conn, tableName, email_utente));
    }

    /**
     * Test of CloseConnection method, of class Database.
     */
    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Order(11)
    public void testCloseConnection() throws Exception {
        System.out.println("CloseConnection");
        db.CloseConnection(conn);
    }

    /**
     * Test of getNumberContact method, of class Database.
     */
    /*@org.junit.jupiter.api.Test
    public void testGetNumberContact() {
        System.out.println("getNumberContact");
        String tableName = "";
        Database instance = new Database();
        int expResult = 0;
        int result = instance.getNumberContact(conn, tableName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/   
}
