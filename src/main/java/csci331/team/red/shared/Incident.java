package csci331.team.red.shared;

import java.util.LinkedList;
import java.util.List;

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
	public final Character actor;

	public final List<Document> incidentDocuments;

	/**
	 * All Serializable classes MUST provide a zero-argument constructor
	 */
	@Deprecated
	public Incident() {
		incidentDocuments = null;
		actor = null;
	}

	/**
	 * Initialize a new @{link Stage}
	 * 
	 * @param actor
	 *            {@link Incident#actor}
	 * @param incidentDocuments
	 */
	public Incident(Character actor, List<Document> incidentDocuments) {
		this.actor = actor;
		this.incidentDocuments = incidentDocuments;
	}
}
