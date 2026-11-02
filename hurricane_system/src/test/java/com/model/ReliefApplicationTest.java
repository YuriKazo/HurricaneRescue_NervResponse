/*
 * Test                                | Reasoning
 * ------------------------------------|----------------------------------------------------------------------
 * getInstance():                      |
 *      Called twice                   | The facade is a singleton, so every call must return the same object
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * login():                            |
 *      Valid credentials              | An existing account must be able to log in
 *      Wrong password                 | A correct email with the wrong password must be rejected
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * logout():                           |
 *      Logged in user                 | After logging out there must be no current account
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * createAccount():                    |
 *      Valid account                  | A new person must be able to sign up
 *      Duplicate email                | Each email can only belong to one account
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * makeRequest():                      |
 *      Logged in                      | A logged in victim must be able to ask for help
 *      Logged out                     | Only logged in users can make requests
 *      Zero victims                   | A request must be for at least one person
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * registerPets():                     |
 *      Victim                         | A victim must be able to say they have pets
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * registerDisabilities():             |
 *      Valid disability               | A victim must be able to list a disability
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * updateLocation():                   |
 *      Logged in                      | The account must remember where the person is
 *      Logged out                     | Must not crash when nobody is logged in
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * markComplete():                     |
 *      Volunteer                      | Volunteers are not allowed to complete requests
 *      Volunteer message              | The system must explain that the volunteer role lacks permission
 *      Victim with rescuer            | A victim can confirm they were helped, which completes the request
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * markComplete(UUID):                 |
 *      Volunteer                      | Volunteers are not allowed to complete requests
 *      Victim                         | A victim can complete their own request
 *      Shelter manager                | A shelter manager must be able to complete requests
 *      Unknown request ID             | An ID with no request must not crash the program
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * viewAccount(UUID):                  |
 *      Existing ID                    | Must return the account with the matching ID
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * viewShelters(int zipcode):          |
 *      Matching zipcode               | Only the shelters in that zip code are returned
 *      No matching zipcode            | No shelters in the zip code must give null
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * viewShelter(UUID):                  |
 *      Existing ID                    | Must return the shelter with the matching ID
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * viewMyShelters():                   |
 *      Shelter manager                | A shelter manager must see the shelters they manage
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * viewNearestSuitableShelter():       |
 *      No suitable shelter            | Must return null when no shelter has the wanted capability
 *      Logged out                     | Must not crash when nobody is logged in
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * viewRequests():                     |
 *      Victim                         | Only requests that nobody accepted are still open
 *      Logged out                     | Must not crash when nobody is logged in
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * viewUSerReliefRequest():            |
 *      Own requests                   | A person must only see the requests they made
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * removeAccount():                    |
 *      Own account                    | People can delete their own account
 *      Victim removes another         | Only admins can remove other people's accounts
 *      Admin removes another          | Admins can remove any account
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * saveAccount():                      |
 *      Unknown ID                     | An ID with no account must change nothing
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * makeContact():                      |
 *      New number                     | A logged in user can save an emergency contact
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * removeContact():                    |
 *      Saved number                   | A saved emergency contact must be removable
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * getNearestRequest():                |
 *      Volunteer                      | A volunteer with an open request should get it
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * getContact():                       |
 *      Volunteer                      | Volunteers need a way to reach the victim
 *      Null account                   | Null must give empty text, not crash
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * makeShelter():                      |
 *      Shelter manager                | A shelter manager can add a new shelter
 *      Volunteer                      | Only shelter managers can add shelters
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * updateOccupancy():                  |
 *      Shelter manager                | A shelter manager can change the occupancy
 *      Above capacity                 | A shelter cannot hold more people than its capacity
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * updateTotalOccupancy():             |
 *      Shelter manager                | A shelter manager can change the capacity
 *      Below current occupancy        | A shelter cannot be smaller than the people inside
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * updateWaterLeft():                  |
 *      Shelter manager                | A shelter manager can change the water left
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * acceptRequest():                    |
 *      Volunteer                      | A volunteer can take on an open request
 *      Completed request              | A finished request cannot be taken again
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * changeRequestStatus():              |
 *      Accepted request               | The volunteer can move the request forward
 *                                     |
 * ------------------------------------|----------------------------------------------------------------------
 * changePriority():                   |
 *      Accepted request               | The priority of the current request can change
 */
