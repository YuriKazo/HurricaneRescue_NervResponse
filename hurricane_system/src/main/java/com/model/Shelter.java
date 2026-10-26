package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A hurricane shelter with a location, capacity, capabilities, water supply, and a
 * list of the people staying there.
 * @author NervResponse
 */
public class Shelter {
    private int totalOccupancy;
    private int currentOccupancy;
    private Location location;
    private String street;
    private ArrayList<Capabilities> capabilities = new ArrayList<>();
    private int waterCapacity;
    private ArrayList<UUID> occupantList;
    private UUID shelterID;

    /**
     * Creates a new empty shelter with a new id
     * @param location The location of the shelter
     * @param street The street address of the shelter
     */
    public Shelter(Location location, String street){
        shelterID = UUID.randomUUID();
        this.location = location;
        this.street = street;
        this.totalOccupancy = 0;
        this.currentOccupancy = 0;
        this.capabilities = new ArrayList<>();
        this.waterCapacity = 0;
        this.occupantList = new ArrayList<>();

    }

    /**
     * Creates a shelter from existing data, such as when loading from file
     * @param id The id of the shelter
     * @param totalOccupancy The maximum number of people the shelter can hold
     * @param currentOccupancy The number of people currently in the shelter
     * @param location The location of the shelter
     * @param street The street address of the shelter
     * @param capabilities The capabilities the shelter has
     * @param waterCapacity The amount of water the shelter has
     * @param occupantList The ids of the people staying in the shelter
     */
    public Shelter(UUID id, int totalOccupancy, int currentOccupancy,Location location, String street, ArrayList<Capabilities> capabilities, int waterCapacity, ArrayList<UUID> occupantList){
        this.shelterID = id;
        this.totalOccupancy = totalOccupancy;
        this.currentOccupancy = currentOccupancy;
        this.location = location;
        this.street = street;
        this.capabilities = capabilities;
        this.waterCapacity = waterCapacity;
        this.occupantList = occupantList;
    }

    /**
     * Checks whether the shelter has enough water
     * @return true if the water capacity is more than 10, false otherwise
     */
    public boolean waterIsAvaliabile(){
        if (this.waterCapacity > 10)
            return true;
        return false;
    }

    /**
     * Gets the maximum number of people the shelter can hold
     * @return The total occupancy
     */
    public int getTotalOccupancy() {
        return totalOccupancy;
    }
 
    /**
     * Gets the number of people currently in the shelter
     * @return The current occupancy
     */
    public int getCurrentOccupancy() {
        return currentOccupancy;
    }

    /**
     * Gets the location of the shelter
     * @return The shelter's location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Gets the street address of the shelter
     * @return The shelter's street
     */
    public String getStreet() {
        return street;
    }
    
    /**
     * Gets what the shelter is able to provide
     * @return The shelter's capabilities
     */
    public ArrayList<Capabilities> getCapabilities() {
        return capabilities;
    }

    /**
     * Gets the amount of water the shelter has
     * @return The water capacity
     */
    public int getWaterCapacity() {
        return waterCapacity;
    }

    /**
     * Gets the ids of the people staying in the shelter
     * @return The list of occupant ids
     */
    public ArrayList<UUID> getOccupantList() {
        return occupantList;
    }

    /**
     * Gets the id of the shelter
     * @return The shelter's id
     */
    public UUID getID(){
        return shelterID;
    }

    /**
     * Sets the maximum number of people the shelter can hold
     * @param totalOccupancy The new total occupancy
     */
    public void setTotalOccupancy(int totalOccupancy) {
        this.totalOccupancy = totalOccupancy;
    }

    /**
     * Sets the number of people currently in the shelter
     * @param currentOccupancy The new current occupancy
     */
    public void setCurrentOccupancy(int currentOccupancy) {
        this.currentOccupancy = currentOccupancy;
    }

    /**
     * Sets the location of the shelter
     * @param location The new location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Sets the street address of the shelter
     * @param street The new street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Sets what the shelter is able to provide
     * @param capabilities The new list of capabilities
     */
    public void setCapabilities(ArrayList<Capabilities> capabilities) {
        this.capabilities = capabilities;
    }

    /**
     * Sets the amount of water the shelter has
     * @param waterCapacity The new water capacity
     */
    public void setWaterCapacity(int waterCapacity) {
        this.waterCapacity = waterCapacity;
    }

    /**
     * Adds a person to the shelter, increases the current occupancy, and saves the shelters
     * @param ID The id of the person to add
     */
    public void addOccupant(UUID ID) {
        occupantList.add(ID);
        currentOccupancy++;
        ShelterList.getInstance().saveShelter();
        
    }

    /**
     * Removes a person from the shelter, decreases the current occupancy, and saves the
     * shelters. Does nothing if the person is not in the shelter.
     * @param ID The id of the person to remove
     */
    public void removeOccupant(UUID ID) {
        for (UUID obj : occupantList) {
            if (obj == ID) {
                occupantList.remove(obj);
                currentOccupancy--;
                ShelterList.getInstance().saveShelter();
                return; 
            }
        }
    }
}
