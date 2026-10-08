package com.model;

public class ReliefUI {
	private ReliefApplication application;

	ReliefUI() {
		application = ReliefApplication.getInstance();
	}

	public void run() {
		scenario1();
	}

	public void scenario1() {
	System.out.println();

	if (application.createAccount("Tom", "Smith", "Tom@gmail.com", "12345") == null) {
		System.out.println("That email is already registered.");
	}

	if (!application.login("Tom@gmail.com", "12345")) {
		System.out.println("Sorry we couldn't login.");
		return;
	}

	System.out.println("Tom is now logged in");
}

	public static void main(String[] args) {
		ReliefUI reliefInterface = new ReliefUI();
		reliefInterface.run();
	}
}
