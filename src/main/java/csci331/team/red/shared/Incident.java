package csci331.team.red.shared;

import java.util.List;
import java.util.Random;

/**
 * A basic java class to represent a stage<br>
 * FIXME: <b>Stub</b>
 * 
 * @author ojourmel
 */
public class Incident extends csci331.team.red.dao.CharacterDAO {

	/**
	 * The actor in this {@link Incident}
	 */
	public final Character actor;
	public final List<Document> incidentDocuments;

	public final List<Alert> alerts;

	public final List<Dialogue> dbDialogue;
	public final List<Dialogue> fieldDialogue;

	// additional flags for fraud/error factor
	public final boolean fraud;
	public final boolean clericalError;

	// Probability Factor (currently a 3 in 7 chance of error)
	public final static int PROBABILITY = 7;

	/**
	 * All Serializable classes MUST provide a zero-argument constructor
	 */
	@Deprecated
	public Incident() {
		actor = null;
		incidentDocuments = null;
		alerts = null;
		dbDialogue = null;
		fieldDialogue = null;
		fraud = false;
		clericalError = false;
	}

	/**
	 * Initialize a new @{link Stage}
	 * 
	 * Uses a 3/7 probability for some kind of error or fraud
	 * 
	 * FIXME: I don't know if you want to give the constructor a list of
	 * documents anymore as DocumentHandler takes an Incident and not a Character
	 * 
	 * @param actor
	 *            {@link Incident#actor}
	 * @param incidentDocuments
	 */
	public Incident(Character actor, List<Document> incidentDocuments) {
		this.actor = actor;
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
