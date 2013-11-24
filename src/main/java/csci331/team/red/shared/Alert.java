package csci331.team.red.shared;

/**
 * Java representation of an Alert.<br>
 * Contains the text to be displayed
 * 
 */
public class Alert {

	public String alertText;

	/**
	 * All Serializable classes MUST provide a zero-argument constructor
	 */
	@Deprecated
	public Alert() {
	}

	/**
	 * 
	 * @param alertText
	 */
	public Alert(String alertText) {
		this.alertText = alertText;
	}
}
