package com.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray; 
import org.json.simple.parser.JSONParser;

public class DataLoader {
    
    public static ArrayList<Account> getAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();
        try {
            FileReader reader = new FileReader(DataConstants.ACCOUNT_FILE_NAME);
            JSONParser parser = new JSONParser();
            JSONArray accountsArray = (JSONArray) parser.parse(reader);

            for (int i = 0; i < accountsArray.size(); i++) {
                JSONObject accountJson = (JSONObject) accountsArray.get(i);
                UUID accountId = UUID.fromString(accountJson.get(DataConstants.ACCOUNT_USER_ID).toString());
                String userType = (String)accountJson.get(DataConstants.ACCOUNT_TYPE);
                String userFirstName = (String)accountJson.get(DataConstants.ACCOUNT_FIRST_NAME);
                String userLastName = (String)accountJson.get(DataConstants.ACCOUNT_LAST_NAME);
                String userEmail = (String)accountJson.get(DataConstants.ACCOUNT_EMAIL);
                String userPassword = (String)accountJson.get(DataConstants.ACCOUNT_PASSWORD);
                ArrayList<Location> userSavedLocations = (ArrayList<Location>) accountJson.get(DataConstants.ACCOUNT_SAVED_LOCATIONS);
                ArrayList<Account> userSavedAccounts = (ArrayList<Account>) accountJson.get(DataConstants.ACCOUNT_SAVED_ACCOUNTS);
                ArrayList<String> userEmergencyContacts = (ArrayList<String>) accountJson.get(DataConstants.ACCOUNT_EMERGENCY_CONTACT);
                JSONObject locationJson = (JSONObject) accountJson.get(DataConstants.ACCOUNT_CURRENT_LOCATION);
                double longitude = Double.parseDouble(locationJson.get(DataConstants.LOCATION_LONGITUDE).toString());
                double latitude = Double.parseDouble(locationJson.get(DataConstants.LOCATION_LATITUDE).toString());
                String zipCode = (String) locationJson.get(DataConstants.LOCATION_ZIP_LOCATION);
                Location userCurrentLocations = new Location(longitude, latitude, zipCode);

                System.out.println("User ID: " + accountId);
                System.out.println("User Type: " + userType);
                System.out.println("User First Name: " + userFirstName);
                System.out.println("User Last Name: " + userLastName);
                System.out.println("User Email: " + userEmail);
                System.out.println("User Password: " + userPassword);
                System.out.println("User Saved Locations: " + userSavedLocations);
                System.out.println("User Saved Accounts: " + userSavedAccounts);
                System.out.println("User Emergency Contacts: " + userEmergencyContacts);
                System.out.println("User Current Location: " + userCurrentLocations);

                if (userType.equals("VICTIM")) {
                    boolean pets = (boolean) accountJson.get(DataConstants.VICTIM_PETS);
                    ArrayList<Disabilities> disabilities = (ArrayList<Disabilities>) accountJson.get(DataConstants.VICTIM_DISABILITIES);
                    Account assignedRescuer = (Account) accountJson.get(DataConstants.VICTIM_ASSIGNED_RESCUER);
                    System.out.println(" Pets: "+pets+
                        "\n Disabilities: "+disabilities+
                        "\n Assigned Rescuer: "+assignedRescuer
                    );
                    //accounts.add(new Victim(accountId, userFirstName, userEmail, userLastName, userPassword, userSavedLocations, userSavedAccounts, userEmergencyContacts, userCurrentLocations, pets, disabilities, assignedRescuer));
                    
                }
                if (userType.equals("VOLUNTEER")) {
                    ArrayList<Abilities> abilities = (ArrayList<Abilities>) accountJson.get(DataConstants.VOLUNTEER_ABILITIES);
                    UUID victimListId = UUID.fromString(accountJson.get(DataConstants.ACCOUNT_USER_ID).toString());
                    UUID currentTargetId = UUID.fromString(accountJson.get(DataConstants.VOLUNTEER_CURRENT_TARGET).toString());
                    System.out.println(" Abilities: "+abilities+
                        "\n Victim List ID: "+victimListId+
                        "\n Current Target ID: "+currentTargetId
                    );
                    //accounts.add(new Volunteer(accountId, userFirstName, userEmail, userLastName, userPassword, userSavedLocations, userSavedAccounts, userEmergencyContacts, userCurrentLocations, abilities, victimListId, currentTargetId));
                }
                if (userType.equals("ADMIN")) {
                    ArrayList<Shelter> shelters = (ArrayList<Shelter>) accountJson.get(DataConstants.ADMIN_SHELTER_LIST);
                    ArrayList<ReliefRequest> requestList = (ArrayList<ReliefRequest>) accountJson.get(DataConstants.ADMIN_REQUEST_LIST);
                    accounts.add(new Admin(accountId, userFirstName, userEmail, userLastName, userPassword, userSavedLocations, userSavedAccounts, userEmergencyContacts, userCurrentLocations, shelters, requestList));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }
    public static ArrayList<Shelter> getShelters() {
        ArrayList<Shelter> shelters = new ArrayList<>();
        try {
            FileReader reader = new FileReader(DataConstants.SHELTER_FILE_NAME);
            JSONParser parser = new JSONParser();
            JSONArray sheltersArray = (JSONArray) parser.parse(reader);
            
            for (int i = 0; i < sheltersArray.size(); i++) {
                JSONObject shelterJson = (JSONObject) sheltersArray.get(i);
                UUID shelterID = UUID.fromString(shelterJson.get(DataConstants.SHELTER_ID).toString());
                int shelterCapacity = Integer.parseInt(shelterJson.get(DataConstants.SHELTER_TOTAL_OCCUPANCY).toString());
                int currentOccupancy = Integer.parseInt(shelterJson.get(DataConstants.SHELTER_CURRENT_OCCUPANCY).toString());
                JSONObject locationJson = (JSONObject) shelterJson.get(DataConstants.SHELTER_LOCATION);
                double longitude = Double.parseDouble(locationJson.get(DataConstants.LOCATION_LONGITUDE).toString());
                double latitude = Double.parseDouble(locationJson.get(DataConstants.LOCATION_LATITUDE).toString());
                String zipCode = (String) locationJson.get(DataConstants.LOCATION_ZIP_LOCATION);
                Location location = (Location) new Location(longitude, latitude, zipCode);
                String street = (String) shelterJson.get(DataConstants.SHELTER_STREET);
                ArrayList<Capabilities> capabilities = (ArrayList<Capabilities>) shelterJson.get(DataConstants.SHELTER_CAPABILITIES);
                int waterCapacity = Integer.parseInt(shelterJson.get("waterCapacity").toString());
 
                shelters.add(new Shelter(shelterID, shelterCapacity, currentOccupancy, location, street, capabilities, waterCapacity));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return shelters;
    }
    public static ArrayList<ReliefRequest> getReliefRequests() {
        ArrayList<ReliefRequest> reliefRequests = new ArrayList<>();
        try {
            FileReader reader = new FileReader(DataConstants.REQUEST_FILE_NAME);
            JSONParser parser = new JSONParser();
            JSONArray reliefRequestsArray = (JSONArray) parser.parse(reader);

            for (int i = 0; i < reliefRequestsArray.size(); i++) {
                JSONObject reliefRequestJson = (JSONObject) reliefRequestsArray.get(i);
                UUID requestID = UUID.fromString(reliefRequestJson.get(DataConstants.REQUEST_ID).toString());
                PriorityLevel priorityLevel = PriorityLevel.valueOf(reliefRequestJson.get("priorityLevel").toString());
                String requestDescription = (String) reliefRequestJson.get(DataConstants.REQUEST_DESCRIPTION);
                JSONObject locationJson = (JSONObject) reliefRequestJson.get(DataConstants.REQUEST_LOCATION);
                double longitude = Double.parseDouble(locationJson.get(DataConstants.LOCATION_LONGITUDE).toString());
                double latitude = Double.parseDouble(locationJson.get(DataConstants.LOCATION_LATITUDE).toString());
                String zipCode = (String) locationJson.get(DataConstants.LOCATION_ZIP_LOCATION);
                Location location = (Location) new Location(longitude, latitude, zipCode);
                Status status = Status.valueOf(reliefRequestJson.get("status").toString());
                int victimCount = Integer.parseInt(reliefRequestJson.get("victimCount").toString());

                reliefRequests.add(new ReliefRequest(requestID, status,requestDescription, priorityLevel, location, victimCount));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return reliefRequests;
    }
    

    public static void main(String[] args) {
        System.out.println("Hello, World");
        DataLoader.getAccounts();
    }
}