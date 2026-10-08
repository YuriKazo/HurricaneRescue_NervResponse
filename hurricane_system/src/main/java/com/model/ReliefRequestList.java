package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class ReliefRequestList {
    private static ReliefRequestList reliefRequestList;
    private ArrayList<ReliefRequest> requests;

    private ReliefRequestList(){
        requests = new ArrayList<ReliefRequest>();
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

    public boolean addReliefRequest(String description, PriorityLevel level, Location location, int victimCount){
        return requests.add(new ReliefRequest(description, level, location, victimCount));
    }

    public boolean saveReliefRequest(){
        return DataWriter.saveReliefRequests(); 
    }
}
