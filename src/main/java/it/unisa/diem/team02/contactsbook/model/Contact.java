package it.unisa.diem.team02.contactsbook.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * @brief Classe che modella un contatto.
 * 
 * Definisce attributi e metodi per effettuare operazioni sugli oggetti Contact.
 * Gli attributi number, email e tag sono implementati tramite un ArrayList.
 * 
 * @author team02
 */
public class Contact implements Comparable<Contact>{
    private String name;
    private String surname;
    private ArrayList<String> number;
    private ArrayList<String> email;
    private ArrayList<Tag> tag;
    private final String ID;
    
    
    /**
     * @brief Crea un nuovo oggetto Contact con il nome e cognome forniti.
     * 
     * Questo costruttore inizializza il contatto con un nome e un cognome, e inizilizza liste vuote per i numeri di telefono, 
     * gli indirizzi email e i tag associati. Inoltre, genera un ID univoco per il contatto.
     * 
     * @param name Il nome del contatto.
     * @param surname Il cognome del contatto.
     * 
     * @pre
     * - I parametri `name` e `surname` devono essere non nulli.
     * 
     * @post
     * - La variabili di istanza 'name' e 'surnname' vengono inizializzate rispettivamente con i valori dei parametri name e surname.
     * - La variabile di istanza `number` è stata inizializzata come una lista vuota.
     * - La variabile di istanza `email` è stata inizializzata come una lista vuota.
     * - La variabile di istanza `tag` è stata inizializzata come una lista vuota di tag.
     * - Un ID univoco è stato generato e assegnato al contatto.
     * 
     * @see Contact#generateID() per la generazione dell'ID univoco.
     */
    public Contact(String name, String surname) {
        this.name=name;
        this.surname=surname;
        number = new ArrayList<String>();
        email = new ArrayList<String>();
        this.tag= new ArrayList<Tag>();
        this.ID = this.generateID();
    }
    
    
     /**
     * @brief Crea un nuovo oggetto {@link Contact} con il nome, cognome e ID forniti.
     * 
     * Questo costruttore inizializza il contatto con un nome, un cognome e un ID, e inizializza liste vuote per i numeri di 
     * telefono, gli indirizzi email e i tag associati.
     * 
     * @param name Il nome del contatto.
     * @param surname Il cognome del contatto.
     * @param ID L'ID univoco del contatto.
     * 
     * @pre
     * - Il parametro `name`, `surname` e `ID` devono essere non nulli.
     * 
     * @post
     * - La variabili di istanza 'name', 'surnname' e ID vengono inizializzate rispettivamente con i valori dei parametri name, surname e ID.
     * - La variabile di istanza `number` è stata inizializzata come una lista vuota.
     * - La variabile di istanza `email` è stata inizializzata come una lista vuota.
     * - La variabile di istanza `tag` è stata inizializzata come una lista vuota di tag.
     * 
     * @note Questo costruttore consente di specificare manualmente l'ID del contatto, rendendolo 
     * utile in scenari come il caricamento di dati da un database o da file.
     */
    public Contact(String name, String surname, String ID) {
        this.name=name;
        this.surname=surname;
        number = new ArrayList<>();
        email = new ArrayList<>();
        this.tag= new ArrayList<>();
        this.ID = ID;  
    }
    
    /**
    * @brief Restituisce il nome della persona.
    *
    * @pre La variabile d'istanza `name` deve essere stata inizializzata correttamente.
    * @post La stringa restituita rappresenta il nome della persona e non è modificata dal metodo.
    *
    * @return Una stringa contenente il nome della persona.
    */
    public String getName() {
        return name;
    }
    
    /**
    * @brief Restituisce il cognome della persona.
    *
    * @pre La variabile d'istanza `surname` deve essere stata inizializzata correttamente.
    * @post La stringa restituita rappresenta il cognome della persona e non è modificata dal metodo.
    *
    * @return Una stringa contenente il cognome della persona.
    */
    public String getSurname() {
        return surname;
    }
    
