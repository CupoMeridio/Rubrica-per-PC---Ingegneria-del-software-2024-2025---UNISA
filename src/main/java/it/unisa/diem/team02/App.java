
package it.unisa.diem.team02;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.IOException;
import static javafx.application.Application.launch;

/**
 * @brief Classe principale dell'applicazione JavaFX.
 * @details Gestisce il caricamento delle viste FXML e l'avvio dell'interfaccia utente.
 * @author team02
 */

public class App extends Application {

    /** Scena principale dell'applicazione */
    private static Scene scene;

/**
 * @brief Metodo principale che avvia l'applicazione JavaFX.
 * 
 * Questo metodo configura e mostra la finestra principale dell'applicazione. 
 * Carica la scena iniziale definita nel file FXML "LoginView" e applica alcune
 * configurazioni alla finestra, come il titolo e l'icona.
 * 
 * @param stage Lo stage principale dell'applicazione, fornito da JavaFX.
 * @throws IOException Se si verifica un errore durante il caricamento del file FXML.
 * 
 * @pre 
 * - Il file "LoginView.fxml" deve essere presente e accessibile nel percorso specificato.
 * - Il file dell'icona "/imgs/icon.png" deve esistere e essere leggibile.
 * 
 * @post 
 * - La finestra principale dell'applicazione viene visualizzata sullo schermo.
 * - La finestra è configurata con la scena "LoginView" e ha titolo e icona impostati.
 *
 * @details 
 * - La scena iniziale viene caricata dal file "LoginView.fxml".
 * - La finestra viene impostata su "massimizzata".
 * - Il titolo della finestra è impostato su "Rubrica".
 * - L'icona della finestra viene caricata dal percorso `/imgs/icon.png`.
 * - Dopo la configurazione, la finestra viene resa visibile.
 */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("LoginView"));
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Rubrica");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/imgs/icon.png")));
        stage.show();
    }

/**
 * @brief Cambia la radice della scena corrente.
 * 
 * Questo metodo permette di aggiornare il contenuto della scena attuale caricando
 * un nuovo layout da un file FXML specificato.
 * 
 * @param fxml Il nome del file FXML da caricare come nuova radice della scena.
 * @throws IOException Se si verifica un errore durante il caricamento del file FXML.
 * 
 * @pre 
 * - Il file FXML specificato deve essere presente, accessibile e correttamente formattato.
 * 
 * @post 
 * - La radice della scena corrente viene aggiornata al contenuto del file FXML specificato.
 * 
 * @invariant 
 * - La variabile `scene` rimane valida durante l'intera esecuzione dell'applicazione.
 * 
 * @details 
 * - Il metodo utilizza il file FXML specificato per aggiornare la struttura visiva
 *   della scena corrente.
 * - La funzione `loadFXML` viene utilizzata per caricare il file FXML e restituire
 *   la nuova radice.
 */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }


    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

/**
 * @brief Metodo principale dell'applicazione.
 * 
 * Questo metodo avvia l'applicazione JavaFX.
 * 
 * @pre 
 * - La configurazione di JavaFX deve essere corretta e pronta per l'esecuzione.
 * - Eventuali risorse necessarie per l'applicazione (come file FXML e immagini) devono essere presenti e accessibili.
 * 
 * @post 
 * - L'applicazione JavaFX viene avviata, e il metodo `start(Stage stage)` viene invocato per configurare e mostrare la finestra principale.
 * 
 */
    public static void main() {
        launch();
    }
}
