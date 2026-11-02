package com.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * Test                                | Reasoning
 * ------------------------------------|-------------------------------------------------------------
 * addShelter()                        |
 *      Valid UUID                     | Valid shelter ID should be added to the list
 *      Duplicate UUID                 | Duplicate shelter IDs should not be added
 *      Null UUID                      | Null shelter IDs should not be added
 *      Multiple UUIDs                 | Multiple different shelters should be allowed
 *                                     |
 * ------------------------------------|-------------------------------------------------------------
 * updateOccupancy()                   |
 *      Valid occupancy                | Shelter occupancy should update
 *      Zero occupancy                 | Shelter occupancy can be zero
 *      Negative occupancy             | Negative occupancy should not be allowed
 *      Null shelter                   | Null shelter should not cause an error
 *                                     |
 * ------------------------------------|-------------------------------------------------------------
 * updateTotalOccupancy()              |
 *      Valid total occupancy          | Shelter capacity should update
 *      Zero total occupancy           | Shelter capacity can be zero
 *      Negative total occupancy       | Negative capacity should not be allowed
 *      Null shelter                   | Null shelter should not cause an error
 *                                     |
 * ------------------------------------|-------------------------------------------------------------
 * updateWaterLeft()                   |
 *      Valid water amount             | Shelter water capacity should update
 *      Zero water                     | Water capacity can be zero
 *      Negative water                 | Negative water capacity should not be allowed
 *      Null shelter                   | Null shelter should not cause an error
 *                                     |
 * ------------------------------------|-------------------------------------------------------------
 * deleteShelter()                     |
 *      Null UUID                      | Null shelter ID should return false
 *      Non-existing UUID              | Shelter must exist before it can be deleted
 *                                     |
 * ------------------------------------|-------------------------------------------------------------
 * Admin Constructor                   |
 *      Default lists                  | New admin should have empty lists
 *      Account type                   | Admin account type should be ADMIN
 *      Existing account data          | Saved admin information should be restored
 *                                     |
 * ------------------------------------|-------------------------------------------------------------
 * getShelterList()                    |
 *      Added shelter                  | Getter should return stored shelter IDs
 *                                     |
 * ------------------------------------|-------------------------------------------------------------
 * getRequestList()                    |
 *      Empty request list             | New admin should have an empty request list
 */

public class AdminTest {
    private Admin admin;
    private Shelter shelter;

    @BeforeEach
    public void setup() {
        admin = new Admin("John", "john@email.com", "Smith",
                "password123", "8035551234");

        shelter = new Shelter(
                UUID.fromString("f4484a06-ee5f-4bf0-920e-53871c52537c"),
                100, 0,
                new Location(0, 0, "25235"),
                "132 Pickens St",
                new ArrayList<>(),
                15,
                new ArrayList<>());
    }

    @Test
    public void testAddShelterValidUUID() {
        UUID id = UUID.randomUUID();

        admin.addShelter(id);

        assertTrue(admin.getShelterList().contains(id));
        assertEquals(1, admin.getShelterList().size());
    }

    @Test
    public void testAddShelterDuplicateUUID() {
        UUID id = UUID.randomUUID();

        admin.addShelter(id);
        admin.addShelter(id);

        assertEquals(1, admin.getShelterList().size());
    }

    @Test
    public void testAddShelterNullUUID() {
        admin.addShelter(null);

        assertFalse(admin.getShelterList().contains(null));
        assertEquals(0, admin.getShelterList().size());
    }

    @Test
    public void testAddShelterMultipleUUIDs() {
        UUID firstId = UUID.randomUUID();
        UUID secondId = UUID.randomUUID();

        admin.addShelter(firstId);
        admin.addShelter(secondId);

        assertTrue(admin.getShelterList().contains(firstId));
        assertTrue(admin.getShelterList().contains(secondId));
        assertEquals(2, admin.getShelterList().size());
    }

    @Test
    public void testUpdateOccupancyValid() {
        admin.updateOccupancy(shelter, 50);

        assertEquals(50, shelter.getCurrentOccupancy());
    }

    @Test
    public void testUpdateOccupancyZero() {
        shelter.setCurrentOccupancy(50);

        admin.updateOccupancy(shelter, 0);

        assertEquals(0, shelter.getCurrentOccupancy());
    }

