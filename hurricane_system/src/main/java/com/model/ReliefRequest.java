package com.model;

import java.util.ArrayList;
import java.util.UUID;
import java.util.ArrayList;

/**
 * A request for help made by a hurricane victim, including what is needed, where,
 * how urgent it is, and how many people it is for.
 * @author NervResponse
 */
public class ReliefRequest {
    private String requestDescription;
    private PriorityLevel priorityLevel;
    private Location location;
    private Status status;
    private int victimCount;
    private UUID requestId;
    private UUID requestorId;
    private ArrayList<Abilities> wantedAbilities;

    /**
     * Creates a new relief request with a new id and a status of not accepted
     * @param description A description of the help that is needed
     * @param level How urgent the request is
     * @param location Where the help is needed
     * @param victimCount The number of people the request is for
     * @param requestor The id of the account making the request
     * @param wantedAbilities The abilities a volunteer should have to help
     */
    public ReliefRequest(String description, PriorityLevel level, Location location, int victimCount, UUID requestor, ArrayList<Abilities> wantedAbilities){
        requestId = UUID.randomUUID();
        this.requestorId = requestor;
        status = Status.NOT_ACCEPTED;
        requestDescription = description;
        priorityLevel = level;
        this.location = location;
        this.victimCount = victimCount;
        this.wantedAbilities = wantedAbilities;
    }

    /**
     * Creates a relief request from existing data, such as when loading from file
     * @param id The id of the request
     * @param requestorId The id of the account that made the request
     * @param status The current status of the request
     * @param description A description of the help that is needed
     * @param level How urgent the request is
     * @param location Where the help is needed
     * @param victimCount The number of people the request is for
     * @param wantedAbilities The abilities a volunteer should have to help
     */
    public ReliefRequest(UUID id, UUID requestorId, Status status, String description, PriorityLevel level, Location location, int victimCount, ArrayList<Abilities> wantedAbilities){
        requestId = id;
        this.requestorId = requestorId; 
        this.status = status;
        requestDescription = description;
        priorityLevel = level;
        this.location = location;
        this.victimCount = victimCount;
        this.wantedAbilities = wantedAbilities;
    }

    /**
     * Sets the status of the request to completed
     */
    public void markComplete(){
        status = Status.COMPLETED;
    }

    /**
     * Changes the status of the request. A null status is ignored.
     * @param status The new status
     */
    public void changeRequestStatus(Status status){
        if (status == null) return;

        this.status = status;
    }

    /**
     * Changes the priority of the request. A null priority is ignored.
     * @param priorityLevel The new priority level
     */
    public void changePriority(PriorityLevel priorityLevel){
        if (priorityLevel == null) return;

        this.priorityLevel = priorityLevel;
    }

    /**
     * Gets the id of the request
     * @return The request's id
     */
    public UUID getID(){
        return requestId;
    }

    /**
     * Gets the id of the account that made the request
     * @return The requestor's id
     */
    public UUID getRequestorID() {
        return this.requestorId;
    }

    /**
	 * Gets the description of the request
	 * @return The request's description
	 */
	public String getRequestDescription() {
		return requestDescription;
	}

    /**
	 * Gets how urgent the request is
	 * @return The request's priority level
	 */
	public PriorityLevel getPriorityLevel() {
		return priorityLevel;
	}

    /**
	 * Gets where the help is needed
	 * @return The request's location
	 */
	public Location getLocation() {
		return location;
	}

    /**
	 * Gets the current status of the request
	 * @return The request's status
	 */
	public Status getStatus() {
		return status;
	}

    /**
	 * Gets the number of people the request is for
	 * @return The victim count
	 */
	public int getVictimCount() {
		return victimCount;
	}

    /**
     * Gets the abilities a volunteer should have to help with this request
     * @return The list of wanted abilities
     */
    public ArrayList<Abilities> getWantedAbilities() {
        return this.wantedAbilities;
    }

}