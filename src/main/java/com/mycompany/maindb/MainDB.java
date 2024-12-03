/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.maindb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author angel
 */
public class MainDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        /*<?php
            $host='localhost';
            $port = '5432';
            $db= 'esercizio2';
            $username= 'www';
            $password= 'pwww';
            //echo "Prima connessione";
            $connection_string = "host=$host port=$port dbname=$db user=$username password=$password";
*/
        
        Connection conn;
        DbFunctions db= new DbFunctions();
        conn=db.ConnectionDB("rubrica", "postgres", "postgres");
        
        String email ="mattia@gmail.com";
        String password="password";
        Contact cont= new Contact("Vittorio","Postiglione",2);
        ArrayList<String> number = new ArrayList<String>();
        ArrayList<String> em= new ArrayList<String>();
        
         ArrayList<Tag> tag= new ArrayList<>();
        
         tag.add(Tag.Home);
         tag.add(Tag.University);
         
        em.add("postiglione@gmail.com");
        em.add("Vittorio2003@libero.it");
        em.add("cupomabello03@gmail.com");
        
        number.add("0815095344");
        number.add("3279006099");
        number.add("391 176 6022");
        cont.setTag(tag);
        cont.setEmail(em);
        cont.setNumber(number);
        /*try {
            db.insertUtente(conn, "utenti", email, password);
        } catch (SQLException ex) {
            Logger.getLogger(MainDB.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        /*
        HashMap<String, String> table =db.getUtenti(conn, "utenti");
        try {
            System.out.print("\nEsiste ? : " +db.checkLogin(conn,"utenti", email, password)+"\n");
            
            //System.out.print("\ntabella : "+ table);
        } catch (SQLException ex) {
            Logger.getLogger(MainDB.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        /* insertContatto-> funziona
        try {
            db.insertContatto(conn,"contatti", cont, email);
         
        } catch (SQLException ex) {
            Logger.getLogger(MainDB.class.getName()).log(Level.SEVERE, null, ex);
        }*/
           
        /*prova get contatti -> funziona */
        HashMap<String, Contact> m = db.getContatti(conn, "contatti", email);
        
        System.out.print("\n Inizio: \n "+m+"\n Fine");
            /*prova Contact createContact ->funziona
            
            String name= "Mattia";
            String Surname="Sanzari";
            String numeri="01;02;03";
            String tag="Home;Job";
            String em_cont="mattia2003@gmail.com;matlibero.com";
            
            db.createContact( name, Surname, numeri,  tag,  em_cont);*/
            
            
            /*prova formattaOut -> funziona
            ArrayList<String> s= new ArrayList<>();
            s.add("prova1");
            s.add("prova2");
            System.out.print( db.formattaOut(s)); */
    }
}
