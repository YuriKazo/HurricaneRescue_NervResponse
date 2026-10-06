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
	}

	public Account(UUID id, String firstName, String email, String lastName, String passWord, AccountType accountType) {
		this.userID = id;
		this.firstName = firstName;
		this.email = email;
		this.lastName = lastName;
		this.password = passWord;
		this.type = accountType;
	}

	public boolean isMatch(String username, String password) {
		return true;
	}

	public void makeContact(int phone) {

	}

	public void removeContact(int phone) {

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
