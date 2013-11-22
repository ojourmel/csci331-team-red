package csci331.team.red.server;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import csci331.team.red.shared.Alert;
import csci331.team.red.shared.Character;
import csci331.team.red.shared.Document;
import csci331.team.red.shared.Incident;

/**
 * 
 * @author ojourmel
 */
public class AlertHandler {

	private Random random;

	public AlertHandler(Random random) {
		this.random = random;
	}

	/**
	 * 
	 * @param character
	 * @return A List of {@link Document}s
	 */
	public List<Alert> getAlerts(Incident incident) {
		List<Alert> alerts = new LinkedList<Alert>();

		// TODO:
		// generate some documents, pulling values from character.
		// Here is where we can corrupt data if we would like

		return documents;
	}
}