    @Test
    public void testUpdateOccupancyNegative() {
        shelter.setCurrentOccupancy(50);

        admin.updateOccupancy(shelter, -1);

        assertEquals(50, shelter.getCurrentOccupancy());
    }

    @Test
    public void testUpdateOccupancyNullShelter() {
        assertDoesNotThrow(() -> admin.updateOccupancy((Shelter) null, 50));
    }

    @Test
    public void testUpdateTotalOccupancyValid() {
        admin.updateTotalOccupancy(shelter, 200);

        assertEquals(200, shelter.getTotalOccupancy());
    }

    @Test
    public void testUpdateTotalOccupancyZero() {
        admin.updateTotalOccupancy(shelter, 0);

        assertEquals(0, shelter.getTotalOccupancy());
    }

    @Test
    public void testUpdateTotalOccupancyNegative() {
        admin.updateTotalOccupancy(shelter, -1);

        assertEquals(100, shelter.getTotalOccupancy());
    }

    @Test
    public void testUpdateTotalOccupancyNullShelter() {
        assertDoesNotThrow(() -> admin.updateTotalOccupancy((Shelter) null, 200));
    }

    @Test
    public void testUpdateWaterLeftValid() {
        admin.updateWaterLeft(shelter, 50);

        assertEquals(50, shelter.getWaterCapacity());
    }

    @Test
    public void testUpdateWaterLeftZero() {
        admin.updateWaterLeft(shelter, 0);

        assertEquals(0, shelter.getWaterCapacity());
    }

    @Test
    public void testUpdateWaterLeftNegative() {
        admin.updateWaterLeft(shelter, -1);

        assertEquals(15, shelter.getWaterCapacity());
    }

    @Test
    public void testUpdateWaterLeftNullShelter() {
        assertDoesNotThrow(() -> admin.updateWaterLeft((Shelter) null, 50));
    }

    @Test
    public void testDeleteShelterNullUUID() {
        assertFalse(admin.deleteShelter(null));
    }

    @Test
    public void testDeleteShelterNonExistingUUID() {
        UUID id = UUID.randomUUID();

        assertFalse(admin.deleteShelter(id));
    }

    @Test
    public void testAdminDefaultLists() {
        assertTrue(admin.getShelterList().isEmpty());
        assertTrue(admin.getRequestList().isEmpty());
    }

    @Test
    public void testAdminAccountType() {
        assertEquals(AccountType.ADMIN, admin.getAccountType());
    }

    @Test
    public void testAdminExistingData() {
        UUID id = UUID.randomUUID();
        UUID shelterId = UUID.randomUUID();
        UUID requestId = UUID.randomUUID();

        ArrayList<Location> locations = new ArrayList<>();
        ArrayList<UUID> accounts = new ArrayList<>();
        ArrayList<String> contacts = new ArrayList<>();
        ArrayList<UUID> shelters = new ArrayList<>();
        ArrayList<UUID> requests = new ArrayList<>();

        Location location = new Location(34.0, -80.0, "29201");

        locations.add(location);
        accounts.add(UUID.randomUUID());
        contacts.add("8035551234");
        shelters.add(shelterId);
        requests.add(requestId);

        Admin restoredAdmin = new Admin(
                id, "John", "john@email.com", "Smith",
                "password123", "8035551234",
                locations, accounts, contacts, location,
                shelters, requests);

        assertEquals(id, restoredAdmin.getID());
        assertEquals("John", restoredAdmin.getFirstName());
        assertEquals("Smith", restoredAdmin.getLastName());
        assertEquals("john@email.com", restoredAdmin.getEmail());
        assertEquals(AccountType.ADMIN, restoredAdmin.getType());
        assertEquals(locations, restoredAdmin.getSavedLocations());
        assertEquals(accounts, restoredAdmin.getSavedAccounts());
        assertEquals(contacts, restoredAdmin.getEmergencyContact());
        assertEquals(location, restoredAdmin.getCurrentLocation());
        assertEquals(shelters, restoredAdmin.getShelterList());
        assertEquals(requests, restoredAdmin.getRequestList());
    }

    @Test
    public void testGetShelterList() {
        UUID id = UUID.randomUUID();

        admin.addShelter(id);

        assertTrue(admin.getShelterList().contains(id));
    }

    @Test
    public void testGetRequestList() {
        assertNotNull(admin.getRequestList());
        assertEquals(0, admin.getRequestList().size());
    }
}
