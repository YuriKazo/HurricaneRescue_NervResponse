package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class Admin extends Account{
    private ArrayList<UUID> shelterList;
    private ArrayList<UUID> requestList;

    public Admin(String firstName, String email, String lastName, String passWord){
        super(firstName, email, lastName, passWord, AccountType.ADMIN);
        shelterList = new ArrayList<UUID>();
        requestList = new ArrayList<UUID>();
    }

    public Admin(UUID id, String firstName, String email, String lastName, String passWord, ArrayList<Location> savedLocations, ArrayList<UUID> savedAccounts, ArrayList<String> emergencyContacts, Location currentLocation, ArrayList<UUID> shelterList, ArrayList<UUID> requestList){
        super(id, firstName, email, lastName, passWord, AccountType.ADMIN, savedLocations, savedAccounts, emergencyContacts, currentLocation);
        this.shelterList = shelterList;
        this.requestList = requestList;
    }

    public void addShelter(UUID id) {
        shelterList.add(id);
    }

    public void updateOccupancy(UUID id, int newOccupancy){
        for(UUID s : shelterList){
            if(s.equals(id))
                ShelterList.getInstance().getShelter(id).setCurrentOccupancy(newOccupancy);
        }
    }

    public void updateTotalOccupancy(UUID id, int totalOccupancy){
        for(UUID s : shelterList){
            if(s.equals(id))
                ShelterList.getInstance().getShelter(id).setTotalOccupancy(totalOccupancy);
        }
    }

    public void updateWaterLeft(UUID id, int waterLeft){
        System.out.println(shelterList);
        for(UUID s : shelterList){
            if(s.equals(id))
                ShelterList.getInstance().getShelter(id).setWaterCapacity(waterLeft);
        }
    }

    public boolean deleteShelter(Shelter shelter){
        return ShelterList.getInstance().removeShelter(shelter.getID());
    }

}
