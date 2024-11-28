package com.mycompany.rubrica_progetto_ids.persone;

public class Contatto extends Persona{
    private String[] number;
    private String[] email;
    private Tag[] tag;
    
    public Contatto(String name, String surname) {
        super(name, surname);
        number = new String[3];
        email = new String[3];
        this.tag= new Tag[3];
    }
    
    public String[] getNumber(){
        return this.number;
    }

    public String[] getEmail() {
        return email;
    }

    public void addTag(Tag tag) {
        if(this.tag.length<3)
            this.tag[this.tag.length] = tag;
        //gestire l' elese
    }
    
    public void addNumber(String number) {
        if (this.number.length>=3){
            //gestire
        }
        
    }
    
    public void addEmail(String email){
        if (this.email.length>=3){
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
        for (int i=0; i<this.number.length;i++)
            sb=sb.append(" Numero di telefono: "+number[i]);
        for (int i=0; i<this.email.length;i++)
            sb=sb.append(" Email: "+email[i]);
        return sb.toString();
    }
    
}