        /**
    * @brief Imposta il nome della persona.
    *
    * @pre Il parametro `name` non deve essere null.
    * @post La variabile d'istanza `name` viene aggiornata con il valore del parametro `name`.
    *
    * @param name Il nuovo nome della persona.
    */
    public void setName(String name) {
        this.name = name;
    }

    /**
    * @brief Imposta il cognome della persona.
    *
    * @pre Il parametro `surname` non deve essere null.
    * @post La variabile d'istanza `surname` viene aggiornata con il valore del parametro `surname`.
    *
    * @param surname Il nuovo cognome della persona.
    */
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    /**
     * @brief Restituisce i numeri di telefono del contatto.
     * 
     * Questo metodo restituisce i numeri di telefono associati al contatto.
     * Se il contatto ha più numeri, vengono restituiti ognuno su una linea diversa.
     * Se non sono presenti numeri, viene restituita una stringa vuota.
     * 
     * @return Una stringa contenente i numeri di telefono del contatto, ognuno su una linea diversa.
     * 
     * @pre
     * - La lista `number` deve essere correttamente inizializzata e contenere i numeri di telefono del contatto.
     * 
     * @post
     * - La stringa restituita contiene i numeri di telefono del contatto.
     */
    public String getNumber(){
        if(number.isEmpty()) return "";
        if(number.size()==1) return number.get(0);
        if(number.size()==2) return number.get(0)+"\n"+number.get(1);
        return number.get(0)+"\n"+number.get(1)+"\n"+number.get(2);
    }
    
    /**
     * @brief Restituisce le email del contatto.
     * 
     * Questo metodo restituisce le email associate al contatto. Se il contatto ha più email, vengono 
     * restituiti ognuna su una linea diversa. Se non sono presenti email, viene restituita una stringa vuota.
     * 
     * @return Una stringa contenente le email del contatto, ognuna su una linea diversa.
     * 
     * @pre
     * - La lista `email` deve essere correttamente inizializzata e contenere le email del contatto.
     * 
     * @post
     * - La stringa restituita contiene le email del contatto.
     */
    public String getEmail() {
        if(email.isEmpty()) return "";
        if(email.size()==1) return email.get(0);
        if(email.size()==2) return email.get(0)+"\n"+email.get(1);
        return email.get(0)+"\n"+email.get(1)+"\n"+email.get(2);
    }
    
    /**
     * @brief Restituisce i tag del contatto.
     * 
     * Questo metodo restituisce i tag associati al contatto. Se il contatto ha più tag, vengono 
     * restituiti ognuno su una linea diversa. Se non sono presenti tag, viene restituita una stringa vuota.
     * 
     * @return Una stringa contenente i tag del contatto, ognuno su una linea diversa.
     * 
     * @pre
     * - La lista `tag` deve essere correttamente inizializzata e contenere i tag del contatto.
     * 
     * @post
     * - La stringa restituita contiene i tag del contatto.
     */
    public String getTag(){
        if(tag.isEmpty()) return "";
        if(tag.size()==1) return tag.get(0).toString();        
        if(tag.size()==2) return tag.get(0).toString()+"\n"+tag.get(1);
        return tag.get(0).toString()+"\n"+tag.get(1).toString()+"\n"+
                tag.get(2).toString();
    }
    
    /**
     * @brief Aggiunge un tag al contatto.
     * 
     * Questo metodo aggiunge un tag alla lista dei tag del contatto, se il tag non è già presente. Se il tag è nullo, viene 
     * generata un'eccezione IllegalArgumentException.
     * 
     * @param tag Il tag da aggiungere al contatto.
     * 
     * @throws IllegalArgumentException Se il parametro `tag` è nullo.
     * 
     * @pre
     * - Il parametro `tag` deve essere non nullo.
     * 
     * @post
     * - Se il tag è valido e non duplicato, viene aggiunto alla lista dei tag del contatto.
     */
    public void addTag(Tag tag) {
        if (tag == null) {
            throw new IllegalArgumentException("Il tag non può essere null.");
        }        
        if (!this.tag.contains(tag)) {
            this.tag.add(tag);
        }
    }
    
