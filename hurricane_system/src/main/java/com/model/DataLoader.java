// @author NervResponse
package com.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray; 
import org.json.simple.parser.JSONParser;

public class DataLoader {
    
    /**
    * Loads user accounts from the accounts JSON file.
    * Reads account information, saved locations, emergency contacts, and account-specific data.
    * Creates Victim, Volunteer, or Admin objects based on the account type stored in the JSON file.
    * @return an ArrayList containing the loaded accounts, or an empty list if no accounts are loaded
    */
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
                String userPhoneNumber = (String)accountJson.get(DataConstants.ACCOUNT_PHONE_NUMBER);
                JSONArray locations = (JSONArray) accountJson.get(DataConstants.ACCOUNT_SAVED_LOCATIONS);
                ArrayList<Location> userSavedLocations = new ArrayList<>();
                for(int l = 0; l < locations.size(); l++){
                    JSONObject jsonLocation = (JSONObject) locations.get(l);
                    double latitude = Double.parseDouble(jsonLocation.get(DataConstants.LOCATION_LATITUDE).toString());
                    double longitude = Double.parseDouble(jsonLocation.get(DataConstants.LOCATION_LONGITUDE).toString());
                    String zip = (String)jsonLocation.get(DataConstants.LOCATION_ZIP_LOCATION);
                    Location location = new Location(longitude, latitude, zip);
                    userSavedLocations.add(location);
                }
                ArrayList<UUID> userSavedAccounts = new ArrayList<>();
                JSONArray savedAccountsJson = (JSONArray) accountJson.get(DataConstants.ACCOUNT_SAVED_ACCOUNTS);
                for (Object s : savedAccountsJson)
                    userSavedAccounts.add(UUID.fromString(s.toString()));
                ArrayList<String> userEmergencyContacts = (ArrayList<String>) accountJson.get(DataConstants.ACCOUNT_EMERGENCY_CONTACT);
                JSONObject locationJson = (JSONObject) accountJson.get(DataConstants.ACCOUNT_CURRENT_LOCATION);
                double longitude = Double.parseDouble(locationJson.get(DataConstants.LOCATION_LONGITUDE).toString());
                double latitude = Double.parseDouble(locationJson.get(DataConstants.LOCATION_LATITUDE).toString());
                String zipCode = (String) locationJson.get(DataConstants.LOCATION_ZIP_LOCATION);
                Location userCurrentLocations = new Location(longitude, latitude, zipCode);

                if (userType.equals("VICTIM")) {
                    boolean pets = (boolean) accountJson.get(DataConstants.VICTIM_PETS);
                    ArrayList<Disabilities> disabilities = (ArrayList<Disabilities>) accountJson.get(DataConstants.VICTIM_DISABILITIES);
                    Account assignedRescuer = (Account) accountJson.get(DataConstants.VICTIM_ASSIGNED_RESCUER);
                    
                    accounts.add(new Victim(accountId, userFirstName, userEmail, userLastName, userPassword, userPhoneNumber, userSavedLocations, userSavedAccounts, userEmergencyContacts, userCurrentLocations, pets, disabilities, assignedRescuer));
                }
                if (userType.equals("VOLUNTEER")) {
                    JSONArray volunteerAbilities = (JSONArray) accountJson.get(DataConstants.VOLUNTEER_ABILITIES);
                    ArrayList<Abilities> abilities = new ArrayList<>();
                    for(Object ability: volunteerAbilities){
                        abilities.add(Abilities.valueOf(ability.toString()));
                    }
                    UUID victimListId = UUID.fromString(accountJson.get(DataConstants.ACCOUNT_USER_ID).toString());
                    Object currentTarget = accountJson.get(DataConstants.VOLUNTEER_CURRENT_TARGET);
                    UUID currentTargetId = currentTarget != null?
                            UUID.fromString(currentTarget.toString()) : null;
                    accounts.add(new Volunteer(accountId, userFirstName, userEmail, userLastName, userPassword, userPhoneNumber, userSavedLocations, userSavedAccounts, userEmergencyContacts, userCurrentLocations, abilities, victimListId, currentTargetId));
                }
                if (userType.equals("ADMIN")) {
                    ArrayList<UUID> shelters = new ArrayList<>();
                    JSONArray savedShelter = (JSONArray) accountJson.get(DataConstants.ADMIN_SHELTER_LIST);
                    for (Object s : savedShelter)
                        shelters.add(UUID.fromString(s.toString()));
                    ArrayList<UUID> requestList = new ArrayList<>();
                    JSONArray savedRequest = (JSONArray) accountJson.get(DataConstants.ADMIN_REQUEST_LIST);
                    for (Object s : savedRequest)
                        requestList.add(UUID.fromString(s.toString()));
                    accounts.add(new Admin(accountId, userFirstName, userEmail, userLastName, userPassword, userPhoneNumber, userSavedLocations, userSavedAccounts, userEmergencyContacts, userCurrentLocations, shelters, requestList));
                }
                

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    /**
    * Loads shelter information from the shelters JSON file.
    * Reads shelter IDs, occupancy information, locations, capabilities, water capacity, and occupant lists.
    * Creates Shelter objects using the stored information.
    * @return an ArrayList containing the loaded shelters, or an empty list if no shelters are loaded
    */
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
                JSONArray shelterCapabilities = (JSONArray) shelterJson.get(DataConstants.SHELTER_CAPABILITIES);
                ArrayList<Capabilities> capabilities = new ArrayList<>();
                for(Object capability: shelterCapabilities){
                    capabilities.add(Capabilities.valueOf(capability.toString()));
                }
                int waterCapacity = Integer.parseInt(shelterJson.get("waterCapacity").toString());
                ArrayList<UUID> occupantList = new ArrayList<>();
                    JSONArray occupants = (JSONArray) shelterJson.get(DataConstants.SHELTER_OCCUPANT_LIST);
                    for (Object s : occupants)
                        occupantList.add(UUID.fromString(s.toString()));

