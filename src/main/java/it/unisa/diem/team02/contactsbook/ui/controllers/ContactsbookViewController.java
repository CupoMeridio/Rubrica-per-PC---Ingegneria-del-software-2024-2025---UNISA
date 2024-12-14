package it.unisa.diem.team02.contactsbook.ui.controllers;

import it.unisa.diem.team02.App;
import it.unisa.diem.team02.contactsbook.database.Database;
import it.unisa.diem.team02.contactsbook.model.Contact;
import it.unisa.diem.team02.contactsbook.model.Contactbook;
import it.unisa.diem.team02.contactsbook.model.Filter;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * @brief Controller per la gestione della schermata principale della rubrica.
 * @author team02
 */
public class ContactsbookViewController implements Initializable {

    @FXML
    private AnchorPane anchorUp;
    @FXML
    private HBox hboxButton;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField txtSearch;
    @FXML
    private AnchorPane anchorBottom;
    @FXML
    private TableColumn<Contact, String> clmName;
    @FXML
    private TableColumn<Contact, String> clmEmail;
    @FXML
    private TableColumn<Contact, String> clmNum;
    @FXML
    private TableColumn<Contact, String> clmTag;
    @FXML
    private Button btnModify;
    @FXML
    private MenuButton mbtnFilter;
    @FXML
    private CheckMenuItem chkmHome;
    @FXML
    private CheckMenuItem chkmUni;
    @FXML
    private CheckMenuItem chkmJob;
    @FXML
    private Button btnImport;
    @FXML
    private Button btnExport;
    @FXML
    private TableView<Contact> tblvRubrica;
    @FXML
    private TableColumn<Contact, String> clmSur;
    @FXML
    private SplitPane interfacciaRubrica;
    @FXML
    private Button btnLogout;
    
    private Contactbook contactbook=new Contactbook();
    private Filter filter = new Filter(contactbook.getContacts());
    private SortedList<Contact> sortedContacts;

/**
 * @brief Inizializza il controller e configura gli elementi dell'interfaccia utente.
 * 
 * Questo metodo viene invocato automaticamente all'avvio della scena e prepara la lista di contatti,
 * i bottoni di modifica e cancellazione, e la funzionalità di ricerca.
 * 
 * @pre La scena e gli elementi dell'interfaccia utente devono essere già stati caricati.
 * @post Gli elementi dell'interfaccia sono stati configurati correttamente e la lista è pronta per l'uso.
 * @invariant La lista di contatti viene creata e popolata, i bottoni di modifica e cancellazione sono abilitati e la ricerca è configurata.
 * 
 * @see createList(), initializeList(), btnModifyInitialize(), btnDeleteInitialize(), initializeSearch()
 */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createList();
        btnMofidyInitialize();
        btnDeleteInitialize();
        initializeSearch();
        tblvRubricaInizialize();
        mbtnFilter.setOnShown(event->{actionFilter();});
           
    }    
    
    /**
     * @brief Configura la tabella visuale con le colonne e i dati dei contatti.
     * 
     * @pre La tabella e le colonne devono essere già configurate nell'interfaccia utente.
     * @post La  tabella è configurata per visualizzare i dati.
     * @invariant La tabella visualizzerà correttamente i contatti con i dettagli impostati nelle rispettive colonne.
     */
    public void createList(){
        sortedContacts= new SortedList<>(filter.getFlContacts());
        tblvRubrica.setItems(sortedContacts);
        clmName.setCellValueFactory(new PropertyValueFactory("name"));
        clmSur.setCellValueFactory(new PropertyValueFactory("surname"));
        clmName.setSortable(true);
        clmSur.setSortable(true);
        clmNum.setCellValueFactory(new PropertyValueFactory("number"));
        clmEmail.setCellValueFactory(new PropertyValueFactory("email"));
        clmTag.setCellValueFactory(new PropertyValueFactory("tag"));
        clmName.setSortable(true);
        clmSur.setSortable(true);
        sortedContacts.comparatorProperty().bind(tblvRubrica.comparatorProperty());
    }
    

/**

 * @brief Inizializza la tabella dei contatti recuperando i contatti dal database.
 *
 * Questo metodo recupera un elenco di contatti dal database e li aggiunge alla raccolta `contactbook`.
 *
 *@pre
 * - `Database.connection` deve essere una connessione al database valida e aperta.
 * - `Database.user` deve essere inizializzato correttamente con un indirizzo email valido.
 * - La tabella del database `contatti` deve esistere ed essere accessibile.
 *
 * @post
 * - Tutti i contatti recuperati dal database vengono aggiunti alla raccolta `contactbook`.
 *
 * @invariant
 * - La raccolta `contactbook` rimane coerente, contenente solo oggetti `Contact` validi.
 */

    public void tblvRubricaInizialize(){

        System.out.println("Sto recuperando i contatti");
        Database database = new Database();
        TreeMap<String, Contact> listaContatti = database.getContact(Database.connection, "contatti",Database.user.getEmail());
        for (Contact c : listaContatti.values()){
            contactbook.add(c);    
        }
        System.out.println(listaContatti);
    }
    
