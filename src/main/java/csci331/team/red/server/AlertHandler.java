package csci331.team.red.server;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import csci331.team.red.dao.CharacterDAO;
import csci331.team.red.dao.CharacterRepository;
import csci331.team.red.shared.Alert;
import csci331.team.red.shared.Boss;
import csci331.team.red.shared.Character;
import csci331.team.red.shared.Incident;
import static csci331.team.red.dao.CharacterDAO.*;

/**
 * 
 * Handler class for generating (semi) random alerts. Contains several
 * hard-coded enums, arrays, and strings which represent the possible kinds,
 * (and wording) of alerts.
 * 
 * @author ojourmel
 */
public class AlertHandler {

	private final Random RANDOM;
	private CharacterRepository repo;
	private CharacterDAO dao;
	private AlertBody alertBody = new AlertBody();

	/**
	 * The probability that any given {@link Alert} is relevant to an incident
	 */
	private static final double ALERT_NOISE = 0.6;
	private static final int MIN_ALERTS = 6;
	private static final int MAX_ALERTS = 10;
	private static final int MAX_GATHERING = 1234;

	/**
	 * Hard-coded values for colors
	 */
	private static final String[] COLORS = { "Blue", "Green", "Army-camo",
			"Dark noir", "Hot pink", "Baby blue", "Tan", "Black", "Plad" };
	/**
	 * Hard-coded values for clothing
	 */
	private static final String[] CLOTHING = { "T-Shirt", "cardigan",
			"V-neck sweater", "Jump Suit", "body paint" };

	/**
	 * Voices of Alerts. Defines who can send out an alert.
	 * 
	 * @author ojourmel
	 */
	private enum Voice {
		UIV("University of Island Visionaries"), COPS(
				"Omianan Police Department");

		private String who;

		private Voice(String who) {
			this.who = who;
		}
	}

	/**
	 * Action Words. The possible "types" of alerts.
	 * 
	 * @author ojourmel
	 */
	private enum ActionWord {
		NOTICE, WARNING, ALERT, CONGRATULATIONS
	}

	/**
	 * A helper class responsible for generating specific types of alert
	 * messages.
	 * 
	 * @author ojourmel
	 */
	private class AlertBody {

		public String personSpoted(String description) {
			String body = "A person matching the description: \"" + description
					+ "\" was recently spotted";
			return body;
		}

		public String apprehended(String name) {
			String body = name
					+ " has been apprehended, and has been released on bail";
			return body;
		}

		public String isVisiting(Character character) {

			String body = "A " + character.getGender().toString()
					+ " who was born on " + character.getDob() + " from "
					+ character.getCountry() + " is visiting town";
			return body;
		}

		public String isLeaving(Character character) {

			String body = "A person working as a " + character.getOccupation()
					+ " visiting from " + character.getCountry()
					+ " has \"Thoroughly injoyed their time here\"";
			return body;

		}

		public String gathering(int size) {
			String body = "A gathering of more than " + size
					+ " people is expected shortly";
			return body;
		}

		public String birthday(int weeks) {
			String body = "A birthday has been planed for " + weeks;
			if (weeks == 1) {
				body += " week";
			} else {
				body += " weeks";
			}

			body += " from now";

			return body;
		}
	}

	/**
	 * Use the provided {@link Random} object to make any RANDOM decisions Use
	 * the provided {@link CharacterRepository} to reference other characters
	 * 
	 * @param RANDOM
	 * @param repo
	 */

	public AlertHandler(Random RANDOM, CharacterRepository repo) {
		this.RANDOM = RANDOM;
		this.repo = repo;
		this.dao = new CharacterDAO();
	}

	/**
	 * @param incident
	 * @return A list of {@link Alert}s<br>
	 *         Most of those Alerts will be useless, but some will be custom
	 *         built for the {@link Character} in this incident
	 */
	public List<Alert> getAlerts(Incident incident) {
		List<Alert> alerts = new LinkedList<Alert>();

		int nAlerts = RANDOM.nextInt(MAX_ALERTS - MIN_ALERTS) + MIN_ALERTS;
		int incidentAlerts = 0;

		// ALERT_RELEVENCE determines if an alert will pertain to the incident
		for (int i = 0; i < nAlerts; i++) {
			if (RANDOM.nextDouble() > ALERT_NOISE) {
				alerts.add(getAlert(incident));
				incidentAlerts++;
			} else {
				alerts.add(genericAlert());
			}
		}

		while (incidentAlerts < 2) {
			alerts.add(getAlert(incident));
			incidentAlerts++;
		}

		return alerts;
	}

	/**
	 * @return the alerts used in the introduction
	 */
	public List<Alert> getIntroAlerts(Incident incident) {
		List<Alert> alerts = new LinkedList<Alert>();

		// The intro alerts will be fairly simple, as most scripted alert will
		// be triggered by a dialogue callback

		Alert newbe = new Alert(
				"NOTICE!\n First day for many Junior Officers. Try not to F*** it up",
				"MANAGMENT");
		alerts.add(newbe);

		int nAlerts = RANDOM.nextInt(MAX_ALERTS - MIN_ALERTS) + MIN_ALERTS;

		for (int i = 0; i < nAlerts; i++) {
			alerts.add(genericAlert());
		}

		return alerts;
	}

