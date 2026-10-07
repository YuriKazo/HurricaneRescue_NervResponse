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
                UUID accountId = UUID.fromString(accountJson.get("userID").toString());
                String userType = (String)accountJson.get("type");
                String userFirstName = (String)accountJson.get("firstName");
                String userLastName = (String)accountJson.get("lastName");
                String userEmail = (String)accountJson.get("email");
                String userPassword = (String)accountJson.get("password");
                ArrayList<Location> userSavedLocations = (ArrayList<Location>) accountJson.get("savedLocations");
                ArrayList<Account> userSavedAccounts = (ArrayList<Account>) accountJson.get("savedAccounts");
                ArrayList<String> userEmergencyContacts = (ArrayList<String>) accountJson.get("emergencyContact");
                JSONObject locationJson = (JSONObject) accountJson.get("currentLocation");
                double longitude = Double.parseDouble(locationJson.get("longitude").toString());
                double latitude = Double.parseDouble(locationJson.get("latitude").toString());
                String zipCode = (String) locationJson.get("zipLocation");
                //Location userCurrentLocations = new Location(longitude, latitude, zipCode);
                System.out.println("UUID: "+accountId+
                    "\n User Type: "+userType+
                    "\n User First Name: "+userFirstName+
                    "\n User Last Name: "+userLastName+
                    "\n User Email: "+userEmail+
                    "\n User Password: "+userPassword+
                    "\n User Saved Locations: "+userSavedLocations+
                    "\n User Saved Accounts: "+userSavedAccounts+
                    "\n User Emergency Contacts: "+userEmergencyContacts+
                    "\n User Current Locations: "+
                    "\n User Longitude: "+longitude+
                    "\n User Latitude: "+latitude+
                    "\n User Zip Code: "+zipCode
                );


                if (userType.equals("VICTIM")) {
                    boolean pets = (boolean) accountJson.get("pets");
                    ArrayList<Disabilities> disabilities = (ArrayList<Disabilities>) accountJson.get("disabilities"); //here 
                    Account assignedRescuer = (Account) accountJson.get("assignedRescuer");
                    System.out.println(" Pets: "+pets+
                        "\n Disabilities: "+disabilities+
                        "\n Assigned Rescuer: "+assignedRescuer
                    );
                    //accounts.add(new Victim(accountId, userFirstName, userEmail, userLastName, userPassword, userSavedLocations, userSavedAccounts, userEmergencyContacts, userCurrentLocations, pets, disabilities, assignedRescuer));
                }
                if (userType.equals("VOLUNTEER")) {
                    ArrayList<Abilities> abilities = (ArrayList<Abilities>) accountJson.get("abilities"); //here
                    UUID victimListId = UUID.fromString(accountJson.get("victimList").toString());
                    UUID currentTargetId = UUID.fromString(accountJson.get("currentTarget").toString());
                    System.out.println(" Abilities: "+abilities+
                        "\n Victim List ID: "+victimListId+
                        "\n Current Target ID: "+currentTargetId
                    );
                    //accounts.add(new Volunteer(accountId, userFirstName, userEmail, userLastName, userPassword, userSavedLocations, userSavedAccounts, userEmergencyContacts, userCurrentLocations, abilities, victimListId, currentTargetId));
                }
                if (userType.equals("ADMIN")) {
                    ArrayList<Shelter> shelters = (ArrayList<Shelter>) accountJson.get("shelters");
                    ArrayList<ReliefRequest> requestList = (ArrayList<ReliefRequest>) accountJson.get("requestList");
                    //accounts.add(new Admin(accountId, userFirstName, userEmail, userLastName, userPassword, userSavedLocations, userSavedAccounts, userEmergencyContacts, userCurrentLocations, shelters, requestList));
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
            FileReader reader = new FileReader("shelters.json");
            JSONParser parser = new JSONParser();
            JSONArray sheltersArray = (JSONArray) parser.parse(reader);

            for (int i = 0; i < sheltersArray.size(); i++) {
                JSONObject shelterJson = (JSONObject) sheltersArray.get(i);
                UUID shelterID = UUID.fromString(shelterJson.get("shelterID").toString());
                String shelterName = (String) shelterJson.get("name");
                String shelterLocation = (String) shelterJson.get("location");
                int shelterCapacity = Integer.parseInt(shelterJson.get("capacity").toString());
                Location location = (Location) shelterJson.get("location");
                String street = (String) shelterJson.get("street");
                ArrayList<Capabilities> capabilities = (ArrayList<Capabilities>) shelterJson.get("capabilities"); //here
                int waterCapacity = Integer.parseInt(shelterJson.get("waterCapacity").toString());

                //shelters.add(new Shelter(shelterID, shelterName, shelterLocation, shelterCapacity, location, street, capabilities, waterCapacity));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return shelters;
    }
    public static ArrayList<ReliefRequest> getReliefRequests() {
        ArrayList<ReliefRequest> reliefRequests = new ArrayList<>();
        try {
            FileReader reader = new FileReader("reliefs.json");
            JSONParser parser = new JSONParser();
            JSONArray reliefRequestsArray = (JSONArray) parser.parse(reader);

            for (int i = 0; i < reliefRequestsArray.size(); i++) {
                JSONObject reliefRequestJson = (JSONObject) reliefRequestsArray.get(i);
                UUID requestID = UUID.fromString(reliefRequestJson.get("requestID").toString());
                String requestDescription = (String) reliefRequestJson.get("requestDescription");
                Location location = (Location) reliefRequestJson.get("location");
                Status status = Status.valueOf(reliefRequestJson.get("status").toString());
                int victimCount = Integer.parseInt(reliefRequestJson.get("victimCount").toString());

                //reliefRequests.add(new ReliefRequest(requestID, requestDescription, location, status, victimCount));
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