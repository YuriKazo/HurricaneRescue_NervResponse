package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class Admin extends Account{
    private ArrayList<Shelter> shelterList;
    private ArrayList<ReliefRequest> requestList;

    public Admin(String firstName, String email, String lastName, String passWord){
        super(firstName, email, lastName, passWord, AccountType.ADMIN);
        shelterList = new ArrayList<Shelter>();
        requestList = new ArrayList<ReliefRequest>();
    }

    public Admin(UUID id, String firstName, String email, String lastName, String passWord, ArrayList<Shelter> shelterList, ArrayList<ReliefRequest> requestList){
        super(id, firstName, email, lastName, passWord, AccountType.ADMIN);
        this.shelterList = shelterList;
        this.requestList = requestList;
    }

    public void updateOccupancy(Shelter shelter, int newOccupancy){
        for(Shelter s : shelterList){
            if(s.equals(shelter))
                s.setCurrentOccupancy(newOccupancy);
        }
    }

    public void updateTotalOccupancy(Shelter shelter, int totalOccupancy){
        for(Shelter s : shelterList){
            if(s.equals(shelter))
                s.setTotalOccupancy(totalOccupancy);
        }
    }

    public void updateWaterLeft(Shelter shelter, int waterLeft){
        for(Shelter s : shelterList){
            if(s.equals(shelter))
                s.setWaterCapacity(waterLeft);
        }
    }

    public void makeShelter(int totalOccupancy, int currentOccupancy, Location location, String street, ArrayList<Capabilities> capabilities, int waterCapacity){
        ShelterList.getInstance().addShelter(totalOccupancy, currentOccupancy, location, street, capabilities, waterCapacity);
    }

    public boolean deleteShelter(Shelter shelter){
        return ShelterList.getInstance().removeShelter(shelter.getID());
    }

}
