package com.model;

/**
 * A class representation of location
 * @author NervResponse
 */
public class Location {
    private double longitude;
    private double latitude;
    private String zipLocation;

    /**
     * Creates a location
     * @param longitude The longitude of a location
     * @param latitude The latitude of a location
     * @param zipLocation The zip code of a location
     */
    public Location(double longitude, double latitude, String zipLocation){
        this.longitude = longitude;
        this.latitude = latitude;
        this.zipLocation = zipLocation;
    }

    /**
     * Gets longitude of a location
     * @return the longitude
     */
    public double getLongitudeCoord(){
        return longitude;
    }

    /**
     * Gets latitude of a location
     * @return the latitude
     */
    public double getLatitudeCoord(){
        return latitude;
    }
    
    /**
     * Gets the zip code of a location
     * @return the zip code
     */
    public String getZip(){
        return zipLocation;
    }

    /**
     * Sets the location
     * @param longitude the longitude of a location
     * @param latitude the latitude of a location
     * @param zip the zip code
     */
    public void setLocation(double longitude, double latitude, String zip){
        this.latitude = latitude;
        this.longitude = longitude;
        this.zipLocation = zip;
    }

    /**    (non-Javadoc)
     * Turns location into string format
     * Longitude:    Latitude:   Zip Code:
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "Longitude: "+this.longitude+" Latitude: "+this.latitude+" Zip Code: "+this.zipLocation;
    }
}
