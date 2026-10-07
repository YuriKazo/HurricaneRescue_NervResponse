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

	public void makeRequest(String description, PriorityLevel level, Location location, int victimCount) {

	}

	public boolean login(String email, String passWord) {
		return true;
	}

	public void logout() {

	}

	public void registerDisabilities() {

	}

	public void registerPets() {

	}

	public Account createAccount(String fN, String lN, String email, String pW) {
		return null;
	}

	public void markComplete() {

	}

	public Hurricane viewHurricane() {
		return null;
	}

	public Account viewAccount(UUID id) {
		return null;
	}

	public void saveAccount(UUID id) {

	}

	public void removeAccount(UUID id) {

	}

	public void makeContact(int phone) {

	}

	public void removeContact(int phone) {

	}

	public ReliefRequest getNearestRequest(UUID id) {
		return null;
	}

	public String getContact(Account account) {
		return "";
	}

	public ArrayList<ReliefRequest> getAvailableRequests() {
		return null;
	}

	public void updateOccupancy(int occupancy) {

	}

	public void updateTotalOccupancy(int totalOccupancy) {

	}

	public void changeRequestStatus(Status status) {

	}

	public void changePriority(PriorityLevel level) {

	}

	public void acceptRequest(UUID id) {

	}

	public void updateWaterLeft(int water) {

	}
}
