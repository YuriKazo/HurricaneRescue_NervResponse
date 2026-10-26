package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A class representation of a hurricane
 * @author NervResponse
 */
public class Hurricane {
    private String hurricaneName;
    private int hurricaneCategory;
    private UUID hurricaneID;
    private ActiveLevel activeLevel;
    private ArrayList<String> currentZip;

    /**
     * Creates a hurricane
     * @param name The name of the hurricane
     * @param category The category of the hurricane
     * @param level The active level of the hurricane
     * @param zip The zipcodes the hurricane covers
     */
    public Hurricane(String name, int category, ActiveLevel level, ArrayList<String> zip){
        hurricaneName = name;
        hurricaneCategory = category;
        activeLevel = level;
        currentZip = zip;
    }
    
    /**
     * Gets hurricane name
     * @return the hurricane name
     */
	public String getHurricaneName() {
		return hurricaneName;
	}

    /**
     * Gets the hurricane category
     * @return hurricane category
     */
	public int getHurricaneCategory() {
		return hurricaneCategory;
	}

    /**
     * Gets the hurricane ID
     * @return hurricane ID as UUID
     */
	public UUID getHurricaneID() {
		return hurricaneID;
	}

    /**
     * Gets active level of hurricane
     * @return active level of hurricane
     */
	public ActiveLevel getActiveLevel() {
		return activeLevel;
	}

    /**
     * Gets hurricane zip codes
     * @return an arraylist of zip codes
     */
    public ArrayList<String> getHurricaneZip(){
        return currentZip;
    }
}
