package com.model;

public class Location {
    private double longitude;
    private double latitude;
    private String zipLocation;

    public Location(double longitude, double latitude, String zipLocation){
        this.longitude = longitude;
        this.latitude = latitude;
        this.zipLocation = zipLocation;
    }

    public double getLongitudeCoord(){
        return longitude;
    }

    public double getLatitudeCoord(){
        return latitude;
    }
    
    public String getZip(){
        return zipLocation;
    }

    public void setLocation(double longitude, double latitude, String zip){
        this.latitude = latitude;
        this.longitude = longitude;
        this.zipLocation = zip;
    }
}
