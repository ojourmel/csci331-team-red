package csci331.team.red.server;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import csci331.team.red.dao.CharacterRepository;
import csci331.team.red.shared.Alert;
import csci331.team.red.shared.Boss;
import csci331.team.red.shared.Character;
import csci331.team.red.shared.Incident;

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
	private AlertBody alertBody = new AlertBody();

	/**
	 * The probability that any given {@link Alert} is relevant to an incident
	 */
	private static final double ALERT_RELEVENCE = 0.2;
	private static final int MIN_ALERTS = 6;
	private static final int MAX_ALERTS = 10;

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

		// ALERT_RELEVENCE determines if an alert will pertain to the incident
		for (int i = 0; i < nAlerts; i++) {
			if (RANDOM.nextDouble() < ALERT_RELEVENCE) {
				alerts.add(getAlert(incident));
			} else {
				alerts.add(genericAlert());
			}
		}

		return alerts;
	}

	/**
	 * @return the alerts used in the introduction
	 */
	public List<Alert> getIntroAlerts(Incident incident) {
		List<Alert> alerts = new LinkedList<Alert>();

		// TODO: Hard-code intro alerts

		return alerts;
	}

	/**
	 * @return the alerts used in the boss stage
	 */
	public List<Alert> getBossAlerts(Incident incident, Boss boss) {
		List<Alert> alerts = new LinkedList<Alert>();

		// TODO: Hard-code boss alerts

		switch (boss) {
		case THUGLIFE:
			break;
		default:
			// Programmer forgot to handle an additional boss type
			throw new RuntimeException("Invalid Boss " + boss.toString());
		}

		return alerts;
	}

	/**
	 * 
	 * @param incident
	 * @return a single alert that is custom built off of the {@link Incident}
	 *         details
	 */
	protected Alert getAlert(Incident incident) {

		// TODO make custom build an alert using the character in this incident

		Voice voice = Voice.values()[RANDOM.nextInt(Voice.values().length)];
		ActionWord word = ActionWord.values()[RANDOM.nextInt(ActionWord
				.values().length)];

		// the message depends on the
		String message = genericMessage(word);

		// now build the alert
		StringBuilder alert = new StringBuilder();
		alert.append(voice.who);
		alert.append(": ");
		alert.append(word.toString());
		alert.append("!\n");
		alert.append(message);

		return new Alert(alert.toString());

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

		// now build the alert
		StringBuilder alert = new StringBuilder();
		alert.append(voice.who);
		alert.append(": ");
		alert.append(word.toString());
		alert.append("!\n");
		alert.append(message);

		return new Alert(alert.toString());
	}

	/**
	 * @return a single alert message, given what kind of {@link ActionWord} is
	 *         provided
	 */
	private String genericMessage(ActionWord word) {
		String msg = "";
		int MAX_GATHERING = 10000;

		switch (word) {
		case ALERT:
			msg = alertBody.personSpoted(getDescription());
			break;
		case NOTICE:
			if (RANDOM.nextDouble() > ALERT_RELEVENCE) {
				msg = alertBody.gathering(RANDOM.nextInt(RANDOM
						.nextInt(MAX_GATHERING)));
			} else {
				msg = alertBody.isVisiting(repo.getCharacter());
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
	 *         to clothing color and description
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
