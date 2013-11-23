package csci331.team.red.server;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import csci331.team.red.shared.Alert;
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
		// generate a list of alerts for the given incident.

		return alerts;
	}

	public List<Alert> introAlerts() {
		List<Alert> alerts = new LinkedList<Alert>();

		return alerts;
	}

	public List<Alert> bossAlerts(Boss boss) {
		List<Alert> alerts = new LinkedList<Alert>();

		switch (boss) {
		case THUGLIFE:

			return alerts;
		case ALIEN:

			return alerts;
		default:
			return null;
		}
	}
}
