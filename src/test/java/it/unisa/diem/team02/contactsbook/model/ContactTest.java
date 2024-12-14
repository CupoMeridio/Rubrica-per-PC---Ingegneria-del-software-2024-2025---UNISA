package it.unisa.diem.team02.contactsbook.model;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author team02
 */
public class ContactTest {
    private Contact Test1, Test2, Test3;
    ArrayList<String> number1, number2;
    ArrayList<String> email1, email2;
    ArrayList<Tag> tag1, tag2;
    
        
    @BeforeEach
    public void setUp() {
        
        //inizializzazione dei contatti
        
        
        //inizializzazione della lista di numeri number1 con i numeri 3393434025
        number1 = new ArrayList<>(3);
        number1.add("3393434025");
        
        //inizializzazione della lista di numeri number2 con i numeri 333173282, 3349891345
        number2 = new ArrayList<>(3);
        number2.add("333173282");
        number2.add("3349891345");
        
        //instanziazione della lista di email email1 con nessuna email.
        email1 = new ArrayList<>(3);
        
        //inizializzazione della lista di email email2 con l'email 337897345
        email2 = new ArrayList<>(3);
        email2.add("337897345");
        
        //inizializzazione della lista dei tag tag1 con i tag Home e Job
        tag1 = new ArrayList<>(3);
        tag1.add(Tag.Home);
        tag1.add(Tag.Job);
        
        //inizializzazione della lista di tag tag2 con il tag University
        tag2 = new ArrayList<>(3);
        tag2.add(Tag.University);
        
    }
    
    @AfterEach
    public void tearDown() {

    }
    
    /**
     * Test del construttore con due parametri.
     */
    @Test
    public void testConstructor1() {
        Test1 = new Contact("Anuar", "Zouhri");
        Test2 = new Contact("Valeria","");
        assertNotNull(Test1);
        assertNotNull(Test2);
    }
    
    
    /**
     * Test del construttore con tre parametri.
     */
    @Test
    public void testConstructor2() {
        Test3 = new Contact("","Postiglione","10");
        assertNotNull(Test3);
    }
    
    
    /**
     * Test del metodo getName.
     */
    
    @Test
    public void testGetName(){
        Test1 = new Contact("Anuar", "Zouhri");
        assertEquals("Anuar",Test1.getName());   
    }
    
    /**
     * Test del metodo setName.
     */
    
    @Test
    public void testSetName(){
        Test1 = new Contact("Anuar", "Zouhri");
        Test1.setName("Vittorio");
        assertEquals("Vittorio",Test1.getName());   
    }
    
    
    /**
     * Test del metodo getSurname.
     */
    
    @Test
    public void testGetSurname(){
        Test1 = new Contact("Anuar", "Zouhri");
        assertEquals("Zouhri",Test1.getSurname());   
    }
    
    /**
     * Test del metodo setSurname.
     */
    
    @Test
    public void testSetSurname(){
        Test1 = new Contact("Anuar", "Zouhri");
        Test1.setSurname("");
        assertEquals("",Test1.getSurname());   
    }
    
    /**
     * Test  del metodo addNumber della classe Contact. Il contatto ha un numero.
     */
    @Test
    public void testAddNumber() {
        Test1 = new Contact("Anuar", "Zouhri");
        Test1.addNumber("3393424025");
        String expResult = "3393424025";
        String result = Test1.getNumber();
        assertEquals(expResult, result);
    }
    
    /**
     * Test del metodo getNumber della classe Contact. Il contatto ha due numeri.
     */
    @Test
    public void testGetNumber() {
        Test2 = new Contact("Valeria","");
        Test2.addNumber("3393424025");
        Test2.addNumber("2238123");
        String expResult = "3393424025\n2238123";
        String result = Test2.getNumber();
        assertEquals(expResult, result);
    }
    

    /**
     * Test del metodo addEmail della classe Contact. Il contatto ha tre email.
     */
    @Test
    public void testAddEmail() {
        Test3 = new Contact("","Postiglione","10");
        Test3.addEmail("vittorio@gmail.it");
        Test3.addEmail("vittorio@gmail.it");
        Test3.addEmail("vitt@gmail.com");
        String expResult = "vittorio@gmail.it\nvittorio@gmail.it\nvitt@gmail.com";
        String result = Test3.getEmail();
        assertEquals(expResult, result);
        
    }
    
    /**
     * Test del metodo getEmail della classe Contact. Il contatto non ha email.
     */
    @Test
    public void testGetEmail() {
        Test3 = new Contact("","Postiglione","10");
        String expResult = "";
        String result = Test3.getEmail();
        assertEquals(expResult, result);
          
    }

