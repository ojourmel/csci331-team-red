package csci331.team.red.shared;

/**
 * Wrapper class for words said by the players and actors. <br>
 * FIXME: <b>Stub</b>
 * 
 * @author ojourmel
 */
public class Dialog {
	public static final Dialog GENERIC = new Dialog("Ha Ha, that was funny");

	private String words;

	public Dialog(String words) {
		this.words = words;
	}

	public String getWords() {
		return words;
	}
}