	/**
	 * @return the alerts used in the boss stage
	 */
	public List<Alert> getBossAlerts(Incident incident, Boss boss) {
		List<Alert> alerts = new LinkedList<Alert>();

		switch (boss) {
		case THUGLIFE:

			Alert thugie = new Alert(
					ActionWord.ALERT.toString()
							+ " Hostile individual wearing \"thug life\" tshirt spotted on campus",
					Voice.UIV.who);

			alerts.add(thugie);

			int nAlerts = RANDOM.nextInt(MAX_ALERTS - MIN_ALERTS) + MIN_ALERTS;

			for (int i = 0; i < nAlerts; i++) {
				alerts.add(genericAlert());
			}

			break;
		default:
			// Programmer forgot to handle an additional boss type
			throw new RuntimeException("Invalid Boss " + boss.toString());
		}

		return alerts;
	}

	/**
	 * @param incident
	 * @return a single alert that is custom built off of the {@link Incident}
	 *         details
	 */
	protected Alert getAlert(Incident incident) {
		StringBuilder alert = new StringBuilder();
		Voice voice = Voice.UIV;
		ActionWord word = ActionWord.NOTICE;
		String message = "";

		boolean alertPending = true;
		while (alertPending) {
			// what variable do you focus on ?
			int focus = RANDOM.nextInt(3);

			switch (focus) {
			case 0:
				// look at fraud
				if (incident.fraud) {
					// generate a vague warning regarding this person
					voice = Voice.values()[RANDOM
							.nextInt(Voice.values().length)];
					word = ActionWord.NOTICE;

					message = "Local "
							+ dao.getOccupation(dao.randomID(OCCUPATION))
							+ " reports seeing a shifty looking "
							+ incident.getActor().getGender().toString();

					alertPending = false;
				}
				break;
			case 1:
				// look at fraudCaught
				if (incident.fraudCaught) {
					// generate a specific alert regarding this person
					voice = Voice.values()[RANDOM
							.nextInt(Voice.values().length)];
					word = ActionWord.ALERT;

					String age = incident.getActor().getDob().split("-")[0];
					message = "A " + incident.getActor().getGender().toString()
							+ " born in " + age
							+ " has been reported as a Con Artist!";

					alertPending = false;
				}
				break;
			case 2:
				// look at clericalErrorCaught
				// look at fraud
				if (incident.clericalErrorCaught) {
					// generate a aplolgetic notice regarding false documents.
					voice = Voice.values()[RANDOM
							.nextInt(Voice.values().length)];
					word = ActionWord.NOTICE;

					message = "It has come to the attention of the "
							+ voice.who
							+ " that certian errors have been make in processing the information of a one "
							+ incident.getActor().getFirstName() + " "
							+ incident.getActor().getLastName() + ".";

					alertPending = false;
				}
				break;
			}
		}
		// now build the alert
		alert.append(word.toString());
		alert.append("!\n");
		alert.append(message);

		return new Alert(alert.toString(), voice.who);
	}

	/**
	 * @return a single genericAlert
	 */
	protected Alert genericAlert() {

		Voice voice = Voice.values()[RANDOM.nextInt(Voice.values().length)];
		ActionWord word = ActionWord.values()[RANDOM.nextInt(ActionWord
				.values().length)];

		// the message depends on the
		String message = genericMessage(word);

		StringBuilder alert = new StringBuilder();
		alert.append(word.toString());
		alert.append("!\n");
		alert.append(message);

		return new Alert(alert.toString(), voice.who);
	}

	/**
	 * @return a single alert message, given what kind of {@link ActionWord} is
	 *         provided
	 */
	private String genericMessage(ActionWord word) {
		String msg = "";

		switch (word) {
		case ALERT:
			msg = alertBody.personSpoted(getDescription());
			break;
		case NOTICE:
			int notice = RANDOM.nextInt(4);

			switch (notice) {
			case 0:
			case 1:
				msg = alertBody.gathering(RANDOM.nextInt(RANDOM
						.nextInt(MAX_GATHERING)));
				break;
			case 2:
				msg = alertBody.isVisiting(repo.getCharacter());
				break;
			case 3:
				msg = alertBody.isLeaving(repo.getCharacter());
				break;
			}
			break;
		case WARNING:
			msg = alertBody.apprehended(repo.getCharacter().getFirstName());
			break;
		case CONGRATULATIONS:
			msg = alertBody.birthday(RANDOM.nextInt(52) + 1);
			break;
		default:
			break;
		}
		return msg;
	}

	/**
	 * @return a physical description of a person. This description is limited
	 *         to clothing color and description, and does NOT have any
	 *         relevance to the actual appearance of the art assets
	 */
	private String getDescription() {
		String c1 = CLOTHING[RANDOM.nextInt(CLOTHING.length)];
		String desc = "";
		desc += COLORS[RANDOM.nextInt(COLORS.length)];
		desc += " ";
		desc += c1.toString();

		if (RANDOM.nextBoolean()) {
			desc += " and ";
			desc += COLORS[RANDOM.nextInt(COLORS.length)];
			desc += " ";

			String c2 = CLOTHING[RANDOM.nextInt(CLOTHING.length)];
			while (c2 == c1) {
				c2 = CLOTHING[RANDOM.nextInt(CLOTHING.length)];
			}
			desc += c2.toString();
		}
		return desc;
	}
}