    /**
     * Test del metodo addTag della classe Contact. Il contatto ha due tag.
     */
    @Test
    public void testAddTag() {
        Test1 = new Contact("Anuar", "Zouhri");
        Test1.addTag(Tag.Job);
        Test1.addTag(Tag.University);
        String expResult = "Job\nUniversity";
        String result = Test1.getTag();
        assertEquals(expResult, result);
        
    }
    
    /**
     * Test del metodo getTag della classe Contact. Il contatto ha tre tag.
     */
    @Test
    public void testGetTag() {
        Test1 = new Contact("Anuar", "Zouhri");
        Test1.addTag(Tag.Home);
        Test1.addTag(Tag.Job);
        Test1.addTag(Tag.University);
        String expResult = "Home\nJob\nUniversity";
        String result = Test1.getTag();
        assertEquals(expResult, result);        
    }

    /**
     * Test del metodi setNumber della classe Contact. Viene inserito un numero nella lista.
     */
    @Test
    public void testSetNumberList() {
        Test1 = new Contact("Anuar", "Zouhri");
        Test1.setNumber(number1);
        assertEquals(number1, Test1.getNumberList()); 
        
    }
    
    /**
     * Test del metodo getNumberList della classe Contact. La lista ha un solo numero.
     */
    @Test
    public void testGetNumberList() {
        Test2 = new Contact("Valeria","");
        Test2.setNumber(number2);
        assertEquals(number2, Test2.getNumberList()); 
        
    }

    /**
     * Test del metodo setEmail della classe Contact. Viene inserita una sola email nella lista
     */
    @Test
    public void testSetEmailList() {
        Test2 = new Contact("Valeria","");
        Test2.setEmail(email1);
        assertEquals(email1, Test2.getEmailList()); 
    }
    
    /**
     * Test del metodo getEmailList della classe Contact. La lista ha una sola email.
     */
    @Test
    public void testGetEmailList() {
        Test3 = new Contact("","Postiglione","10");
        Test3.setEmail(email2);
        assertEquals(email2, Test3.getEmailList()); 
    }

    /**
     * Test del metodo setTagList  della classe Contact. Viene inserito un solo tag nella lista.
     */
    @Test
    public void testSetTagList() {
        Test1 = new Contact("Anuar", "Zouhri");
        Test1.setTag(tag1);
        assertEquals(tag1, Test1.getTagList()); 
    }
    
    /**
     * Test del metodo getTagList della classe Contact. La lista ha un solo tag.
     */
    @Test
    public void testGetTagList() {
        Test2 = new Contact("Valeria","");
        Test2.setTag(tag2);
        assertEquals(tag2, Test2.getTagList()); 
    }
    
    
    /**
     * 
     * Test n1 del metodo equals. Il metodo è utilizzato su due oggetti con diverso nome e cognome. 
     * Viene utilizzato assertFalse.
     *
     */
    @Test
    public void testEquals1() {
        Test1 = new Contact("Anuar", "Zouhri");
        Test2 = new Contact("Valeria","");
        assertFalse(Test1.equals(Test2));
    
    }
    
    /**
     * 
     * Test n2 del metodo equal. Il metodo è utilizzato su due contatti che hanno lo stesso nome
     * e cognome. Viene utilizzato AssertTrue
     */
    @Test
    public void testEquals2() {
        Test1 = new Contact("Anuar", "Zouhri");
        Contact c = new Contact(Test1.getName(),Test1.getSurname());
        assertTrue(Test1.equals(c));
    
    }
    
    /**
     * 
     * Test n3 del metodo equal. Il metodo è utilizzato su due contatti che hanno lo stesso nome
     * ma cognome diverso. Viene utilizzato AssertFalse
     */
    @Test
    public void testEquals3() {
        Test1 = new Contact("Anuar", "Zouhri");
        Test2 = new Contact("Valeria","");
        Contact c = new Contact(Test1.getName(),Test2.getSurname());
        assertFalse(Test1.equals(c));
    
    }
    
    /**
     * 
     * Test n4 del metodo equal. Il metodo è utilizzato su due contatti che hanno nome
     * diverso ma stesso cognome. Viene utilizzato AssertFalse
     */
    @Test
    public void testEquals4() {
        Test1 = new Contact("Anuar", "Zouhri");
        Test2 = new Contact("Valeria","");
        Contact c = new Contact(Test1.getName(),Test2.getSurname());
        assertFalse(Test2.equals(c));
    
    }
    


   
}