/**
 * @brief Apre una nuova finestra per aggiungere un nuovo contatto.
 * 
 * Questo metodo carica la vista per aggiungere un nuovo contatto, passa al prossimo controller la 
 * rubrica da aggiornare, e mostra la finestra in modalità finestra modale.
 * 
 * @param event L'evento di azione che attiva il metodo.
 * 
 * @throws IOException Se si verifica un errore durante il caricamento della vista FXML.
 * 
 * @pre La scena corrente deve contenere il bottone di aggiunta per attivare questa azione.
 * @post Viene mostrata una nuova finestra con la possibilità di aggiungere un nuovo contatto alla lista.
 * @invariant La nuova finestra è una finestra modale e non permette di interagire con la finestra principale fino alla sua chiusura.
 * 
 * @see AddViewController
 */
    @FXML
    public void actionAdd(ActionEvent event) throws IOException{
              FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddView.fxml"));
              Parent root = loader.load();
              Scene scene=new Scene(root);
              
              AddViewController addC=loader.getController();
              addC.setContactbook(contactbook);
              
    
              //gestire eccezione
              Stage newStage = new Stage();
              newStage.setScene(scene);
              
              newStage.initModality(Modality.WINDOW_MODAL);
              newStage.initOwner(btnAdd.getScene().getWindow());
              newStage.showAndWait(); 
    }
    
/**
 * @brief Apre una finestra modale per modificare un contatto selezionato.
 * 
 * Questo metodo carica la vista per modificare un contatto esistente,  passa al prossimo 
 * controller la rubrica da aggiornare e mostra la finestra in modalità finestra modale. 
 * Inoltre, imposta il contatto selezionato nella tabella per essere modificato nella nuova finestra.
 * 
 * @param event L'evento di azione che attiva il metodo.
 * 
 * @throws IOException Se si verifica un errore durante il caricamento della vista FXML.
 * 
 * @pre La scena corrente deve contenere il bottone di modifica e un contatto deve essere 
 * selezionato dalla lista.
 * @post Viene mostrata una nuova finestra con le informazioni del contatto selezionato, pronto 
 * per essere modificato.
 * @invariant La finestra di modifica è una finestra modale e non permette di interagire con la finestra principale fino alla sua chiusura.
 * 
 * @see ModifyViewController
 */
    @FXML
    public void actionModify(ActionEvent event) throws IOException{
              Database database = new Database();
              FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ModifyView.fxml"));
            
              Parent root = loader.load();
              Scene scene=new Scene(root);
              
              ModifyViewController modifyC=loader.getController();          
    
              //gestire eccezione
              Stage newStage = new Stage();
              newStage.setScene(scene);
              
              newStage.initModality(Modality.WINDOW_MODAL);
              newStage.initOwner(btnModify.getScene().getWindow());
              newStage.show();
              
              Contact selectedContact = tblvRubrica.getSelectionModel().getSelectedItem();
              
            
              
              modifyC.setContactbook(contactbook);
              modifyC.setContact(selectedContact);
    }
    
/**
 * @brief Inizializza il comportamento del bottone di modifica.
 * 
 * Questo metodo imposta lo stato del bottone di modifica (btnModify) su disabilitato. 
 * Inoltre, aggiunge un listener alla selezione della tabella dei contatti (tblvRubrica). Quando 
 * viene selezionato un contatto, il bottone di modifica viene abilitato; se nessun contatto è 
 * selezionato, il bottone viene disabilitato.
 * 
 * @post Il bottone di modifica sarà disabilitato finché non viene selezionato un contatto nella tabella.
 */
    public void btnMofidyInitialize(){
        btnModify.setDisable(true);

        tblvRubrica.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contact>() {
            @Override
            public void changed(ObservableValue<? extends Contact> observable, Contact oldValue, Contact newValue) {
                btnModify.setDisable(newValue == null);
            }
        });
    }
    
