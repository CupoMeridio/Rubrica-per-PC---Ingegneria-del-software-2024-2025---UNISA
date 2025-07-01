package it.unisa.diem.team02.contactsbook.ui.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @brief Classe utility per gestire centralmente gli alert di errore e informazione nell'applicazione.
 * 
 * Questa classe fornisce metodi statici per mostrare messaggi di errore, avvisi e informazioni
 * all'utente tramite finestre di dialogo JavaFX Alert, centralizzando la gestione degli errori
 * dell'interfaccia utente.
 * 
 * @author team02
 */
public class AlertUtils {
    
    /**
     * @brief Mostra un alert di errore con il messaggio specificato.
     * 
     * @param title Il titolo della finestra di dialogo
     * @param message Il messaggio di errore da visualizzare
     * 
     * @pre title e message non devono essere null
     * @post Viene mostrato un alert di tipo ERROR all'utente
     */
    public static void showError(String title, String message) {
        showAlert(title, message, AlertType.ERROR);
    }
    
    /**
     * @brief Mostra un alert di informazione con il messaggio specificato.
     * 
     * @param title Il titolo della finestra di dialogo
     * @param message Il messaggio informativo da visualizzare
     * 
     * @pre title e message non devono essere null
     * @post Viene mostrato un alert di tipo INFORMATION all'utente
     */
    public static void showInfo(String title, String message) {
        showAlert(title, message, AlertType.INFORMATION);
    }
    
    /**
     * @brief Mostra un alert di avviso con il messaggio specificato.
     * 
     * @param title Il titolo della finestra di dialogo
     * @param message Il messaggio di avviso da visualizzare
     * 
     * @pre title e message non devono essere null
     * @post Viene mostrato un alert di tipo WARNING all'utente
     */
    public static void showWarning(String title, String message) {
        showAlert(title, message, AlertType.WARNING);
    }
    
    /**
     * @brief Mostra un alert generico con il tipo specificato.
     * 
     * @param title Il titolo della finestra di dialogo
     * @param message Il messaggio da visualizzare
     * @param type Il tipo di alert (ERROR, INFORMATION, WARNING, etc.)
     * 
     * @pre title, message e type non devono essere null
     * @post Viene mostrato un alert del tipo specificato all'utente
     */
    private static void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * @brief Mostra un alert di errore per problemi di connessione al database.
     * 
     * @post Viene mostrato un alert di errore specifico per problemi di database
     */
    public static void showDatabaseError() {
        showError("Errore Database", "Si è verificato un errore durante la comunicazione con il database. Riprova più tardi.");
    }
    
    /**
     * @brief Mostra un alert di errore per operazioni non riuscite.
     * 
     * @param operation Il nome dell'operazione che è fallita
     * 
     * @pre operation non deve essere null
     * @post Viene mostrato un alert di errore specifico per l'operazione fallita
     */
    public static void showOperationError(String operation) {
        showError("Errore Operazione", "Si è verificato un errore durante l'operazione: " + operation + ". Riprova più tardi.");
    }
}