package com.mycompany.rubrica_progetto_ids.persone;

public abstract class Persona {
    private String name;
    private String surname;

    public Persona(String name, String surname){
        this.name=name;
        this.surname=surname;
    }
    
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    
    
    @Override
    public String toString(){
        return "Nome: "+name+" Cognome: "+surname+"\n";
    }
    
    public abstract String getRole();
}
