package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants{

public static boolean saveAccounts(){
    //AccountList accountList = AccountList.getInstance();
    //ArrayList<Account> accounts = accountList.getUsers();

    // hard coded
    ArrayList<Account> accounts = new ArrayList<>();
    accounts.add(new Victim("David", "dsmith@gmail.com", "Smith", "password123"));
    accounts.add(new Volunteer("Mark", "mbryant@gmail.com", "Byrant", "password321"));

    JSONArray jsonAccounts = new JSONArray();

    //creates the JSON objects
    for(int i = 0; i < accounts.size(); i++){
        jsonAccounts.add(getAccountJSON(accounts.get(i)));
    }

    try (FileWriter file = new FileWriter(ACCOUNT_TEMP_FILE_NAME)){
        file.write(jsonAccounts.toJSONString());
        file.flush();

    } catch (IOException e){
        e.printStackTrace();
    }

    return true;
}

private static JSONObject getAccountJSON(Account account) {
    JSONObject accountDetails = new JSONObject();
    accountDetails.put(ACCOUNT_USER_ID, account.getID().toString());
    accountDetails.put(ACCOUNT_FIRST_NAME, account.getFirstName());
    accountDetails.put(ACCOUNT_LAST_NAME, account.getLastName());
    accountDetails.put(ACCOUNT_EMAIL, account.getEmail());
    accountDetails.put(ACCOUNT_PASSWORD, account.getPassword());
    JSONArray locations = new JSONArray();
    for(Location location: account.getSavedLocations())
        locations.add(getLocationJSON(location));
    accountDetails.put(ACCOUNT_SAVED_LOCATIONS, locations);

    JSONArray savedAccounts = new JSONArray();
    for(Account savedAccount: account.getSavedAccounts())
        savedAccounts.add(savedAccount.getID());
    accountDetails.put(ACCOUNT_SAVED_ACCOUNTS, savedAccounts);

    JSONArray contacts = new JSONArray();
    for(String contact: account.getEmergencyContact())
        contacts.add(contact);
    contacts.addAll(account.getEmergencyContact());
    accountDetails.put(ACCOUNT_EMERGENCY_CONTACT, contacts);
    accountDetails.put(ACCOUNT_TYPE, account.getAccountType().name());

    if(account.getAccountType() == AccountType.VICTIM){
        accountDetails.put(VICTIM_PETS, ((Victim)account).hasPets());
        accountDetails.put(VICTIM_DISABILITIES, ((Victim)account).getDisabilities());

        accountDetails.put(VICTIM_ASSIGNED_RESCUER, ((Victim)account).getAssignedRescuer() != null ? ((Victim)account).getID().toString() : null);
    }
    else if(account.getAccountType() == AccountType.VOLUNTEER){
        accountDetails.put(VOLUNTEER_ABILITIES, ((Volunteer)account).getAbilities());
        //accountDetails.put(VOLUNTEER_CURRENT_TARGET, ((Volunteer)account).getCurrentTarget().getID());
        //TODO add current target/ relief request
    }
    // else if(account.getAccountType() == AccountType.ADMIN){
        //TODO add Admin data writing
    // }
    return accountDetails;
}



public static boolean saveShelters(){
    
    //ShelterList shelterList = ShelterList.getInstance();
    //ArrayList<Shelter> shelters = shelterList.getShelters();

    // hard coded
    ArrayList<Shelter> shelters = new ArrayList<>();
    shelters.add(new Shelter(new Location(72.4, 82.34, "23525")));
    shelters.add(new Shelter(UUID.randomUUID(), 200, 122, new Location(84.12,65.32,"23523"), "East St", new ArrayList<Capabilities>(List.of(Capabilities.FOOD,Capabilities.WATER)), 20));

    JSONArray jsonShelters = new JSONArray();

    //creates the JSON objects
    for(int i = 0; i < shelters.size(); i++){
        jsonShelters.add(getShelterJSON(shelters.get(i)));
    }

    try (FileWriter file = new FileWriter(SHELTER_TEMP_FILE_NAME)){
        file.write(jsonShelters.toJSONString());
        file.flush();

    } catch (IOException e){
        e.printStackTrace();
    }
    return true;
}


private static JSONObject getShelterJSON(Shelter shelter){
    JSONObject shelterDetails = new JSONObject();
    shelterDetails.put(SHELTER_ID, shelter.getID().toString());
    shelterDetails.put(SHELTER_TOTAL_OCCUPANCY, shelter.getTotalOccupancy());
    shelterDetails.put(SHELTER_CURRENT_OCCUPANCY, shelter.getCurrentOccupancy());
    shelterDetails.put(SHELTER_LOCATION, getLocationJSON(shelter.getLocation()));
    shelterDetails.put(SHELTER_STREET, shelter.getStreet());
    JSONArray shelterCapabilties = new JSONArray();
    for(Capabilities capability: shelter.getCapabilities()){
        shelterCapabilties.add(capability.name());
    }
    shelterDetails.put(SHELTER_CAPABILITIES, shelterCapabilties);
    
    shelterDetails.put(SHELTER_WATER_CAPACITY, shelter.getWaterCapacity());
    
    return shelterDetails;
}


private static JSONObject getLocationJSON(Location location){
    JSONObject locationDetails = new JSONObject();
    locationDetails.put(LOCATION_LONGITUDE,location.getLongitudeCoord());
    locationDetails.put(LOCATION_LATITUDE,location.getLatitudeCoord());
    locationDetails.put(LOCATION_ZIP_LOCATION, location.getZip());
    return locationDetails;
}

public static boolean saveReliefRequests(){
    return true;
}

public static void main(String[] args) {
    saveAccounts();
    saveShelters();
}
}
