package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

// Test                                                 // Reasoning 
// testLoadsAccountsAndAccountTypes                     // Tests loading of accounts and their types
// testLoadsVictimAccountData                           // Tests that the loader will return correct victim details and nested account data
// testLoadsVolunteerAccountData                        // Tests that the loader will return correct volunteer abilities and current target
// testLoadsAdminAccountData                            // Tests that loader will return correct admin shelter and request IDs
// testLoadsShelterData                                 // Tests that loader will return correct shelter information
// testLoadsReliefRequestData                           // Tests that loader will return correct relief request information
// testLoadsEnumValue                                   // Tests that loader will return correct enum values
// testLoaderReturnsIndependentList                     // Tests that the loader will return correct list size

import org.junit.Test;

public class DataLoaderTest {
    @Test
    public void testLoadsAccountsAndAccountTypes() {
        ArrayList<Account> accounts = DataLoader.getAccounts();

        assertTrue(accounts.get(0) instanceof Victim);
        assertTrue(accounts.get(1) instanceof Volunteer);
        assertTrue(accounts.get(3) instanceof Admin);

        Account victim = accounts.get(0);
        assertEquals(UUID.fromString("a1b2c3d4-0001-4aaa-9c11-000000000004"), victim.getID());
        assertEquals("Tom", victim.getFirstName());
        assertEquals("Bradley", victim.getLastName());
        assertEquals(AccountType.VICTIM, victim.getType());
        assertEquals("tbradley@gmail.com", victim.getEmail());
        assertEquals("123-456-7891", victim.getPhoneNumber());
        assertEquals("stormcat7", victim.getPassword());
    }

    @Test
    public void testLoadsVictimAccountData() {
        Victim victim = (Victim) DataLoader.getAccounts().get(0);

        assertFalse(victim.hasPets());
        assertEquals(Arrays.asList(Disabilities.PARALYSIS), victim.getDisabilities());
        assertNull(victim.getAssignedRescuer());
        assertEquals(Arrays.asList(
                UUID.fromString("a1b2c3d4-0002-4bbb-9c11-000000000002"),
                UUID.fromString("a1b2c3d4-0002-4bbb-9c11-000000000004")),
                victim.getSavedAccounts());
        assertEquals(Arrays.asList("803-555-9911"), victim.getEmergencyContact());
        assertEquals(-80.9401, victim.getCurrentLocation().getLongitudeCoord(), 0.000001);
        assertEquals(32.7765, victim.getCurrentLocation().getLatitudeCoord(), 0.000001);
        assertEquals("29403", victim.getCurrentLocation().getZip());
    }

    @Test
    public void testLoadsVolunteerAccountData() {
        ArrayList<Account> accounts = DataLoader.getAccounts();
        Volunteer volunteer = (Volunteer) accounts.get(2);

        assertEquals(AccountType.VOLUNTEER, volunteer.getType());
        assertEquals("Jake", volunteer.getFirstName());
        assertEquals(Arrays.asList(
                Abilities.BOAT,
                Abilities.MEDICAL_ASSISTANCE,
                Abilities.HIGH_STRENGTH), volunteer.getAbilities());
        assertEquals(2, volunteer.getSavedLocations().size());
        assertEquals("69420", volunteer.getSavedLocations().get(1).getZip());

        Volunteer volunteerWithoutTarget = (Volunteer) accounts.get(1);
        assertNull(volunteerWithoutTarget.getCurrentTarget());
    }

    @Test
    public void testLoadsAdminAccountData() {
        Admin admin = (Admin) DataLoader.getAccounts().get(3);

        assertEquals(AccountType.ADMIN, admin.getType());
        assertEquals("Blake", admin.getFirstName());
        assertEquals(Arrays.asList(
                UUID.fromString("8c860493-3fb8-48ab-8127-beaf991340b3")),
                admin.getShelterList());
        assertEquals(Arrays.asList(
                UUID.fromString("62029f22-a9ef-445e-aef6-2354194db37d")),
                admin.getRequestList());
    }

