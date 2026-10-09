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
			if (shelter.getLocation().equals(location)) {
				return shelter;
			}
		}
		return null;
	}

	public void getShelter(int zipCode) {
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
			if (shelter.getLocation().getZip().equals(String.valueOf(zipCode))) 
				System.out.println(shelter.getStreet());
		}
	}

	public boolean addShelter(int totalOccupancy, int currentOccupancy, Location location, String street, ArrayList<Capabilities> capabilities, int waterCapacity) {
		return shelters.add(new Shelter(totalOccupancy, currentOccupancy, location, street, capabilities, waterCapacity));
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
