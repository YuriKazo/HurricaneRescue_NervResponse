package com.model;

import java.util.ArrayList;

public class RankedRequest {
    private ReliefRequest request;
    private int score;
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
        score++;
    }

    /**
     * Score is based on volunteer and request abilties
     * @return score of a RankedRequest
     */
    public int getScore(){
        return score;
    }

    /**
     * Builds a recommendation message listing the abilities that matched
     * between the volunteer and request
     * @return the recommendation message, or an empty string if there are no matching abilities
     */
    public String getMessage(){
        if(matchedAbilities.size()<1)
            return "";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Recommended request based on matching abilities: ");
        for(Abilities ability: matchedAbilities){
            stringBuilder.append(ability.name().toLowerCase() + ", ");
        }
        stringBuilder.setLength(stringBuilder.length() -2 );
        stringBuilder.append(". ");

        return stringBuilder.toString();
    }
}
