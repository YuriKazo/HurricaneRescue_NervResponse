package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A volunteer account. Keeps track of the volunteer's abilities and the relief
 * request they are currently working on.
 * @author NervResponse
 */
public class Volunteer extends Account{
    private ArrayList<Abilities> abilities;
    private ReliefRequest currentTarget;

    /**
     * Creates a new volunteer with no abilities and no current request
     * @param firstName The first name of the volunteer
     * @param email The email of the volunteer
     * @param lastName The last name of the volunteer
     * @param passWord The password of the volunteer
     * @param phoneNumber The phone number of the volunteer
     */
    public Volunteer(String firstName, String email, String lastName, String passWord, String phoneNumber){
        super(firstName, email, lastName, passWord, phoneNumber, AccountType.VOLUNTEER);
        abilities = new ArrayList<Abilities>();
        currentTarget = null;
    }

    /**
     * Creates a volunteer from existing data, such as when loading from file
     * @param id The id of the volunteer
     * @param firstName The first name of the volunteer
     * @param email The email of the volunteer
     * @param lastName The last name of the volunteer
     * @param passWord The password of the volunteer
     * @param phoneNumber The phone number of the volunteer
     * @param savedLocations The volunteer's saved locations
     * @param savedAccounts The ids of the volunteer's saved accounts
     * @param emergencyContacts The volunteer's emergency contacts
     * @param currentLocation The volunteer's current location
     * @param abilities The volunteer's abilities
     * @param victimListId The id of the volunteer's victim list
     * @param currentTargetId The id of the request the volunteer is working on, or null for none
     */
    public Volunteer(UUID id, String firstName, String email, String lastName, String passWord, String phoneNumber, ArrayList<Location> savedLocations, ArrayList<UUID> savedAccounts, ArrayList<String> emergencyContacts, Location currentLocation, ArrayList<Abilities> abilities, UUID victimListId, UUID currentTargetId){
        super(id, firstName, email, lastName, passWord, phoneNumber, AccountType.VOLUNTEER, savedLocations, savedAccounts, emergencyContacts, currentLocation);
        this.abilities = abilities;
        this.currentTarget = currentTargetId != null ? ReliefRequestList.getInstance().getReliefRequest(currentTargetId) : null;
    }

    /**
     * Gets the nearest relief request to the volunteer
     * @return The nearest request
     */
    public ReliefRequest getNearestRequest(){
        return null;
    }

    /**
     * Marks the volunteer's current request complete and clears it.
     * Does nothing if the volunteer has no current request.
     */
    public void markComplete() {
        if (currentTarget != null) {
            currentTarget.markComplete();
            currentTarget = null;
        }
    }

    /**
     * Adds an ability to the volunteer
     * @param ability the ability to add
     * @return true if added, false if already present or null
     */
    public boolean addAbilities(Abilities ability) {
        if (ability == null || abilities.contains(ability)) {
            return false;
        }
        abilities.add(ability);
         return true;
    }

    /**
     * Gets the contact information of an account
     * @param account The account whose contact information is wanted
     * @return The account's email
     */
    public String getContact(Account account){
        return account.getEmail(); 
    }

    /**
     * Gets the volunteer's abilities
     * @return The list of abilities
     */
    public ArrayList<Abilities> getAbilities() {
        return abilities;
    }

    /**
     * Gets the request the volunteer is currently working on
     * @return The current request, or null if there is none
     */
    public ReliefRequest getCurrentTarget() {
        return currentTarget;
    }

    /**
     * Sets the volunteer's abilities
     * @param abilities The new list of abilities
     */
    public void setAbilities(ArrayList<Abilities> abilities) {
        this.abilities = abilities;
    }

    /**
     * Sets the request the volunteer is currently working on
     * @param currentTarget The new current request
     */
    public void setCurrentTarget(ReliefRequest currentTarget) {
        this.currentTarget = currentTarget;
    }
}