package it.unisa.diem.team02.contactsbook.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author team02
 */
public class FilterTest {
    Filter flContactTest;
    ObservableList<Contact> listTest; 
    Contact contactTest1, contactTest2, contactTest3;
    
    @BeforeEach
    public void setUp() {
        listTest = FXCollections.observableArrayList();
        
        //inizializzazione oggetti Contact
        contactTest1 = new Contact("","Zouhri");
        contactTest2 = new Contact("Costantino","");
        contactTest3 = new Contact("Antonietta","Ferrara");
        
        contactTest1.addEmail("a.zouhri@gmail.com");
        contactTest2.addNumber("57891011");
        contactTest3.addEmail("yoga@mystress.it");
        contactTest3.addEmail("english@geometry4.it");
        
        contactTest2.addTag(Tag.Home);
        contactTest2.addTag(Tag.University);
        contactTest2.addTag(Tag.Job);
        contactTest3.addTag(Tag.Job);
        
        
        //aggiunta contatti alla lista
        listTest.add(contactTest1);
        listTest.add(contactTest2);
        listTest.add(contactTest3);
    }
    
    @AfterEach
    public void tearDown() {
        
    }
    
    
    /**
     * Test del construttore per verificare che l'oggetto venga istanziato correttamente 
     * e che siano inizializzati correttamente i parametri matchString, isSelectedHome, isSelectedUni, 
     * isSelectedJob.
     */
    
    @Test
    public void testConstructor() {
        flContactTest = new Filter (listTest);
        assertNotNull(flContactTest);
        assertEquals(3,flContactTest.getFlContacts().size());
        assertFalse(flContactTest.getIsSelectedHome());
        assertFalse(flContactTest.getIsSelectedUniversity());
        assertFalse(flContactTest.getIsSelectedJob());
    }
    
   
    
    /**
     * Test n1 del metodo updateFilter della classe Filter. Il filtro viene aggiornato passando solamente
     * una sottostringa
     */
    @Test
    public void testUpdateFilter1() {
        flContactTest = new Filter(listTest);
        String string = "Zou";
        boolean h = false;
        boolean u = false;
        boolean j = false;
        flContactTest.updateFilter(string, h, u, j);
        
        assertEquals(1,flContactTest.getFlContacts().size());
        
    }
    
    /**
     * Test n2 del metodo updateFilter della classe Filter. Il filtro viene aggiornato settando un solo tag
     */
    @Test
    public void testUpdateFilter2() {
        flContactTest = new Filter(listTest);
        String string = "";
        boolean h = false;
        boolean u = false;
        boolean j = true;
        flContactTest.updateFilter(string, h, u, j);
        
        assertEquals(2,flContactTest.getFlContacts().size());
        
    }
    
    /**
     * Test n3 del metodo UpdateFilter della classe Filter. Nessun filtro viene selezionato
     */
    @Test
    public void testUpdateFilter3() {
        flContactTest = new Filter(listTest);
        String string = "";
        boolean h = false;
        boolean u = false;
        boolean j = false;
        flContactTest.updateFilter(string, h, u, j);
        
        assertEquals(3,flContactTest.getFlContacts().size());
    }
    
    /**
     * Test n4 del metodo updateFilter della classe Filter. Il filtro viene impostando una sottostringa
     * e settando due tag.
     */
    @Test
    public void testUpdateFilter4() {
        flContactTest = new Filter(listTest);
        String string = "an";
        boolean h = true;
        boolean u = false;
        boolean j = true;
        flContactTest.updateFilter(string, h, u, j);
        
        assertEquals(2,flContactTest.getFlContacts().size());
        
    }
    
    /**
     * Test del metodo getMatchString 
     * 
     */
     @Test
     public void testGetMatchString(){
        flContactTest = new Filter(listTest);
        String string = "an";
        boolean h = true;
        boolean u = false;
        boolean j = true;
        flContactTest.updateFilter(string, h, u, j);
        
        assertEquals(string,flContactTest.getMatchString());
    
    }
     
     /**
     * Test del metodo getIsSelectedHome
     */
     
     @Test
     public void testGetIsSelectedHome() {
        flContactTest = new Filter(listTest);
        String string = "an";
        boolean h = true;
        boolean u = false;
        boolean j = true;
        flContactTest.updateFilter(string, h, u, j);
        
        assertTrue(flContactTest.getIsSelectedHome());
     }
     
     /**
     * Test del metodo getIsSelectedUniversity
     */
     
     @Test
     public void testGetIsSelectedUniversity() {
        flContactTest = new Filter(listTest);
        String string = "an";
        boolean h = true;
        boolean u = false;
        boolean j = true;
        flContactTest.updateFilter(string, h, u, j);
        
        assertFalse(flContactTest.getIsSelectedUniversity());
     }
     
     /**
     * Test del metodo getIsSelectedJob
     */
     
     @Test
     public void testGetIsSelectedJob() {
        flContactTest = new Filter(listTest);
        String string = "an";
        boolean h = true;
        boolean u = false;
        boolean j = true;
        flContactTest.updateFilter(string, h, u, j);
        
        assertTrue(flContactTest.getIsSelectedJob());
     }
     
     
    
}
