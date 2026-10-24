package com.model;
import java.util.UUID; 
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
		if (application.createAccount("Sophred", "Banks", "BSophred@email.com", "word", "111-222-3333") != null) {
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

	public static void main(String[] args) {
		ReliefUI reliefInterface = new ReliefUI();
		reliefInterface.run();
	}
}
