package com.model;
import java.util.UUID;

import com.location.Address;
import com.location.MapTools;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * A console driver that runs the relief scenarios for the hurricane system, such as
 * making a request, matching volunteers, finding shelters, and exporting a trip.
 * @author NervResponse
 */
public class ReliefUI {
	private ReliefApplication application;

	ReliefUI() {
		application = ReliefApplication.getInstance();
	}

	public void run() {
		
		createReliefRequestScenario();  // check account_temp.json, reliefrequest_temp.json
		matchRequestScenario();			
		findShelterAcceptRequestScenario(); 
		exportTripScenario();			// txt file
		rolePermissionScenario();		
		checkFamilyIntoShelterBranch();	// shelter.json, relief request temp json
		
	}


	/**
	 * Creates a victim account, logs in, and makes a relief request that needs a car
	 * and medical assistance
	 */
	public void createReliefRequestScenario() {
		if (application.createAccount("Sophred", "Carter", "BSophred@email.com", "word", "111-222-3333") != null) {
			System.out.println("Account created for Sophred Banks");
			if(application.login("BSophred@email.com", "word"))
				System.out.println("Login Successful for Sophred");
			application.updateLocation(new Location(-79.9374, 32.7787, "29401"));
			ArrayList<Abilities> abilities = new ArrayList<>();
			abilities.add(Abilities.CAR);
			abilities.add(Abilities.MEDICAL_ASSISTANCE);
			application.makeRequest("Transportation for three, we also have a dog, and require medical assistance for our 9 year old daughter Sophie, and we are at 128 Franklin St", PriorityLevel.HIGH, 3, abilities);
			//System.out.println(application.viewUSerReliefRequest()); this pulls from request.Json but we are currently
						                                            // inputing information to requestTemp.Json for
																	// safety porpoises so this doesn't work right now
		}

	}

	/**
	 * Logs in as a volunteer, finds the victim's request, and writes the trip
	 * information (family, contact info, directions, and shelter) to Trip.txt
	 */
	public void exportTripScenario() {
		application.login("rdelgado@email.com", "password");
		System.out.println("Rosa is now logged in");
 
		ReliefRequest request = null;
		Account carter = null;
		for (ReliefRequest item : application.getAvailableRequests()) {
			Account asker = application.viewAccount(item.getRequestorID());
			if (asker != null && asker.getEmail().equals("BSophred@email.com")) {
				request = item;
				carter = asker;
			}
		}
		if (request == null) {
			System.out.println("Rosa could not find the Carter request");
			return;
		}
 
		Address rosaAddress = new Address("1 Vendue Range", "Charleston", "SC", "29401");
		Address carterHome = new Address("128 Franklin Street", "Charleston", "SC", "29401");
		Address shelter = new Address("2950 Carner Avenue", "North Charleston", "SC", "29405");
 
		String trip = "HURRICANE RELIEF TRIP INFORMATION"
		+"\n\nFAMILY MEMBERS"
		+"\n"+carter.getFirstName()+" "+carter.getLastName()+", age 42"
		+"\nMaria Carter, age 40"
		+"\nSophie Carter, age 9"
		+"\nBuddy, dog"
		+"\n\nCONTACT INFORMATION"
		+"\nPhone: "+carter.getPhoneNumber()
		+"\nEmail: "+carter.getEmail()
		+"\nPickup address: "+carterHome;
 
		if (request.getWantedAbilities().toString().contains("MEDICAL_ASSISTANCE"))
			trip += "\n\nMEDICAL NOTE\nSophie needs medical assistance.";
 
		try {
			trip += "\n\nDIRECTIONS FROM ROSA'S LOCATION TO THE CARTER HOME"
			+"\nRosa's location: "+rosaAddress;
			for (String direction : MapTools.getInstance().getDrivingDirections(rosaAddress, carterHome))
				trip += "\n"+direction;
 
			trip += "\n\nDIRECTIONS FROM THE CARTER HOME TO THE SHELTER"
			+"\nClosest shelter: "+shelter;
			for (String direction : MapTools.getInstance().getDrivingDirections(carterHome, shelter))
				trip += "\n"+direction;
 
			trip += "\n\nSHELTER\nName: Military Magnet\nAddress: "+shelter;
 
			PrintWriter file = new PrintWriter("Trip.txt");
			file.print(trip);
			file.close();
			System.out.println(trip);
		} catch (Exception e) {
			System.out.println("There was a problem exporting the trip information: "+e);
		}
	}

	/**
	 * Logs in as a volunteer and prints the available requests ranked by how well they
	 * match the volunteer's abilities and distance
	 */
	public void matchRequestScenario(){
		application.login("rosad@email.com", "password123");
		System.out.println("Avaliable Requests:");
		int i = 1;
		ArrayList<RankedRequest> requests = application.viewRequests();
		System.out.println("Top result based on distance and abilities");
		for(RankedRequest request: requests){
			System.out.println(i++ + ". " +  "Request description: " + request.getRequest().getRequestDescription());
			String message = request.getMessage();
			if (!message.isEmpty()) {
				System.out.println("   " + message.trim());
			}
			System.out.println();
		}
		ReliefRequest reliefRequest= requests.get(0).getRequest();
		System.out.println("Request description: " + reliefRequest.getRequestDescription() + "\nVictim Count:" + reliefRequest.getVictimCount());
		System.out.println("Wanted abilities:");
		for(Abilities abilities: reliefRequest.getWantedAbilities()){
			System.out.print(abilities.name().toLowerCase() + " ");
		}
		System.out.println("\n");
	}