    /**
     * @brief Aggiunge un numero di telefono al contatto.
     * 
     * Questo metodo aggiunge un numero di telefono alla lista dei numeri di telefono del contatto.
     * 
     * @param number Il numero di telefono da aggiungere al contatto.
     * 
     * @pre
     * - Il parametro `number` deve essere una stringa non nulla.
     * 
     * @post
     * - Il numero di telefono fornito viene aggiunto alla lista dei numeri del contatto.
     */
    public void addNumber(String number) {
        this.number.add(number);
    }
    
    /**
     * @brief Aggiunge un indirizzo email al contatto.
     * 
     * Questo metodo aggiunge un indirizzo email alla lista degli indirizzi email del contatto.
     * 
     * @param email L'indirizzo email da aggiungere al contatto.
     * 
     * @pre
     * - Il parametro `email` deve essere una stringa non nulla.
     * 
     * @post
     * - L'indirizzo email fornito viene aggiunto alla lista degli indirizzi email del contatto.
     */
    public void addEmail(String email){
        this.email.add(email);
    }
    
    /**
     * @brief Imposta il campo number con la lista fornita.
     *
     * @param number L'ArrayList di stringhe da assegnare al campo number.
     *
     * @pre Il parametro `number` non deve essere null.
     * @post Il campo `number` dell'oggetto viene aggiornato con la lista fornita.
     */
    public void setNumber(ArrayList<String> number) {
        this.number = number;
    }
    
    /**
     * @brief Imposta il campo email con la lista fornita.
     *
     * @param email L'ArrayList di stringhe da assegnare al campo email.
     *
     * @pre Il parametro `email` non deve essere null.
     * @post Il campo `email` dell'oggetto viene aggiornato con la lista fornita.
     */
    public void setEmail(ArrayList<String> email) {
        this.email = email;
    }
    
    /**
     * @brief Imposta il campo tag con la lista fornita.
     *
     * @param tag L'ArrayList di oggetti Tag da assegnare al campo tag.
     *
     * @pre Il parametro `tag` non deve essere null.
     * @post Il campo `tag` dell'oggetto viene aggiornato con la lista fornita.
     */
    public void setTag(ArrayList<Tag> tag) {
        this.tag = tag;
    }
    
    /**
     * @brief Restituisce la lista dei tag associati al contatto.
     *
     * @return L'ArrayList di oggetti Tag contenuto nel campo tag.
     *
     * @pre Il campo `tag` deve essere inizializzato.
     * @post Si ottiene l'ArrayList dei tag associati al contatto.
     * @invariant Nessuna modifica allo stato dell'oggetto.
     */
     public ArrayList<Tag> getTagList() {
        return tag;
    }
     
    /**
     * @brief Restituisce la lista dei numeri di telefono associati al contatto.
     *
     * @return L'ArrayList di oggetti String contenuto nel campo number.
     *
     * @pre Il campo `number` deve essere inizializzato.
     * @post Si ottiene l'ArrayList dei numeri di telefono associati al contatto.
     * @invariant Nessuna modifica allo stato dell'oggetto.
     */
    public ArrayList<String> getNumberList(){
        return this.number ;
    }
    
     /**
     * @brief Restituisce la lista delle email associate al contatto.
     *
     * @return L'ArrayList di oggetti String contenuto nel campo email.
     *
     * @pre Il campo `email` deve essere inizializzato.
     * @post Si ottiene l'ArrayList delle email associate al contatto.
     * @invariant Nessuna modifica allo stato dell'oggetto.
     */
    public ArrayList<String> getEmailList() {
        return email;
    }
    
    /**
     * @brief Restituisce l'ID associato a questo oggetto.
     *
     * @pre La variabile d'istanza `ID` deve essere stata inizializzata e non deve essere null.
     * @post Si ottiene l'ID associato al contatto.
     * @invariant Il valore della variabile `ID` rimane immutato dopo la chiamata del metodo.
     *
     * @return Una stringa contenente l'ID associato a questo oggetto.
     */
     public String getID() {
        return ID;
    }
     
