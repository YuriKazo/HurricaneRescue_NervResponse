// @author NervResponse
package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class Admin extends Account {
    private ArrayList<UUID> shelterList;
    private ArrayList<UUID> requestList;

 public Admin(String firstName, String email, String lastName, String password, String phoneNumber) {
    super(firstName, email, lastName, password,phoneNumber, AccountType.ADMIN);

    shelterList = new ArrayList<UUID>();
    requestList = new ArrayList<UUID>();
}

public Admin(UUID id, String firstName, String email,
             String lastName, String password,
             String phoneNumber,
             ArrayList<Location> savedLocations,
             ArrayList<UUID> savedAccounts,
             ArrayList<String> emergencyContacts,
             Location currentLocation,
             ArrayList<UUID> shelterList,
             ArrayList<UUID> requestList) {

    super(id, firstName, email, lastName, password, phoneNumber,
          AccountType.ADMIN, savedLocations, savedAccounts,
          emergencyContacts, currentLocation);

    this.shelterList = shelterList;
    this.requestList = requestList;
}
    
     // @param id the shelter's unique ID
    public void addShelter(UUID id) {
        if (id != null && !shelterList.contains(id)) {
            shelterList.add(id);
        }
    }

     // @param shelter the shelter to update
     // @param occupancy the new occupancy
    public void updateOccupancy(Shelter shelter,
                                int occupancy) {
        if (shelter != null && occupancy >= 0) {
            shelter.setCurrentOccupancy(occupancy);
        }
    }

     // @param shelter the shelter to update
     // @param totalOccupancy the new capacity
    public void updateTotalOccupancy(Shelter shelter,
                                     int totalOccupancy) {
        if (shelter != null && totalOccupancy >= 0) {
            shelter.setTotalOccupancy(totalOccupancy);
        }
    }

     // @param shelter the shelter to update
     // @param waterLeft the new water amount
    public void updateWaterLeft(Shelter shelter,
                                int waterLeft) {
        if (shelter != null && waterLeft >= 0) {
            shelter.setWaterCapacity(waterLeft);
        }
    }

     // @param shelterId the shelter's ID
     // @return true if removed, otherwise false
    public boolean deleteShelter(UUID shelterId) {
        if (shelterId == null) {
            return false;
        }

        ShelterList shelters = ShelterList.getInstance();
        Shelter shelter = shelters.getShelter(shelterId);
        if (shelter == null) {
            return false;
        }

        boolean removed =
            shelters.removeShelter(shelterId);
        if (removed) {
            shelterList.remove(shelterId);
        }
        return removed;
    }

    //@return ArrayList of shelter UUIDs
    public ArrayList<UUID> getShelterList() {
        return shelterList;
    }

    //@return ArrayList of relief request UUIDs
    public ArrayList<UUID> getRequestList() {
        return requestList;
    }
    
/**
 * Updates the current occupancy of a shelter using its UUID.
 * @param shelterID the unique ID of the shelter
 * @param occupancy the new current occupancy
 */
public void updateOccupancy(UUID shelterID, int occupancy) {
    Shelter shelter = ShelterList.getInstance().getShelter(shelterID);

    if (shelter == null) {
        return;
    }

    updateOccupancy(shelter, occupancy);
}

    // @param shelterID the unique ID of the shelter
    // @param totalOccupancy the new maximum occupancy
 
    public void updateTotalOccupancy(UUID shelterID, int totalOccupancy) {
        Shelter shelter = ShelterList.getInstance().getShelter(shelterID);
        if (shelter == null) {
        return;
    }
    updateTotalOccupancy(shelter, totalOccupancy);
    }

    // @param shelterID the unique ID of the shelter
    // @param water the new amount of water remaining
 
    public void updateWaterLeft(UUID shelterID, int water) {
    Shelter shelter = ShelterList.getInstance().getShelter(shelterID);
    if (shelter == null) {
        return;
    }

    updateWaterLeft(shelter, water);
    }
}
