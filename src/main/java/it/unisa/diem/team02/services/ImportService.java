package it.unisa.diem.team02.services;

import it.unisa.diem.team02.contactsbook.database.Database;
import it.unisa.diem.team02.contactsbook.model.Contact;
import it.unisa.diem.team02.contactsbook.model.Contactbook;
import it.unisa.diem.team02.contactsbook.ui.controllers.ContactsbookViewController;
import java.io.File;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import it.unisa.diem.team02.contactsbook.ui.utils.AlertUtils;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Service per l'importazione dei contatti da un file.
 * Esegue il processo in background con aggiornamento progressivo.
 */
public class ImportService extends Service<Void> {
    private final File file;
    private final ContactsbookViewController contactsbookViewController;
    private final ProgressBar progressBar;
    private final Contactbook contactbook;
    private final Database database;

    /**
     * Costruttore della classe ImportService.
     * 
     * @param file Il file da cui importare i contatti.
     * @param controller Il controller della vista della rubrica.
     * 
     * @pre file non deve essere null.
     * @pre controller non deve essere null.
     * @post L'oggetto ImportService è inizializzato con il file e il controller specificati.
     */
    public ImportService(File file, ContactsbookViewController controller) {
        this.file = file;
        this.contactsbookViewController = controller;
        this.contactbook = controller.getContactbook();
        this.database = new Database();
        this.progressBar = controller.getProgressBar(); // Ora non sarà null!
    }

    /**
     * Crea e restituisce il task per l'importazione dei contatti.
     * 
     * @return Il task che esegue l'importazione in background.
     * 
     * @pre file deve essere un file valido e accessibile.
     * @pre contactbook deve essere stato inizializzato.
     * @post I nuovi contatti vengono caricati nel Contactbook e inseriti nel database.
     */
    @Override
    protected Task<Void> createTask() {
        contactsbookViewController.getBtnImport().setDisable(true);
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                TableView<Contact> tblvContacts = contactsbookViewController.getTblvContacts();
                int primaSize = contactbook.getContacts().size();

                // Carica i contatti dal file
                contactbook.loadFromFile(file);
                int secondaSize = contactbook.getContacts().size();
                int total = secondaSize - primaSize;
                int count = 0;

                // Inserisce i nuovi contatti nel database con aggiornamento progressivo
                for (Contact contact : contactbook.getContacts().subList(primaSize, secondaSize)) {
                    try {
                        database.insertContact(Database.connection, "contatti", contact, Database.user.getEmail());

                        // Aggiorna l'ordinamento della tabella
                        TableColumn<Contact, ?> c = tblvContacts.getSortOrder().get(0);
                        tblvContacts.getSortOrder().clear();
                        tblvContacts.getSortOrder().add(c);
                    } catch (Exception ex) {
                        tblvContacts.getSortOrder().add(contactsbookViewController.getClmSur());
                    }

                    count++;
                    updateProgress(count, total);
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
        contactsbookViewController.getBtnImport().setDisable(false);
        AlertUtils.showInfo("Operation completed", "File import was successfully completed.");
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
        contactsbookViewController.getBtnImport().setDisable(false);
        AlertUtils.showError("Error", "An error occurred during the import. No contact has been imported.");
    }


}