    /**
    * @brief Confronta l'oggetto su cui viene invocato il metodo con un altro per verificarne l'uguaglianza. Il metodo
    * equals gode della proprietà asimmetrica
    *
    * Questo metodo confronta l'oggetto corrente con un altro oggetto di tipo `Object`. Due oggetti sono considerati uguali
    * se hanno lo stesso nome e cognome.
    *
    * @post Il metodo restituisce `true` se l'oggetto corrente è uguale all'oggetto passato come parametro, 
    *       altrimenti restituisce `false`.
    *
    * @param o L'oggetto da confrontare con l'oggetto corrente.
    * @return `true` se l'oggetto corrente è uguale all'oggetto passato, `false` altrimenti.
    */

    @Override
    public boolean equals(Object o){
        if(o==null) return false;
        if(o==this) return true;
        if (!(o instanceof Contact)) return false;
        
        Contact c = (Contact) o;
        
        return c.getName().toUpperCase().equals(this.getName().toUpperCase()) && c.getSurname().toUpperCase().equals(this.getSurname().toUpperCase());
    }
    
    /**
     * @brief Genera una rappresentazione testuale dell'oggetto.
     *
     * @pre La variabile d'istanza `number` deve essere stata inizializzata correttamente 
     *      (non null) e può contenere una lista di numeri di telefono.
     *      La variabile d'istanza `email` deve essere stata inizializzata correttamente 
     *      (non null) e può contenere una lista di indirizzi email.
     * @post La stringa restituita include il nome, il cognome, i numeri di telefono e gli indirizzi email associati al contatto.
     * @invariant Le variabili `number` ed `email` non vengono modificate durante l'esecuzione del metodo.
     *
     * @return Una stringa che rappresenta l'oggetto, contenente il nome, il cognome, 
     *         i numeri di telefono e gli indirizzi email associati.
     */
    @Override
    public String toString(){
        StringBuffer sb=new StringBuffer("Name: "+name+" Surname: "+surname+" ");
        for (int i=0; i<this.number.size();i++)
            sb=sb.append("Phone number: ").append(number.get(i));
        for (int i=0; i<this.email.size();i++)
            sb=sb.append("Email: ").append(email.get(i));
        return sb.toString();
    }
    
    /**
     * @brief Genera un ID basato su data e ora correnti con precisione ai nanosecondi.
     *
     * @post Viene restituito un identificatore univoco sotto forma di stringa con il pattern specificato "yyyy-MM-dd HH:mm:ss.SSSSSSSSS".
     *
     * @return Una stringa che rappresenta la data e ora correnti con precisione ai nanosecondi.
     */
        private  String generateID(){
    
        LocalDateTime now = LocalDateTime.now();
    
        // Formatta l'ora corrente con nanosecondi 
        DateTimeFormatter nanoFormatter; 
        nanoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSSSS");
        String id = now.format(nanoFormatter); 
        return id;
    }
     
       
        
    /**
     * @brief  Confronta l'oggetto su cui viene invocato il metodo con un altro per verificarne la relazione d'ordine
     * Questo metodo confronta l'oggetto corrente con un altro oggetto di tipo `Contact`.
     * Il confronto viene effettuato in base all'ordine alfabetico dei cognomi.
     * Nel caso in cui i cognomi risultassero uguali, allora vengono confrontati i nomi.
     * 
     * @param o Contatto da confrontare con il Contatto corrente.
     * @return un intero:
     * - positivo se l'oggetto corrente è maggiore di quello passato come parametro
     * - negativo se l'oggetto corrente è minore di quello passato come parametro
     * - uguale a 0 se l'oggetto corrente è uguale a quello passato come parametro
     */
    @Override
    public int compareTo(Contact o) {
    
        if(!this.surname.equals(o.getSurname()))
            return this.surname.compareTo(o.getSurname());
        return this.name.compareTo(o.getName());
    
}
    
}
    
    
  
