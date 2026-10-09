package com.model;

import java.util.ArrayList;
import java.util.UUID;


public class Shelter {
    private int totalOccupancy;
    private int currentOccupancy;
    private Location location;
    private String street;
    private ArrayList<Capabilities> capabilities;
    private int waterCapacity;
    private UUID shelterID;

    public Shelter(Location location, String street){
        shelterID = UUID.randomUUID();
        this.location = location;
        this.street = street;
        this.totalOccupancy = 0;
        this.currentOccupancy = 0;
        this.capabilities = new ArrayList<>();
        this.waterCapacity = 0;

    }

    public Shelter(UUID id, int totalOccupancy, int currentOccupancy,Location location, String street, ArrayList<Capabilities> capabilities, int waterCapacity){
        this.shelterID = id;
        this.totalOccupancy = totalOccupancy;
        this.currentOccupancy = currentOccupancy;
        this.location = location;
        this.street = street;
        this.capabilities = capabilities;
        this.waterCapacity = waterCapacity;
    }

    public boolean waterIsAvaliabile(){
        if (this.waterCapacity > 10)
            return true;
        return false;
    }

    public int getTotalOccupancy() {
        return totalOccupancy;
    }
 
    public int getCurrentOccupancy() {
        return currentOccupancy;
    }

    public Location getLocation() {
        return location;
    }

    public String getStreet() {
        return street;
    }
    
    public ArrayList<Capabilities> getCapabilities() {
        return capabilities;
    }

    public int getWaterCapacity() {
        return waterCapacity;
    }

    public UUID getID(){
        return shelterID;
    }

    public void setTotalOccupancy(int totalOccupancy) {
        this.totalOccupancy = totalOccupancy;
    }

    public void setCurrentOccupancy(int currentOccupancy) {
        this.currentOccupancy = currentOccupancy;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCapabilities(ArrayList<Capabilities> capabilities) {
        this.capabilities = capabilities;
    }

    public void setWaterCapacity(int waterCapacity) {
        this.waterCapacity = waterCapacity;
    }

    
}
