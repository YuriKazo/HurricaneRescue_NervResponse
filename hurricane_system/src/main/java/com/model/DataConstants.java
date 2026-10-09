package com.model;

public abstract class DataConstants {
    protected static final String ACCOUNT_FILE_NAME = "json/accounts.json";
    protected static final String HURRICANE_FILE_NAME = "json/hurricanes.json";
    protected static final String REQUEST_FILE_NAME = "json/requests.json";
    protected static final String SHELTER_FILE_NAME = "json/shelters.json";
    protected static final String ACCOUNT_TEMP_FILE_NAME = "json/accounts_temp.json";
    protected static final String HURRICANE_TEMP_FILE_NAME = "json/hurricanes_temp.json";
    protected static final String REQUEST_TEMP_FILE_NAME = "json/requests_temp.json";
    protected static final String SHELTER_TEMP_FILE_NAME = "json/shelters_temp.json";

    protected static final String ACCOUNT_USER_ID = "userID";
    protected static final String ACCOUNT_TYPE = "type";
    protected static final String ACCOUNT_FIRST_NAME = "firstName";
    protected static final String ACCOUNT_LAST_NAME = "lastName";
    protected static final String ACCOUNT_EMAIL = "email";
    protected static final String ACCOUNT_PASSWORD = "password";
    protected static final String ACCOUNT_SAVED_LOCATIONS = "savedLocations";
    protected static final String ACCOUNT_SAVED_ACCOUNTS = "savedAccounts";
    protected static final String ACCOUNT_EMERGENCY_CONTACT = "emergencyContact";
    protected static final String ACCOUNT_CURRENT_LOCATION = "currentLocation";
    protected static final String VICTIM_PETS = "pets";
    protected static final String VICTIM_DISABILITIES = "disabilities";
    protected static final String VICTIM_ASSIGNED_RESCUER = "assignedRescuer";
    protected static final String VOLUNTEER_ABILITIES = "abilities";
    protected static final String VOLUNTEER_VICTIM_LIST = "victimList";
    protected static final String VOLUNTEER_CURRENT_TARGET = "currentTarget";
    protected static final String ADMIN_SHELTER_LIST = "shelterList";
    protected static final String ADMIN_REQUEST_LIST = "requestList";
    
    protected static final String HURRICANE_ID = "hurricaneID";
    protected static final String HURRICANE_NAME = "hurricaneName";
    protected static final String HURRICANE_CATEGORY = "hurricaneCategory";
    protected static final String HURRICANE_ACTIVE_LEVEL = "activeLevel";
    protected static final String HURRICANE_CURRENT_ZIP = "currentZip";

    protected static final String REQUEST_ID = "requestID";
    protected static final String REQUEST_DESCRIPTION = "requestDescription";
    protected static final String REQUEST_LOCATION = "location";
    protected static final String REQUEST_PRIORITY_LEVEL = "priorityLevel";
    protected static final String REQUEST_STATUS = "status";
    protected static final String REQUEST_VICTIM_COUNT = "victimCount";


    protected static final String SHELTER_ID = "shelterID";
    protected static final String SHELTER_TOTAL_OCCUPANCY = "totalOccupancy";
    protected static final String SHELTER_CURRENT_OCCUPANCY = "currentOccupancy";
    protected static final String SHELTER_LOCATION = "location";
    protected static final String SHELTER_STREET = "street";
    protected static final String SHELTER_CAPABILITIES = "capabilities";
    protected static final String SHELTER_WATER_CAPACITY = "waterCapacity";

    protected static final String LOCATION_LONGITUDE = "longitude";
    protected static final String LOCATION_LATITUDE = "latitude";
    protected static final String LOCATION_ZIP_LOCATION = "zipLocation";
}
