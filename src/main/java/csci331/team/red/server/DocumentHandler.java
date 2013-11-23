package csci331.team.red.server;

import java.util.Random;

import csci331.team.red.shared.Incident;

/**
 * 
 * @author ojourmel
 */
public class DocumentHandler {

	private Random random;

	public DocumentHandler(Random random) {
		this.random = random;
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
		case ALIEN:

			break;
		}

	}
}
