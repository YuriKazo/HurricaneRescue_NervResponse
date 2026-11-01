package com.model;


import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * Test                                | Reasoning
 * ------------------------------------|---------------------------------------------------------------------
 * waterIsAvaliable()                  |
 *      Water capacity greater than 10 | Water capacity must be greater than 10 for there to be enough water
 *      Water capacity equal to 10     | Water capacity must be greater than 10
 *      Water capacity less than 10    | There is not enough water when capacity less than 10
 *                                     |
 * ------------------------------------|---------------------------------------------------------------------
 * addOccupant()                       |
 *      Valid UUID                     | Occupants must be added when there is space available
 *      Full occupancy                 | Shelters must not go over max shelter capacity
 *      One space remaining            | Occupants must be added as long as there is space available 
 *      Null UUID                      | Occupants must have a valid UUID
 *                                     |
 * ------------------------------------|---------------------------------------------------------------------
 * removeOccupant()                    |
 *      Matching UUID                  | Shelter must remove the occupant with matching UUID
 *      No matching UUID               | Only existing occupants can be removed
 *      Null UUID                      | Occupants must have a valid UUID
 */

public class ShelterTest {
    private Shelter shelter;
    
     @BeforeEach
    public void setup(){
        shelter = new Shelter(UUID.fromString("f4484a06-ee5f-4bf0-920e-53871c52537c"), 100, 0, new Location(0, 0, "25235"), "132 Pickens St", new ArrayList<>(), 15, new ArrayList<>());
    }

    @Test
    public void testWaterIsAvailableGreaterThan10() {
        shelter.setWaterCapacity(11);

        assertTrue(shelter.waterIsAvaliabile());
    }

    @Test
    public void testWaterIsAvailableEqualTo10() {
        shelter.setWaterCapacity(10);

        assertFalse(shelter.waterIsAvaliabile());
    }

    @Test
    public void testWaterIsAvailableLessThan10() {
        shelter.setWaterCapacity(9);

        assertFalse(shelter.waterIsAvaliabile());
    }

    @Test
    public void testAddOccupantValidUUID() {
        UUID id = UUID.randomUUID();

        shelter.addOccupant(id);

        assertTrue(shelter.getOccupantList().contains(id));
        assertEquals(1, shelter.getCurrentOccupancy());
    }

    @Test
    public void testAddOccupantFullOccupancy() {
        shelter.setCurrentOccupancy(shelter.getTotalOccupancy());
        UUID id = UUID.randomUUID();

        shelter.addOccupant(id);

        assertFalse(shelter.getOccupantList().contains(id));
        assertEquals(shelter.getTotalOccupancy(), shelter.getCurrentOccupancy());
    }

    @Test
    public void testAddOccupantOneSpaceRemaining() {
        shelter.setCurrentOccupancy(shelter.getTotalOccupancy() - 1);
        UUID id = UUID.randomUUID();

        shelter.addOccupant(id);

        assertTrue(shelter.getOccupantList().contains(id));
        assertEquals(shelter.getTotalOccupancy(), shelter.getCurrentOccupancy());
    }

    @Test
    void testAddOccupantNullUUID() {
        shelter.addOccupant(null);

        assertFalse(shelter.getOccupantList().contains(null));
        assertEquals(0, shelter.getCurrentOccupancy());
    }

    @Test
    public void testRemoveOccupantMatchingUUID() {
        UUID id = UUID.randomUUID();
        shelter.addOccupant(id);
        shelter.addOccupant(UUID.randomUUID());

        shelter.removeOccupant(id);

        assertFalse(shelter.getOccupantList().contains(id));
        assertEquals(1, shelter.getCurrentOccupancy());
    }

    @Test
    public void testRemoveOccupantNoMatchingUUID() {
        UUID id = UUID.randomUUID();
        UUID existingId = UUID.randomUUID();
        shelter.addOccupant(existingId);

        shelter.removeOccupant(id);

        assertEquals(1, shelter.getCurrentOccupancy());
        assertTrue(shelter.getOccupantList().contains(existingId));
    }

    @Test
    public void testRemoveOccupantNullUUID() {
        UUID id = UUID.randomUUID();
        shelter.addOccupant(id);

        shelter.removeOccupant(null);

        assertTrue(shelter.getOccupantList().contains(id));
        assertEquals(1, shelter.getCurrentOccupancy());
    }
}
