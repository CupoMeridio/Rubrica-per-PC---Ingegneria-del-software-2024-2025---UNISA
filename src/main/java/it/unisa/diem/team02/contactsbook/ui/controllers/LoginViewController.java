
package it.unisa.diem.team02.contactsbook.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import java.sql.Connection;
import it.unisa.diem.team02.contactsbook.database.Database;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import it.unisa.diem.team02.App;
import it.unisa.diem.team02.contactsbook.model.User;
import java.io.IOException;
import javafx.scene.input.KeyEvent;


/**
 * @brief Controller per la gestione della schermata di login e registrazione.
 * 
 * Questo controller gestisce la validazione dei campi email e password.
 * 
 * @author team02
 */
public class LoginViewController implements Initializable {

    @FXML
    private AnchorPane login; 
    
    @FXML
    private VBox vBoxLogin; 
    
    @FXML
    private Label lblLogMail; 
    
    @FXML
    private TextField txtLogMail; 
    
    @FXML
    private Label lblLogPass; 
    
    @FXML
    private PasswordField txtLogPass;
    
    @FXML
    private Button btnLogin; 
    
    @FXML
    private Label lblSignMail;
    
    @FXML
    private TextField txtSignMail;
    
    @FXML
    private Label lblSignPass;
    
    @FXML
    private PasswordField txtSignPass;
    
    @FXML
    private Label lblSignConfirm;
    
    @FXML
    private PasswordField txtConfirmPass;
    
    @FXML
    private Button btnSign; 
    
    @FXML
    private HBox hboxLogin;
    
    @FXML
    private VBox vboxSignin;
    
    @FXML
    private Label lblErrorPass;
    
    @FXML
    private Label lblPassInequals;
    
    @FXML
    private Label lblErrorEmail;
    
    @FXML
    private Label lblLogErr;
    
    @FXML
    private Label lblLogOpp;
    
    @FXML
    private Label txtSignError;
    
