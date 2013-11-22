package csci331.team.red.shared;

public class Document {

	public Type DocumentType;
	public String[] TextFields;
	public Face face;

	public enum Type {
		DriversLicence, 
		GoldenTicket,
	}

	/**
	 * All Serializable classes MUST provide a zero-argument constructor
	 */
	@Deprecated
	public Document() {
	}

	public Document(Type t, String[] TextFields , Face face) {
		DocumentType = t;
		this.TextFields = TextFields;
		this.face = face;
	}

}