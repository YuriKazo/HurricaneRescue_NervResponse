package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class Volunteer extends Account{
    private ArrayList<Abilities> abilities;
    private ReliefRequest currentTarget;

    public Volunteer(String firstName, String email, String lastName, String passWord){
        super(firstName, email, lastName, passWord, AccountType.VOLUNTEER);
        abilities = new ArrayList<Abilities>();
        currentTarget = null;
    }

    public Volunteer(UUID id, String firstName, String email, String lastName, String passWord, ArrayList<Location> savedLocations, ArrayList<UUID> savedAccounts, ArrayList<String> emergencyContacts, Location currentLocation, ArrayList<Abilities> abilities, UUID victimListId, UUID currentTargetId){
        super(id, firstName, email, lastName, passWord, AccountType.VOLUNTEER, savedLocations, savedAccounts, emergencyContacts, currentLocation);
        this.abilities = abilities;
        this.currentTarget = ReliefRequestList.getInstance().getReliefRequest(currentTargetId);
    }

    public ReliefRequest getNearestRequest(){
        return null;
    }

    public void markComplete(){

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