    private BooleanProperty bMail=new SimpleBooleanProperty(true);
    private BooleanProperty bPass=new SimpleBooleanProperty(true);
    private BooleanProperty bConfirm=new SimpleBooleanProperty(true);
    

/**
 * @brief Inizializza le proprietà dell'interfaccia utente per la registrazione.
 * 
 * Questo metodo configura le proprietà di binding dei controlli dell'interfaccia utente, come la disabilitazione 
 * del bottone di registrazione in base alle condizioni dei campi di testo.
 * 
 * @param url URL utilizzato per il caricamento della risorsa FXML (non utilizzato in questo caso).
 * @param rb Risorse locali associate alla vista (non utilizzato in questo caso).
 * 
 * @pre
 * - La vista FXML è stata caricata correttamente e i controlli sono disponibili.
 * 
 * @post
 * - Il pulsante `btnSign` sarà abilitato solo se tutte le condizioni nei campi di input sono soddisfatte.
 * 
 * @invariant
 * - La UI deve essere in uno stato consistente, con proprietà di binding correttamente applicate.
 * 
 */
    @Override
public void initialize(URL url, ResourceBundle rb) {
    txtSignMailInitialize();
    txtSignPassInitialize();
    txtConfirmPassInitialize();

    btnSign.disableProperty().bind(bMail.or(bPass).or(bConfirm));
    Database.connection = null;
}
    
    
/**
 * @brief Inizializza e gestisce la validazione del campo email per la registrazione.
 * 
 * Questo metodo crea una proprietà booleana osservabile che riflette la validità dell'email inserita.
 * Assegna inoltre un ascoltatore al campo di testo `txtSignMail` per aggiornare lo stile del bordo 
 * e visualizzare messaggi di errore a seconda della validità dell'indirizzo email.
 * 
 * @return Una proprietà booleana (`BooleanProperty`) che rappresenta lo stato di validità dell'email:
 *         - `true` se l'email è non valida.
 *         - `false` se l'email è valida.
 * 
 * @pre
 * - L'etichetta `lblErrorEmail` deve essere inizializzata.
 * 
 * @post
 * - Il bordo di `txtSignMail` cambierà colore a seconda della validità dell'email:
 *   - Verde per valido.
 *   - Rosso per non valido.
 * - L'etichetta `lblErrorEmail` sarà aggiornata con un messaggio di errore per email non valide.
 * 
 * @invariant
 * - La proprietà booleana riflette sempre lo stato corrente della validità dell'email.
 * 
 * @see #isValidEmail(String)
 * 
 * @note Lo stile del bordo è modificato tramite CSS inline.
 */
private void txtSignMailInitialize() {
    txtSignMail.textProperty().addListener((observable, oldValue, newValue) -> {
        if (isValidEmail(newValue)) {
            txtSignMail.setStyle("-fx-border-color: green;");
            bMail.set(false);
            lblErrorEmail.setText("");
        } else {
            txtSignMail.setStyle("-fx-border-color: red;");
            bMail.set(true);
            lblErrorEmail.setText("Invalid email address");
        }
    });
}
    
/**
 * @brief Inizializza e gestisce la validazione del campo password per la registrazione.
 * 
 * Questo metodo crea una proprietà booleana osservabile che riflette la validità della password inserita.
 * Assegna inoltre un ascoltatore al campo di testo `txtSignPass` per aggiornare lo stile del bordo 
 * e visualizzare messaggi di errore a seconda della validità della password.
 * 
 * @return Una proprietà booleana (`BooleanProperty`) che rappresenta lo stato di validità della password:
 *         - `true` se la password è non valida.
 *         - `false` se la password è valida.
 * 
 * @pre
 * - L'etichetta `lblErrorPass` deve essere inizializzata.
 * 
 * @post
 * - Il bordo di `txtSignPass` cambierà colore a seconda della validità della password:
 *   - Verde per valida.
 *   - Rosso per invalida.
 * - L'etichetta `lblErrorPass` sarà aggiornata con un messaggio di errore per password non valide.
 * 
 * @invariant
 * - La proprietà booleana riflette sempre lo stato corrente della validità della password.
 * 
 * @see #isValidPassword(String)
 * 
 * @note Lo stile del bordo è modificato tramite CSS inline.
 */
private void txtSignPassInitialize() {
    txtSignPass.textProperty().addListener((observable, oldValue, newValue) -> {
        if (isValidPassword(newValue)) {
            txtSignPass.setStyle("-fx-border-color: green;");
            bPass.set(false);
            lblErrorPass.setText("");
        } else {
            txtSignPass.setStyle("-fx-border-color: red;");
            bPass.set(true);
            lblErrorPass.setText("The password must be 8 characters long and contain a special \ncharacter, an uppercase, a lowercase and a number");
        }
    });
}
    
/**
 * @brief Inizializza e gestisce la validazione del campo di conferma password.
 * 
 * Questo metodo crea una proprietà booleana osservabile che riflette la corrispondenza tra 
 * il valore del campo di conferma password e quello del campo password principale. Assegna un 
 * ascoltatore al campo di testo `txtConfirmPass` per aggiornare lo stile del bordo e visualizzare
 * messaggi di errore in caso di non corrispondenza.
 * 
 * @return Una proprietà booleana (`BooleanProperty`) che rappresenta lo stato di validità della conferma password:
 *         - `true` se le password non corrispondono.
 *         - `false` se le password corrispondono.
 * 
 * @pre
 * - Il campo `txtSignPass` deve essere inizializzato e contenere il valore della password principale.
 * - L'etichetta `lblPassInequals` deve essere inizializzata.
 * 
 * @post
 * - Il bordo di `txtConfirmPass` cambierà colore a seconda della corrispondenza delle password:
 *   - Verde per corrispondenti.
 *   - Rosso per non corrispondenti.
 * - L'etichetta `lblPassInequals` sarà aggiornata con un messaggio di errore in caso di non corrispondenza.
 * 
 * @invariant
 * - La proprietà booleana riflette sempre lo stato corrente della corrispondenza delle password.
 * 
 * @note Lo stile del bordo è modificato tramite CSS inline.
 */
private void txtConfirmPassInitialize() {
    txtConfirmPass.textProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue.equals(txtSignPass.getText())) {
            txtConfirmPass.setStyle("-fx-border-color: green;");
            bConfirm.set(false);
            lblPassInequals.setText("");
        } else {
            txtConfirmPass.setStyle("-fx-border-color: red;");
            bConfirm.set(true);
            lblPassInequals.setText("The passwords do not match");
        }
    });
}
    
    
    
/**
 * @brief Verifica la validità di un indirizzo email.
 * 
 * Questo metodo utilizza un'espressione regolare per verificare se una stringa 
 * rappresenta un indirizzo email valido. La validità è determinata in base al formato 
 * comune degli indirizzi email.
 * 
 * @param email La stringa da validare come indirizzo email.
 * 
 * @return `true` se la stringa è un indirizzo email valido, `false` altrimenti.
 * 
 * @pre
 * - La stringa `email` non deve essere `null`.
 * 
 * @post
 * - Nessuna modifica a stati esterni o variabili di istanza.
 * 
 * @invariant
 * - La validità di un indirizzo email è determinata esclusivamente dal confronto con 
 *   l'espressione regolare definita.
 * 
 * @note
 * L'espressione regolare utilizzata supporta:
 * - Lettere, numeri, punti (`.`), trattini (`-`) e underscore (`_`) per il nome utente.
 * - Un dominio con lettere, numeri, punti e trattini.
 * - Estensioni di dominio tra 2 e 6 caratteri alfabetici.
 */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }
    
