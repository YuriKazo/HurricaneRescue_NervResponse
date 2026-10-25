package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class ReliefRequestList {
    private static ReliefRequestList reliefRequestList;
    private ArrayList<ReliefRequest> requests;

    private ReliefRequestList(){
        requests = DataLoader.getReliefRequests();
    }

    public static ReliefRequestList getInstance(){
        if (reliefRequestList == null)
            reliefRequestList = new ReliefRequestList();
        return reliefRequestList;
    }

    public ArrayList<ReliefRequest> getReliefRequest(){
        ArrayList<ReliefRequest> available = new ArrayList<ReliefRequest>();
        for (ReliefRequest request : requests){
            if (request.getStatus() == Status.NOT_ACCEPTED)
                available.add(request);
        }
        return available;
    }
    public ReliefRequest getReliefRequest(UUID id){
        for (ReliefRequest request : requests){
            if (request.getID().equals(id))
                return request;
        }
        return null;
    }

    public ArrayList<ReliefRequest> getUserReliefRequests(UUID id) {
        ArrayList<ReliefRequest> toReturn = new ArrayList<>();
        for (ReliefRequest request : requests) {
            if (request.getID().equals(id))
                toReturn.add(request);
        }
        return toReturn;
    }

    public boolean addReliefRequest(String description, PriorityLevel level, Location location, int victimCount, UUID requestor, ArrayList<Abilities> wantedAbilities){
        return requests.add(new ReliefRequest(description, level, location, victimCount, requestor, wantedAbilities ));
    }

    public boolean saveReliefRequest(){
        return DataWriter.saveReliefRequests(); 
    }

    public ArrayList<ReliefRequest> getRequests(){
        return requests;
    }

    /**
     * Sorts requests based on matching abilities, requests with no matches are still included
     * @param volunteerAbilities the abilities the voluteer has
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
