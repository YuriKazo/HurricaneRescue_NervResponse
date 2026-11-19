package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class HurricaneList {
    public static HurricaneList hurricaneList;
    private ArrayList<Hurricane> hurricanes;

    public HurricaneList() {
        hurricanes = DataLoader.getHurricanes();
    }

    public static HurricaneList getInstance() {
        if (hurricaneList == null)
            hurricaneList = new HurricaneList();
        return hurricaneList;
    }

    public ArrayList<Hurricane> getHurricanes() {
        return hurricanes;
    }

    public Hurricane getNearestHurricane(String zip) {
        
        Hurricane nearestHurricane = null;
        int nearestDistance = 200;
        for (Hurricane hurricane : hurricanes) {
            for (String hurricaneZip : hurricane.getHurricaneZip()) {
                if (Math.abs(nearestDistance) > Math.abs(Integer.parseInt(hurricaneZip) - Integer.parseInt(zip))) {
                    nearestDistance = Integer.parseInt(hurricaneZip) - Integer.parseInt(zip);
                    nearestHurricane = hurricane;
                    break; 
                }
            }
        }
        return nearestHurricane;
    }
}
