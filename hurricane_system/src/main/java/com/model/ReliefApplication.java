package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class ReliefApplication {
	private static ReliefApplication reliefApplication;
	private AccountList accountList;
	private ShelterList shelterList;
	private ReliefRequestList reliefRequestList;
	private Account currentAccount;
	private Hurricane currentHurricane;
	private Shelter currentShelter;
	private ReliefRequest currentRequest;
	private boolean adminFlag;
	private boolean volunteerFlag;
	private Hurricane activeHurricane;

	private ReliefApplication() {
		accountList = AccountList.getInstance();
		shelterList = ShelterList.getInstance();
		reliefRequestList = ReliefRequestList.getInstance();
	}

	public static ReliefApplication getInstance() {
		if (reliefApplication == null) {
			reliefApplication = new ReliefApplication();
		}

		return reliefApplication;
	}

	private boolean isLoggedIn() {
		return currentAccount != null;
	}

	public void makeRequest(String description, PriorityLevel level, Location location, int victimCount) {
		if (!isLoggedIn()) return;

		if (reliefRequestList.addReliefRequest(description, level, location, victimCount)) {
			reliefRequestList.saveReliefRequest();
		}
	}

	public boolean login(String email, String passWord) {
		Account account = accountList.getAccount(email, passWord);
		if (account == null) return false;

		currentAccount = account;
		adminFlag = account instanceof Admin;
		volunteerFlag = account instanceof Volunteer;
		return true;
	}

	public void logout() {
		currentAccount = null;
		currentShelter = null;
		currentRequest = null;
		adminFlag = false;
		volunteerFlag = false;
	}

	public void registerDisabilities() {
		if (currentAccount instanceof Victim) {
			((Victim) currentAccount).registerDisabilities();
			accountList.saveAccount();
		}
	}

	public void registerPets() {
		if (currentAccount instanceof Victim) {
			((Victim) currentAccount).setPets(true);
			accountList.saveAccount();
		}
	}

	public Account createAccount(String fN, String lN, String email, String pW) {
		if (!accountList.addUser(fN, lN, email, pW)) return null;

		return accountList.getAccount(email, pW);
	}

	public void markComplete() {
		if (currentAccount instanceof Victim) {
			((Victim) currentAccount).markComplete();
		} else if (currentAccount instanceof Volunteer) {
			((Volunteer) currentAccount).markComplete();
		}

		if (currentRequest != null) {
			currentRequest.markComplete();
			reliefRequestList.saveReliefRequest();
		}
	}

	public Hurricane viewHurricane() {
		return activeHurricane;
	}

	public Account viewAccount(UUID id) {
		return accountList.getAccount(id);
	}

	public void saveAccount(UUID id) {
		if (accountList.getAccount(id) == null) return;

		accountList.saveAccount();
	}

	public void removeAccount(UUID id) {
		if (!isLoggedIn()) return;

		// admins can remove anyone; everyone else can only remove themselves
		if (!adminFlag && !currentAccount.getID().equals(id)) return;

		if (accountList.removeAccount(id) && currentAccount.getID().equals(id)) {
			logout();
		}
	}

	public void makeContact(int phone) {
		if (!isLoggedIn()) return;

		currentAccount.makeContact(phone);
		accountList.saveAccount();
	}

	public void removeContact(int phone) {
		if (!isLoggedIn()) return;

		currentAccount.removeContact(phone);
		accountList.saveAccount();
	}

	public ReliefRequest getNearestRequest(UUID id) {
		Account account = accountList.getAccount(id);

		if (account instanceof Volunteer) {
			return ((Volunteer) account).getNearestRequest();
		}
		return null;
	}

	public String getContact(Account account) {
		if (currentAccount instanceof Volunteer) {
			return ((Volunteer) currentAccount).getContact(account);
		}
		return "";
	}

	public ArrayList<ReliefRequest> getAvailableRequests() {
		return reliefRequestList.getReliefRequest();
	}

	public void updateOccupancy(Shelter shelter, int occupancy) {
		if (!adminFlag) return;

		((Admin) currentAccount).updateOccupancy(shelter, occupancy);
		shelterList.saveShelter();
	}

	public void updateTotalOccupancy(Shelter shelter, int totalOccupancy) {
		if (!adminFlag) return;

		((Admin) currentAccount).updateTotalOccupancy(shelter, totalOccupancy);
		shelterList.saveShelter();
	}

	public void changeRequestStatus(Status status) {
		if (currentRequest == null) return;

		currentRequest.changeRequestStatus(status);
		reliefRequestList.saveReliefRequest();
	}

	public void changePriority(PriorityLevel level) {
		if (currentRequest == null) return;

		currentRequest.changePriority(level);
		reliefRequestList.saveReliefRequest();
	}

	public void acceptRequest(UUID id) {
		if (!volunteerFlag) return;

		ReliefRequest request = reliefRequestList.getReliefRequest(id);
		if (request == null) return;

		currentRequest = request;
		currentRequest.changeRequestStatus(Status.ACCEPTED);
		reliefRequestList.saveReliefRequest();
	}

	public void updateWaterLeft(Shelter shelter, int water) {
		if (!adminFlag) return;

		((Admin) currentAccount).updateWaterLeft(shelter, water);
		shelterList.saveShelter();
	}
}