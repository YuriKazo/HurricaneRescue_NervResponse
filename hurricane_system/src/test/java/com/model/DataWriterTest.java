package com.model;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// Test                                Reasoning
// testSaveAccount                     Test to see if saving an account works correctly
// testSaveShelter                     Test to see if saving a shelter works correctly
// testSaveReliefRequest               Test to see if saving a relief request works correctly
// testSaveEmptyField                  Should still save even if the instance variable of whatever it is saving is empty
// testSaveNullField                   Should still save even if the instance variable of whatever it is saving is null
// testSaveEmptyList                   Should still save even if the list being saved is empty

public class DataWriterTest {
private AccountList accounts = AccountList.getInstance();
    private ArrayList<Account> accountList = accounts.getUsers(); 
    private ShelterList shelters = ShelterList.getInstance();
    private ArrayList<Shelter> shelterList = shelters.getShelters();
    private ReliefRequestList reliefRequests = ReliefRequestList.getInstance();
    private ArrayList<ReliefRequest> reliefRequestList = reliefRequests.getRequests();

    @Before
    public void setup() {
        accountList.clear();
        shelterList.clear();
        reliefRequestList.clear();
        DataWriter.saveAccounts();
        DataWriter.saveReliefRequests();
        DataWriter.saveShelters();
    }

    @After
    public void teardown() {
        AccountList.getInstance().getUsers().clear();
        ShelterList.getInstance().getShelters().clear();
        ReliefRequestList.getInstance().getRequests().clear();
        DataWriter.saveAccounts();
        DataWriter.saveReliefRequests();
        DataWriter.saveShelters();
    }

    @Test
    public void testSaveAccount () {
        accountList.add(new Admin("John", "DJohnMustard@gmail.com", "Doe", "Mustard","111-222-3333"));
        accountList.add(new Victim("P", "QPMayo@gmail.com", "Quaso", "Mayo","111-222-3333"));
        accountList.add(new Volunteer("Holly", "HollyGuacamole@email.com", "guac", "mole", "222-333-4444"));
        DataWriter.saveAccounts();
        assertEquals(3, accountList.size());
    }

    @Test
    public void testSaveShelter() {
        shelterList.add(new Shelter(new Location(1, 2, "12345"), "Test Shelter"));
        shelterList.add(new Shelter(new Location(0, 0, "00000"), "null point"));
        DataWriter.saveShelters();
        assertEquals(2, shelterList.size());
    }

    @Test 
    public void testSaveReliefRequest() {
        reliefRequestList.add(new ReliefRequest("Do you have any ibuprofen I got a headache", PriorityLevel.LOW, new Location(87, 41, "60637"), 1, UUID.fromString("a1b2c3d4-0001-4aaa-9c11-000000000004"), new ArrayList<>()));
        reliefRequestList.add(new ReliefRequest("Do you have any headache I got ibuprofen", PriorityLevel.LOW, new Location(41, 87, "73606"), 99, UUID.fromString("9ad2a59e-4c84-49d7-af89-d874061f80f9"), new ArrayList<>()));
        DataWriter.saveReliefRequests();
        assertEquals(2, reliefRequestList.size());
    }

    @Test 
    public void testSaveEmptyField() {
        reliefRequestList.add(new ReliefRequest("", PriorityLevel.LOW, new Location(0, 0, ""), 0, UUID.fromString("00000000-0000-0000-0000-000000000000"), new ArrayList<>()));
        DataWriter.saveReliefRequests();
        assertEquals(1, reliefRequestList.size());
        shelterList.add(new Shelter(new Location(0, 0, ""), ""));
        DataWriter.saveShelters();
        assertEquals(1, shelterList.size());
        accountList.add(new Admin("", "", "", "", ""));
        DataWriter.saveAccounts();
        assertEquals(1, accountList.size());
    }

    @Test
    public void testSaveNullField() {
        reliefRequestList.add(new ReliefRequest(null, null, new Location(0, 0, null), 0, null, new ArrayList<>()));
        DataWriter.saveReliefRequests();
        assertEquals(1, reliefRequestList.size());
        shelterList.add(new Shelter(new Location(0, 0, null), null));
        DataWriter.saveShelters();
        assertEquals(1, shelterList.size());
        accountList.add(new Admin(null, null, null, null, null));
        DataWriter.saveAccounts();
        assertEquals(1, accountList.size());
    }

    @Test 
    public void testSaveEmptyList() {
        DataWriter.saveAccounts();
        assertEquals(0, accountList.size());
        DataWriter.saveShelters();
        assertEquals(0, shelterList.size());
        DataWriter.saveReliefRequests();
        assertEquals(0, reliefRequestList.size());

    }

}