	/**
	 * Logs in as a volunteer, finds the nearest shelter that fits the family's needs,
	 * and has the volunteer assign themselves to the top request
	 */
	public void findShelterAcceptRequestScenario(){
		application.login("rosad@email.com", "password123");
		ArrayList<Capabilities> capabilities = new ArrayList<>();
		capabilities.add(Capabilities.PETS_ALLOWED);
		capabilities.add(Capabilities.MEDICAL_ASSISTANCE);
		Shelter shelter = application.viewNearestSuitableShelter(3, capabilities);
		double distance = 0;
		try{
			distance = MapTools.getInstance().getDrivingDistance(application.viewAccount().getCurrentLocation(), shelter.getLocation());
		}
		catch(Exception e){
			System.err.println(e);
		}
		System.out.println("Nearest suitable shelter: " + shelter.getStreet());
		System.out.printf("Driving distance: %.2f miles. \n", distance/1609.344);
		System.out.print("Capabilities: ");
		for(Capabilities capability: shelter.getCapabilities()){
			System.out.println(capability.name().toLowerCase().replace('_', ' '));
		}
		System.out.println();
		System.out.println();
		ReliefRequest request = application.viewRequests().get(0).getRequest();
		Account requester = application.viewAccount(request.getRequestorID());
		System.out.println("Self-assigning to request");
		((Volunteer)application.viewAccount()).setCurrentTarget(request);
		System.out.println("Accepted " + requester.getFirstName() + " " + requester.getLastName() +  "'s Request");
		System.out.println();
	}

	/**
	 * Shows that a volunteer can accept a request but is not allowed to mark it complete
	 */
	public void rolePermissionScenario() {
		application.login("rosad@email.com", "password123");
		System.out.println("Rosa is now logged in");
		Volunteer rosa = (Volunteer) application.viewAccount();
 
		ReliefRequest request = null;
		for (ReliefRequest item : application.getAvailableRequests()) {
			Account asker = application.viewAccount(item.getRequestorID());
			if (asker != null && asker.getEmail().equals("BSophred@email.com"))
				request = item;
		}
		if (request == null) {
			System.out.println("Rosa could not find the Carter request");
			return;
		}
		application.acceptRequest(request.getID());
		rosa.setCurrentTarget(request);
		System.out.println("Request status: "+request.getStatus()+", assigned to "+rosa.getFirstName()+" "+rosa.getLastName());
 
		System.out.println("\nRosa tries to mark the request Complete");
		application.markComplete();
 
		System.out.println("\nRequest status: "+request.getStatus()
		+"\nAssigned to: "+(rosa.getCurrentTarget() == request ? rosa.getFirstName()+" "+rosa.getLastName() : "nobody"));
	}

	/**
	 * Logs in as a shelter admin, prints the admin's shelters and occupants, checks
	 * three people into a shelter, and marks a request complete
	 */
	public void checkFamilyIntoShelterBranch(){ 
		if (application.login("felixk@email.com", "password")){
			System.out.println("Login successful for Felix");
			for (UUID shelterID : application.viewMyShelters()) {
				System.out.println("Shelter Street: "+application.viewShelter(shelterID).getStreet());
				System.out.println("Shelter Location: "+application.viewShelter(shelterID).getLocation());
				System.out.println("Current Occupancy: "+application.viewShelter(shelterID).getCurrentOccupancy());
				System.out.println("Max Occupancy: "+application.viewShelter(shelterID).getTotalOccupancy());
				int i = 1;
				for (UUID occupantID : application.viewShelter(shelterID).getOccupantList()) {
					System.out.println("Occupant "+i+": "+application.viewAccount(occupantID).getFirstName()+" "+application.viewAccount(occupantID).getLastName());
					i++;
				}
			}
			
			application.viewShelter(UUID.fromString("8c860493-3fb8-48ab-8127-beaf991340b3")).addOccupant(UUID.fromString("d60ebda6-bd56-423d-ac0f-dc13a8b58afb"));
			application.viewShelter(UUID.fromString("8c860493-3fb8-48ab-8127-beaf991340b3")).addOccupant(UUID.fromString("c55960c5-c9ad-4781-bc98-c59fa5239bdc"));
			application.viewShelter(UUID.fromString("8c860493-3fb8-48ab-8127-beaf991340b3")).addOccupant(UUID.fromString("d9dacd20-206b-488a-87b3-09672e6a7b64"));
			int j = 1;
			for (UUID occupantID : application.viewShelter(UUID.fromString("8c860493-3fb8-48ab-8127-beaf991340b3")).getOccupantList()) {
					System.out.println("Occupant "+j+": "+application.viewAccount(occupantID).getFirstName()+" "+application.viewAccount(occupantID).getLastName());
					j++;
				}
			application.markComplete(UUID.fromString("18ba33c2-6627-4bba-810f-ac29c40fac42"));

		}
	}

	public static void main(String[] args) {
		ReliefUI reliefInterface = new ReliefUI();
		reliefInterface.run();
	}
}