/**
 * @brief Inizializza il comportamento del bottone di eliminazione.
 * 
 * Questo metodo imposta lo stato del bottone di eliminazione (btnDelete) su disabilitato. 
 * Viene poi aggiunto un listener alla selezione della tabella dei contatti (tblvRubrica). Quando
 * viene selezionato un contatto, il bottone di eliminazione viene abilitato; se nessun contatto 
 * è selezionato, il bottone viene disabilitato.
 * 
 * @details Il bottone di eliminazione permette all'utente di eliminare un contatto dalla rubrica. 
 * Tuttavia, il bottone è disabilitato quando non è selezionato alcun contatto, impedendo azioni non desiderate.
 * 
 * @post Il bottone di eliminazione sarà disabilitato finché non viene selezionato un contatto nella tabella.
 */
    
    public void btnDeleteInitialize(){
        btnDelete.setDisable(true);

        tblvRubrica.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contact>() {
            @Override
            public void changed(ObservableValue<? extends Contact> observable, Contact oldValue, Contact newValue) {
                btnDelete.setDisable(newValue == null);
            }
        });
    }

/**
 * @brief Elimina un contatto selezionato dalla tabella.
 * 
 * Questo metodo rimuove il contatto attualmente selezionato dalla tabella dei contatti dalla 
 * rubrica.
 * 
 * @details Quando l'utente clicca sul bottone di eliminazione, il contatto selezionato viene 
 * rimosso dalla rubrica associata alla tabella. Questo comporta l'aggiornamento dinamico della 
 * vista della tabella, con la rimozione visibile del contatto.
 * 
 * @pre Un contatto deve essere selezionato nella tabella.
 * @post Il contatto selezionato viene rimosso dalla rubrica.
 * 
 * @see contacts
 */
    @FXML
    private void actionDelete(ActionEvent event) {
        Database database = new Database ();
        Contact selectedContact = tblvRubrica.getSelectionModel().getSelectedItem();
        try {
            database.removeContactByID(Database.connection, "contatti", selectedContact.getID(), Database.user.getEmail());
        } catch (SQLException ex){
            Logger.getLogger(ContactsbookViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        contactbook.delete(selectedContact); 
    }
    
/**
 * @brief Applica un filtro alla lista di contatti mostrata nella tabella in funizone di una tag selezionato.
 *
 * Questo metodo imposta la lista filtrata FilteredList di contatti sulla tabella tblvRubrica 
 * e aggiunge dei listener alle checkbox (chkmHome, chkmJob, chkmUni) per aggiornare il filtro
 * ogni volta che una di queste viene selezionata o deselezionata. Ogni cambio di stato nelle checkbox
 * farà chiamare il metodo updateFilter di filter per aggiornare la visualizzazione della lista dei contatti.
 *
 * @pre La lista filtrata deve essere stata inzizializzata.
 * @pre La tabella tblvRubrica deve essere inizializzata correttamente.
 * @post La lista filtrata viene modificata in funzione dei tag selezionati.
 * 
 * @invariant non viene rimosso il filtro fornito da initializeSearch()
 *
 * 
 * @see Filter
 */
    private void actionFilter() {
    
        chkmHome.selectedProperty().addListener((obs, oldValue, newValue) -> filter.updateFilter(
        txtSearch.getText(),chkmHome.isSelected(),chkmUni.isSelected(),chkmJob.isSelected()));
        
        chkmJob.selectedProperty().addListener((obs, oldValue, newValue) -> filter.updateFilter(
        txtSearch.getText(),chkmHome.isSelected(),chkmUni.isSelected(),chkmJob.isSelected()));
        
        chkmUni.selectedProperty().addListener((obs, oldValue, newValue) -> filter.updateFilter(
        txtSearch.getText(),chkmHome.isSelected(),chkmUni.isSelected(),chkmJob.isSelected()));
        }

         
    /**
    * @brief Inizializza la funzionalità di ricerca per filtrare i contatti in funzione di una sottostringa inserita
    * all'interno della barra di ricerca.
    * 
    * Questo metodo imposta un campo di ricerca che permette agli utenti di filtrare i contatti 
    * nella rubrica in base ai criteri inseriti nella barra di ricerca. Ogni volta che l'utente 
    * digita un carattere, la lista dei contatti viene filtrata in tempo reale richiamando il metodo 
    * updateFilter di filter per aggiornare la visualizzazione della lista dei contatti. La ricerca viene eseguita
    * su tutti i campi eccetto il campo tag.
    *
    * 
    * 
    * @pre La lista filtrata deve essere inizializzata correttamente.
    * @pre La tabella tblvRubrica deve essere inizializzata correttamente.
    * @post La lista viene aggiornata e contiene solo quei contatti in cui uno dei campi è presente la
    *   sottostringa inserita nella barra di ricerca.
    *
    * @invariant non viene rimosso il filtro da actionFilter()
    * 
    * 
    * @see Filter
    * 
    */
    private void initializeSearch() {
        
        SimpleStringProperty string = new SimpleStringProperty("");
        
        txtSearch.textProperty().bindBidirectional(string);
        
        //Listener al campo testo txtSearch
        txtSearch.textProperty().addListener((obs, oldValue, newValue) -> filter.updateFilter(
        txtSearch.getText(),chkmHome.isSelected(),chkmUni.isSelected(),chkmJob.isSelected()));
        

    }
    

/**
 * @brief Importa i contatti da un file CSV e li aggiunge alla rubrica.
 * 
 * Questo metodo permette all'utente di selezionare un file CSV tramite un dialogo di selezione 
 * file. Ogni riga del file CSV viene letta e i dati vengono utilizzati per creare nuovi oggetti 
 * Contact, che vengono successivamente aggiunti alla rubrica. Il formato del file CSV 
 * deve essere conforme alla struttura prevista, con i campi separati da punto e virgola.
 * 
 * @details Il metodo apre un file CSV selezionato tramite un FileChooser, legge ogni riga e 
 * divide i dati nei rispettivi campi (nome, cognome, numeri di telefono, e-mail, tag). I contatti 
 * creati vengono aggiunti alla rubrica. Prima di aggiungere un campo (ad esempio numero di telefono) al contatto, si verifica
 * che questo non sia vuoto.
 * 
 * @pre Il file selezionato deve essere un file CSV valido contenente i dati dei contatti.
 * @post I contatti letti dal file vengono aggiunti alla rubrica e visualizzati nella tabella.
 *
 */
    @FXML
    private void actionImport(ActionEvent event) {
        Database database = new Database();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Apri un file");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV", "*.csv"));

        Window window = tblvRubrica.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(window);
        
        if (selectedFile!=null)
            try {
                contactbook.loadFromFile(selectedFile);
                System.out.println(contactbook.getContacts());
                for(Contact c : contactbook.getContacts())
                try {
                        database.insertContact(Database.connection, "contatti", c, Database.user.getEmail());
                    } catch (SQLException ex) {
                        System.out.println("Rilevato contatto duplicato: " + c);
                    }
                
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Operation completed");
                alert.setHeaderText("");
                alert.setContentText("File import was successfully completed.");
                alert.showAndWait();
        } catch (Exception ex) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("An error occurred during the import. No contact has been imported.");
            alert.showAndWait();
        }
    }
    
