
package it.unisa.diem.team02.contactsbook.model;

/**
 * @brief
 * Classe che definisce l'utente che accede all'applicazione
 * 
 * Classe che estende la classe astratta Person. Definisce degli ulteriori attributi e metodi
 * per effettuare specifiche operazioni sugli oggetti User.
 */
public class User{
    private String password;
    private final String email;

    
/**
 * @brief Costruttore per inizializzare un oggetto User con una password, un'email, un nome e un cognome.
 *
 * Questo costruttore inizializza un oggetto `User` con i valori forniti per la password, l'email, 
 * il nome e il cognome. La classe `User` estende la classe `Person`, quindi il nome e il cognome 
 * sono gestiti dal costruttore della classe padre.
 *
 * @pre 
 * - Il parametro `password` deve essere non null e non vuoto.
 * - Il parametro `email` deve essere un'email valida (non null e con il formato corretto).
 * - I parametri `name` e `surname` devono essere non null e non vuoti.
 * @post
 * L'oggetto `User` viene creato con i valori di `password`, `email`, `name` e `surname`.
 * @invariant 
 * La variabile `password` deve essere sempre valida e sicura, la variabile `email` 
 * deve essere un'email correttamente formattata, e `name` e `surname` devono essere 
 * consistenti con i dati forniti.
 *
 * @param password La password dell'utente.
 * @param email L'email dell'utente.
 */
    public User(String password, String email) {
        this.password = password;
        this.email = email;
    }
    
/**
 * @brief Imposta la password dell'utente.
 *
 * Questo metodo consente di impostare una nuova password per l'utente.
 *
 * @pre Il parametro `password` deve essere non null e non vuoto.
 * @post La variabile d'istanza `password` viene aggiornata con il valore del parametro `password`.
 * @invariant La variabile `password` deve contenere una password valida e sicura.
 *
 * @param password La nuova password dell'utente.
 */
    public void setPassword(String password) {
        this.password = password;
    }
    
/**
 * @brief Restituisce l'email dell'utente.
 *
 * Questo metodo restituisce l'email associata all'utente.
 *
 * @pre La variabile d'istanza `email` deve essere stata inizializzata correttamente.
 * @post Il metodo restituisce l'email dell'utente, senza modificarla.
 * @invariant La variabile `email` deve contenere un'email valida e correttamente formattata.
 *
 * @return Una stringa contenente l'email dell'utente.
 */
    public String getEmail() {
        return email;
    }
    
/**
 * @brief Restituisce una rappresentazione testuale dell'oggetto.
 *
 * Questo metodo restituisce una stringa che rappresenta l'oggetto, includendo il ruolo dell'oggetto,
 * i dettagli della classe genitore (`toString` di `Person`), e l'email dell'utente.
 *
 * @pre L'oggetto deve essere stato correttamente inizializzato con un ruolo e un'email validi.
 * @post Il metodo restituisce una stringa formattata che include il ruolo, il risultato di `toString` 
 *       della classe `Person` e l'email dell'utente.
 * @invariant Le variabili `role` e `email` devono contenere valori validi.
 *
 * @return Una stringa contenente il ruolo, il nome e cognome (tramite `super.toString()`), 
 *         e l'email dell'utente.
 */
    @Override
    public String toString(){
        return " Email: "+email;
    }
}

