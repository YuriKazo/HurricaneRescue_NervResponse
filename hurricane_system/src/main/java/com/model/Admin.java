package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class Admin extends Account{
    private ArrayList<Abilities> abilities;
    private ReliefRequest currentTarget;

    public Admin(String firstName, String email, String lastName, String passWord){
        super(firstName, email, lastName, passWord);
        abilities = new ArrayList<Abilities>();
        currentTarget = null;
    }

    public Admin(UUID id, String firstName, String email, String lastName, String passWord, ArrayList<Abilities> abilities){
        super(id, firstName, email, lastName, passWord);
        this.abilities = abilities;
    }

    public ReliefRequest getNearestRequest(){
        return null;
    }

    public void markComplete(){

    }

    public void makeRequest(String description){

    }

    public boolean addAbilities(Abilities ability){
        return true;
    }

    public String getContact(Account account){
        return account.getEmail(); 
    }

    public ArrayList<Abilities> getAbilities() {
        return abilities;
    }

    public ReliefRequest getCurrentTarget() {
        return currentTarget;
    }

    public void setAbilities(ArrayList<Abilities> abilities) {
        this.abilities = abilities;
    }

    public void setCurrentTarget(ReliefRequest currentTarget) {
        this.currentTarget = currentTarget;
    }
    
}
