package csci331.team.red.server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import csci331.team.red.shared.Document;
import csci331.team.red.shared.Gender;
import csci331.team.red.shared.Incident;

/**
 * 
 * 
 */
public class DocumentHandler extends csci331.team.red.dao.CharacterDAO {

	private Random random;

	// Probability lets me change one piece of data if fraud/error
	private static final int PROBABILITY = 11;

	// FIXME: I have no idea what this is here for...
	public DocumentHandler(Random random) {
		this.random = random;
	}

	/**
	 * 
	 * Given an Incident, data for an incident.actor is corrupted if the
	 * fraud/clericalError flag has been set
	 * 
	 * Corrupted data is saved to a document
	 * 
	 * Three documents are created: DriversLisence, GoldenTicket(?), and
	 * Passport
	 * 
	 * @param incident
	 * @return A List of {@link Document}s
	 */
	public List<Document> getDocuments(Incident incident) {
		List<Document> documents = new LinkedList<Document>();

		HashMap<String, String> characterAttributes = new HashMap<String, String>();

		characterAttributes.put(FIRSTNAME, incident.actor.getFirstName());
		characterAttributes.put(LASTNAME, incident.actor.getLastName());
		characterAttributes.put(DOB, incident.actor.getDob());
		characterAttributes.put(DRIVERSID, incident.actor.getDriversID());
		characterAttributes.put(PASSPORTID, incident.actor.getPassportID());
		characterAttributes.put(ADDRESS, incident.actor.getAddress());
		characterAttributes.put(CITY, incident.actor.getCity());
		characterAttributes.put(REGION, incident.actor.getRegion());
		characterAttributes.put(POSTAL, incident.actor.getPostal());
		characterAttributes.put(COUNTRY, incident.actor.getCountry());
		characterAttributes.put(OCCUPATION, incident.actor.getOccupation());

		if (incident.actor.getGender() == Gender.MALE) {
			characterAttributes.put(GENDER, "Male");
		} else {
			characterAttributes.put(GENDER, "Female");
		}

		// If incident.fraud or incident.ClericalError has been flagged
		// corrupt some of the data
		if ((incident.fraud) || (incident.clericalError)) {

			// Excluding Gender, there are 11 items that can be messed with
			// This will corrupt with ONE item
			Random r = new Random();
			int s = r.nextInt(PROBABILITY);
			String newInput = "";
			switch (s) {
			case 0:
				// change firstName
				while ((newInput != "")
						&& (newInput != incident.actor.getFirstName())) {
					newInput = getFName(randomID(FIRSTNAME));
				}
				characterAttributes.put(FIRSTNAME, newInput);
				break;
			case 1:
				// change lastName
				while ((newInput != "")
						&& (newInput != incident.actor.getLastName())) {
					newInput = getLName(randomID(LASTNAME));
				}
				characterAttributes.put(LASTNAME, newInput);
				break;
			case 2:
				// change dob
				while ((newInput != "")
						&& (newInput != incident.actor.getDob())) {
					newInput = getDOB();
				}
				characterAttributes.put(DOB, newInput);
				break;
			case 3:
				// change driversID
				while ((newInput != "")
						&& (newInput != incident.actor.getDriversID())) {
					newInput = getDriversID();
				}
				characterAttributes.put(DRIVERSID, newInput);
				break;
			case 4:
				// change passportID
				while ((newInput != "")
						&& (newInput != incident.actor.getPassportID())) {
					newInput = getPassportID(randomID(PASSPORTID));
				}
				characterAttributes.put(PASSPORTID, newInput);
				break;
			case 5:
				// change address
				while ((newInput != "")
						&& (newInput != incident.actor.getAddress())) {
					newInput = getAddress(randomID(ADDRESS));
				}
				characterAttributes.put(ADDRESS, newInput);
				break;
			case 6:
				// change city
				while ((newInput != "")
						&& (newInput != incident.actor.getCity())) {
					newInput = getCity(randomID(CITY));
				}
				characterAttributes.put(CITY, newInput);
				break;
			case 7:
				// change region
				while ((newInput != "")
						&& (newInput != incident.actor.getRegion())) {
					newInput = getRegion(randomID(REGION));
				}
				characterAttributes.put(REGION, newInput);
				break;
			case 8:
				// change postal
				while ((newInput != "")
						&& (newInput != incident.actor.getPostal())) {
					newInput = getPostal(randomID(POSTAL));
				}
				characterAttributes.put(POSTAL, newInput);
				break;
			case 9:
				// change country
				while ((newInput != "")
						&& (newInput != incident.actor.getCountry())) {
					newInput = getCountry(randomID(COUNTRY));
				}
				characterAttributes.put(COUNTRY, newInput);
				break;
			case 10:
				// change occupation
				while ((newInput != "")
						&& (newInput != incident.actor.getOccupation())) {
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
		// FIXME: I have no idea what the "Golden Ticket" represents... but I
		// added it to the list
		// Also I added a Passport.
		// ...also why this list of documents...they all have the same info, whether it is fraudulent or not
		Document.Type drivers = Document.Type.DriversLicence;
		Document driversDocument = new Document(drivers, characterAttributes);
		documents.add(driversDocument);

		Document.Type golden = Document.Type.GoldenTicket;
		Document goldenDocument = new Document(golden, characterAttributes);
		documents.add(goldenDocument);

		Document.Type passport = Document.Type.Passport;
		Document passportDocument = new Document(passport, characterAttributes);
		documents.add(passportDocument);

		return documents;
	}
}
