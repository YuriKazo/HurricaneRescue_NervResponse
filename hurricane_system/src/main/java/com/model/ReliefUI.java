package com.model;
import java.util.UUID;

import com.location.Address;
import com.location.MapTools;

import java.io.PrintWriter;
import java.util.ArrayList;

public class ReliefUI {
	private ReliefApplication application;

	ReliefUI() {
		application = ReliefApplication.getInstance();
	}

	public void run() {
		//loginScenario();
		//logOutScenario();
		//signUpScenario();
		//getSheltersFromZipCodeScenario();
		//createShelterScenario();
		createReliefRequestScenario();
		exportTripScenario();
		matchRequestScenario();
		rolePermissionScenario();
		findShelterAcceptRequestScenario();
	}

	
	public void loginScenario() {

		if (application.login("timmy@gmail.com", "password")) {
			System.out.println("Timmy is now logged in");
		}else {
			System.out.println("Login failed for Timmy");
		}

		if (application.login("tbradley@gmail.com", "stormcat7")) {
			System.out.println("TOM is now logged in");
		}else {
			System.out.println("Login failed for TOM");
		}
		System.out.println("User Email: "+application.viewAccount().getEmail()
		+"\nUser Name: "+application.viewAccount().getFirstName()+" "+application.viewAccount().getLastName()
		+"\nAccount Type: "+application.viewAccount().getAccountType());
	}

	public void signUpScenario() {
		if (application.createAccount("Luke", "Skywalker", "jlogano@email.com", "word", "999-888-7777") == null) {
			System.out.println("That email is already registered.");
		}
		if (application.createAccount("Luke", "Skywalker", "luke@email.com", "word", "666-777-9999") != null) {
			System.out.println("Account created for Luke Skywalker");
		}

		System.out.println("User Email: "+application.viewAccount().getEmail()
		+"\nUser Name: "+application.viewAccount().getFirstName()+" "+application.viewAccount().getLastName()
		+"\nAccount Type: "+application.viewAccount().getAccountType());

		application.login("luke@email.com", "word");

		System.out.println("User Email: "+application.viewAccount().getEmail()
		+"\nUser Name: "+application.viewAccount().getFirstName()+" "+application.viewAccount().getLastName()
		+"\nAccount Type: "+application.viewAccount().getAccountType());
	}

	public void logOutScenario() {
		if (application.login("tbradley@gmail.com", "stormcat7")) {
			System.out.println("TOM is now logged in");
		}else {
			System.out.println("Login failed for TOM");
		}
		System.out.println("User Email: "+application.viewAccount().getEmail()
		+"\nUser Name: "+application.viewAccount().getFirstName()+" "+application.viewAccount().getLastName()
		+"\nAccount Type: "+application.viewAccount().getAccountType());

		application.logout();

		System.out.println("User Email: "+application.viewAccount().getEmail()
		+"\nUser Name: "+application.viewAccount().getFirstName()+" "+application.viewAccount().getLastName()
		+"\nAccount Type: "+application.viewAccount().getAccountType());
	}

	public void getSheltersFromZipCodeScenario() {
		ArrayList<Shelter> list1 = application.viewShelters(29293);
		if (list1.size() > 0) {
			for (Shelter item : list1) {
				System.out.println("Total Occupancy: "+item.getTotalOccupancy()
				+"\nCurrent Occupancy: "+item.getCurrentOccupancy()
				+"\nStreet: "+item.getStreet()
				+"\nCapabilities: "+item.getCapabilities());
			}
		}else
			System.out.println("No shelter found at this zip code");
		ArrayList<Shelter> list2 = application.viewShelters(19999);
		if (list1.size() > 0) {
			for (Shelter item : list2) {
				System.out.println("Total Occupancy: "+item.getTotalOccupancy()
				+"\nCurrent Occupancy: "+item.getCurrentOccupancy()
				+"\nStreet: "+item.getStreet()
				+"\nCapabilities: "+item.getCapabilities());
			}
		}else
			System.out.println("No shelter found at this zip code");
	}

	public void createShelterScenario() {
		application.login("blagano@email.com", "password");

		System.out.println("User Email: "+application.viewAccount().getEmail()
		+"\nUser Name: "+application.viewAccount().getFirstName()+" "+application.viewAccount().getLastName()
		+"\nAccount Type: "+application.viewAccount().getAccountType());
		
		UUID id = application.makeShelter(78.111, 34.349, "12356", "Negra Arroyo Lane");
		application.viewShelter(id);
				System.out.println("Total Occupancy: "+application.viewShelter(id).getTotalOccupancy()
				+"\nCurrent Occupancy: "+application.viewShelter(id).getCurrentOccupancy()
				+"\nStreet: "+application.viewShelter(id).getStreet()
				+"\nCapabilities: "+application.viewShelter(id).getCapabilities());
		
		application.updateWaterLeft(id, 70);
		
	}

	public void createReliefRequestScenario() {
		if (application.createAccount("Sophred", "Carter", "BSophred@email.com", "word", "111-222-3333") != null) {
			System.out.println("Account created for Sophred Banks");
			if(application.login("BSophred@email.com", "word"))
				System.out.println("Login Successful for Sophred");
			
			ArrayList<Abilities> abilities = new ArrayList<>();
			abilities.add(Abilities.CAR);
			abilities.add(Abilities.MEDICAL_ASSISTANCE);
			application.makeRequest("Transportation for three, we also have a dog, and require medical assistance for our 9 year old daughter Sophie, and we are at 128 Franklin St", PriorityLevel.HIGH, 3, abilities);
			//System.out.println(application.viewUSerReliefRequest()); this pulls from request.Json but we are currently
						                                            // inputing information to requestTemp.Json for
																	// safety porpoises so this doesn't work right now
		}

	}

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

	public void matchRequestScenario(){
		application.login("rosad@email.com", "password123");
		System.out.println("Avaliable Requests:");
		int i = 1;
		for(RankedRequest request: application.viewRequests()){
			System.out.println(i++ + ". " +  "Request description: " + request.getRequest().getRequestDescription());
			String message = request.getMessage();
			if (!message.isEmpty()) {
				System.out.println("   " + message.trim());
			}
			System.out.println();
		}
	}

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
		System.out.println("Driving distance: " + distance/1609.344 + " miles");
		System.out.println("Capabilities: ");
		for(Capabilities capability: shelter.getCapabilities()){
			System.out.println(capability.name().toLowerCase().replace('_', ' '));
		}
	}

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

	public static void main(String[] args) {
		ReliefUI reliefInterface = new ReliefUI();
		reliefInterface.run();
	}
}
