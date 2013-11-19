package csci331.team.red.shared;

public class Document
{

	public Type DocumentType;
	public String[] TextFields;
	
	public enum Type
	{
		DriversLicence,
		GoldenTicket,
	}
	
	public Document(Type t, String[] TextFields)
	{
		
		DocumentType = t;
		this.TextFields = TextFields;
		
	}

}