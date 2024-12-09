/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maindb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;


/**
 *
 * @author Mattia Sanzari
 */
public class DbFunctions {   
    public Connection ConnectionDB(String dbname, String user, String password) {
        Connection conn=null;
        
       
        try {
            Class.forName("org.postgresql.Driver");
            System.out.print("Driver trovato ");
            //conn= DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname,user,password);
            conn=DriverManager.getConnection("jdbc:postgresql://rubrica-mattiasanzari2003-19e7.k.aivencloud.com:14305/"+dbname+"?ssl=require&user="+user+"&password="+password);
            if(conn!=null){
                System.out.println("Connessione Stabilita");
            }else{
                System.out.println("Connessione Fallita");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbFunctions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DbFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
           return conn;
    }
    
    public void insertUtente(Connection conn, String tableName, String email, String password) throws SQLException{
    
           Statement statment;
           String pass= hashPassword(password);
           String query= String.format("insert into %s(email,password) values('%s','%s');",tableName, email, pass);
           statment= conn.createStatement();
           statment.executeUpdate(query);
           System.out.println("Utente inserito");
        
    }
    
    public HashMap<String, String> getUtenti(Connection conn, String tableName){
        
        Statement statement;
        ResultSet rs= null;
        Map <String,String>table=null;
        String email;
        String password;
        try {
            table =  new HashMap();
            String query= String.format("select * from %s", tableName);
            statement= conn.createStatement();
            rs= statement.executeQuery(query);
            while(rs.next()){
                email=rs.getString("email");
                password=rs.getString("password");
                table.put(email, password);
                
               /* System.out.print(email+" ");
                System.out.print(password+" ");*/
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return (HashMap<String, String>) table;
    }
    
    public int checkLogin(Connection conn,String tableName, String email, String password) throws SQLException{
    
        
    ResultSet rs= null;
    int esito;  
            Statement statment;
            String query= String.format("select * from %s where email='%s'", tableName,email);
            statment= conn.createStatement();
            rs=statment.executeQuery(query);
         
            if (!rs.isBeforeFirst()) {
                System.out.println("No data found."); 
                esito= -1;// -1 perchè email non è presente nel db
            }else{
                do{
                    rs.next();// Da rivedere funziona solo perchè c'è un colo valore
                    String em=rs.getString("email");
                    String hashed = rs.getString("password");
                    System.out.print("\n"+email+"   checkLogin");
                   

                    if( checkPassword( password,hashed)){
                         System.out.print(" \n password Giusta ");
                         esito=1; // email e password sono corette e l' utente entra
                    }else{
                       System.out.print(" \n password Sbagliata ");
                       esito= 0;// email corretta e password sbagliata
                    }
                }while(rs.next());
           }       
        return esito;
    }
    
    private static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
        //per renderlo più sicuro conviene aggiungere un salt
    }

    private static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
    
    public HashMap<String, Contact> getContact(Connection conn, String tableName,String email){
        
        Statement statement;
        ResultSet rs= null;
        Map <String,Contact>table=null;
        try {
            table =  new HashMap();
            String query= String.format("select * from %s where email='%s'", tableName,email);
            statement= conn.createStatement();
            rs= statement.executeQuery(query);
            while(rs.next()){
            //  String em = rs.getString("email");
                String name = rs.getString("name");
                String surname= rs.getString("surname");
                String numeri = rs.getString("number");
                String tag = rs.getString("tag");
                String em_cont = rs.getString("email_contact");
                String ID = rs.getString("id");
                table.put(ID, createContact(name,surname,numeri,tag,em_cont,ID));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return (HashMap<String, Contact>) table;
    }

    private Contact createContact(String name, String surname, String numeri, String tag, String em_cont, String ID) {
        Contact c= new Contact(name,surname, Integer.valueOf(ID));
        ArrayList<String> n = new ArrayList<>();
        ArrayList<String> e = new ArrayList<>();
        ArrayList<Tag> t = new ArrayList<>();
        
        
            Scanner i = new Scanner(tag);
            i.useDelimiter(";");
             while(i.hasNext()){
                 t.add( Tag.valueOf(i.next()));
            }
             System.out.print(t+" \n");
             c.setTag(t);
             
            i = new Scanner(numeri);
            i.useDelimiter(";");
            while(i.hasNext()){
                 n.add( i.next());
            }
             System.out.print(n+" \n");
             c.setNumber(n);
            
            i = new Scanner(em_cont);
            i.useDelimiter(";");
            while(i.hasNext()){
                 e.add( i.next());
            }
            System.out.print(e+" \n");
             c.setEmail(e);
        
        return c;
    }
    
    public void insertContatto(Connection conn, String tableName, Contact cont, String email_Utente) throws SQLException{
    
           Statement statment;
           String nm=cont.getName();
           String srn=cont.getSurname();
           String ID= String.valueOf(cont.getID());
           
           ArrayList<String> e = cont.getEmailList();
           ArrayList<String> n = cont.getNumberList();
           ArrayList<Tag> t =cont.getTagList();
           
            /*formatto le email*/
           String email= formattaOut(e);
           
            /*formatto i numeri*/
           String number= formattaOut(n);
           
           /*formatto i tag*/
           ArrayList<String> St= new ArrayList<>();
           for (Tag i : t) { 
               St.add(i.name());
           }
           String tag= formattaOut(St);
           
           
           String query;
        query = String.format("insert into %s(email,name,surname,number,tag,email_contact,id) values('%s','%s','%s','%s','%s','%s','%s');",tableName,  email_Utente,nm,srn,number,tag,email,ID);
           statment= conn.createStatement();
           statment.executeUpdate(query);
           System.out.println("Contatto inserito");
        
    }

    private String formattaOut(ArrayList<String> s) {
        
        String formattata="";
        for(String i: s){
          
            formattata=formattata+i+";";
        }
        return formattata;
    }
    
    public void modifyContact(Connection conn, String tableName, Contact cont, String email_Utente) throws SQLException{
        
         Statement statment;
           String nm=cont.getName();
           String srn=cont.getSurname();
           String ID= String.valueOf(cont.getID());
           
           ArrayList<String> e = cont.getEmailList();
           ArrayList<String> n = cont.getNumberList();
           ArrayList<Tag> t =cont.getTagList();
        
             /*formatto le email*/
           String email= formattaOut(e);
           
            /*formatto i numeri*/
           String number= formattaOut(n);
           
           /*formatto i tag*/
           ArrayList<String> St= new ArrayList<>();
           for (Tag i : t) { 
               St.add(i.name());
           }
           String tag= formattaOut(St);
           
           
      String query = String.format("UPDATE into %s(email,name,surname,number,tag,email_contact,id) values('%s','%s','%s','%s','%s','%s','%s'); WHERE email='%s'",tableName,  email_Utente,nm,srn,number,tag,email,ID,email_Utente);
      
      statment= conn.createStatement();
      statment.executeUpdate(query);
      System.out.println("Contatto modificato");
    }
    
       public void remuveContactByID(Connection conn, String tableName, String ID, String email) throws SQLException{
    
       Statement statment;
       
       String query= String.format("delete from %s where ID='%s' AND email='%s'",tableName,ID,email);
       statment= conn.createStatement();
       statment.execute(query);
       System.out.print("\n Dato eliminato per ID");
    }
    /**
    * 
    * @brief Chiude la connessione col database
    * @param conn Oggetto Connection per interagire con il database.
    * 
    * @throws SQLException Se si verifica un errore durante l'interrogazione.
    */

    
    public void CloseConnection(Connection conn) throws SQLException{
        conn.close();
        System.out.print("Chiusura connessione");
    }
    
    public int getNumberContact(Connection conn, String tableName){
        
        int numero_righe=0;
        try {
            Statement statement = conn.createStatement();
            String query = String.format("SELECT COUNT(*) AS rowcount FROM %s", tableName);//conta il numero di righe nella tabella nome_tabella e assegna il risultato alla colonna rowcount
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) { 
             numero_righe = rs.getInt("rowcount"); 
            System.out.println("Numero di righe: " + numero_righe); 
        }
        } catch (SQLException ex) {
            Logger.getLogger(DbFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }   
         
        return numero_righe;
    }
}

