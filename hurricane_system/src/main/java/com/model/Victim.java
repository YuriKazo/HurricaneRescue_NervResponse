package com.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

/**
 * A hurricane victim account. Keeps track of pets, disabilities, and the volunteer
 * assigned to rescue them.
 * @author NervResponse
 */
public class Victim extends Account{
    private boolean pets;
    private ArrayList<Disabilities> disabilities;
    private Volunteer assignedRescuer;

    /**
     * Creates a new victim with no pets, no disabilities, and no assigned rescuer
     * @param firstName The first name of the victim
     * @param email The email of the victim
     * @param lastName The last name of the victim
     * @param passWord The password of the victim
     * @param phoneNumber The phone number of the victim
     */
    public Victim(String firstName, String email, String lastName, String passWord, String phoneNumber){
        super(firstName, email, lastName, passWord, phoneNumber, AccountType.VICTIM);
        pets = false;
        disabilities = new ArrayList<Disabilities>();
        assignedRescuer = null;
    }

    /**
     * Creates a victim from existing data, such as when loading from file
     * @param id The id of the victim
     * @param firstName The first name of the victim
     * @param email The email of the victim
     * @param lastName The last name of the victim
     * @param passWord The password of the victim
     * @param phoneNumber The phone number of the victim
     * @param pets Whether the victim has pets
     * @param disabilities The victim's disabilities
     */
    public Victim(UUID id, String firstName, String email, String lastName, String passWord, String phoneNumber, boolean pets, ArrayList<Disabilities> disabilities){
        super(id, firstName, email, lastName, passWord, phoneNumber, AccountType.VICTIM,
                new ArrayList<Location>(), new ArrayList<UUID>(), new ArrayList<String>(), new Location(0.0, 0.0, "00000"));
        this.pets = pets;
        this.disabilities = new ArrayList<Disabilities>();
        this.assignedRescuer = null;

        if (disabilities != null) {
            for (Object entry : disabilities) {
                if (entry instanceof Disabilities) {
                    this.disabilities.add((Disabilities) entry);
                } else if (entry != null) {
                    this.disabilities.add(Disabilities.valueOf(entry.toString()));
                }
            }
        }
    }

    /**
     * Creates a victim from existing data, including saved locations, contacts, current
     * location, and assigned rescuer
     * @param id The id of the victim
     * @param firstName The first name of the victim
     * @param email The email of the victim
     * @param lastName The last name of the victim
     * @param passWord The password of the victim
     * @param phoneNumber The phone number of the victim
     * @param savedLocations The victim's saved locations
     * @param savedAccounts The ids of the victim's saved accounts
     * @param emergencyContacts The victim's emergency contacts
     * @param currentLocations The victim's current location
     * @param pets Whether the victim has pets
     * @param disabilities The victim's disabilities
     * @param assignedRescuer The volunteer assigned to the victim, ignored if not a Volunteer
     */
    public Victim(UUID id, String firstName, String email, String lastName, String passWord, String phoneNumber, ArrayList<Location> savedLocations, ArrayList<UUID> savedAccounts, ArrayList<String> emergencyContacts, Location currentLocations, boolean pets, ArrayList<Disabilities> disabilities, Account assignedRescuer){
        this(id, firstName, email, lastName, passWord, phoneNumber, pets, disabilities);

        if (savedLocations != null) this.savedLocations = savedLocations;
        if (savedAccounts != null) this.savedAccounts = savedAccounts;
        if (emergencyContacts != null) this.emergencyContact = emergencyContacts;
        if (currentLocations != null) this.currentLocation = currentLocations;

        if (assignedRescuer instanceof Volunteer) {
            this.assignedRescuer = (Volunteer) assignedRescuer;
        }
    }

    // asks on the yser which disabilities apply, "BLIND, DEAF" and blank for none
    public void registerDisabilities(){
        System.out.println("Enter your disabilities separated by commas " + Arrays.toString(Disabilities.values())
                + ", or press enter for none:");

        Scanner input = new Scanner(System.in);
        if (!input.hasNextLine()) return;

        String line = input.nextLine().trim();
        if (line.isEmpty()) return;

        for (String entry : line.split(",")) {
            try {
                setDisabilities(Disabilities.valueOf(entry.trim().toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("Skipping unknown disability: " + entry.trim());
            }
        }
    }

    /**
     * Marks that the victim has pets
     */
    public void registerPets(){
        setPets(true);
    }

    /**
     * Tells the assigned rescuer to mark their request complete and clears the rescuer.
     * Does nothing if there is no assigned rescuer.
     */
    public void markComplete(){
        if (assignedRescuer == null) return;

        assignedRescuer.markComplete();
        assignedRescuer = null;
    }

    /**
     * Sets whether the victim has pets
     * @param pets true if the victim has pets
     */
    public void setPets(boolean pets) {
        this.pets = pets;
    }

    /**
     * Adds a disability to the victim. Null or duplicate disabilities are ignored.
     * @param disabilities The disability to add
     */
    public void setDisabilities(Disabilities disabilities) {
        if (disabilities == null || this.disabilities.contains(disabilities)) return;

        this.disabilities.add(disabilities);
    }

    /**
     * Sets the volunteer assigned to rescue the victim
     * @param assignedRescuer The assigned volunteer
     */
    public void setAssignedRescuer(Volunteer assignedRescuer) {
        this.assignedRescuer = assignedRescuer;
    }

    /**
     * Checks whether the victim has pets
     * @return true if the victim has pets, false otherwise
     */
    public boolean hasPets() {
        return pets;
    }

    /**
     * Gets the victim's disabilities
     * @return The list of disabilities
     */
    public ArrayList<Disabilities> getDisabilities() {
        return disabilities;
    }

    /**
     * Gets the volunteer assigned to rescue the victim
     * @return The assigned volunteer, or null if there is none
     */
    public Volunteer getAssignedRescuer() {
        return assignedRescuer;
    }

}