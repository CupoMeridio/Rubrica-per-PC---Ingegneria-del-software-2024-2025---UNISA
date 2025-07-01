package it.unisa.diem.team02.services;

import it.unisa.diem.team02.contactsbook.model.Contactbook;
import it.unisa.diem.team02.contactsbook.ui.controllers.ContactsbookViewController;
import java.io.File;
import java.io.IOException;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import it.unisa.diem.team02.contactsbook.ui.utils.AlertUtils;
import javafx.scene.control.ProgressBar;

/**
 * Classe che gestisce l'esportazione dei contatti su un file.
 * Estende la classe {@link Service} per eseguire l'operazione in un thread separato.
 */
public class ExportService extends Service<Void> {
    private final File selectedFile;
    private final ProgressBar progressBar;
    private final ContactsbookViewController contactsbookViewController;
    private final Contactbook contactbook;

    /**
     * Costruttore che inizializza il servizio di esportazione.
     * 
     * @param file Il file selezionato per salvare i contatti esportati.
     * @param controller Il controller della vista di contatti.
     */
    public ExportService(File file, ContactsbookViewController controller) {
        this.contactsbookViewController = controller;
        this.contactbook = controller.getContactbook();
        this.selectedFile = file;
        this.progressBar = controller.getProgressBar(); // Ora non sarà null!
    }

    /**
     * Crea il task che eseguirà l'operazione di esportazione.
     * Il task salva i contatti su un file e gestisce eventuali errori.
     * 
     * @return Il task che esegue l'esportazione.
     */
    @Override
    protected Task<Void> createTask() {
        contactsbookViewController.getBtnExport().setDisable(true);
        return new Task<>() {
            @Override
            protected Void call() {
                try {
                    // Salva i contatti nel file selezionato
                    contactbook.saveOnFile(selectedFile);
                } catch (IOException ex) {
                    AlertUtils.showError("Error", "An error occurred during the export. No contact has been exported.");
                }
                return null;
            }
        };
    }

    /**
     * Metodo chiamato al termine con successo dell'operazione.
     * 
     * @post La barra di progresso è completata e viene mostrato un messaggio di successo.
     */
    @Override
    protected void succeeded() {
        super.succeeded();
        progressBar.progressProperty().unbind();
        progressBar.setProgress(1);
        contactsbookViewController.getBtnExport().setDisable(false);
        AlertUtils.showInfo("Operation completed", "File export was successfully completed.");
    }

    /**
     * Metodo chiamato in caso di fallimento dell'operazione.
     * 
     * @post La barra di progresso viene azzerata e viene mostrato un messaggio di errore.
     */
    @Override
    protected void failed() {
        super.failed();
        progressBar.progressProperty().unbind();
        progressBar.setProgress(0);
        contactsbookViewController.getBtnExport().setDisable(false);
        AlertUtils.showError("Error", "An error occurred during the export. No contact has been exported.");
    }


}
