package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class ShelterList {
	private static ShelterList shelterList;
	private ArrayList<Shelter> shelters;

	private ShelterList() {
		shelters = DataLoader.getShelters();
	}

	public static ShelterList getInstance() {
		if (shelterList == null) {
			shelterList = new ShelterList();
		}

		return shelterList;
	}

	public Shelter getShelter(Location location) {
		for (Shelter shelter : shelters) {
			if (shelter.getLocation().equals(location)) 
				return shelter;
		}
		return null;
	}

	public Shelter getShelter(UUID id) {
		for (Shelter shelter : shelters) {
			if (shelter.getID().equals(id))
				return shelter;
		}
		return null;
	}

	public void viewShelter(UUID id) {
		
		for (Shelter shelter : shelters) {
			if (shelter.getID().equals(id)) {
				System.out.println("Street Name: "+shelter.getStreet());
				System.out.println("Total Occupancy: "+shelter.getTotalOccupancy());
				System.out.println("Current Occupancy"+shelter.getCurrentOccupancy());
				System.out.println("Water Capcity : "+shelter.getWaterCapacity());
				System.out.println("Capabilities: "+shelter.getCapabilities());
			}
		}
	}

	public void getShelters(int zipCode) {
		boolean found = false;
		for (Shelter shelter : shelters) {
			if (shelter.getLocation().getZip().equals(String.valueOf(zipCode))) 
				found = true;
		}
		if (!found) {
			System.out.println("No shelters found in zip code " + zipCode);
			return;
		}
		for (Shelter shelter : shelters) {
			if (shelter.getLocation().getZip().equals(String.valueOf(zipCode))) {
				System.out.println(shelter.getStreet());
				System.out.println(shelter.getTotalOccupancy());
				System.out.println(shelter.getCurrentOccupancy());
				System.out.println(shelter.getWaterCapacity());
				System.out.println(shelter.getCapabilities());
			}
		}
	}

	public UUID addShelter(double longitude, double latittude, String zip, String street) {
		Location location = new Location(longitude, latittude, zip);
		Shelter newShelter = new Shelter(location, street);
		shelters.add(newShelter);
		return newShelter.getID();
	}

	public boolean removeShelter(UUID shelterId) {
		for (Shelter shelter : shelters) {
			if (shelter.getID().equals(shelterId)) {
				return shelters.remove(shelter);
			}
		}
		return false;
	}

	public boolean saveShelter() {
		return DataWriter.saveShelters();
	}
}
