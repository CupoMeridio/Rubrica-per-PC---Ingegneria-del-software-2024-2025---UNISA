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
import java.util.HashMap;
import java.util.Map;
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
            conn= DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname,user,password);
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
    
     public HashMap<String, Contact> getContatti(Connection conn, String tableName,String email){
        
        Statement statement;
        ResultSet rs= null;
        Map <String,Contact>table=null;
        try {
            table =  new HashMap();
            String query= String.format("select * from %s where email='%s'", tableName,email);
            statement= conn.createStatement();
            rs= statement.executeQuery(query);
            while(rs.next()){
                String name = rs.getString("name");
                String surname= rs.getString("surname");
                String em = rs.getString("email");
                String numeri = rs.getString("number");
                String tag = rs.getString("tag");
                String em_cont = rs.getString("email_contact");
                table.put(em, createContact(name,surname,numeri,tag,em_cont));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return (HashMap<String, Contact>) table;
    }

    private Contact createContact(String name, String surname, String numeri, String tag, String em_cont) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


 
}
