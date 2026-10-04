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
		return null;
	}

	public boolean addShelter(Location location) {
		return true;
	}

	public boolean removeShelter(Location location) {
		return true;
	}

	public boolean saveShelter() {
		return true;
	}
}
