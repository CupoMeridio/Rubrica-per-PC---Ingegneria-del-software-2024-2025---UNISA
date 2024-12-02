
package com.mycompany.maindb;

public class User extends Person{
    private String password;
    private final String email;

    public User(String password, String email, String name, String surname) {
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

