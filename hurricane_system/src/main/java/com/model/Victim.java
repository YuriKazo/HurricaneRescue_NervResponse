package com.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
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

    public Victim(UUID id, String firstName, String email, String lastName, String passWord, boolean pets, ArrayList<Disabilities> disabilities){
        super(id, firstName, email, lastName, passWord, AccountType.VICTIM,
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

    public Victim(UUID id, String firstName, String email, String lastName, String passWord, ArrayList<Location> savedLocations, ArrayList<UUID> savedAccounts, ArrayList<String> emergencyContacts, Location currentLocations, boolean pets, ArrayList<Disabilities> disabilities, Account assignedRescuer){
        this(id, firstName, email, lastName, passWord, pets, disabilities);

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

    public void registerPets(){
        setPets(true);
    }

    public void markComplete(){
        if (assignedRescuer == null) return;

        assignedRescuer.markComplete();
        assignedRescuer = null;
    }

    public void setPets(boolean pets) {
        this.pets = pets;
    }

    public void setDisabilities(Disabilities disabilities) {
        if (disabilities == null || this.disabilities.contains(disabilities)) return;

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