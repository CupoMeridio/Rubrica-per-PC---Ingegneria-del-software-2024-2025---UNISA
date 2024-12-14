package it.unisa.diem.team02.contactsbook.model;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * @brief Questa classe definisce il comportamento del filtro con cui vengono filtrati i contatti
 * della rubrica.
 * 
 * @author team02
 */
public class Filter {
    
    private FilteredList<Contact> flContacts;
    private String matchString;
    private boolean isSelectedHome;
    private boolean isSelectedUni;
    private boolean isSelectedJob;
    
    
    /**
     * @brief Crea un oggetto FilteredList a partire da lista di osservabili
     * passata come parametro.
     * 
     * Questo costruttore inizializza la lista filtrata a partire da una lista di osservabili passata come 
     * parametro. Vengono anche inizializzati gli attributi di tipo String matchString e gli attributi di 
     * tipo boolean isSelectedHome, isSelectedUni, isSelectedJob.
     * 
     * @param list Lista osservabile contenenete tutti i contatti della rubrica.
     *
     * @pre La lista di osservabili passata come parametro non deve essere un riferimento a null
     * @post 
     * - La lista filtrata contiene tutti gli elementi della lista osservabile passata come riferimento.
     * - isSelectedHome è settato su false;
     * - isSelectedUni è settato su false;
     * - isSelectedJob è settato su false;
     * 
     */
    public Filter(ObservableList<Contact> list){
        flContacts = new FilteredList<>(list, c->true);   
        isSelectedHome=false;
        isSelectedUni=false;
        isSelectedJob=false;
        matchString="";
    }
    
    /**
    * @brief Metodo getter che restituisce il nome della stringa attualmente presente nella barra di ricerca.
    *
    * @return Una stringa contenente il valore della stringa presente nella barra di ricerca. Restituisce
    * la stringa vuota "" se non è stato scritto nulla nella barra di ricerca. 
    */
    public String getMatchString(){
        return this.matchString;
    }
    
    /**
    * @brief Restituisce un valore di verità che indica se il tag Home checkmenuitem è stato selezionato dal menu
    * button Filter
    *
    * @return Restituisce false se il tag Home non è stato selezionato, true altrimenti
    */
    public boolean getIsSelectedHome(){
        return this.isSelectedHome;
    }
    
    
    /**
    * @brief Restituisce un valore di verità che indica se il tag University checkmenuitem è stato selezionato dal menu
    * button Filter
    *
    * @return Restituisce false se il tag University non è stato selezionato, true altrimenti
    */
    public boolean getIsSelectedUniversity(){
        return this.isSelectedUni;
    }
    
    
    /**
    * @brief Restituisce un valore di verità che indica se il tag Job checkmenuitem è stato selezionato dal menu
    * button Filter
    *
    * @return Restituisce false se il tag Job non è stato selezionato, true altrimenti
    */
    public boolean getIsSelectedJob(){
        return this.isSelectedJob;
    }
    
    
    /**
     * 
     * @brief Questo metodo restituisce il riferimento alla lista filtrata.
     * 
     * @return Il riferimento alla lista filtrata.
     * 
     * @pre La lista flContacts deve essere stata precedentemente inizializzata.
     * @post La lista ottenuta è quella filtrata.
     * 
     * 
    */
    
    public FilteredList<Contact> getFlContacts() {
        return flContacts;
    }
    
    /**
    * @brief Aggiorna il filtro dei contatti in base ai criteri di ricerca e selezione dei tag.
    *
    * Questo metodo applica un filtro alla lista di contatti `flContacts` in base al testo di ricerca inserito
    * nel paramento string e ai valori boolean h, u, e j, che indicano se all'interno del menu button è stato selezionato
    * il bottone Home (h), University (u) oppure Job (j).
    * Se il testo di ricerca è vuoto e sia h, u e h sono false viene visualizzata tutta la rubrica. Altrimenti il filtro
    * cercherà i contatti che contengono la stringa nei loro campi (`name`, `surname`, `number` ed `email`) o i contatti 
    * per cui si è selezionato un certo tag.
    * 
    * @param string è la sottostringa con cui si deve effettuare la ricerca tra i contatti.
    * @param h è settato su false se la check box di Home non è selezionata, altrimenti è settata su true.
    * @param u è settato su false se la check box di University non è selezionata, altrimenti è settata su true.
    * @param j è settato su false se la check box di Job non è selezionata, altrimenti è settata su true.
    * 
    * @pre La stringa passata non deve essere un riferimento a null. I valori di h, u e j devono essere coerenti con 
    *      il fatto che la check box sia stata selezionata o meno.
    * @post La lista `flContacts` è filtrata in base al testo di ricerca e alle selezioni dei tag, aggiornando
    *       i contatti visibili nella lista.
    * @invariant I contatti che soddisfano i criteri di ricerca e tag selezionati saranno visibili nella lista.
    */
    
    public void updateFilter(String string, boolean h, boolean u, boolean j){
            this.matchString=string;
            this.isSelectedHome=h;
            this.isSelectedUni=u;
            this.isSelectedJob=j;
        
        
            flContacts.setPredicate(contact -> {
                
            String lowerCaseFilter = matchString.toLowerCase();
            boolean match = lowerCaseFilter.isEmpty() ||  
                    contact.getName().toLowerCase().contains(lowerCaseFilter) ||
                    contact.getSurname().toLowerCase().contains(lowerCaseFilter) ||
                    contact.getNumber().toLowerCase().contains(lowerCaseFilter) ||
                    contact.getEmail().toLowerCase().contains(lowerCaseFilter);
            
            
            boolean noTag = !isSelectedHome && !isSelectedUni && !isSelectedJob;
            
            
            boolean home = isSelectedHome && contact.getTag().toLowerCase().contains("home");
            boolean uni = isSelectedUni && contact.getTag().toLowerCase().contains("university");
            boolean job = isSelectedJob && contact.getTag().toLowerCase().contains("job");

           
            return match && (noTag || home || uni || job);
        });
    
    
    }
    
}
