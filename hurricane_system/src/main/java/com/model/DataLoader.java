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
            FileReader reader = new FileReader("accounts.json");
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
                String[] userSavedLocations = (String[]) accountJson.get("savedLocations");
                Accounts[] userSavedAccounts = (Account[]) accountJson.get("savedAccounts");
                String[] userEmergencyContacts = (String[]) accountJson.get("emergencyContacts");
                Location[] userCurrentLocations = (Location[]) accountJson.get("currentLocations");
                boolean pets = (boolean) accountJson.get("pets");
                


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
                shelters.add(new Shelter(shelterID, shelterName, shelterLocation, shelterCapacity));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return shelters;
    }
    public static ArrayList<ReliefRequest> getReliefRequests() {
        ArrayList<ReliefRequest> reliefRequests = new ArrayList<>();
        try {
            FileReader reader = new FileReader("reliefRequests.json");
            JSONParser parser = new JSONParser();
            JSONArray reliefRequestsArray = (JSONArray) parser.parse(reader);

            for (int i = 0; i < reliefRequestsArray.size(); i++) {
                JSONObject reliefRequestJson = (JSONObject) reliefRequestsArray.get(i);
                UUID requestID = UUID.fromString(reliefRequestJson.get("requestID").toString());
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return reliefRequests;
    }
}
