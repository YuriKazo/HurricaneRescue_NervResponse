package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants{

public static boolean saveAccounts(){
    AccountList accountList = AccountList.getInstance();
    //ArrayList<Account> accounts = accountList.getUsers();

    // hard code
    ArrayList<Account> accounts = new ArrayList<Account>();
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

public static JSONObject getAccountJSON(Account account) {
    JSONObject accountDetails = new JSONObject();
    accountDetails.put(ACCOUNT_USER_ID, account.getID().toString());
    accountDetails.put(ACCOUNT_FIRST_NAME, account.getFirstName());
    accountDetails.put(ACCOUNT_LAST_NAME, account.getLastName());
    accountDetails.put(ACCOUNT_EMAIL, account.getEmail());
    accountDetails.put(ACCOUNT_PASSWORD, account.getPassword());
    accountDetails.put(ACCOUNT_SAVED_LOCATIONS, account.getSavedLocations());
    accountDetails.put(ACCOUNT_SAVED_ACCOUNTS, account.getSavedAccounts());
    accountDetails.put(ACCOUNT_EMERGENCY_CONTACT, account.getEmergencyContact());

    if(account instanceof Victim){
        accountDetails.put(ACCOUNT_TYPE, "VICTIM");
        accountDetails.put(VICTIM_PETS, ((Victim)account).hasPets());
        accountDetails.put(VICTIM_DISABILITIES, ((Victim)account).getDisabilities());

        accountDetails.put(VICTIM_ASSIGNED_RESCUER, ((Victim)account).getAssignedRescuer() != null ? ((Victim)account).getID().toString() : null);
    }
    else if(account instanceof Volunteer){
        accountDetails.put(ACCOUNT_TYPE, "VOLUNTEER");
        accountDetails.put(VOLUNTEER_ABILITIES, ((Volunteer)account).getAbilities());
        //TODO add current target (relief request)
        //accountDetails.put(VOLUNTEER_CURRENT_TARGET, );

    }
    // else if(account instanceof Admin){
        //TODO add Admin data writing
    // }
    return accountDetails;
}



public static boolean saveShelters(){
    /* 
    ShelterList shelterList = ShelterList.getInstance();
    ArrayList<Shelter> shelters = shelterList.getShelters();

    // hard code later

    JSONArray jsonAccounts = new JSONArray();

    for(int i = 0; i < shelters.size(); i++){
        jsonAccounts.add(getShelterJSON(shelters.get(i)));

    }
    */
    return true;
}

/* 
public static JSONObject getShelterJSON(Shelter shelter){
    JSONObject shelterDetails = new JSONObject();
    shelterDetails.put(SHELTER_ID, shelter.getId().toString());
    shelterDetails.put(SHELTER_TOTAL_OCCUPANCY, shelter.getTotalOccupancy());
    shelterDetails.put(SHELTER_CURRENT_OCCUPANCY, shelter.getCurrentOccupancy());
    shelterDetails.put(SHELTER_LOCATION, shelter.getLocation());
    shelterDetails.put(SHELTER_STREET, shelter.getAge());
    shelterDetails.put(SHELTER_CAPABILITIES, shelter.getPhoneNumber());
    shelterDetails.put(SHELTER_WATER_CAPACITY, shelter.getPhoneNumber());
    return shelterDetails;
}
*/

public static boolean saveReliefRequests(){
    return true;
}

public static void main(String[] args) {
    saveAccounts();
}
}
