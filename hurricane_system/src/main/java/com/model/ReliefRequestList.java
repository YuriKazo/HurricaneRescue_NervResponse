package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A singleton list that holds every relief request in the system and loads them
 * from file.
 * @author NervResponse
 */
public class ReliefRequestList {
    private static ReliefRequestList reliefRequestList;
    private ArrayList<ReliefRequest> requests;

    /**
     * Creates the list and loads the relief requests from file.
     */
    private ReliefRequestList(){
        requests = DataLoader.getReliefRequests();
    }

    /**
     * Gets the one instance of the list, creating it the first time it is called
     * @return The single ReliefRequestList instance
     */
    public static ReliefRequestList getInstance(){
        if (reliefRequestList == null)
            reliefRequestList = new ReliefRequestList();
        return reliefRequestList;
    }

    /**
     * Gets the requests that have not been accepted yet
     * @return A list of the requests with a status of not accepted
     */
    public ArrayList<ReliefRequest> getReliefRequest(){
        ArrayList<ReliefRequest> available = new ArrayList<ReliefRequest>();
        for (ReliefRequest request : requests){
            if (request.getStatus() == Status.NOT_ACCEPTED)
                available.add(request);
        }
        return available;
    }

    /**
     * Looks up a request by its id
     * @param id The id of the request
     * @return The matching request, or null if there is none
     */
    public ReliefRequest getReliefRequest(UUID id){
        for (ReliefRequest request : requests){
            if (request.getID().equals(id))
                return request;
        }
        return null;
    }

    /**
     * Gets the requests belonging to a user
     * @param id The id of the user
     * @return A list of the user's requests
     */
    public ArrayList<ReliefRequest> getUserReliefRequests(UUID id) {
        ArrayList<ReliefRequest> toReturn = new ArrayList<>();
        for (ReliefRequest request : requests) {
            if (request.getID().equals(id))
                toReturn.add(request);
        }
        return toReturn;
    }

    /**
     * Creates a relief request and adds it to the list
     * @param description A description of the help that is needed
     * @param level How urgent the request is
     * @param location Where the help is needed
     * @param victimCount The number of people the request is for
     * @param requestor The id of the account making the request
     * @param wantedAbilities The abilities a volunteer should have to help
     * @return true if the request was added
     */
    public boolean addReliefRequest(String description, PriorityLevel level, Location location, int victimCount, UUID requestor, ArrayList<Abilities> wantedAbilities){
        return requests.add(new ReliefRequest(description, level, location, victimCount, requestor, wantedAbilities ));
    }

    /**
     * Saves the relief requests to file
     * @return true if the save was successful
     */
    public boolean saveReliefRequest(){
        return DataWriter.saveReliefRequests(); 
    }

    /**
     * Gets every request in the list, whatever its status
     * @return The list of all relief requests
     */
    public ArrayList<ReliefRequest> getRequests(){
        return requests;
    }

    /**
     * Sorts requests based on matching abilities, requests with no matches are still included
     * @param volunteerAbilities the abilities the voluteer has
     * @param volunteerLocation the current location of the volunteer
     * @return an ArrayList of RankedRequest ordered from best match to worst
     */
    public ArrayList<RankedRequest> matchRequests(ArrayList<Abilities> volunteerAbilities, Location volunteerLocation){
        ArrayList<ReliefRequest> requests = getReliefRequest();
        ArrayList<RankedRequest> rankedRequests = new ArrayList<>();
        for(ReliefRequest request: requests){
            RankedRequest rankedRequest = new RankedRequest(request);
            rankedRequests.add(rankedRequest);

            for(Abilities ability: request.getWantedAbilities()){
                if(volunteerAbilities.contains(ability)){
                    rankedRequest.addMatchedAbility(ability);
                }
            }
            rankedRequest.checkDistance(volunteerLocation);
        }

        // uses a lambda function to sort by score
        rankedRequests.sort((request1, request2) -> Double.compare(request2.getScore(), request1.getScore()));
        return rankedRequests;
    }

}