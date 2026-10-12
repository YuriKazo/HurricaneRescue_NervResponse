package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class Admin extends Account {
    private ArrayList<UUID> shelterList;
    private ArrayList<UUID> requestList;

    public Admin(String firstName, String email,
                 String lastName, String password,
                 AccountType type) {
        super(firstName, email, lastName, password, type);

        shelterList = new ArrayList<UUID>();
        requestList = new ArrayList<UUID>();
    }

    public Admin(UUID id, String firstName, String email,
             String lastName, String password,
             AccountType type) {
    super(id, firstName, email, lastName, password, type,
          new ArrayList<Location>(),
          new ArrayList<UUID>(),
          new ArrayList<String>(),
          new Location(0.0, 0.0, "00000"));

    shelterList = new ArrayList<UUID>();
    requestList = new ArrayList<UUID>();
    }
    
     // @param id the shelter's unique ID
    public void addShelter(UUID id) {
        if (id != null && !shelterList.contains(id)) {
            shelterList.add(id);
        }
    }

     //@param shelter the shelter to update
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
}
