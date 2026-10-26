// @author NervResponse 
package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Stores personal information, account details, saved locations, emergency contacts, and associated accounts.
 * Provides methods for account authentication, managing contacts, updating locations, and completing relief requests.
 */
public abstract class Account {
	protected String firstName;
	protected String lastName;
	protected String email;
	protected String password;
	protected UUID userID;
	protected String phoneNumber;
	protected ArrayList<Location> savedLocations;
	protected ArrayList<UUID> savedAccounts;
	protected ArrayList<String> emergencyContact;
	protected Location currentLocation;
	protected AccountType type;

	/**
     * Constructs a new account with the provided user information.
     * Generates a unique user ID, initializes empty lists, and assigns a default location.
     * @param firstName the user's first name
     * @param email the user's email address
     * @param lastName the user's last name
     * @param passWord the user's password
     * @param phoneNumber the user's phone number
     * @param accountType the type of account being created
     */
	public Account(String firstName, String email, String lastName, String passWord, String phoneNumber, AccountType accountType) {
		this.userID = UUID.randomUUID();
		this.firstName = firstName;
		this.email = email;
		this.lastName = lastName;
		this.password = passWord;
		this.phoneNumber = phoneNumber;
		this.type = accountType;
		this.savedLocations = new ArrayList<Location>();
		this.savedAccounts = new ArrayList<UUID>();	
		this.emergencyContact = new ArrayList<String>();
		this.currentLocation = new Location(0.0, 0.0, "00000");
	}

	/**
     * Constructs an account using existing user information.
     * Used to restore an account with previously saved data.
     * @param id the unique identifier of the account
     * @param firstName the user's first name
     * @param email the user's email address
     * @param lastName the user's last name
     * @param passWord the user's password
     * @param phoneNumber the user's phone number
     * @param accountType the type of account
     * @param savedLocations the user's saved locations
     * @param savedAccounts the IDs of saved accounts
     * @param emergencyContacts the user's emergency contacts
     * @param currentLocation the user's current location
     */
	public Account(UUID id, String firstName, String email, String lastName, String passWord, String phoneNumber, AccountType accountType, ArrayList<Location> savedLocations, ArrayList<UUID> savedAccounts, ArrayList<String> emergencyContacts, Location currentLocation) {
		this.userID = id;
		this.firstName = firstName;
		this.email = email;
		this.lastName = lastName;
		this.password = passWord;
		this.phoneNumber = phoneNumber;
		this.type = accountType;
		this.savedLocations = savedLocations;
		this.savedAccounts = savedAccounts;
		this.emergencyContact = emergencyContacts;
		this.currentLocation = currentLocation;
	}

	 /**
     * Checks whether the provided login credentials match the account.
     * Email comparison is case-insensitive, while password comparison is case-sensitive.
     * @param username the email address entered by the user
     * @param password the password entered by the user
     * @return true if the credentials match, false otherwise
     */
	public boolean isMatch(String username, String password) {
		if (username == null || password == null) return false;

		return this.email.equalsIgnoreCase(username) && this.password.equals(password);
	}

	/**
     * Adds a phone number to the emergency contact list.
     * Prevents duplicate phone numbers from being added.
     * @param phone the phone number to add
     */
	public void makeContact(int phone) {
		String contact = String.valueOf(phone);

		if (!emergencyContact.contains(contact)) {
			emergencyContact.add(contact);
		}
	}

	/**
     * Removes a phone number from the emergency contact list.
     * @param phone the phone number to remove
     */
	public void removeContact(int phone) {
		emergencyContact.remove(String.valueOf(phone));
	}

	/**
     * Gets the user's first name.
     * @return the user's first name
     */
	public String getFirstName() {
		return firstName;
	}

	/**
     * Gets the user's last name.
     * @return the user's last name
     */
	public String getLastName() {
		return lastName;
	}

	/**
     * Gets the user's email address.
     * @return the user's email address
     */
	public String getEmail() {
		return email;
	}

	/**
     * Gets the user's password.
     * @return the user's password
     */
	public String getPassword() {
		return password;
	}

	 /**
     * Gets the unique identifier associated with the account.
     * @return the account's UUID
     */
	public UUID getID() {
		return userID;
	}

	  /**
     * Gets the user's phone number.
     * @return the user's phone number
     */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	 /**
     * Gets the type of account.
     * @return the account type
     */
	public AccountType getType() {
		return type;
	}

	 /**
     * Gets the list of locations saved by the user.
     * @return the list of saved locations
     */
	public ArrayList<Location> getSavedLocations() {
		return savedLocations;
	}

	/**
     * Gets the list of account IDs saved by the user.
     * @return the list of saved account UUIDs
     */
	public ArrayList<UUID> getSavedAccounts() {
		return savedAccounts;
	}

	/**
     * Gets the user's emergency contact phone numbers.
     * @return the list of emergency contacts
     */
	public ArrayList<String> getEmergencyContact() {
		return emergencyContact;
	}

	/**
     * Gets the user's current location.
     * @return the current location of the user
     */
	public Location getCurrentLocation() {
		return currentLocation;
	}

	/**
     * Gets the account type associated with the user.
     * @return the user's account type
     */
	public AccountType getAccountType(){
		return type;
	}

	/**
     * Updates the user's current location.
     * @param location the new location to assign to the user
     */
	public void setLocation(Location location){
		this.currentLocation = location;
	}

	/**
     * Marks a relief request as completed using its unique ID.
     * Retrieves the request from the relief request list and changes its status to COMPLETED.
     * @param requestID the unique identifier of the relief request
     */
	public void markComplete(UUID requestID) {
		ReliefRequestList.getInstance().getReliefRequest(requestID).changeRequestStatus(Status.COMPLETED);
	}
}