                shelters.add(new Shelter(shelterID, shelterCapacity, currentOccupancy, location, street, capabilities, waterCapacity, occupantList));
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return shelters;
    }

    /**
    * Loads relief requests from the requests JSON file.
    * Reads request IDs, descriptions, priority levels, locations, statuses, victim counts, and required abilities.
    * Creates ReliefRequest objects using the stored information.
    * @return an ArrayList containing the loaded relief requests, or an empty list if no requests are loaded
    */
    public static ArrayList<ReliefRequest> getReliefRequests() {
        ArrayList<ReliefRequest> reliefRequests = new ArrayList<>();
        try {
            FileReader reader = new FileReader(DataConstants.REQUEST_FILE_NAME);
            JSONParser parser = new JSONParser();
            JSONArray reliefRequestsArray = (JSONArray) parser.parse(reader);
            
            for (int i = 0; i < reliefRequestsArray.size(); i++) {
                JSONObject reliefRequestJson = (JSONObject) reliefRequestsArray.get(i);
                UUID requestID = UUID.fromString(reliefRequestJson.get(DataConstants.REQUEST_ID).toString());
                UUID requestorID = UUID.fromString(reliefRequestJson.get(DataConstants.REQUESTOR_ID).toString());
                PriorityLevel priorityLevel = PriorityLevel.valueOf(reliefRequestJson.get("priorityLevel").toString());
                String requestDescription = (String) reliefRequestJson.get(DataConstants.REQUEST_DESCRIPTION);
                JSONObject locationJson = (JSONObject) reliefRequestJson.get(DataConstants.REQUEST_LOCATION);
                double longitude = Double.parseDouble(locationJson.get(DataConstants.LOCATION_LONGITUDE).toString());
                double latitude = Double.parseDouble(locationJson.get(DataConstants.LOCATION_LATITUDE).toString());
                String zipCode = (String) locationJson.get(DataConstants.LOCATION_ZIP_LOCATION);
                Location location = (Location) new Location(longitude, latitude, zipCode);
                Status status = Status.valueOf(reliefRequestJson.get("status").toString());
                int victimCount = Integer.parseInt(reliefRequestJson.get("victimCount").toString());
                JSONArray abilities = (JSONArray) reliefRequestJson.get(DataConstants.REQUEST_WANTED_ABILITIES);
                ArrayList<Abilities> wantedAbilities = new ArrayList<>();
                for(Object ability: abilities){
                    wantedAbilities.add(Abilities.valueOf(ability.toString()));
                }

                reliefRequests.add(new ReliefRequest(requestID, requestorID, status,requestDescription, priorityLevel, location, victimCount, wantedAbilities));
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return reliefRequests;
    }

    private static BufferedReader getReaderFromFile(String fileName, String jsonFileName){
		try {
			if(isJUnitTest()){
				InputStream inputStream = Database.class.getResourceAsStream(jsonFileName);
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				return new BufferedReader(inputStreamReader);
			} else {
				FileReader reader = new FileReader(fileName);
				return new BufferedReader(reader);
			}
		} catch(Exception e){
			System.out.println("Can't load");
			return null;
		}
			
	}

}