/**
 * @brief Verifica la validità di una password.
 * 
 * Questo metodo utilizza un'espressione regolare per verificare se una stringa soddisfa 
 * i criteri di validità di una password. Una password valida deve contenere:
 * - Almeno 8 caratteri.
 * - Almeno una lettera maiuscola.
 * - Almeno un numero.
 * - Almeno un carattere speciale.
 * 
 * @param password La stringa da validare come password.
 * 
 * @return `true` se la stringa è una password valida, `false` altrimenti.
 * 
 * @pre
 * - La stringa `password` non deve essere `null`.
 * 
 * @post
 * - Nessuna modifica a stati esterni o variabili di istanza.
 * 
 * @invariant
 * - La validità di una password è determinata esclusivamente dal confronto con 
 *   l'espressione regolare definita.
 * 
 * @note
 * L'espressione regolare utilizzata supporta:
 * - Una lettera maiuscola (`A-Z`).
 * - Un numero (`\\d`).
 * - Un carattere speciale tra quelli inclusi nella lista: `!@#$%^&*()_+-=[]{};':",.<>?/`.
 * - Una lunghezza minima di 8 caratteri.
 */
    private boolean isValidPassword(String password) {
        // Espressione regolare per la validazione della password
        //  Espressione regolare per validare la password
        // @lang en Regular expression to validate the password
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>?/]).{8,}$";
        return password.matches(passwordRegex);
    }
    
    
