package com.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * Test                                | Reasoning
 * ------------------------------------|-------------------------------------------------------------
 * isMatch()                           |
 *      Correct credentials            | Correct email and password should return true
 *      Incorrect email                | Email must match the account email
 *      Incorrect password             | Password must match the account password
 *      Uppercase email                | Email comparison should not be case-sensitive
 *      Uppercase password             | Password comparison should be case-sensitive
 *      Null email                     | Null email should return false
 *      Null password                  | Null password should return false
 *                                     |
 * ------------------------------------|-------------------------------------------------------------
 * makeContact()                       |
 *      Valid phone number             | Contact should be added to the list
 *      Duplicate phone number         | Duplicate contacts should not be added
 *      Multiple phone numbers         | Multiple different contacts should be allowed
 *                                     |
 * ------------------------------------|-------------------------------------------------------------
 * removeContact()                     |
 *      Existing contact               | Matching contact should be removed
 *      Non-existing contact           | Other contacts should not be affected
 *      Empty contact list             | Removing from an empty list should not cause an error
 *                                     |
 * ------------------------------------|-------------------------------------------------------------
 * setLocation()                       |
 *      Valid location                 | Current location should update
 *      New location                   | Previous location should be replaced
 *                                     |
 * ------------------------------------|-------------------------------------------------------------
 * Account Constructor                 |
 *      Default lists                  | New accounts should have empty lists
 *      Default location               | New accounts should have a default location
 *      Unique UUID                    | Each new account should have a unique ID
 *      Existing account data          | Saved account information should be restored
 */

public class AccountTest {
    private Account account;

    @BeforeEach
    public void setup() {
        account = new Account("John", "john@email.com", "Smith",
                "password123", "8035551234", AccountType.VOLUNTEER) {};
    }

    @Test
    public void testIsMatchCorrectCredentials() {
        assertTrue(account.isMatch("john@email.com", "password123"));
    }

    @Test
    public void testIsMatchIncorrectEmail() {
        assertFalse(account.isMatch("wrong@email.com", "password123"));
    }

    @Test
    public void testIsMatchIncorrectPassword() {
        assertFalse(account.isMatch("john@email.com", "wrongpassword"));
    }

    @Test
    public void testIsMatchUppercaseEmail() {
        assertTrue(account.isMatch("JOHN@EMAIL.COM", "password123"));
    }

    @Test
    public void testIsMatchUppercasePassword() {
        assertFalse(account.isMatch("john@email.com", "PASSWORD123"));
    }

    @Test
    public void testIsMatchNullEmail() {
        assertFalse(account.isMatch(null, "password123"));
    }

    @Test
    public void testIsMatchNullPassword() {
        assertFalse(account.isMatch("john@email.com", null));
    }

    @Test
    public void testMakeContactValidPhoneNumber() {
        account.makeContact(803555123);

        assertTrue(account.getEmergencyContact().contains("803555123"));
        assertEquals(1, account.getEmergencyContact().size());
    }

    @Test
    public void testMakeContactDuplicatePhoneNumber() {
        account.makeContact(803555123);
        account.makeContact(803555123);

        assertEquals(1, account.getEmergencyContact().size());
    }

    @Test
    public void testMakeContactMultiplePhoneNumbers() {
        account.makeContact(803555123);
        account.makeContact(803555456);

        assertEquals(2, account.getEmergencyContact().size());
        assertTrue(account.getEmergencyContact().contains("803555123"));
        assertTrue(account.getEmergencyContact().contains("803555456"));
    }

    @Test
    public void testRemoveContactExistingContact() {
        account.makeContact(803555123);

        account.removeContact(803555123);

        assertFalse(account.getEmergencyContact().contains("803555123"));
        assertEquals(0, account.getEmergencyContact().size());
    }

    @Test
    public void testRemoveContactNonExistingContact() {
        account.makeContact(803555123);

        account.removeContact(803555456);

        assertTrue(account.getEmergencyContact().contains("803555123"));
        assertEquals(1, account.getEmergencyContact().size());
    }

    @Test
    public void testRemoveContactEmptyList() {
        account.removeContact(803555123);

        assertEquals(0, account.getEmergencyContact().size());
    }

    @Test
    public void testSetLocationValidLocation() {
        Location location = new Location(34.0, -80.0, "29201");

        account.setLocation(location);

        assertEquals(location, account.getCurrentLocation());
    }

    @Test
    public void testSetLocationNewLocation() {
        Location firstLocation = new Location(34.0, -80.0, "29201");
        Location secondLocation = new Location(35.0, -81.0, "29020");

        account.setLocation(firstLocation);
        account.setLocation(secondLocation);

        assertEquals(secondLocation, account.getCurrentLocation());
    }

    @Test
    public void testAccountDefaultLists() {
        assertTrue(account.getSavedLocations().isEmpty());
        assertTrue(account.getSavedAccounts().isEmpty());
        assertTrue(account.getEmergencyContact().isEmpty());
    }

    @Test
    public void testAccountDefaultLocation() {
        assertNotNull(account.getCurrentLocation());
    }

    @Test
    public void testAccountUniqueUUID() {
        Account secondAccount = new Account("Jane", "jane@email.com",
                "Smith", "password456", "8035555678",
                AccountType.VOLUNTEER) {};

        assertNotEquals(account.getID(), secondAccount.getID());
    }

    @Test
    public void testAccountExistingData() {
        UUID id = UUID.randomUUID();
        ArrayList<Location> locations = new ArrayList<>();
        ArrayList<UUID> accounts = new ArrayList<>();
        ArrayList<String> contacts = new ArrayList<>();
        Location location = new Location(34.0, -80.0, "29201");

        locations.add(location);
        accounts.add(UUID.randomUUID());
        contacts.add("8035551234");

        Account restoredAccount = new Account(id, "John",
                "john@email.com", "Smith", "password123",
                "8035551234", AccountType.VOLUNTEER,
                locations, accounts, contacts, location) {};

        assertEquals(id, restoredAccount.getID());
        assertEquals("John", restoredAccount.getFirstName());
        assertEquals("Smith", restoredAccount.getLastName());
        assertEquals("john@email.com", restoredAccount.getEmail());
        assertEquals("password123", restoredAccount.getPassword());
        assertEquals("8035551234", restoredAccount.getPhoneNumber());
        assertEquals(AccountType.VOLUNTEER, restoredAccount.getType());
        assertEquals(locations, restoredAccount.getSavedLocations());
        assertEquals(accounts, restoredAccount.getSavedAccounts());
        assertEquals(contacts, restoredAccount.getEmergencyContact());
        assertEquals(location, restoredAccount.getCurrentLocation());
    }
}
