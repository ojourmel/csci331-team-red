package csci331.team.red.shared;

import java.util.List;
import java.util.Random;

import csci331.team.red.server.AlertHandler;
import csci331.team.red.server.DialogueHandler;

/**
 * A basic java class to represent a stage<br>
 * FIXME: <b>Stub</b>
 * 
 * @author ojourmel
 */
public class Incident {

	/**
	 * The actor in this {@link Incident}
	 */
	private Character actor;
	private List<Document> incidentDocuments;
	private List<Dialogue> dbDialogue;
	private List<Dialogue> fieldDialogue;
	private List<Alert> alerts;

	// additional flags for fraud/error factor
	public boolean fraud;
	public boolean fraudCaught = false;
	public boolean clericalError;
	public boolean clericalErrorCaught = false;

	// Probability Factor (currently a 3 in 7 chance of error)
	private final static int PROBABILITY = 7;

	/**
	 * All Serializable classes MUST provide a zero-argument constructor
	 */
	@Deprecated
	public Incident() {
	}

	/**
	 * Creates a skeleton Incident with just a {@link Character} Use
	 * {@link DocumentHandler}, {@link DialogueHandler}, and
	 * {@link AlertHandler} to initialize the full incident
	 * 
	 * @param actor
	 * @author ojourmel
	 */
	public Incident(Character actor) {
		this(actor, null, null, null, null);
	}

	/**
	 * Initialize a new @{link Stage}
	 * 
	 * Uses a 3/7 probability for some kind of error or fraud
	 * 
	 * FIXME: I don't know if you want to give the constructor a list of
	 * documents anymore as DocumentHandler takes an Incident and not a
	 * Character
	 * 
	 * @param actor
	 *            {@link Incident#actor}
	 * @param incidentDocuments
	 * @param alerts
	 * @param dbDialogue
	 * @param fieldDialogue
	 */
	public Incident(Character actor, List<Document> incidentDocuments,
			List<Alert> alerts, List<Dialogue> dbDialogue,
			List<Dialogue> fieldDialogue) {
		this.actor = actor;
		this.incidentDocuments = incidentDocuments;
		this.alerts = alerts;
		this.dbDialogue = dbDialogue;
		this.fieldDialogue = fieldDialogue;
	}

	public Character getActor() {
		return actor;
	}

	public List<Alert> getAlerts() {
		return alerts;
	}

	public List<Dialogue> getDbDialogue() {
		return dbDialogue;
	}

	public List<Dialogue> getFieldDialogue() {
		return fieldDialogue;
	}

	public List<Document> getIncidentDocuments() {
		return incidentDocuments;
	}

	public void setActor(Character actor) {
		this.actor = actor;
	}

	public void setAlerts(List<Alert> alerts) {
		this.alerts = alerts;
	}

	public void setDbDialogue(List<Dialogue> dbDialogue) {
		this.dbDialogue = dbDialogue;
	}

	public void setFieldDialogue(List<Dialogue> fieldDialogue) {
		this.fieldDialogue = fieldDialogue;
	}

	public void setIncidentDocuments(List<Document> incidentDocuments) {
		this.incidentDocuments = incidentDocuments;
		alerts = null;
		dbDialogue = null;
		fieldDialogue = null;

		Random random = new Random();

		// Determine if this incident has a fraud/error factor
		if (random.nextInt(PROBABILITY) == 0) {
			this.fraud = true;
			this.clericalError = false;

		} else if (random.nextInt(PROBABILITY) == 0) {
			this.fraud = false;
			this.clericalError = true;

		} else if (random.nextInt(PROBABILITY) == 0) {
			this.fraud = true;
			this.clericalError = true;

		} else {
			this.fraud = false;
			this.clericalError = false;
		}

	}
}