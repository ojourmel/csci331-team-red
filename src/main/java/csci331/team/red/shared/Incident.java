package csci331.team.red.shared;

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

	/**
	 * The probability the {@link Incident#actor} in this {@link Incident} is a fraud
	 */
	public final double fraudFactor;
	
	/**
	 * The probability of a clerical error with the {@link Incident#actor}'s information in this {@link Incident}
	 */
	public final double errorFactor;
	
	public final List<Document> incidentDocuments;
	
	
	/**
	 * Initialize a new @{link Stage}
	 * @param {@link Incident#actor}
	 * @param {@link Incident#conFactor}
	 * @param {@link {@link Incident#errorFactor}
	 */
	public Incident(Character actor, double fraudFactor, double errorFactor , List<Document> incidentDocuments) {
		this.actor = actor;
		this.fraudFactor = fraudFactor;
		this.errorFactor = errorFactor;
		this.incidentDocuments = incidentDocuments;
	}
}

