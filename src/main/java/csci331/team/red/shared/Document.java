package csci331.team.red.shared;

public class Document {

	public Type DocumentType;
	public String[] TextFields;

	public enum Type {
		DriversLicence, GoldenTicket,
	}

	/**
	 * All Serializable classes MUST provide a zero-argument constructor
	 */
	@Deprecated
	public Document() {
	}

	public Document(Type t, String[] TextFields) {
		DocumentType = t;
		this.TextFields = TextFields;
	}

}