package csci331.team.red.shared;

import java.util.HashMap;

public class Document {
	public Type DocumentType;
	public HashMap<String, String> characterAttributes;
	public String[] textFields;

	public enum Type {
		DriversLicence, GoldenTicket, Passport,
	}

	/**
	 * All Serializable classes MUST provide a zero-argument constructor
	 */
	@Deprecated
	public Document() {
	}

	public Document(Type t, HashMap<String, String> characterAttributes) {
		DocumentType = t;
		this.characterAttributes = characterAttributes;
	}
}