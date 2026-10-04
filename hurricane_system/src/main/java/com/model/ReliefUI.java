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

		if (!application.login("Tom@gmail.com", "12345")) {
			System.out.println("Sorry we couldn't login.");
			return;
		}

		System.out.println("User is now logged in");
	}

	public static void main(String[] args) {
		ReliefUI reliefInterface = new ReliefUI();
		reliefInterface.run();
	}
}
