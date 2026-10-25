package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class Volunteer extends Account{
    private ArrayList<Abilities> abilities;
    private ReliefRequest currentTarget;

    public Volunteer(String firstName, String email, String lastName, String passWord, String phoneNumber){
        super(firstName, email, lastName, passWord, phoneNumber, AccountType.VOLUNTEER);
        abilities = new ArrayList<Abilities>();
        currentTarget = null;
    }

    public Volunteer(UUID id, String firstName, String email, String lastName, String passWord, String phoneNumber, ArrayList<Location> savedLocations, ArrayList<UUID> savedAccounts, ArrayList<String> emergencyContacts, Location currentLocation, ArrayList<Abilities> abilities, UUID victimListId, UUID currentTargetId){
        super(id, firstName, email, lastName, passWord, phoneNumber, AccountType.VOLUNTEER, savedLocations, savedAccounts, emergencyContacts, currentLocation);
        this.abilities = abilities;
        this.currentTarget = currentTargetId != null ? ReliefRequestList.getInstance().getReliefRequest(currentTargetId) : null;
    }

    public ReliefRequest getNearestRequest(){
        return null;
    }

    public void markComplete() {
        if (currentTarget != null) {
            currentTarget.markComplete();
            currentTarget = null;
        }
    }

    // @param ability the ability to add
    // @return true if added, false if already present or null
    public boolean addAbilities(Abilities ability) {
        if (ability == null || abilities.contains(ability)) {
            return false;
        }
        abilities.add(ability);
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