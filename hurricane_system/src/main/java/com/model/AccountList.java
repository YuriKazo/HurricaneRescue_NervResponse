// @author NervResponse 
package com.model;

import java.util.ArrayList;
import java.util.UUID;


/*
 * Manages the list of user accounts in the NervResponse relief management system.
 * Provides methods for retrieving accounts, checking existing emails, adding and removing users, and saving account information.
 * Uses the Singleton design pattern to maintain a single account list.
 */
public class AccountList {
	private static AccountList accountList;
	private ArrayList<Account> users;

	private AccountList() {
		users = DataLoader.getAccounts();
	}

	/**
     * Constructs the account list by loading existing user accounts from the data storage system.
     * Private constructor prevents multiple instances from being created.
     */
	public static AccountList getInstance() {
		if (accountList == null) {
			accountList = new AccountList();
		}

		return accountList;
	}

	/**
     * Searches for a user account matching the provided login credentials.
     * @param email the email address associated with the account
     * @param password the password associated with the account
     * @return the matching account, or null if no match is found
     */
	public Account getAccount(String email, String password) {
		for (Account user : users) {
			if (user.isMatch(email, password)) {
				return user;
			}
		}

		return null;
	}

	/**
     * Searches for a user account using its unique identifier.
     * @param id the unique identifier of the account
     * @return the matching account, or null if no account is found
     */
	public Account getAccount(UUID id) {
		for (Account user : users) {
			if (user.getID().equals(id)) {
				return user;
			}
		}

		return null;
	}

	/**
     * Checks whether an email address is already associated with an existing account.
     * Email comparison is case-insensitive.
     * @param email the email address to check
     * @return true if the email already exists, false otherwise
     */
	public boolean haveEmail(String email) {
		for (Account user : users) {
			if (user.getEmail().equalsIgnoreCase(email)) {
				return true;
			}
		}

		return false;
	}

	/**
     * Creates and adds a new victim account to the account list.
     * Validates the provided information and prevents duplicate emails.
     * Saves the updated account list after successfully adding the user.
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param email the user's email address
     * @param passWord the user's password
     * @param phoneNumber the user's phone number
     * @return true if the account was added, false if validation fails or the email already exists
     */
	public boolean addUser(String firstName, String lastName, String email, String passWord, String phoneNumber) {
		if (firstName == null || lastName == null || email == null || passWord == null) return false;
		if (email.isBlank() || passWord.isBlank()) return false;
		if (haveEmail(email)) return false;

		users.add(new Victim(firstName, email, lastName, passWord, phoneNumber));
		saveAccount();
		return true;
	}

	/**
     * Removes a user account from the account list using its unique ID.
     * Saves the updated account list if an account is successfully removed.
     * @param id the unique identifier of the account to remove
     * @return true if the account was removed, false if no matching account was found
     */
	public boolean removeAccount(UUID id) {
		boolean removed = users.removeIf(user -> user.getID().equals(id));

		if (removed) {
			saveAccount();
		}
		return removed;
	}

	/**
     * Saves the current list of accounts to a data file.
     * @return true if the save operation was successful, false otherwise
     */
	public boolean saveAccount() {
		return DataWriter.saveAccounts();
	}

	/**
     * Retrieves the list of all user accounts.
     * @return an ArrayList containing all user accounts
     */
	public ArrayList<Account> getUsers() {
		return users;
	}
}