package com.model;

import java.util.ArrayList;

import com.location.MapTools;

/**
 * A class representation of a ranked request
 * @author NerveResponse
 */
public class RankedRequest {
    private ReliefRequest request;
    private double score;
    private double distance = 0;
    private ArrayList<Abilities> matchedAbilities = new ArrayList<>();

    /**
     * Creates a Ranked Request
     * @param request The request being scored
     */
    public RankedRequest(ReliefRequest request){
        this.request = request;
        score = 0;
    }

    /**
     * Returns the relief request that is being scored/ranked
     * @return the request under evaluation
     */
    public ReliefRequest getRequest(){
        return request;
    }

    /**
     * Adds an ability if it is a match between relief request and volunteer
     * @param ability The ability that matches
     */
    public void addMatchedAbility(Abilities ability){
        matchedAbilities.add(ability);
        score+=100;
    }

    /**
     * Score is based on volunteer and request abilties, and the distance to the request
     * @return score of a RankedRequest
     */
    public double getScore(){
        return score;
    }

    /**
     * Calculate the distance from request
     * @param location The location of the volunteer
     */
    public void checkDistance(Location location){
        try{
            double distanceMeters = MapTools.getInstance().getDrivingDistance(location, request.getLocation());
            score -= distanceMeters*0.1;
            distance = distanceMeters/1609.344;
        }
        catch(Exception e){
            System.err.println(e);
        }
    }
    /**
     * Builds a recommendation message listing the abilities that matched
     * between the volunteer and request
     * @return the recommendation message, or an empty string if there are no matching abilities
     */
    public String getMessage(){
        if(matchedAbilities.size()<1)
            return "Distance: " + Math.floor(distance*100)/100.0 + " miles.";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Distance: " + Math.floor(distance * 100)/100.0 + " miles. Matching abilities: ");
        for(Abilities ability: matchedAbilities){
            stringBuilder.append(ability.name().toLowerCase() + ", ");
        }
        stringBuilder.setLength(stringBuilder.length() -2 );
        stringBuilder.append(". ");

        return stringBuilder.toString();
    }
}
