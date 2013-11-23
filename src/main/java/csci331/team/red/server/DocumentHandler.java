package csci331.team.red.server;

import java.util.Random;

import csci331.team.red.shared.Boss;
import csci331.team.red.shared.Incident;

/**
 * 
 * @author ojourmel
 */
public class DocumentHandler {

	private final Random RANDOM;

	public DocumentHandler(Random RANDOM) {
		this.RANDOM = RANDOM;
	}

	public void initIntroDocuments(Incident incident) {

	}

	/**
	 * FIXME: <b> Stub </b>
	 */
	public void initDocuments(Incident incident) {

		// TODO: Implemented by melany
	}

	public void initBossDocuments(Incident incident, Boss boss) {
		switch (boss) {
		case THUGLIFE:
			// TODO:
			break;
		}

	}
}
