package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReliefRequestListTest {
    private ReliefRequestList requestList = ReliefRequestList.getInstance();
	private ArrayList<ReliefRequest> requests = requestList.getRequests();
    private static final Location SAME_LOCATION = new Location(-81.0287, 33.9982, "29201");

	@BeforeEach 
    public void setup(){
        requests.clear();
        requestList.getRequests().add(new ReliefRequest(UUID.fromString("f88e14b2-db27-4ce9-9e09-dc6e98daa0e1"), UUID.fromString("c4f7c31f-e626-4791-bea8-f5a97fb9dd75"), Status.NOT_ACCEPTED, "fallen tree", PriorityLevel.LOW, new Location(-82.4234, 19.2345, "29534"), 1, new ArrayList<>()));
        requestList.getRequests().add(new ReliefRequest(UUID.fromString("f7483fc7-5c37-4a68-889c-49a0ddcca476"), UUID.fromString("d0a04e9f-4a30-48e0-b87b-9b056974ccc8"), Status.NOT_ACCEPTED, "broken leg", PriorityLevel.MEDIUM, new Location(-81.3524, 14.5235, "23525"), 1, new ArrayList<>()));
        requestList.getRequests().add(new ReliefRequest(UUID.fromString("ffbad3d8-b0c3-462c-bb22-629dbcb73f4c"), UUID.fromString("2b84cc28-2767-4eef-8677-b81197d4fe2c"), Status.NOT_ACCEPTED, "flooded house", PriorityLevel.MEDIUM, new Location(-81.2345, 15.2355, "23524"), 2, new ArrayList<>()));
    }

    @AfterEach
    public void tearDown(){
        requestList.getRequests().clear();
    }


    @Test 
    public void testGetReliefRequestEmpty() {
        requests.clear();
        assertEquals(0,requestList.getReliefRequest().size());
    }

    @Test 
    public void testGetReliefRequestAllNotAccepted() {
        assertEquals(3, requestList.getReliefRequest().size());
    }

    @Test 
    public void testMixedStatusSize() {
        requests.get(2).changeRequestStatus(Status.COMPLETED);
        requests.get(1).changeRequestStatus(Status.INPROGRESS);
        assertEquals(1, requestList.getReliefRequest().size());
    }

    @Test
    public void testMixedStatus() {
        requests.get(2).changeRequestStatus(Status.COMPLETED);
        requests.get(1).changeRequestStatus(Status.INPROGRESS);
        assertEquals("fallen tree", requestList.getReliefRequest().get(0).getRequestDescription());
    }

    @Test
    public void testNoAvaliableRequests() {
        for(ReliefRequest request: requests)
            request.changeRequestStatus(Status.COMPLETED);
        assertEquals(0, requestList.getReliefRequest().size());
    }

    @Test
    public void testGetRequestByIdNoMatch(){
        ReliefRequest request = requestList.getReliefRequest(UUID.fromString("e9a6e6dc-d6be-48bd-89c8-5c532cfb727c"));
        assertNull(request);
    }

    @Test
    public void testGetRequestByIdMatch(){
        ReliefRequest request = requestList.getReliefRequest(UUID.fromString("ffbad3d8-b0c3-462c-bb22-629dbcb73f4c"));
        assertEquals("ffbad3d8-b0c3-462c-bb22-629dbcb73f4c", request.getID().toString());
    }

    @Test
    public void testGetUserRequestsNone(){
        assertEquals(0, requestList.getUserReliefRequests(UUID.fromString("fb66a075-248d-4799-8226-fbaf1df189f9")).size());
    }

    @Test
    public void testGetUserSingleRequest(){
        requestList.getRequests().add(new ReliefRequest(UUID.fromString("e0d55e92-3432-4d9d-9d59-a7b4c90f3804"), UUID.fromString("fb66a075-248d-4799-8226-fbaf1df189f9"), Status.INPROGRESS, "broken car",PriorityLevel.LOW, new Location(0, 0, "23523"), 1, new ArrayList<>()));
        assertEquals(1, requestList.getUserReliefRequests(UUID.fromString("fb66a075-248d-4799-8226-fbaf1df189f9")).size());
    }

    @Test
    public void testGetUserTwoRequests(){
        requestList.getRequests().add(new ReliefRequest(UUID.fromString("1d94bfb4-0431-4eb8-8443-80f939b7b7b4"), UUID.fromString("fb66a075-248d-4799-8226-fbaf1df189f9"), Status.INPROGRESS, "debris blocking driveway",PriorityLevel.LOW, new Location(0, 0, "23523"), 1, new ArrayList<>()));
        requestList.getRequests().add(new ReliefRequest(UUID.fromString("e0d55e92-3432-4d9d-9d59-a7b4c90f3804"), UUID.fromString("fb66a075-248d-4799-8226-fbaf1df189f9"), Status.INPROGRESS, "broken car",PriorityLevel.LOW, new Location(0, 0, "23523"), 1, new ArrayList<>()));
        assertEquals(2, requestList.getUserReliefRequests(UUID.fromString("fb66a075-248d-4799-8226-fbaf1df189f9")).size());
    }

    @Test
    public void testAddReliefRequestValidRequest(){
        requests.clear();
        requestList.addReliefRequest("tree fallen on house", PriorityLevel.MEDIUM, new Location(-82.4234, 19.2345, "29534"), 1, UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), new ArrayList<>());
        assertEquals("tree fallen on house", requests.get(0).getRequestDescription());
    }

    @Test
    public void testAddReliefRequestEmptyDescription(){
        requests.clear();
        requestList.addReliefRequest("", PriorityLevel.MEDIUM, new Location(-82.4234, 19.2345, "29534"), 1, UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), new ArrayList<>());
        assertEquals(0, requests.size());
    }

    @Test
    public void testAddReliefRequestEmptyAbilitiesList(){
        requests.clear();
        requestList.addReliefRequest("tree fallen on house", PriorityLevel.MEDIUM, new Location(-82.4234, 19.2345, "29534"), 1, UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), new ArrayList<>());
        assertEquals(0, requests.get(0).getWantedAbilities().size());
    }

    @Test
    public void testAddReliefRequestNullDescription(){
        requests.clear();
        requestList.addReliefRequest(null, PriorityLevel.MEDIUM, new Location(-82.4234, 19.2345, "29534"), 1, UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), new ArrayList<>());
        assertEquals(0, requests.size());
    }

    @Test
    public void testAddReliefRequestNullLocation(){
        requests.clear();
        requestList.addReliefRequest("tree fallen on house", PriorityLevel.MEDIUM, null, 1, UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), new ArrayList<>());
        assertEquals(0, requests.size());
    }

    @Test
    public void testAddReliefRequestNegativeVictimCount(){
        requests.clear();
        requestList.addReliefRequest("tree fallen on house", PriorityLevel.MEDIUM, new Location(-82.4234, 19.2345, "29534"), -1, UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), new ArrayList<>());
        assertEquals(0, requests.size());
    }

    @Test
    public void testAddReliefRequestNullRequestor(){
        requests.clear();
        requestList.addReliefRequest("tree fallen on house", PriorityLevel.MEDIUM, new Location(-82.4234, 19.2345, "29534"), 1, null, new ArrayList<>());
        assertEquals(0, requests.size());
    }

    @Test
    public void testAddReliefRequestNullAbilitiesList(){
        requests.clear();
        requestList.addReliefRequest("tree fallen on house", PriorityLevel.MEDIUM, new Location(-82.4234, 19.2345, "29534"), 1, UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), null);
        assertEquals(0, requests.size());
    }

    @Test
    public void testMatchRequestsAbilityMatchRanksFirst() {
        requests.clear();
        requests.add(buildRequest("needs boat", new ArrayList<>(List.of(Abilities.BOAT)), SAME_LOCATION));
        requests.add(buildRequest("needs car", new ArrayList<>(List.of(Abilities.CAR)), SAME_LOCATION));

        ArrayList<RankedRequest> ranked = requestList.matchRequests(
                new ArrayList<>(List.of(Abilities.CAR)), SAME_LOCATION);

        assertEquals("needs car", ranked.get(0).getRequest().getRequestDescription());
    }

    @Test
    public void testMatchRequestsNoMatchStillIncluded() {
        requests.clear();
        requests.add(buildRequest("needs boat", new ArrayList<>(List.of(Abilities.BOAT)), SAME_LOCATION));

        ArrayList<RankedRequest> ranked = requestList.matchRequests(
                new ArrayList<>(List.of(Abilities.CAR)), SAME_LOCATION);

        assertEquals(1, ranked.size());
    }

    @Test
    public void testMatchRequestsMoreMatchesRanksHigher() {
        requests.clear();
        requests.add(buildRequest("needs boat", new ArrayList<>(List.of(Abilities.BOAT)), SAME_LOCATION));
        requests.add(buildRequest("needs car and swimming",
                new ArrayList<>(List.of(Abilities.CAR, Abilities.SWIMMING)), SAME_LOCATION));

        ArrayList<RankedRequest> ranked = requestList.matchRequests(
                new ArrayList<>(List.of(Abilities.CAR, Abilities.SWIMMING)), SAME_LOCATION);

        assertEquals("needs car and swimming", ranked.get(0).getRequest().getRequestDescription());
    }

    @Test
    public void testMatchRequestsEqualScoresKeepOriginalOrder() {
        requests.clear();
        requests.add(buildRequest("first request", new ArrayList<>(List.of(Abilities.CAR)), SAME_LOCATION));
        requests.add(buildRequest("second request", new ArrayList<>(List.of(Abilities.CAR)), SAME_LOCATION));

        ArrayList<RankedRequest> ranked = requestList.matchRequests(
                new ArrayList<>(List.of(Abilities.CAR)), SAME_LOCATION);

        assertEquals("first request", ranked.get(0).getRequest().getRequestDescription());
    }

    @Test
    public void testMatchRequestsEmptyRequestList() {
        requests.clear();

        ArrayList<RankedRequest> ranked = requestList.matchRequests(
                new ArrayList<>(List.of(Abilities.CAR)), SAME_LOCATION);

        assertEquals(0, ranked.size());
    }

    @Test
    public void testMatchRequestsNoVolunteerAbilitiesNearestFirst() {
        requests.clear();
        // Far request is added first so a passing test can't be due to list order
        requests.add(buildRequest("far request", new ArrayList<>(), new Location(-79.9311 ,32.7765, "29401")));
        requests.add(buildRequest("near request", new ArrayList<>(), SAME_LOCATION));

        ArrayList<RankedRequest> ranked = requestList.matchRequests(new ArrayList<>(), SAME_LOCATION);

        assertEquals("near request", ranked.get(0).getRequest().getRequestDescription());
    }

    private ReliefRequest buildRequest(String description, ArrayList<Abilities> wanted, Location location) {
        return new ReliefRequest(UUID.randomUUID(), UUID.randomUUID(), Status.NOT_ACCEPTED,
                description, PriorityLevel.LOW, location, 1, wanted);
    }
}
