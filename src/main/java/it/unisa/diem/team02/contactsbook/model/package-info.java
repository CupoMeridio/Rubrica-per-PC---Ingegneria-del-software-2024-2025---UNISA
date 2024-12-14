/**
 * @package it.unisa.diem.team02.contactsbook.model
 * 
 * Descrizione del package:
 * Questo package contiene le classi modello utilizzate per rappresentare i dati nel sistema di gestione 
 * della rubrica (ContactsBook). Le classi in questo package definiscono le entità fondamentali per la gestione 
 * dei contatti, come le informazioni personali, i tag associati ai contatti e gli utenti che utilizzano il sistema.
 * 
 * Classi principali:
 * - Contact: Rappresenta un singolo contatto nella rubrica, contenente informazioni come nome, cognome, 
 *   numero di telefono, email e altri dettagli.
 * - Contactbook: Rappresenta la rubrica dei contatti ed è implementata con una lista osservabil di oggetti 
 *   di tipo Contact. Implementa metodi per aggiungere o rimuovere un contatto e per verificare se un contatto
 *   è già presente nella rubrica.
 * - Filter: Rappresenta il filtro con cui vengono selezionati i contatti della rubrica che soddisfanno determinati
 *   criteri: se sono associati a un certo tag oppure se uno dei campi coincide con una data sottostringa.
 * - Person: Contiene informazioni anagrafiche di una persona, come nome, cognome e data di nascita. 
 *   Viene utilizzato come base per la classe `Contact`.
 * - Tag: Rappresenta un tag che può essere associato a un contatto per facilitarne la categorizzazione 
 *   e la ricerca.
 * - User: Rappresenta un utente del sistema ContactsBook, contenente informazioni come nome utente, 
 *   password e dati di accesso.
 * 
 * Queste classi sono utilizzate per organizzare i dati e fornire una struttura per la gestione dei contatti 
 * e degli utenti nell'applicazione. La loro interazione permette di creare, modificare e cercare contatti 
 * in modo efficiente e organizzato.
 * 
 * Dettagli di implementazione:
 * - La classe `Contact` estende `Person` per includere dettagli specifici del contatto come numeri di telefono, 
 *   email, indirizzo e altre informazioni.
 * - I `Tag` vengono associati ai `Contact` per permettere una classificazione dei contatti in categorie (ad esempio, 
 *   famiglia, lavoro, amici, ecc.).
 * - La classe `User` gestisce l'autenticazione e i dati dell'utente, consentendo l'accesso e la gestione personalizzata 
 *   della rubrica.
 */
package it.unisa.diem.team02.contactsbook.model;
