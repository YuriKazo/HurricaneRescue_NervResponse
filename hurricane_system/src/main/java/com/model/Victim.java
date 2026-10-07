package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class Victim extends Account{
    private boolean pets;
    private ArrayList<Disabilities> disabilities;
    private Volunteer assignedRescuer;

    public Victim(String firstName, String email, String lastName, String passWord){
        super(firstName, email, lastName, passWord, AccountType.VICTIM);
        type = AccountType.VICTIM;
        pets = false;
        disabilities = new ArrayList<Disabilities>();
        assignedRescuer = null;
    }

    public Victim(UUID id, String firstName, String email, String lastName, String passWord, boolean pets, ArrayList<Disabilities> disabilities){
        super(id, firstName, email, lastName, passWord, AccountType.VICTIM);
        this.type = AccountType.VICTIM;
        this.pets = pets;
        this.disabilities = disabilities;
        this.assignedRescuer = null;
    }
    
    public void registerDisabilities(){

    }
    
    public void registerPets(){

    }

    public void markComplete(){

    }
    
    public void setPets(boolean pets) {
        this.pets = pets;
    }

    public void setDisabilities(ArrayList<Disabilities> disabilities) {
        this.disabilities = disabilities;
    }

    public void setAssignedRescuer(Volunteer assignedRescuer) {
        this.assignedRescuer = assignedRescuer;
    }

    public boolean hasPets() {
        return pets;
    }

    public ArrayList<Disabilities> getDisabilities() {
        return disabilities;
    }

    public Volunteer getAssignedRescuer() {
        return assignedRescuer;
    }

}
