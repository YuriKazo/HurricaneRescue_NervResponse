package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class AccountList {
	private static AccountList accountList;
	private ArrayList<Account> users;

	private AccountList() {
		users = DataLoader.getAccounts();
	}

	public static AccountList getInstance() {
		if (accountList == null) {
			accountList = new AccountList();
		}

		return accountList;
	}

	public Account getAccount(String email, String password) {
		for (Account user : users) {
			if (user.isMatch(email, password)) {
				return user;
			}
		}

		return null;
	}

	public Account getAccount(UUID id) {
		for (Account user : users) {
			if (user.getID().equals(id)) {
				return user;
			}
		}

		return null;
	}

	public boolean haveEmail(String email) {
		for (Account user : users) {
			if (user.getEmail().equalsIgnoreCase(email)) {
				return true;
			}
		}

		return false;
	}

	public boolean addUser(String firstName, String lastName, String email, String passWord) {
		if (firstName == null || lastName == null || email == null || passWord == null) return false;
		if (email.isBlank() || passWord.isBlank()) return false;
		if (haveEmail(email)) return false;

		users.add(new Victim(firstName, email, lastName, passWord));
		saveAccount();
		return true;
	}

	public boolean removeAccount(UUID id) {
		boolean removed = users.removeIf(user -> user.getID().equals(id));

		if (removed) {
			saveAccount();
		}
		return removed;
	}

	public boolean saveAccount() {
		return DataWriter.saveAccounts();
	}

	public ArrayList<Account> getUsers() {
		return users;
	}
}