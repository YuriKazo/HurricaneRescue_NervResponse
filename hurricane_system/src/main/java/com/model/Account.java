package com.model;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Account {
	protected String firstName;
	protected String lastName;
	protected String email;
	protected String password;
	protected UUID userID;
	protected ArrayList<Location> savedLocations;
	protected ArrayList<Account> savedAccounts;
	protected ArrayList<String> emergencyContact;
	protected Location currentLocation;
	protected AccountType type;

	public Account(String firstName, String email, String lastName, String passWord, AccountType accountType) {
		this.userID = UUID.randomUUID();
		this.firstName = firstName;
		this.email = email;
		this.lastName = lastName;
		this.password = passWord;
		this.type = accountType;
		this.savedLocations = new ArrayList<Location>();
		this.savedAccounts = new ArrayList<Account>();	
		this.emergencyContact = new ArrayList<String>();
		this.currentLocation = new Location(0.0, 0.0, "00000");
	}

	public Account(UUID id, String firstName, String email, String lastName, String passWord, AccountType accountType, ArrayList<Location> savedLocations, ArrayList<Account> savedAccounts, ArrayList<String> emergencyContacts, Location currentLocation) {
		this.userID = id;
		this.firstName = firstName;
		this.email = email;
		this.lastName = lastName;
		this.password = passWord;
		this.type = accountType;
		this.savedLocations = savedLocations;
		this.savedAccounts = savedAccounts;
		this.emergencyContact = emergencyContacts;
		this.currentLocation = currentLocation;
	}

	public boolean isMatch(String username, String password) {
		if (username == null || password == null) return false;

		return this.email.equalsIgnoreCase(username) && this.password.equals(password);
	}

	public void makeRequest(String description, PriorityLevel level, Location location, int victimCount) {
		ReliefRequestList.getInstance().addReliefRequest(description, level, location, victimCount); 
	}

	public void makeContact(int phone) {
		String contact = String.valueOf(phone);

		if (!emergencyContact.contains(contact)) {
			emergencyContact.add(contact);
		}
	}

	public void removeContact(int phone) {
		emergencyContact.remove(String.valueOf(phone));
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public UUID getID() {
		return userID;
	}

	public AccountType getType() {
		return type;
	}

	public ArrayList<Location> getSavedLocations() {
		return savedLocations;
	}

	public ArrayList<Account> getSavedAccounts() {
		return savedAccounts;
	}

	public ArrayList<String> getEmergencyContact() {
		return emergencyContact;
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}
}