/**
 * @brief Gestisce l'evento di login dell'utente.
 * 
 * Questo metodo viene invocato quando l'utente clicca sul pulsante di login. Si connette al database,
 * verifica le credenziali inserite dall'utente e visualizza un messaggio corrispondente all'esito
 * della verifica. In caso di successo, carica la vista principale della rubrica.
 * 
 * @param event L'evento scatenato dall'interazione dell'utente con l'interfaccia.
 * 
 * @throws IOException Se si verifica un errore durante il caricamento della nuova vista.
 * @throws SQLException Se si verifica un errore di comunicazione con il database.
 * 
 * @pre
 * - `txtLogMail` contiene un indirizzo email valido.
 * - `txtLogPass` contiene una password.
 * 
 * @post
 * - Mostra un messaggio di errore o successo nella label `lblLogErr`.
 * - Cambia la vista dell'applicazione in caso di login riuscito.
 * 
 * @see Database#ConnectionDB(String, String, String)
 * @see Database#checkLogin(Connection, String, String, String)
 * @see App#setRoot(String)
 * 
 * @note
 * - I messaggi di errore sono mostrati nella label `lblLogErr`.
 * - La connessione al database utilizza un nome utente e una password fissi.
 */
    
    @FXML
    private void actionLogin(ActionEvent event) {
        Database database = new Database();
          Database.connection = database.ConnectionDB("rubrica", "avnadmin", "AVNS_rgkdmIqyKlbMdHqenly");
        //Database.connection = database.ConnectionDB("rubrica", "postgres", "postgres");
        try {
            int esito = database.checkLogin(Database.connection, "utenti", txtLogMail.getText(), txtLogPass.getText());
             if(esito==0){
                lblLogErr.setText("Password errata.");
                database.CloseConnection(database.connection);
                Database.connection=null;
             }
             else if(esito==-1){
                lblLogErr.setText("Non esiste alcun account legato a questa email.");
                database.CloseConnection(Database.connection);
                Database.connection=null;
            }
            else if(esito==1){
                lblLogErr.setText("Login effettuato con successo.");
                Database.user= new User(txtLogPass.getText(), txtLogMail.getText());
                try {
                    App.setRoot("ContactsbookView");
                } catch (IOException ex) {
                    Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
                }    
            }
            else{
            lblLogErr.setText("Qualcosa è anadato storto.");
            database.CloseConnection(Database.connection);
            Database.connection=null;
            }


            
        } catch (SQLException ex) {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
            try {
                database.CloseConnection(Database.connection);
            } catch (SQLException ex1) {
                Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Database.connection=null;
        }
       
            
    }
    
    /*
    * Implementa l'azione associata al pulsante Sign-In. Se le credenziali inserite
    * non sono già presenti nel database permette all'utente di registrarsi
    */
    
    @FXML
    private void actionSignin(ActionEvent event) {
        Database database= new Database();
          Database.connection = database.ConnectionDB("rubrica", "avnadmin", "AVNS_rgkdmIqyKlbMdHqenly");
        //Database.connection = database.ConnectionDB("rubrica", "postgres", "postgres");
        try {
            database.insertUser(Database.connection, "utenti" ,txtSignMail.getText(), txtSignPass.getText());
        } catch (SQLException ex) {
            txtSignError.setText("L'email è già associata ad un'account.\nEsegui il login.");
            Database.connection=null;
            
            try {
                database.CloseConnection(Database.connection);
            } catch (SQLException ex1) {
                Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
        Database.user = new User(txtSignPass.getText(), txtSignMail.getText());
        
        try {
               App.setRoot("ContactsbookView");
            } catch (IOException ex) {
               Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    /**
    * @brief Il metodo verifica che la mail inserita nel relativo campo di testo sia valida.
    * 
    * Questo metodo viene invocato ogni volta che viene premuto un tasto nel campo di testo relativo all'email per
    * la registrazione.
    * Assegna un ascoltatore al campo di testo `txtSignMail` per aggiornare lo stile del bordo 
    * e visualizzare messaggi di errore a seconda della validità dell'indirizzo email.
    * 
    * @pre
    * - L'etichetta `lblErrorEmail` deve essere inizializzata.
    * 
    * @post
    * - Il bordo di `txtSignMail` cambierà colore a seconda della validità dell'email:
    *   - Verde per valido.
    *   - Rosso per non valido.
    * - L'etichetta `lblErrorEmail` sarà aggiornata con un messaggio di errore per email non valide.
    * 
    * @see #isValidEmail(String)
    * 
    * @note Lo stile del bordo è modificato tramite CSS inline.
    */
    @FXML
    private void keyEmailSign(KeyEvent event) {
        txtSignMail.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isValidEmail(newValue)) {
                txtSignMail.setStyle("-fx-border-color: green;");  ///<  Bordo verde se valido
                                                                 
                bMail.set(false);
                lblErrorEmail.setText("");
                
            } else {
                txtSignMail.setStyle("-fx-border-color: red;");    ///<  Bordo rosso se non valido
                                                           
                lblErrorEmail.setText("Invalid email address");
                bMail.set(true);
            }
        });
    }

    /**
    * @brief Il metodo verifica che la password inserita nel relativo campo di testo sia valida.
    * 
    * Questo metodo viene invocato ogni volta che viene premuto un tasto nel campo di testo relativo alla password per
    * la registrazione.
    * Assegna un ascoltatore al campo di testo `txtSignPass` per aggiornare lo stile del bordo 
    * e visualizzare messaggi di errore a seconda della validità della password.
    * 
    * @pre
    * - L'etichetta `lblErrorPass` deve essere inizializzata.
    * 
    * @post
    * - Il bordo di `txtSignPass` cambierà colore a seconda della validità dell'email:
    *   - Verde per valido.
    *   - Rosso per non valido.
    * - L'etichetta `lblErrorEmail` sarà aggiornata con un messaggio di errore per email non valide.
    * 
    * @see #isValidPassword(String)
    * 
    * @note Lo stile del bordo è modificato tramite CSS inline.
    */
    @FXML
    private void keyPassSign(KeyEvent event) {
        txtSignPass.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isValidPassword(newValue)) {
                txtSignPass.setStyle("-fx-border-color: green;");  ///<  Bordo verde se valido
                                                                 ///< @lang en Green border if valid
                bPass.set(false);
                lblErrorPass.setText("");
                   
            } else {
                txtSignPass.setStyle("-fx-border-color: red;");    ///<  Bordo rosso se non valido
                                                                 ///< @lang en Red border if invalid
                lblErrorPass.setText("The password must be 8 characters long and contain a special \ncharacter, an uppercase, " + "a lowercase and a number");
                bPass.set(true);
            }
        }); 
    }

    /**
    * @brief Il metodo verifica che la password di conferma coincida con quella principale.
    * 
    * Questo metodo viene invocato ogni volta che viene premuto un tasto nel campo di testo relativo alla password di conferma.
    * Assegna un ascoltatore al campo di testo `txtConfirmPass` per aggiornare lo stile del bordo e visualizzare
    * messaggi di errore in caso di non corrispondenza.
    * 
    * @pre
    * - Il campo `txtSignPass` deve essere inizializzato e contenere il valore della password principale.
    * - L'etichetta `lblPassInequals` deve essere inizializzata.
    * 
    * @post
    * - Il bordo di `txtConfirmPass` cambierà colore a seconda della corrispondenza delle password:
    *   - Verde per corrispondenti.
    *   - Rosso per non corrispondenti.
    * - L'etichetta `lblPassInequals` sarà aggiornata con un messaggio di errore in caso di non corrispondenza.
    * 
    * @note Lo stile del bordo è modificato tramite CSS inline.
    */
    @FXML
    private void keyConfirmPass(KeyEvent event) {
        txtConfirmPass.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(txtSignPass.getText())) {
                txtConfirmPass.setStyle("-fx-border-color: green;");  // Bordo verde se corrispondono
                lblPassInequals.setText("");
                bConfirm.set(false);
            } else {
                txtConfirmPass.setStyle("-fx-border-color: red;");    // Bordo rosso se non corrispondono
                lblPassInequals.setText("The passwords do not match");
                bConfirm.set(true);
            }
        });
    }
}
