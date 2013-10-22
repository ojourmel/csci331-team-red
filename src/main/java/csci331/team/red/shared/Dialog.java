package csci331.team.red.shared;

/**
 * Wrapper class for words said by the players and actors. <br>
 * FIXME: <b>Stub</b>
 * 
 * @author ojourmel
 */
public class Dialog {
	
	/**
	 * A basic generic {@link Dialog}
	 */
	public static final Dialog GENERIC = new Dialog("Generic dialog, lol");

	private String words;

	/**
	 * Create a new Dialog object
	 * @param words set words in this {@link Dialog}
	 */
	public Dialog(String words) {
		this.words = words;
	}

	/**
	 * @return the words stored in this {@link Dialog}
	 */
	public String getWords() {
		return words;
	}
}
