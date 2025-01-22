package it.unisa.diem.team02;
/**
 * La classe {@code RunMain} funge da punto di ingresso principale per l'applicazione.
 * 
 * Questa classe è stata creata esclusivamente per far funzionare correttamente il file JAR
 * del progetto JavaFX. Senza di essa, le dipendenze dell'applicazione non venivano riconosciute
 * correttamente.
 * 
 * Il metodo {@link #main(String[])} invoca il metodo {@link App#main(String[])} per avviare
 * l'applicazione JavaFX, gestendo correttamente le dipendenze necessarie al funzionamento
 * dell'applicazione.
 * 
 * @see App
 * @see #main(String[])
 */
public class RunMain {

    /**
     * Punto di ingresso principale per l'applicazione.
     * 
     * Questo metodo è necessario per permettere l'esecuzione dell'applicazione in un file JAR
     * e per far sì che le dipendenze vengano riconosciute correttamente durante l'esecuzione.
     * 
     * @param args gli argomenti della riga di comando
     * @see App#main(String[])
     */
    public static void main(String[] args) {
        App.main(args);
    }
}