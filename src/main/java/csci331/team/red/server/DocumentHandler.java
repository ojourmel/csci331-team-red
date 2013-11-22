package csci331.team.red.server;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import csci331.team.red.shared.Character;
import csci331.team.red.shared.Document;

/**
 * 
 * @author ojourmel
 */
public class DocumentHandler {

	private Random random;

	public DocumentHandler(Random random) {
		this.random = random;
	}

	/**
	 * 
	 * @param character
	 * @return A List of {@link Document}s
	 */
	public List<Document> getDocuments(Character character) {
		List<Document> documents = new LinkedList<Document>();

		// TODO:
		// generate some documents, pulling values from character.
		// Here is where we can corrupt data if we would like

		return documents;
	}
}
