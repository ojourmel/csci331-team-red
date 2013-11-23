package csci331.team.red.shared;

import java.util.List;

import com.sun.beans.decoder.DocumentHandler;

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

	private List<Alert> alerts;

	private List<Dialogue> dbDialogue;
	private List<Dialogue> fieldDialogue;

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
	}

}
