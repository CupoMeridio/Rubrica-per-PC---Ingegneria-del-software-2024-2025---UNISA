
package it.unisa.diem.team02.contactsbook.ui.controllers;

import it.unisa.diem.team02.contactsbook.database.Database;
import it.unisa.diem.team02.contactsbook.model.Contact;
import it.unisa.diem.team02.contactsbook.model.Contactbook;
import it.unisa.diem.team02.contactsbook.model.Tag;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @brief Controller per la gestione della schermata di aggiunta di un nuovo contatto.
 * @author team02
 */
public class AddViewController implements Initializable {

    @FXML
    private AnchorPane add;
    @FXML
    private HBox hboxInsertInfo; 
    @FXML
    private HBox hboxName;
    @FXML
    private Label lblName;
    @FXML
    private VBox vboxName;
    @FXML
    private TextField txtName;
    @FXML
    private HBox hboxSur;
    @FXML
    private Label lblSur;
    @FXML
    private VBox vboxSur;
    @FXML
    private TextField txtSur;
    @FXML
    private HBox hboxEmail;
    @FXML
    private Label lblEmail;
    @FXML
    private VBox vboxEmail;
    @FXML
    private TextField txtEmail1;
    @FXML
    private TextField txtEmail2;
    @FXML
    private TextField txtEmail3;
    @FXML
    private HBox hboxNumber;
    @FXML
    private Label lblNumber;
    @FXML
    private VBox vboxNumber;
    @FXML
    private TextField txtNumber1;
    @FXML
    private TextField txtNumber2;
    @FXML
    private HBox hboxButton;
    @FXML
    private Button btnCanc;
    @FXML
    private TextField txtNumber3;
    @FXML
    private Button btnAdd;
    @FXML
    private Label lblTag;
    @FXML
    private CheckBox chkmHome;
    @FXML
    private CheckBox chkmUni;
    @FXML
    private CheckBox chkmJob;
    
    private Contactbook contactbook;
    

/**
 * @brief Inizializza la finestra e il controller associato alla vista FXML.
 * 
 * Questo metodo configura le proprietà di abilitazione/disabilitazione di alcuni componenti dell'interfaccia utente,
 * utilizzando binding JavaFX per collegare le proprietà degli elementi.
 * 
 * @param url L'URL utilizzato per individuare le risorse necessarie al controller (può essere nullo).
 * @param rb Il ResourceBundle contenente le risorse internazionalizzate per il controller (può essere nullo).
 * 
 * @details 
 * - Il pulsante `btnAdd` viene disabilitato se entrambi i campi di testo `txtName` e `txtSur` sono vuoti.
 * - I campi `txtNumber2` e `txtNumber3` vengono abilitati sequenzialmente solo se i campi precedenti contengono testo.
 * - I campi `txtEmail2` e `txtEmail3` seguono una logica simile a quella dei numeri.
 * - Le proprietà di abilitazione/disabilitazione sono gestite con il metodo `bind` della classe `Bindings`.
 * 
 * @pre
 * - I campi `txtName`, `txtSur`, `txtNumber1`, `txtNumber2`, `txtNumber3`, `txtEmail1`, `txtEmail2`, `txtEmail3`
 *   devono essere inizializzati correttamente e associati agli elementi corrispondenti nella vista FXML.
 * - Il pulsante `btnAdd` deve essere inizializzato e associato a un elemento della vista.
 * 
 * @post
 * - Le proprietà di abilitazione/disabilitazione dei componenti sono configurate in base alle condizioni specificate.
 */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAdd.disableProperty().bind(Bindings.and(txtName.textProperty().isEmpty(), txtSur.textProperty().isEmpty()));
        txtNumber2.disableProperty().bind(Bindings.isEmpty(txtNumber1.textProperty()));
        txtNumber3.disableProperty().bind(Bindings.isEmpty(txtNumber2.textProperty()));
        txtEmail2.disableProperty().bind(Bindings.isEmpty(txtEmail1.textProperty()));
        txtEmail3.disableProperty().bind(Bindings.isEmpty(txtEmail2.textProperty()));
    }

    /**
    * @brief Imposta l'istanza di rubrica da utilizzare.
    * 
    * @param c La rubrica da aggiornare.
    * 
    * @pre
    * - Il parametro `c` non deve essere nullo.
    * 
    * @post
    * - L'attributo `contactbook` contiene il riferimento alla rubrica specificata.
    */
    public void setContactbook(Contactbook c){
        contactbook=c;
    }
    
