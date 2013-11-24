package csci331.team.red.server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import csci331.team.red.shared.Boss;
import csci331.team.red.shared.Document;
import csci331.team.red.shared.Gender;
import csci331.team.red.shared.Incident;

/**
 * Responsible for generating documents, corrupting them as we go
 * 
 * @author ojourmel
 * @author melany
 */
public class DocumentHandler extends csci331.team.red.dao.CharacterDAO {

	private final Random RANDOM;

	// Probability lets me change one piece of data if fraud/error
	private static final int PROBABILITY = 11;

	/**
	 * @param RANDOM
	 *            object to be used for probibility calculations
	 */
	public DocumentHandler(Random RANDOM) {
		this.RANDOM = RANDOM;
	}

	/**
	 * Initializes the scripted documents needed for the intro
	 * 
	 * @param incident
	 */
	public void initIntroDocuments(Incident incident) {

		// TODO:

	}

	/**
	 * Initializes the scripted documents needed for a given boss
	 * 
	 * @param incident
	 * @param boss
	 */
	public void initBossDocuments(Incident incident, Boss boss) {
		switch (boss) {
		case THUGLIFE:
			// TODO:
			break;
		}
	}

	/**
	 * 
	 * Given an Incident, data for an incident.actor is corrupted if the
	 * fraud/clericalError flag has been set
	 * 
	 * Corrupted data is saved to a document, which is stored in the Incident
	 * 
	 * Three documents are created: DriversLisence, GoldenTicket(?), and
	 * Passport
	 * 
	 * @param incident
	 * @author melany
	 */
	public void initDocuments(Incident incident) {
		List<Document> documents = new LinkedList<Document>();

		HashMap<String, String> characterAttributes = new HashMap<String, String>();

		characterAttributes.put(FIRSTNAME, incident.getActor().getFirstName());
		characterAttributes.put(LASTNAME, incident.getActor().getLastName());
		characterAttributes.put(DOB, incident.getActor().getDob());
		characterAttributes.put(DRIVERSID, incident.getActor().getDriversID());
		characterAttributes
				.put(PASSPORTID, incident.getActor().getPassportID());
		characterAttributes.put(ADDRESS, incident.getActor().getAddress());
		characterAttributes.put(CITY, incident.getActor().getCity());
		characterAttributes.put(REGION, incident.getActor().getRegion());
		characterAttributes.put(POSTAL, incident.getActor().getPostal());
		characterAttributes.put(COUNTRY, incident.getActor().getCountry());
		characterAttributes
				.put(OCCUPATION, incident.getActor().getOccupation());

		if (incident.getActor().getGender() == Gender.MALE) {
			characterAttributes.put(GENDER, "Male");
		} else {
			characterAttributes.put(GENDER, "Female");
		}

		// If incident.fraud or incident.ClericalError has been flagged
		// corrupt some of the data
		if ((incident.fraud) || (incident.clericalError)) {

			// Excluding Gender, there are 11 items that can be messed with
			// This will corrupt ONE item
			int s = RANDOM.nextInt(PROBABILITY);
			String newInput = "";
			switch (s) {
			case 0:
				// change firstName
				while ((newInput != "")
						&& (newInput != incident.getActor().getFirstName())) {
					newInput = getFName(randomID(FIRSTNAME));
				}
				characterAttributes.put(FIRSTNAME, newInput);
				break;
			case 1:
				// change lastName
				while ((newInput != "")
						&& (newInput != incident.getActor().getLastName())) {
					newInput = getLName(randomID(LASTNAME));
				}
				characterAttributes.put(LASTNAME, newInput);
				break;
			case 2:
				// change dob
				while ((newInput != "")
						&& (newInput != incident.getActor().getDob())) {
					newInput = getDOB();
				}
				characterAttributes.put(DOB, newInput);
				break;
			case 3:
				// change driversID
				while ((newInput != "")
						&& (newInput != incident.getActor().getDriversID())) {
					newInput = getDriversID();
				}
				characterAttributes.put(DRIVERSID, newInput);
				break;
			case 4:
				// change passportID
				while ((newInput != "")
						&& (newInput != incident.getActor().getPassportID())) {
					newInput = getPassportID(randomID(PASSPORTID));
				}
				characterAttributes.put(PASSPORTID, newInput);
				break;
			case 5:
				// change address
				while ((newInput != "")
						&& (newInput != incident.getActor().getAddress())) {
					newInput = getAddress(randomID(ADDRESS));
				}
				characterAttributes.put(ADDRESS, newInput);
				break;
			case 6:
				// change city
				while ((newInput != "")
						&& (newInput != incident.getActor().getCity())) {
					newInput = getCity(randomID(CITY));
				}
				characterAttributes.put(CITY, newInput);
				break;
			case 7:
				// change region
				while ((newInput != "")
						&& (newInput != incident.getActor().getRegion())) {
					newInput = getRegion(randomID(REGION));
				}
				characterAttributes.put(REGION, newInput);
				break;
			case 8:
				// change postal
				while ((newInput != "")
						&& (newInput != incident.getActor().getPostal())) {
					newInput = getPostal(randomID(POSTAL));
				}
				characterAttributes.put(POSTAL, newInput);
				break;
			case 9:
				// change country
				while ((newInput != "")
						&& (newInput != incident.getActor().getCountry())) {
					newInput = getCountry(randomID(COUNTRY));
				}
				characterAttributes.put(COUNTRY, newInput);
				break;
			case 10:
				// change occupation
				while ((newInput != "")
						&& (newInput != incident.getActor().getOccupation())) {
					newInput = getOccupation(randomID(OCCUPATION));
				}
				characterAttributes.put(OCCUPATION, newInput);
				break;
			default:
				// Error
				break;
			}
		}

		// Now create a new driver's license document
		// Also I added a Passport.
		// ...also why this list of documents...they all have the same info,
		// whether it is fraudulent or not
		Document.Type drivers = Document.Type.DriversLicence;
		Document driversDocument = new Document(drivers, characterAttributes);
		documents.add(driversDocument);

		// FIXME: Remove GoldenTicket
		Document.Type golden = Document.Type.GoldenTicket;
		Document goldenDocument = new Document(golden, characterAttributes);
		documents.add(goldenDocument);

		Document.Type passport = Document.Type.Passport;
		Document passportDocument = new Document(passport, characterAttributes);
		documents.add(passportDocument);

		incident.setIncidentDocuments(documents);
	}
}