    @Test
    public void testLoadsShelterData() {
        ArrayList<Shelter> shelters = DataLoader.getShelters();

        assertEquals(12, shelters.size());
        Shelter shelter = shelters.get(0);
        assertEquals(UUID.fromString("8c860493-3fb8-48ab-8127-beaf991340b3"), shelter.getID());
        assertEquals(200, shelter.getTotalOccupancy());
        assertEquals(1, shelter.getCurrentOccupancy());
        assertEquals("Boulevard of Broken Dreams", shelter.getStreet());
        assertEquals(Arrays.asList(
                Capabilities.MEDICAL_ASSISTANCE,
                Capabilities.FOOD,
                Capabilities.WATER), shelter.getCapabilities());
        assertEquals(84, shelter.getWaterCapacity());
        assertEquals(Arrays.asList(
                UUID.fromString("422a3491-6b57-4280-81c9-8c987feba223")),
                shelter.getOccupantList());
        assertEquals(-80.8650, shelter.getLocation().getLongitudeCoord(), 0.000001);
        assertEquals(33.4895, shelter.getLocation().getLatitudeCoord(), 0.000001);
        assertEquals("29293", shelter.getLocation().getZip());
    }

    @Test
    public void testLoadsReliefRequestData() {
        ArrayList<ReliefRequest> requests = DataLoader.getReliefRequests();

        assertEquals(7, requests.size());
        ReliefRequest request = requests.get(0);
        assertEquals(UUID.fromString("37b08b54-f977-45f3-a382-081942691d62"), request.getID());
        assertEquals(UUID.fromString("18ba33c2-6627-4bba-810f-ac29c40fac42"),
                request.getRequestorID());
        assertEquals("broken leg", request.getRequestDescription());
        assertEquals(PriorityLevel.MEDIUM, request.getPriorityLevel());
        assertEquals(Status.NOT_ACCEPTED, request.getStatus());
        assertEquals(2, request.getVictimCount());
        assertEquals(Arrays.asList(Abilities.MEDICAL_ASSISTANCE), request.getWantedAbilities());
        assertEquals(-81.0816, request.getLocation().getLongitudeCoord(), 0.000001);
        assertEquals(32.0456, request.getLocation().getLatitudeCoord(), 0.000001);
        assertEquals("29103", request.getLocation().getZip());
    }

    @Test
    public void testLoadsEnumValue() {
        ReliefRequest acceptedRequest = DataLoader.getReliefRequests().get(1);
        Shelter shelterWithoutWater = DataLoader.getShelters().get(1);
        Victim victimWithPets = (Victim) DataLoader.getAccounts().get(6);

        assertEquals(Status.ACCEPTED, acceptedRequest.getStatus());
        assertEquals(PriorityLevel.HIGH, acceptedRequest.getPriorityLevel());
        assertEquals(Arrays.asList(Abilities.BOAT, Abilities.POWER_TOOLS),
                acceptedRequest.getWantedAbilities());
        assertEquals(Arrays.asList(Capabilities.FOOD), shelterWithoutWater.getCapabilities());
        assertEquals(0, shelterWithoutWater.getWaterCapacity());
        assertTrue(victimWithPets.hasPets());
        assertTrue(victimWithPets.getDisabilities().isEmpty());
    }

    @Test
    public void testLoadersReturnIndependentLists() {
        ArrayList<Account> accounts = DataLoader.getAccounts();
        ArrayList<Shelter> shelters = DataLoader.getShelters();
        ArrayList<ReliefRequest> requests = DataLoader.getReliefRequests();

        assertNotNull(accounts);
        assertNotNull(shelters);
        assertNotNull(requests);
        accounts.clear();
        shelters.clear();
        requests.clear();

        assertEquals(9, DataLoader.getAccounts().size());
        assertEquals(12, DataLoader.getShelters().size());
        assertEquals(7, DataLoader.getReliefRequests().size());
    }
}