/**
 * @brief Aggiunge un nuovo contatto alla lista osservabile e gestisce duplicati.
 * 
 * Questo metodo crea un oggetto `Contact` basato sui dati inseriti nei campi di testo 
 * e lo aggiunge al contactbook e alla tabella del database . Se il contatto esiste già, viene mostrata una finestra per gestire il duplicato.
 * 
 * @param event L'evento che ha attivato l'azione, ovvero il clic sul pulsante "Aggiungi".
 * 
 * @throws IOException Se si verifica un errore durante il caricamento del file FXML per la gestione dei duplicati.
 * 
 * @pre
 * - Il campo `contactbook` deve essere inizializzato.
 * 
 * @post
 * - Se il contatto non è un duplicato, viene aggiunto a `contactbook`.
 * - Se il contatto è un duplicato, l'utente decide se aggiungerlo o meno.
 * - La finestra corrente viene chiusa dopo l'aggiunta del contatto o se l'utente decide di annullare l'operazione.
 * 
 * @details
 * - I dati vengono estratti dai campi di testo `txtName`, `txtSur`, `txtNumber1`, `txtNumber2`, 
 *   `txtNumber3`, `txtEmail1`, `txtEmail2`, `txtEmail3` e dai textBox.
 * - Se un campo di numero o email è vuoto, non viene aggiunto al contatto.
 * - Se una checkbox dei tag non è selezionata, non viene aggiunto un tag.
 * - In caso di duplicato, viene caricata la vista `DuplicateContactView.fxml`, e l'utente 
 *   sceglie se aggiungere il contatto o tornare a modificare i dati.
 * 
 * @see Contact
 * @see DuplicateContactViewController
 */
    @FXML
    public void actionAdd(ActionEvent event) throws IOException{
        Database database = new Database();
        Contact c=new Contact(txtName.getText(), txtSur.getText());
        if (!txtNumber1.getText().isEmpty()) c.addNumber(txtNumber1.getText());
        if (!txtNumber2.getText().isEmpty()) c.addNumber(txtNumber2.getText());
        if (!txtNumber3.getText().isEmpty()) c.addNumber(txtNumber3.getText());
        if (!txtEmail1.getText().isEmpty()) c.addEmail(txtEmail1.getText());
        if (!txtEmail2.getText().isEmpty()) c.addEmail(txtEmail2.getText());
        if (!txtEmail3.getText().isEmpty()) c.addEmail(txtEmail3.getText());
        if (chkmHome.isSelected()) c.addTag(Tag.Home);
        if (chkmUni.isSelected()) c.addTag(Tag.University);
        if (chkmJob.isSelected()) c.addTag(Tag.Job);
        
        
        
        if (contactbook.contains(c)){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DuplicateContactView.fxml"));
            Parent root = loader.load();
            Scene scene=new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);

            DuplicateContactViewController duplicateC=loader.getController();

            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.initOwner(btnAdd.getScene().getWindow());
            newStage.showAndWait();
                
            if (duplicateC.getBoolean()){
                
                    try {
                        database.insertContact(Database.connection, "contatti", c, Database.user.getEmail());
                    } catch (SQLException ex) {
                        Logger.getLogger(AddViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    contactbook.add(c);
                    
                    Stage stage=(Stage) btnAdd.getScene().getWindow();
                    stage.close();
                } else {
                    Stage stage=(Stage) btnAdd.getScene().getWindow();
                    stage.show();
                }       
        }
        else{
            try {
                database.insertContact(Database.connection, "contatti", c, Database.user.getEmail());
                } catch (SQLException ex) {
                    Logger.getLogger(AddViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            contactbook.add(c);
            Stage stage=(Stage) btnAdd.getScene().getWindow();
            stage.close();
        }
    }
    
/**
 * @brief Gestisce l'azione di annullamento dell'operazione corrente.
 * 
 * Questo metodo chiude la finestra attualmente aperta senza apportare modifiche ai dati o alle operazioni in corso.
 * 
 * @param event L'evento che ha attivato l'azione di annullamento, ovvero il click sul tasto 'annulla'.
 * 
 * @pre 
 * - Il pulsante associato a questa azione deve essere correttamente configurato e visibile nell'interfaccia utente.
 * 
 * @post
 * - La finestra corrente viene chiusa e non vengono modificati i dati.
 * 
 */
    @FXML
    public void actionCancel(ActionEvent event){
        Stage stage=(Stage) btnCanc.getScene().getWindow();
        stage.close();
    }   
    /**
     * @brief Funzione di utilità per gestire l'interfaccia di aggiunta di un contatto
     * Metodo privato
     */
    
    @FXML
    private void onKeyReleasedEmail1(KeyEvent event) {
        if(txtEmail1.getText().equals("")){
            if(txtEmail2.getText().equals(""))
                return;
            txtEmail1.setText(txtEmail2.getText());
            txtEmail2.setText(txtEmail3.getText());
            txtEmail3.setText("");
        }
    }
    
    /**
     * @brief Funzione di utilità per gestire l'interfaccia di aggiunta di un contatto
     * Metodo privato
     */
    @FXML
    private void onKeyReleasedEmail2(KeyEvent event) {
        if (txtEmail2.getText().equals("")){
            txtEmail2.setText(txtEmail3.getText());
            txtEmail3.setText("");
        }
    }
    
    /**
     * @brief Funzione di utilità per gestire l'interfaccia di aggiunta di un contatto
     * Metodo privato
     */
    @FXML
    private void onKeyReleasedNumber1(KeyEvent event) {
        if(txtNumber1.getText().equals("")){
            if(txtNumber2.getText().equals(""))
                return;
            txtNumber1.setText(txtNumber2.getText());
            txtNumber2.setText(txtNumber3.getText());
            txtNumber3.setText("");
        }
    }

    /**
     * @brief Funzione di utilità per gestire l'interfaccia di aggiunta di un contatto
     * Metodo privato
     */
    @FXML
    private void onKeyReleasedNumber2(KeyEvent event) {
        if (txtNumber2.getText().equals("")){
            txtNumber2.setText(txtNumber3.getText());
            txtNumber3.setText("");
        }
    }
}
