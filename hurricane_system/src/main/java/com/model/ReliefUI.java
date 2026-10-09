package com.model;

public class ReliefUI {
	private ReliefApplication application;

	ReliefUI() {
		application = ReliefApplication.getInstance();
	}

	public void run() {
		//loginScenario();
		//logOutScenario();
		//signUpScenario();
		getShelterFromZipCodeScenario();
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
		application.viewAccount();
	}

	public void signUpScenario() {
		if (application.createAccount("Luke", "Skywalker", "jlogano@email.com", "word") == null) {
			System.out.println("That email is already registered.");
		}
		if (application.createAccount("Luke", "Skywalker", "luke@email.com", "word") != null) {
			System.out.println("Account created for Luke Skywalker");
		}
		application.viewAccount();
		application.login("luke@email.com", "word");
		application.viewAccount();
	}

	public void logOutScenario() {
		application.logout();
		application.viewAccount();
	}

	public void getShelterFromZipCodeScenario() {
		application.viewShelters(29293);
		application.viewShelters(19999);
	}

	public static void main(String[] args) {
		ReliefUI reliefInterface = new ReliefUI();
		reliefInterface.run();
	}
}