/**
 * @brief Esporta la lista dei contatti in un file CSV.
 *
 * Questo metodo consente all'utente di scegliere un file in cui salvare i contatti presenti nella rubrica.
 * La lista dei contatti viene esportata in formato CSV.
 *
 * @pre La rubrica deve essere inizializzata.
 * @post I contatti vengono esportati nel file CSV selezionato dall'utente. Il file viene creato o sovrascritto
 *       con i dati esportati.
 *
 * @param event L'evento che ha causato l'azione (ad esempio, il clic del pulsante).
 */
    @FXML
    private void actionExport(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Scegli un file in cui salvare");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV", "*.csv"));
     
        Window window = (tblvRubrica.getParent().getScene().getWindow());
        File selectedFile = fileChooser.showSaveDialog(window);
        
        if (selectedFile!=null)
            try {
                contactbook.saveOnFile(selectedFile);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Operation completed");
                alert.setHeaderText("");
                alert.setContentText("File export was successfully completed.");
                alert.showAndWait();
        } catch (Exception ex) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("An error occurred during the export. No contact has been exported.");
            alert.showAndWait();
        }
    }
    
    
/**
 * @brief Esegue il logout dell'utente e redirige alla schermata di login.
 * 
 * Questo metodo gestisce l'azione di logout dell'utente, riportandolo alla schermata di login. 
 * Dopo il logout, l'applicazione cambia la vista corrente alla schermata di login.
 * 
 * @details Quando l'utente esegue il logout, il metodo cerca di cambiare la vista della 
 *           finestra principale a quella di login utilizzando il metodo App.setRoot().
 *           In caso di errore durante il caricamento della vista, viene loggato un errore.
 * 
 * @pre L'utente deve aver effettuato l'accesso.
 * @post La schermata attuale viene cambiata con la vista di login.
 * @invariant Nessuna modifica permanente ai dati dell'utente o allo stato dell'applicazione.
 * 
 * @see App#setRoot(String)
 */
    @FXML
    private void actionLogout(ActionEvent event) {
        Database database = new Database();
        try {
            database.CloseConnection(Database.connection);
        } catch (SQLException ex) {
            Logger.getLogger(ContactsbookViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Database.connection=null;
        
        try{
            App.setRoot("LoginView");} 
        catch (IOException ex) {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);}
    }
    
}
