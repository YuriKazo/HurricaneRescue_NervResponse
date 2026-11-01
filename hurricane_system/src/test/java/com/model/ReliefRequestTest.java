package com.model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.model.*;

/*
 *
 * Test                              | Reasoning
 * ----------------------------------|-------------------------------------------------
 * markComplete()                    |
 *      Normal case                  | Status should change to COMPLETED
 * ----------------------------------|--------------------------------------------------                              
 * changeRequestStatus()             |
 *      Different status             | New status should reflect the given status
 *      Null status                  | Status must not be null
 * ----------------------------------|--------------------------------------------------
 * changePriority()                  |
 *      Different priority           | New priority should reflect the given priority
 *      Null priority                | Priority must not be null
 */

public class ReliefRequestTest {
    private ReliefRequest reliefRequest;

    @BeforeEach
    public void setup(){
        reliefRequest = new ReliefRequest("flooded house", PriorityLevel.MEDIUM, new Location(-81.2, 34.2, "29523"), 2, UUID.randomUUID(), new ArrayList<>());
    }

    @Test
    public void testMarkComplete() {
        reliefRequest.markComplete();
        assertEquals(Status.COMPLETED, reliefRequest.getStatus());
    }

    @Test
    public void testChangeRequestStatus() {
        reliefRequest.changeRequestStatus(Status.ACCEPTED);

        assertEquals(Status.ACCEPTED, reliefRequest.getStatus());
    }

    @Test
    public void testChangeRequestStatusNull() {
        Status originalStatus = reliefRequest.getStatus();

        reliefRequest.changeRequestStatus(null);

        assertEquals(originalStatus, reliefRequest.getStatus());
    }

    @Test
    public void testChangePriority() {
        reliefRequest.changePriority(PriorityLevel.HIGH);

        assertEquals(PriorityLevel.HIGH, reliefRequest.getPriorityLevel());
    }

    @Test
    public void testChangePriorityNull() {
        PriorityLevel originalPriority = reliefRequest.getPriorityLevel();

        reliefRequest.changePriority(null);

        assertEquals(originalPriority, reliefRequest.getPriorityLevel());
    }

}
