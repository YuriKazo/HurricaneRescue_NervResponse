package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * The main entry point (facade) into the hurricane relief model. It is a singleton
 * that coordinates the account, shelter, and relief request lists, keeps track of
 * who is currently logged in, and enforces role based permissions.
 * @author NervResponse
 */
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

	/**
	 * Creates the application and loads the account, shelter, and relief request lists.
	 */
	private ReliefApplication() {
		accountList = AccountList.getInstance();
		shelterList = ShelterList.getInstance();
		reliefRequestList = ReliefRequestList.getInstance();
	}

	/**
	 * Gets the one instance of the application creating it the first time it is called
	 * @return The single ReliefApplication instance
	 */
	public static ReliefApplication getInstance() {
		if (reliefApplication == null) {
			reliefApplication = new ReliefApplication();
		}

		return reliefApplication;
	}

	/**
	 * Checks whether an account is currently logged in
	 * @return true if someone is logged in, false otherwise
	 */
	private boolean isLoggedIn() {
		return currentAccount != null;
	}

	/**
	 * Creates a new relief request at the logged in user's current location and saves it.
	 * Does nothing if no one is logged in.
	 * @param description A description of the help that is needed
	 * @param level How urgent the request is
	 * @param victimCount The number of people the request is for
	 * @param wantedAbilities The abilities a volunteer should have to help
	 */
	public void makeRequest(String description, PriorityLevel level, int victimCount, ArrayList<Abilities> wantedAbilities) {
		if (!isLoggedIn()) return;

		if (reliefRequestList.addReliefRequest(description, level, currentAccount.getCurrentLocation(), victimCount, currentAccount.getID(), wantedAbilities)) {
			reliefRequestList.saveReliefRequest();
		}
	}

	/**
	 * Logs in the account matching the given email and password and records whether
	 * it is an admin or a volunteer
	 * @param email The email of the account
	 * @param passWord The password of the account
	 * @return true if the login succeeded, false if no account matched
	 */
	public boolean login(String email, String passWord) {
		Account account = accountList.getAccount(email, passWord);
		if (account == null) return false;

		currentAccount = account;
		adminFlag = account instanceof Admin;
		volunteerFlag = account instanceof Volunteer;
		return true;
	}

	/**
	 * Logs out the current account and clears the current shelter, current request,
	 * and role flags
	 */
	public void logout() {
		currentAccount = null;
		currentShelter = null;
		currentRequest = null;
		adminFlag = false;
		volunteerFlag = false;
	}

	/**
	 * Adds a disability to the logged in victim's account and saves it.
	 * Does nothing if the current account is not a victim.
	 * @param disability The disability to register
	 */
	public void registerDisabilities(Disabilities disability) {
		if (currentAccount instanceof Victim) {
			((Victim) currentAccount).setDisabilities(disability);
			accountList.saveAccount();
		}
	}

	/**
	 * Marks that the logged in victim has pets and saves the account.
	 * Does nothing if the current account is not a victim.
	 */
	public void registerPets() {
		if (currentAccount instanceof Victim) {
			((Victim) currentAccount).setPets(true);
			accountList.saveAccount();
		}
	}

	/**
	 * Creates a new account and returns it
	 * @param fN The first name of the new user
	 * @param lN The last name of the new user
	 * @param email The email of the new user
	 * @param pW The password of the new user
	 * @param pN The phone number of the new user
	 * @return The newly created account, or null if it could not be created
	 */
	public Account createAccount(String fN, String lN, String email, String pW, String pN) {
		if (!accountList.addUser(fN, lN, email, pW, pN)) return null;

		return accountList.getAccount(email, pW);
	}
	
	/**
	 * Updates the current location of the logged in account
	 * @param location The account's new location
	 */
	public void updateLocation(Location location){
		currentAccount.setLocation(location);
	}

	/**
	 * Marks the current relief request complete for the logged in account.
	 * Volunteers are not allowed to do this and are shown a permission denied message.
	 */
	public void markComplete() {
		if (currentAccount instanceof Volunteer) {
			System.out.println("Permission denied: the volunteer role does not have permission to complete relief requests.");
			return;
		}

		if (currentAccount instanceof Victim) {
			((Victim) currentAccount).markComplete();
		} else if (currentAccount instanceof Volunteer) {
			((Volunteer) currentAccount).markComplete(); 
		}
	}

	/**
	 * Marks the given relief request complete for the logged in account and saves the
	 * change. Volunteers are not allowed to do this and are shown a permission denied message.
	 * @param requestID The id of the request to mark complete
	 */
	public void markComplete(UUID requestID) {
		if (currentAccount instanceof Volunteer) {
			System.out.println("Permission denied: the volunteer role does not have permission to complete relief requests.");
			return;
		}

		if (currentAccount instanceof Victim) {
			((Victim) currentAccount).markComplete(requestID);
		} else if (currentAccount instanceof Volunteer) {
			((Volunteer) currentAccount).markComplete(requestID);
		}

		if (currentRequest != null) {
			currentRequest.markComplete();
			reliefRequestList.saveReliefRequest();
		}
	}

	/**
	 * Gets the hurricane that is currently active
	 * @return The active hurricane
	 */
	public Hurricane viewHurricane() {
		return activeHurricane;
	}

	/**
	 * Looks up an account by its id
	 * @param id The id of the account
	 * @return The matching account, or null if there is none
	 */
	public Account viewAccount(UUID id) {
		return accountList.getAccount(id);
	}

	/**
	 * Gets the account that is currently logged in
	 * @return The current account, or null if no one is logged in
	 */
	public Account viewAccount() {
		return currentAccount;
	}

	/**
	 * Gets the shelters in a zip code
	 * @param zipCode The zip code to search
	 * @return The shelters in that zip code, or null if there are none
	 */
	public ArrayList<Shelter> viewShelters(int zipCode) {
		return shelterList.getShelters(zipCode);
	}

	/**
	 * Finds the closest shelter to the current user that has room for the given number
	 * of people and has all of the needed capabilities
	 * @param victimCount The number of people who need space
	 * @param capabilities The capabilities the shelter must have
	 * @return The nearest suitable shelter, or null if none was found
	 */
	public Shelter viewNearestSuitableShelter(int victimCount, ArrayList<Capabilities> capabilities){
		return shelterList.getNearestShelter(currentAccount.getCurrentLocation(), shelterList.getSuitableShelters(victimCount, capabilities));
	}

	/**
	 * Gets the relief requests that belong to the logged in user
	 * @return A list of the user's relief requests
	 */
	public ArrayList<ReliefRequest> viewUSerReliefRequest() {
		return reliefRequestList.getUserReliefRequests(currentAccount.getID());
	}

	/**
	 * Looks up a shelter by its id
	 * @param id The id of the shelter
	 * @return The matching shelter, or null if there is none
	 */
	public Shelter viewShelter(UUID id) {
		return shelterList.getShelter(id);
	}

	/**
	 * Gets the ids of the shelters managed by the logged in admin
	 * @return The admin's shelter ids, or null if the current account is not an admin
	 */
	public ArrayList<UUID> viewMyShelters() {
		if (!adminFlag) return null;
		ArrayList<UUID> toReturn = new ArrayList<>();
		if (currentAccount instanceof Admin) {
			toReturn = ((Admin) currentAccount).getShelterList();
		}
		return toReturn;
	}

	/**
	 * Saves the accounts to file if an account with the given id exists
	 * @param id The id of the account
	 */
	public void saveAccount(UUID id) {
		if (accountList.getAccount(id) == null) return;

		accountList.saveAccount();
	}

	/**
	 * Gets the available relief requests. Volunteers get them ranked by how well the
	 * requests match their abilities and distance; everyone else gets them unranked.
	 * @return A list of ranked requests
	 */
	public ArrayList<RankedRequest> viewRequests(){
		if(currentAccount.getAccountType() != AccountType.VOLUNTEER){
			ArrayList<RankedRequest> rankedRequests = new ArrayList<>();
			for(ReliefRequest request: reliefRequestList.getReliefRequest())
				rankedRequests.add(new RankedRequest(request));
			return rankedRequests;
		}
		return reliefRequestList.matchRequests(((Volunteer)currentAccount).getAbilities(), currentAccount.getCurrentLocation());
	}

	/**
	 * Removes an account. Admins can remove any account, and everyone else can only
	 * remove their own. If the logged in user removes themselves they are logged out.
	 * @param id The id of the account to remove
	 */
	public void removeAccount(UUID id) {
		if (!isLoggedIn()) return;

		// admins can remove anyone; everyone else can only remove themselves
		if (!adminFlag && !currentAccount.getID().equals(id)) return;

		if (accountList.removeAccount(id) && currentAccount.getID().equals(id)) {
			logout();
		}
	}

	/**
	 * Adds a phone number to the logged in account's contacts and saves the account
	 * @param phone The phone number to add
	 */
	public void makeContact(int phone) {
		if (!isLoggedIn()) return;

		currentAccount.makeContact(phone);
		accountList.saveAccount();
	}

	/**
	 * Removes a phone number from the logged in account's contacts and saves the account
	 * @param phone The phone number to remove
	 */
	public void removeContact(int phone) {
		if (!isLoggedIn()) return;

		currentAccount.removeContact(phone);
		accountList.saveAccount();
	}

	/**
	 * Gets the nearest relief request for a volunteer
	 * @param id The id of the volunteer's account
	 * @return The nearest request, or null if the account is not a volunteer
	 */
	public ReliefRequest getNearestRequest(UUID id) {
		Account account = accountList.getAccount(id);

		if (account instanceof Volunteer) {
			return ((Volunteer) account).getNearestRequest();
		}
		return null;
	}

	/**
	 * Gets the contact information of an account. Only volunteers can view it.
	 * @param account The account whose contact information is wanted
	 * @return The contact information, or an empty string if the current user is not a volunteer
	 */
	public String getContact(Account account) {
		if (currentAccount instanceof Volunteer) {
			return ((Volunteer) currentAccount).getContact(account);
		}
		return "";
	}

	/**
	 * Gets the relief requests that have not been accepted yet
	 * @return A list of available relief requests
	 */
	public ArrayList<ReliefRequest> getAvailableRequests() {
		return reliefRequestList.getReliefRequest();
	}

	/**
	 * Updates how many people are in a shelter and saves it. Admin only.
	 * @param shelterID The id of the shelter
	 * @param occupancy The new current occupancy
	 */
	public void updateOccupancy(UUID shelterID, int occupancy) {
		if (!adminFlag) return;

		((Admin) currentAccount).updateOccupancy(shelterID, occupancy);
		shelterList.saveShelter();
	}

	/**
	 * Creates a new shelter and adds it to the logged in admin's shelters. Admin only.
	 * @param longitude The longitude of the shelter
	 * @param latittude The latitude of the shelter
	 * @param zip The zip code of the shelter
	 * @param street The street address of the shelter
	 * @return The id of the new shelter, or null if the current account is not an admin
	 */
	public UUID makeShelter(double longitude, double latittude, String zip, String street) {
		if (!adminFlag) return null; 

		UUID id = shelterList.addShelter(longitude, latittude, zip, street);
		((Admin) currentAccount).addShelter(id);
		return id; 
	}

	/**
	 * Updates the maximum number of people a shelter can hold and saves it. Admin only.
	 * @param shelterID The id of the shelter
	 * @param totalOccupancy The new maximum occupancy
	 */
	public void updateTotalOccupancy(UUID shelterID, int totalOccupancy) {
		if (!adminFlag) return;

		((Admin) currentAccount).updateTotalOccupancy(shelterID, totalOccupancy);
		shelterList.saveShelter();
	}

	/**
	 * Changes the status of the current relief request and saves it
	 * @param status The new status
	 */
	public void changeRequestStatus(Status status) {
		if (currentRequest == null) return;

		currentRequest.changeRequestStatus(status);
		reliefRequestList.saveReliefRequest();
	}

	/**
	 * Changes the priority of the current relief request and saves it
	 * @param level The new priority level
	 */
	public void changePriority(PriorityLevel level) {
		if (currentRequest == null) return;

		currentRequest.changePriority(level);
		reliefRequestList.saveReliefRequest();
	}

	/**
	 * Lets a volunteer accept a relief request, making it the current request and
	 * setting its status to accepted. Volunteer only.
	 * @param id The id of the request to accept
	 */
	public void acceptRequest(UUID id) {
		if (!volunteerFlag) return;

		ReliefRequest request = reliefRequestList.getReliefRequest(id);
		if (request == null) return;

		currentRequest = request;
		currentRequest.changeRequestStatus(Status.ACCEPTED);
		
		reliefRequestList.saveReliefRequest();
	}

	/**
	 * Updates how much water a shelter has left and saves it. Admin only.
	 * @param id The id of the shelter
	 * @param water The new amount of water
	 */
	public void updateWaterLeft(UUID id, int water) {
		if (!adminFlag) return;

		((Admin) currentAccount).updateWaterLeft(id, water);
		shelterList.saveShelter();
	}

}