/**
 * Package che contiene i servizi di importazione ed esportazione dei contatti.
 * 
 * Questo pacchetto include i servizi necessari per gestire l'importazione e l'esportazione
 * dei contatti da e verso file. I servizi sono implementati come classi che estendono
 * e vengono eseguiti su un thread separato per garantire che l'interfaccia utente
 * rimanga reattiva durante l'esecuzione delle operazioni di importazione ed esportazione.
 * 
 * - {@link ExportService}: Gestisce l'esportazione dei contatti in un file (ad esempio in formato CSV).
 * - {@link ImportService}: Gestisce l'importazione dei contatti da un file.
 * 
 * Entrambi i servizi utilizzano una barra di progresso per monitorare lo stato dell'operazione
 * e forniscono messaggi di feedback all'utente tramite finestre di dialogo (alert).
 */
package it.unisa.diem.team02.services;
