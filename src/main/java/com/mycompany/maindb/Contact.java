/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maindb;

import java.util.ArrayList;

/**
 *
 * @author cupom
 */
public class Contact extends Person{
    private ArrayList<String> number;
    private ArrayList<String> email;
    private ArrayList<Tag> tag;
    private static int contatore; // bisogna Salvare il numero di contatti da rivedere 
    private final int ID;
    
    public Contact(String name, String surname) {
        super(name, surname);
        number = new ArrayList<String>();
        email = new ArrayList<String>();
        this.tag= new ArrayList<Tag>();
        this.ID = contatore;
         contatore++;
        
    }
    public Contact(String name, String surname, int ID) {
        super(name, surname);
        number = new ArrayList<String>();
        email = new ArrayList<String>();
        this.tag= new ArrayList<Tag>();
        this.ID = ID;  
    }

    public void setNumber(ArrayList<String> number) {
        this.number = number;
    }

    public void setEmail(ArrayList<String> email) {
        this.email = email;
    }

    public void setTag(ArrayList<Tag> tag) {
        this.tag = tag;
    }
    
    public ArrayList<Tag> getTag() {
        return tag;
    }
    public ArrayList<String> getNumber(){
        return this.number ;
    }

    public ArrayList<String> getEmail() {
        return email;
    }

    public void addTag(Tag tag) {
        if(this.tag.size()<3)
            this.tag.set(this.tag.size(), tag);
        //gestire l' elese
        
    }

    public int getID() {
        return ID;
    }
    
    public void addNumber(String number) {
        
        if (this.number.size()>=3){
            //gestire
        }
        
    }
    
    public void addEmail(String email){
        if (this.email.size()>=3){
            //gestire
        }
    }
            
    @Override
    public String getRole() {
        return ""+this.getClass();
    }
    
    @Override
    public String toString(){
        StringBuffer sb=new StringBuffer(this.getRole()+" "+super.toString());
        for (int i=0; i<this.number.size();i++)
            sb=sb.append(" Phone number: ").append(number.get(i));
        for (int i=0; i<this.email.size();i++)
            sb=sb.append(" Email: ").append(email.get(i));
        return sb.toString();
    }
    
}