package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReliefApplicationTest {
	private ReliefApplication application = ReliefApplication.getInstance();
	private AccountList accountList = AccountList.getInstance();
	private ShelterList shelterList = ShelterList.getInstance();
	private ReliefRequestList requestList = ReliefRequestList.getInstance();
	private PrintStream originalOut = System.out;
	private Victim victim;
	private Volunteer volunteer;
	private Admin admin;

	@Before
	public void setUp() {
		application.logout();
		accountList.getUsers().clear();
		shelterList.getShelters().clear();
		requestList.getRequests().clear();

		victim = new Victim("Sophred", "sophred@test.com", "Carter", "victimPass1", "803-555-0101");
		volunteer = new Volunteer("Rosa", "rosa@test.com", "Delgado", "volunteerPass1", "803-555-0102");
		admin = new Admin("Felix", "felix@test.com", "Kjellberg", "adminPass1", "803-555-0103");
		accountList.getUsers().add(victim);
		accountList.getUsers().add(volunteer);
		accountList.getUsers().add(admin);
	}

	@After
	public void tearDown() {
		System.setOut(originalOut);
		application.logout();
		accountList.getUsers().clear();
		shelterList.getShelters().clear();
		requestList.getRequests().clear();
	}

	// adds a request with the given status to the request list
	private ReliefRequest addRequest(Status status, UUID requestor) {
		ReliefRequest request = new ReliefRequest("Need a ride to a shelter", PriorityLevel.HIGH,
				new Location(-79.93, 32.77, "29401"), 3, requestor, new ArrayList<>());
		request.changeRequestStatus(status);
		requestList.getRequests().add(request);
		return request;
	}

	// adds a shelter in the given zip code to the shelter list
	private Shelter addShelter(String zip, int total, int current, Capabilities... capabilities) {
		Shelter shelter = new Shelter(UUID.randomUUID(), total, current, new Location(-79.97, 32.85, zip),
				"100 Test Street", new ArrayList<>(List.of(capabilities)), 100, new ArrayList<>());
		shelterList.getShelters().add(shelter);
		return shelter;
	}

	// getInstance()

	@Test
	public void testGetInstanceCalledTwice() {
		assertSame(ReliefApplication.getInstance(), ReliefApplication.getInstance());
	}

	// login()

	@Test
	public void testLoginValidCredentials() {
		assertTrue(application.login("sophred@test.com", "victimPass1"));
	}

	@Test
	public void testLoginWrongPassword() {
		assertFalse(application.login("sophred@test.com", "wrongPass"));
	}

	// logout()

	@Test
	public void testLogoutLoggedInUser() {
		application.login("sophred@test.com", "victimPass1");
		application.logout();

		assertNull(application.viewAccount());
	}

	// createAccount()

	@Test
	public void testCreateAccountValidAccount() {
		Account created = application.createAccount("Maria", "Lopez", "maria@test.com", "mariaPass1", "803-555-0199");

		assertEquals("maria@test.com", created.getEmail());
	}

	@Test
	public void testCreateAccountDuplicateEmail() {
		assertNull(application.createAccount("Maria", "Lopez", "sophred@test.com", "mariaPass1", "803-555-0199"));
	}

	// makeRequest()

	@Test
	public void testMakeRequestLoggedIn() {
		application.login("sophred@test.com", "victimPass1");
		application.makeRequest("Need a ride to a shelter", PriorityLevel.HIGH, 3, new ArrayList<>(List.of(Abilities.CAR)));

		assertEquals(1, requestList.getRequests().size());
	}

	@Test
	public void testMakeRequestLoggedOut() {
		application.makeRequest("Need a ride to a shelter", PriorityLevel.HIGH, 3, new ArrayList<>(List.of(Abilities.CAR)));

		assertEquals(0, requestList.getRequests().size());
	}

	@Test
	public void testMakeRequestZeroVictims() {
		application.login("sophred@test.com", "victimPass1");
		application.makeRequest("Need a ride to a shelter", PriorityLevel.HIGH, 0, new ArrayList<>());

		assertEquals(0, requestList.getRequests().size());
	}

	// registerPets()

	@Test
	public void testRegisterPetsVictim() {
		application.login("sophred@test.com", "victimPass1");
		application.registerPets();

		assertTrue(victim.hasPets());
	}

	// registerDisabilities()

	@Test
	public void testRegisterDisabilitiesValidDisability() {
		application.login("sophred@test.com", "victimPass1");
		application.registerDisabilities(Disabilities.BLIND);

		assertTrue(victim.getDisabilities().contains(Disabilities.BLIND));
	}

	// updateLocation()

	@Test
	public void testUpdateLocationLoggedIn() {
		application.login("sophred@test.com", "victimPass1");
		application.updateLocation(new Location(-79.93, 32.77, "29401"));

		assertEquals("29401", victim.getCurrentLocation().getZip());
	}

	@Test
	public void testUpdateLocationLoggedOut() {
		try {
			application.updateLocation(new Location(-79.93, 32.77, "29401"));
		} catch (Exception error) {
			fail("Updating the location while logged out should not throw: " + error);
		}
	}

	// markComplete()

	@Test
	public void testMarkCompleteVolunteer() {
		ReliefRequest request = addRequest(Status.ACCEPTED, victim.getID());
		volunteer.setCurrentTarget(request);
		application.login("rosa@test.com", "volunteerPass1");

		application.markComplete();

		assertEquals(Status.ACCEPTED, request.getStatus());
	}

	@Test
	public void testMarkCompleteVolunteerMessage() {
		ByteArrayOutputStream printed = new ByteArrayOutputStream();
		System.setOut(new PrintStream(printed));
		application.login("rosa@test.com", "volunteerPass1");

		application.markComplete();

		assertTrue(printed.toString().contains("Permission denied"));
	}

	@Test
	public void testMarkCompleteVictimWithRescuer() {
		ReliefRequest request = addRequest(Status.ACCEPTED, victim.getID());
		volunteer.setCurrentTarget(request);
		victim.setAssignedRescuer(volunteer);
		application.login("sophred@test.com", "victimPass1");

		application.markComplete();

		assertEquals(Status.COMPLETED, request.getStatus());
	}

	// markComplete(UUID)

	@Test
	public void testMarkCompleteByIdVolunteer() {
		ReliefRequest request = addRequest(Status.ACCEPTED, victim.getID());
		application.login("rosa@test.com", "volunteerPass1");

		application.markComplete(request.getID());

		assertEquals(Status.ACCEPTED, request.getStatus());
	}

	@Test
	public void testMarkCompleteByIdVictim() {
		ReliefRequest request = addRequest(Status.ACCEPTED, victim.getID());
		application.login("sophred@test.com", "victimPass1");

		application.markComplete(request.getID());

		assertEquals(Status.COMPLETED, request.getStatus());
	}

	@Test
	public void testMarkCompleteByIdShelterManager() {
		ReliefRequest request = addRequest(Status.ACCEPTED, victim.getID());
		application.login("felix@test.com", "adminPass1");

		application.markComplete(request.getID());

		assertEquals(Status.COMPLETED, request.getStatus());
	}

	@Test
	public void testMarkCompleteByIdUnknownRequest() {
		application.login("sophred@test.com", "victimPass1");

		try {
			application.markComplete(UUID.randomUUID());
		} catch (Exception error) {
			fail("Completing an unknown request should not throw: " + error);
		}
	}

	// viewAccount(UUID)

	@Test
	public void testViewAccountExistingId() {
		assertSame(volunteer, application.viewAccount(volunteer.getID()));
	}

	// viewShelters(int zipcode)

	@Test
	public void testViewSheltersMatchingZipcode() {
		addShelter("29405", 100, 10);
		addShelter("29405", 100, 10);
		addShelter("29418", 100, 10);

		assertEquals(2, application.viewShelters(29405).size());
	}

	@Test
	public void testViewSheltersNoMatchingZipcode() {
		addShelter("29405", 100, 10);

		assertNull(application.viewShelters(29201));
	}

	// viewShelter(UUID)

	@Test
	public void testViewShelterExistingId() {
		Shelter shelter = addShelter("29405", 100, 10);

		assertSame(shelter, application.viewShelter(shelter.getID()));
	}

	// viewMyShelters()

	@Test
	public void testViewMySheltersShelterManager() {
		Shelter shelter = addShelter("29405", 100, 10);
		admin.addShelter(shelter.getID());
		application.login("felix@test.com", "adminPass1");

		assertTrue(application.viewMyShelters().contains(shelter.getID()));
	}

	// viewNearestSuitableShelter()

	@Test
	public void testViewNearestSuitableShelterNoSuitableShelter() {
		addShelter("29405", 100, 10, Capabilities.FOOD);
		application.login("sophred@test.com", "victimPass1");

		assertNull(application.viewNearestSuitableShelter(3, new ArrayList<>(List.of(Capabilities.PETS_ALLOWED))));
	}

	@Test
	public void testViewNearestSuitableShelterLoggedOut() {
		try {
			application.viewNearestSuitableShelter(3, new ArrayList<>(List.of(Capabilities.PETS_ALLOWED)));
		} catch (Exception error) {
			fail("Searching for a shelter while logged out should not throw: " + error);
		}
	}

	// viewRequests()

	@Test
	public void testViewRequestsVictim() {
		addRequest(Status.NOT_ACCEPTED, victim.getID());
		addRequest(Status.NOT_ACCEPTED, victim.getID());
		addRequest(Status.COMPLETED, victim.getID());
		application.login("sophred@test.com", "victimPass1");

		assertEquals(2, application.viewRequests().size());
	}

	@Test
	public void testViewRequestsLoggedOut() {
		try {
			application.viewRequests();
		} catch (Exception error) {
			fail("Viewing requests while logged out should not throw: " + error);
		}
	}

	// viewUSerReliefRequest()

	@Test
	public void testViewUserReliefRequestOwnRequests() {
		addRequest(Status.NOT_ACCEPTED, victim.getID());
		addRequest(Status.NOT_ACCEPTED, victim.getID());
		addRequest(Status.NOT_ACCEPTED, volunteer.getID());
		application.login("sophred@test.com", "victimPass1");

		assertEquals(2, application.viewUSerReliefRequest().size());
	}

	// removeAccount()

	@Test
	public void testRemoveAccountOwnAccount() {
		application.login("sophred@test.com", "victimPass1");

		application.removeAccount(victim.getID());

		assertNull(accountList.getAccount(victim.getID()));
	}

	@Test
	public void testRemoveAccountVictimRemovesOther() {
		application.login("sophred@test.com", "victimPass1");

		application.removeAccount(volunteer.getID());

		assertNotNull(accountList.getAccount(volunteer.getID()));
	}

	@Test
	public void testRemoveAccountAdminRemovesOther() {
		application.login("felix@test.com", "adminPass1");

		application.removeAccount(volunteer.getID());

		assertNull(accountList.getAccount(volunteer.getID()));
	}

	// saveAccount()

	@Test
	public void testSaveAccountUnknownId() {
		application.saveAccount(UUID.randomUUID());

		assertEquals(3, accountList.getUsers().size());
	}

	// makeContact()

	@Test
	public void testMakeContactNewNumber() {
		application.login("sophred@test.com", "victimPass1");

		application.makeContact(5551234);

		assertTrue(victim.getEmergencyContact().contains("5551234"));
	}

	// removeContact()

	@Test
	public void testRemoveContactSavedNumber() {
		application.login("sophred@test.com", "victimPass1");
		application.makeContact(5551234);

		application.removeContact(5551234);

		assertEquals(0, victim.getEmergencyContact().size());
	}

	// getNearestRequest()

	@Test
	public void testGetNearestRequestVolunteer() {
		ReliefRequest request = addRequest(Status.NOT_ACCEPTED, victim.getID());

		assertSame(request, application.getNearestRequest(volunteer.getID()));
	}

	// getContact()

	@Test
	public void testGetContactVolunteer() {
		application.login("rosa@test.com", "volunteerPass1");

		assertEquals("sophred@test.com", application.getContact(victim));
	}

	@Test
	public void testGetContactNullAccount() {
		application.login("rosa@test.com", "volunteerPass1");

		assertEquals("", application.getContact(null));
	}

	// makeShelter()

	@Test
	public void testMakeShelterShelterManager() {
		application.login("felix@test.com", "adminPass1");

		UUID id = application.makeShelter(-79.97, 32.85, "29405", "100 Test Street");

		assertNotNull(shelterList.getShelter(id));
	}

	@Test
	public void testMakeShelterVolunteer() {
		application.login("rosa@test.com", "volunteerPass1");

		assertNull(application.makeShelter(-79.97, 32.85, "29405", "100 Test Street"));
	}

	// updateOccupancy()

	@Test
	public void testUpdateOccupancyShelterManager() {
		Shelter shelter = addShelter("29405", 100, 10);
		application.login("felix@test.com", "adminPass1");

		application.updateOccupancy(shelter.getID(), 40);

		assertEquals(40, shelter.getCurrentOccupancy());
	}

	@Test
	public void testUpdateOccupancyAboveCapacity() {
		Shelter shelter = addShelter("29405", 100, 10);
		application.login("felix@test.com", "adminPass1");

		application.updateOccupancy(shelter.getID(), 150);

		assertEquals(10, shelter.getCurrentOccupancy());
	}

	// updateTotalOccupancy()

	@Test
	public void testUpdateTotalOccupancyShelterManager() {
		Shelter shelter = addShelter("29405", 100, 10);
		application.login("felix@test.com", "adminPass1");

		application.updateTotalOccupancy(shelter.getID(), 300);

		assertEquals(300, shelter.getTotalOccupancy());
	}

	@Test
	public void testUpdateTotalOccupancyBelowCurrentOccupancy() {
		Shelter shelter = addShelter("29405", 100, 50);
		application.login("felix@test.com", "adminPass1");

		application.updateTotalOccupancy(shelter.getID(), 20);

		assertEquals(100, shelter.getTotalOccupancy());
	}

	// updateWaterLeft()

	@Test
	public void testUpdateWaterLeftShelterManager() {
		Shelter shelter = addShelter("29405", 100, 10);
		application.login("felix@test.com", "adminPass1");

		application.updateWaterLeft(shelter.getID(), 25);

		assertEquals(25, shelter.getWaterCapacity());
	}

	// acceptRequest()

	@Test
	public void testAcceptRequestVolunteer() {
		ReliefRequest request = addRequest(Status.NOT_ACCEPTED, victim.getID());
		application.login("rosa@test.com", "volunteerPass1");

		application.acceptRequest(request.getID());

		assertEquals(Status.ACCEPTED, request.getStatus());
	}

	@Test
	public void testAcceptRequestCompletedRequest() {
		ReliefRequest request = addRequest(Status.COMPLETED, victim.getID());
		application.login("rosa@test.com", "volunteerPass1");

		application.acceptRequest(request.getID());

		assertEquals(Status.COMPLETED, request.getStatus());
	}

	// changeRequestStatus()

	@Test
	public void testChangeRequestStatusAcceptedRequest() {
		ReliefRequest request = addRequest(Status.NOT_ACCEPTED, victim.getID());
		application.login("rosa@test.com", "volunteerPass1");
		application.acceptRequest(request.getID());

		application.changeRequestStatus(Status.INPROGRESS);

		assertEquals(Status.INPROGRESS, request.getStatus());
	}

	// changePriority()

	@Test
	public void testChangePriorityAcceptedRequest() {
		ReliefRequest request = addRequest(Status.NOT_ACCEPTED, victim.getID());
		application.login("rosa@test.com", "volunteerPass1");
		application.acceptRequest(request.getID());

		application.changePriority(PriorityLevel.LOW);

		assertEquals(PriorityLevel.LOW, request.getPriorityLevel());
	}
}