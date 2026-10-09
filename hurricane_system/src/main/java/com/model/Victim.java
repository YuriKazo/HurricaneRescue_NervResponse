package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class Victim extends Account{
    private boolean pets;
    private ArrayList<Disabilities> disabilities;
    private Volunteer assignedRescuer;

    public Victim(String firstName, String email, String lastName, String passWord){
        super(firstName, email, lastName, passWord, AccountType.VICTIM);
        pets = false;
        disabilities = new ArrayList<Disabilities>();
        assignedRescuer = null;
    }

    public Victim(UUID id, String firstName, String email, String lastName, String passWord, ArrayList<Location> savedLocations, ArrayList<UUID> savedAccounts, ArrayList<String> emergencyContacts, Location currentLocations, boolean pets, ArrayList<Disabilities> disabilities, Account assignedRescuer){
        super(id, firstName, email, lastName, passWord, AccountType.VICTIM, savedLocations, savedAccounts, emergencyContacts, currentLocations);
        this.pets = pets;
        this.disabilities = disabilities;
        this.assignedRescuer = null;
    }

    public void markComplete(){

    }
    
    public void setPets(boolean pets) {
        this.pets = pets;
    }

    public void setDisabilities(Disabilities disabilities) {
        this.disabilities.add(disabilities);
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
