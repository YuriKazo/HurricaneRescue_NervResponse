package com.model;

import java.util.ArrayList;

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

	public boolean addShelter(Location location) {
		return shelters.add(new Shelter(location));
	}

	public boolean removeShelter(Location location) {
		return shelters.remove(new Shelter(location));
	}

	public boolean saveShelter() {
		return DataWriter.saveShelters();
	}
}
