package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShelterListTest {
    private ShelterList shelterList = ShelterList.getInstance();
    private ArrayList<Shelter> shelters = shelterList.getShelters();

    @BeforeEach
    void setUp() {
        shelters.clear();
        shelterList.getShelters().add(createShelter(-80.8, 34.0, "29201", "123 Main St"));
        shelterList.getShelters().add(createShelter(-81.0, 34.1, "29202", "456 Oak St"));
        shelterList.getShelters().add(createShelter(-82.0, 33.5, "29203", "789 Pine St"));
    }

    @AfterEach
    void tearDown(){
        shelterList.getShelters().clear();
    }
    private Shelter createShelter(
            double longitude, double latitude, String zip, String street) {
        Location location = new Location(longitude, latitude, zip);
        return new Shelter(location, street);
    }

    // getShelter(Location)

    @Test
    void testGetShelterMatchingLocation() {
        Shelter shelter = shelters.get(0);


        assertSame(shelter, shelterList.getShelter(shelter.getLocation()));
    }

    @Test
    void testGetShelterNoMatchingLocation() {
        Location otherLocation = new Location(-85.0, 35.0, "29202");

        assertNull(shelterList.getShelter(otherLocation));
    }

    @Test
    void testGetShelterNullLocation() {
        Location location = null;
        assertNull(shelterList.getShelter(location));
    }

    // getShelter(UUID)

    @Test
    void testGetShelterMatchingUUID() {
        Shelter shelter = shelters.get(0);
        assertSame(shelter, shelterList.getShelter(shelter.getID()));
    }

    @Test
    void testGetShelterNoMatchingUUID() {
        assertNull(shelterList.getShelter(UUID.randomUUID()));
    }

    @Test
    void testGetShelterNullUUID() {
        assertNull(shelterList.getShelter((UUID) null));
    }

    // getShelters(int zipcode)

    @Test
    void testGetSheltersOneMatchingZipcode() {
        ArrayList<Shelter> result = shelterList.getShelters(29202);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertSame(shelters.get(1), result.get(0));
    }

    @Test
    void testGetSheltersTwoMatchingZipcodes() {
        Shelter extraShelter = createShelter(-80.9, 34.1, "29201", "456 Oak St");
        shelters.add(extraShelter);

        ArrayList<Shelter> result = shelterList.getShelters(29201);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(shelters.get(0)));
        assertTrue(result.contains(extraShelter));
    }

    @Test
    void testGetSheltersNoMatchingZipcode() {

        assertNull(shelterList.getShelters(29685));
    }

    // addShelter()

    @Test
    void testAddShelterValidShelter() {
        int initialSize = shelterList.getShelters().size();

        UUID id = shelterList.addShelter(
                -80.8, 34.0, "29201", "123 Main St");

        assertEquals(initialSize + 1, shelterList.getShelters().size());
        assertNotNull(shelterList.getShelter(id));
    }

    @Test
    void testAddShelterOutOfBoundsLatitude() {
        assertNull(shelterList.addShelter(-80.8, 100.0, "29201", "123 Main St"));
    }

    @Test
    void testAddShelterOutOfBoundsLongitude() {
        assertNull(shelterList.addShelter(200.0, 34.0, "29201", "123 Main St"));
    }

    @Test
    void testAddShelterEmptyZip() {
        assertNull(shelterList.addShelter(-80.8, 34.0, "", "123 Main St"));
    }

    @Test
    void testAddShelterNullZip() {
        assertNull(shelterList.addShelter(-80.8, 34.0, null, "123 Main St"));
    }

    @Test
    void testAddShelterEmptyStreet() {
        assertNull(shelterList.addShelter(-80.8, 34.0, "29201", ""));
    }

    @Test
    void testAddShelterNullStreet() {
        assertNull(shelterList.addShelter(-80.8, 34.0, "29201", null));
    }

    // removeShelter()

    @Test
    void testRemoveShelterMatchingUUID() {
        Shelter shelter = createShelter(-80.8, 34.0, "29201", "123 Main St");
        shelterList.getShelters().add(shelter);

        assertTrue(shelterList.removeShelter(shelter.getID()));
        assertFalse(shelterList.getShelters().contains(shelter));
    }

    @Test
    void testRemoveShelterNoMatchingUUID() {
        Shelter shelter = createShelter(-80.8, 34.0, "29201", "123 Main St");
        shelterList.getShelters().add(shelter);

        int initialSize = shelterList.getShelters().size();

        assertFalse(shelterList.removeShelter(UUID.randomUUID()));
        assertEquals(initialSize, shelterList.getShelters().size());
    }

    @Test
    void testRemoveShelterNullUUID() {
        assertFalse(shelterList.removeShelter(null));
    }

    // getSuitableShelters()

    @Test
    void testGetSuitableSheltersAllMatchingCapabilities() {
        shelters.clear();;
        ArrayList<Capabilities> capability = new ArrayList<>();
        capability.add(Capabilities.FOOD);

        Shelter shelter1 = createShelter(-80.8, 34.0, "29201", "123 Main St");
        Shelter shelter2 = createShelter(-80.9, 34.1, "29201", "456 Oak St");

        shelter1.setTotalOccupancy(10);
        shelter2.setTotalOccupancy(10);
        shelter1.setCapabilities(capability);
        shelter2.setCapabilities(capability);
        shelterList.getShelters().add(shelter1);
        shelterList.getShelters().add(shelter2);

        ArrayList<Capabilities> wanted = capability;
        ArrayList<Shelter> result = shelterList.getSuitableShelters(2, wanted);

        assertEquals(2, result.size());
        assertTrue(result.contains(shelter1));
        assertTrue(result.contains(shelter2));
    }

    @Test
    void testGetSuitableSheltersWithExtraCapabilities() {
        shelters.clear();
        Capabilities wantedCapability = Capabilities.values()[0];
        Capabilities extraCapability = Capabilities.values().length > 1
                ? Capabilities.values()[1] : wantedCapability;

        Shelter shelter = createShelter(-80.8, 34.0, "29201", "123 Main St");
        shelter.setTotalOccupancy(10);
        shelter.getCapabilities().add(wantedCapability);
        shelter.getCapabilities().add(extraCapability);

        shelterList.getShelters().add(shelter);

        ArrayList<Capabilities> wanted = new ArrayList<>();
        wanted.add(wantedCapability);

        assertTrue(shelterList.getSuitableShelters(2, wanted).contains(shelter));
    }

    @Test
    void testGetSuitableSheltersMixedCapabilities() {
        shelters.clear();
        Capabilities capability = Capabilities.values()[0];

        Shelter matching = createShelter(-80.8, 34.0, "29201", "123 Main St");
        matching.setTotalOccupancy(10);
        matching.getCapabilities().add(capability);

        Shelter notMatching = createShelter(-80.9, 34.1, "29201", "456 Oak St");
        notMatching.setTotalOccupancy(10);

        shelterList.getShelters().add(matching);
        shelterList.getShelters().add(notMatching);

        ArrayList<Capabilities> wanted = new ArrayList<>();
        wanted.add(capability);

        ArrayList<Shelter> result = shelterList.getSuitableShelters(2, wanted);

        assertTrue(result.contains(matching));
        assertFalse(result.contains(notMatching));
    }

    @Test
    void testGetSuitableSheltersNoMatchingCapabilities() {
        shelters.clear();
        Capabilities capability = Capabilities.values()[0];

        Shelter shelter = createShelter(-80.8, 34.0, "29201", "123 Main St");
        shelter.setTotalOccupancy(10);
        shelterList.getShelters().add(shelter);

        ArrayList<Capabilities> wanted = new ArrayList<>();
        wanted.add(capability);

        assertTrue(shelterList.getSuitableShelters(2, wanted).isEmpty());
    }

    @Test
    void testGetSuitableSheltersNegativeVictimCount() {
        assertNull(shelterList.getSuitableShelters(-1,new ArrayList<>()));
    }

    @Test
    void testGetSuitableSheltersEmptyCapabilities() {
        shelters.clear();
        Shelter shelter = createShelter(-80.8, 34.0, "29201", "123 Main St");
        shelter.setTotalOccupancy(5);
        shelter.setCurrentOccupancy(2);

        shelterList.getShelters().add(shelter);

        ArrayList<Capabilities> wanted = new ArrayList<>();

        assertTrue(shelterList.getSuitableShelters(3, wanted).contains(shelter));
        assertFalse(shelterList.getSuitableShelters(4, wanted).contains(shelter));
    }

    @Test
    void testGetSuitableSheltersNullCapabilities() {
        assertNull(shelterList.getSuitableShelters(1, null));
    }

    // getNearestShelter()

    @Test
    void testGetNearestShelterNullList() {
        Location location = new Location(-80.8, 34.0, "29201");

        assertNull(shelterList.getNearestShelter(location, null));
    }

    @Test
    void testGetNearestShelterEmptyList() {
        Location location = new Location(-80.8, 34.0, "29201");

        assertNull(shelterList.getNearestShelter(location, new ArrayList<>()));
    }

    @Test
    void testGetNearestShelterDifferentLocations() {
        Location location = new Location(-80.7, 34.0, "29201");

        Shelter result = shelterList.getNearestShelter(location, shelters);
        Shelter nearest = shelters.get(0);
        assertEquals(nearest.getID(), result.getID());
    }

    @Test
    void testGetNearestShelterNullLocation() {
        Shelter shelter = createShelter(-80.8, 34.0, "29201", "123 Main St");
        ArrayList<Shelter> candidates = new ArrayList<>();
        candidates.add(shelter);

        assertNull(shelterList.getNearestShelter(null, candidates));
    }
}


