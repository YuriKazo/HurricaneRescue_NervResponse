package com.model;

import java.util.UUID;

public class ReliefRequest {
    private String requestDescription;
    private PriorityLevel priorityLevel;
    private Location location;
    private Status status;
    private int victimCount;
    private UUID requestId;

    public ReliefRequest(String description, PriorityLevel level, Location location, int victimCount){

    }

    public ReliefRequest(UUID id, String description, PriorityLevel level, Location location, int victimCount){

    }

    public void markComplete(){

    }

    public void changeRequestStatus(Status status){

    }

    public void changePriority(PrioritiyLevel priorityLevel){

    }
    
    public UUID getID(){
        return null;
    }

}
