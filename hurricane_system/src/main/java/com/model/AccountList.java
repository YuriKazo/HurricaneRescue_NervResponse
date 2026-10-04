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
		return null;
	}

	public Account getAccount(UUID id) {
		return null;
	}

	public boolean addUser(String firstName, String lastName, String email, String passWord) {
		return true;
	}

	public boolean saveAccount() {
		return true;
	}

	public ArrayList<Account> getUsers(){
		return users;
	}
}
