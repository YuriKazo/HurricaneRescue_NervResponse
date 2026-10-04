package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class Hurricane {
    private String hurricaneName;
    private int hurricaneCategory;
    private UUID hurricaneID;
    private ActiveLevel activeLevel;
    private ArrayList<String> currentZip;

    public Hurricane(String name, int category, ActiveLevel level, ArrayList<String> zip){
        hurricaneName = name;
        hurricaneCategory = category;
        activeLevel = level;
        currentZip = zip;
    }
    
	public String getHurricaneName() {
		return hurricaneName;
	}

	public int getHurricaneCategory() {
		return hurricaneCategory;
	}

	public UUID getHurricaneID() {
		return hurricaneID;
	}

	public ActiveLevel getActiveLevel() {
		return activeLevel;
	}

    public ArrayList<String> getHurricaneZip(){
        return currentZip;
    }
}
