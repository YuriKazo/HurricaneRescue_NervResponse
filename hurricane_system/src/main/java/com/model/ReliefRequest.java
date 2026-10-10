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
        requestId = UUID.randomUUID();
        status = Status.NOT_ACCEPTED;
        requestDescription = description;
        priorityLevel = level;
        this.location = location;
        this.victimCount = victimCount;
    }

    public ReliefRequest(UUID id, Status status, String description, PriorityLevel level, Location location, int victimCount){
        requestId = id;
        this.status = status;
        requestDescription = description;
        priorityLevel = level;
        this.location = location;
        this.victimCount = victimCount;
    }

    public void markComplete(){
        status = Status.COMPLETED;
    }

    public void changeRequestStatus(Status status){
        if (status == null) return;

        this.status = status;
    }

    public void changePriority(PriorityLevel priorityLevel){
        if (priorityLevel == null) return;

        this.priorityLevel = priorityLevel;
    }

    public UUID getID(){
        return requestId;
    }

	public String getRequestDescription() {
		return requestDescription;
	}

	public PriorityLevel getPriorityLevel() {
		return priorityLevel;
	}

	public Location getLocation() {
		return location;
	}

	public Status getStatus() {
		return status;
	}

	public int getVictimCount() {
		return victimCount;
	}

}