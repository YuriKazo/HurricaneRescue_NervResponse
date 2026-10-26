package com.model;

import java.util.ArrayList;
import java.util.UUID;

import com.location.MapTools;

/**
 * A singleton list that holds every shelter in the system and loads them from file.
 * @author NervResponse
 */
public class ShelterList {
	private static ShelterList shelterList;
	private ArrayList<Shelter> shelters;

	/**
	 * Creates the list and loads the shelters from file.
	 */
	private ShelterList() {
		shelters = DataLoader.getShelters();
	}

	/**
	 * Gets the one instance of the list, creating it the first time it is called
	 * @return The single ShelterList instance
	 */
	public static ShelterList getInstance() {
		if (shelterList == null) {
			shelterList = new ShelterList();
		}

		return shelterList;
	}

	/**
	 * Looks up a shelter by its location
	 * @param location The location of the shelter
	 * @return The matching shelter, or null if there is none
	 */
	public Shelter getShelter(Location location) {
		for (Shelter shelter : shelters) {
			if (shelter.getLocation().equals(location)) 
				return shelter;
		}
		return null;
	}

	/**
	 * Looks up a shelter by its id
	 * @param id The id of the shelter
	 * @return The matching shelter, or null if there is none
	 */
	public Shelter getShelter(UUID id) {
		for (Shelter shelter : shelters) {
			if (shelter.getID().equals(id))
				return shelter;
		}
		return null;
	}

	/**
	 * Gets the shelters in a zip code
	 * @param zipCode The zip code to search
	 * @return A list of the shelters in that zip code, or null if there are none
	 */
	public ArrayList<Shelter> getShelters(int zipCode) {
		boolean found = false;
		for (Shelter shelter : shelters) {
			if (shelter.getLocation().getZip().equals(String.valueOf(zipCode))) 
				found = true;
		}
		if (!found) {
			System.out.println("No shelters found in zip code " + zipCode);
			return null;
		}
		ArrayList<Shelter> toReturn = new ArrayList<>();
		for (Shelter shelter : shelters) {
			if (shelter.getLocation().getZip().equals(String.valueOf(zipCode))) {
				toReturn.add(shelter);
			}
		}
		return toReturn;
	}

	/**
	 * Creates a new shelter and adds it to the list
	 * @param longitude The longitude of the shelter
	 * @param latittude The latitude of the shelter
	 * @param zip The zip code of the shelter
	 * @param street The street address of the shelter
	 * @return The id of the new shelter
	 */
	public UUID addShelter(double longitude, double latittude, String zip, String street) {
		Location location = new Location(longitude, latittude, zip);
		Shelter newShelter = new Shelter(location, street);
		shelters.add(newShelter);
		return newShelter.getID();
	}

	/**
	 * Removes a shelter from the list
	 * @param shelterId The id of the shelter to remove
	 * @return true if the shelter was removed, false if it was not found
	 */
	public boolean removeShelter(UUID shelterId) {
		for (Shelter shelter : shelters) {
			if (shelter.getID().equals(shelterId)) {
				return shelters.remove(shelter);
			}
		}
		return false;
	}

	/**
	 * Saves the shelters to file
	 * @return true if the save was successful
	 */
	public boolean saveShelter() {
		return DataWriter.saveShelters();
	}

	/**
	 * Gets every shelter in the list
	 * @return The list of all shelters
	 */
	public ArrayList<Shelter> getShelters(){
		return shelters;
	}

	/**
	 * Finds the shelters that have room for the given number of people and have all of
	 * the needed capabilities
	 * @param victimCount The number of people who need space
	 * @param capabilities The capabilities the shelter must have
	 * @return A list of the suitable shelters
	 */
	public ArrayList<Shelter> getSuitableShelters(int victimCount, ArrayList<Capabilities> capabilities){
		ArrayList<Shelter> suitableShelters = new ArrayList<>();
		boolean suitable;
		for(Shelter shelter : shelters){
			suitable = true;
			if(shelter.getCurrentOccupancy()+ victimCount <= shelter.getTotalOccupancy()){
				for(Capabilities capability: capabilities){
					if(!(shelter.getCapabilities().contains(capability))){
						suitable = false;
						break;
					}
				}
			if(suitable)
				suitableShelters.add(shelter);
			}
		}
		return suitableShelters;
	}

	/**
	 * Finds the shelter closest by driving distance to a location
	 * @param location The location to measure from
	 * @param shelters The shelters to choose from
	 * @return The nearest shelter, or null if the list is empty or the distance could not be found
	 */
	public Shelter getNearestShelter(Location location, ArrayList<Shelter> shelters){
		if(shelters == null || shelters.size() < 1)
			return null;
		Shelter nearestShelter = null;
		double shortestDistance = Double.MAX_VALUE;
		try{
		MapTools mapTools = MapTools.getInstance();
		for(Shelter shelter : shelters){
			double distance = mapTools.getDrivingDistance(location,shelter.getLocation());
			if(distance < shortestDistance){
				nearestShelter = shelter;
				shortestDistance = mapTools.getDrivingDistance(location, shelter.getLocation());
			}
		}
		}
		catch(Exception e){
			System.err.println(e);
		}
		return nearestShelter;
	}
}
