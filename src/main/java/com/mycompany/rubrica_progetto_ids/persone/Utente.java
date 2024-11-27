package com.mycompany.rubrica_progetto_ids.persone;

public class Utente extends Persona{
    private String password;
    private final String email;

    public Utente(String password, String email, String name, String surname) {
        super(name, surname);
        this.password = password;
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    
    @Override
    public String getRole() {
        return this.getClass().toString();
    }
    
    @Override
    public String toString(){
        return this.getRole()+" "+super.toString()+" Email: "+email;
    }
}
