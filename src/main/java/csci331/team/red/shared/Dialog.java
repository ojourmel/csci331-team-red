package csci331.team.red.shared;

import java.util.ArrayList;

/**
 * Wrapper class for words said by the players and actors. <br>
 * FIXME: <b>Stub</b>
 * 
 * @author ojourmel, lduperron
 */
public class Dialog {
	/**
	 * A basic generic {@link Dialog}
	 */
	public static final Dialog GENERIC = new Dialog("Ha Ha, that was funny",
			"Diety");

	private String speaker;
	private String dialogue;
	private Enum<?> callbackCode;

	/**
	 * Create a new piece of dialog
	 * 
	 * @param words
	 *            in this dialog
	 * @param speaker
	 *            of this dialog
	 */
	public Dialog(String words, String speaker) {
		this(words, speaker, null);
	}

	public Dialog(String words, String speaker, Enum<?> c) {
		this.dialogue = words;
		this.speaker = speaker;
		this.callbackCode = c;
	}

	/**
	 * @param strings
	 *            an array of strings, with each string's associated speaker, built into an array of
	 *            {@link Dialog}s
	 * @return an array of {@link Dialog}s
	 */
	public static Dialog[] returnDialogArray(String[][] strings) {
		ArrayList<Dialog> temp = new ArrayList<Dialog>();

		for (int i = 0; i < strings.length; i++) {
			Dialog tempDialog = new Dialog(strings[i][0], strings[i][1]);
			temp.add(tempDialog);

		}
		return temp.toArray(new Dialog[0]);
	}

	/**
	 * @param strings
	 *            an array of strings, with each string's associated speaker, built into an array of {@link Dialog}s
	 * @param callbackArray callbacks for each dialog pair.
	 * @return an array of {@link Dialog}s, with their associated callbacks 
	 */
	public static Dialog[] returnDialogArray(String[][] strings,
			Enum<?>[] callbackArray) {
		ArrayList<Dialog> temp = new ArrayList<Dialog>();

		for (int i = 0; i < strings.length; i++) {
			Dialog tempDialog = null;
			if (callbackArray[i] != null) {
				tempDialog = new Dialog(strings[i][0], strings[i][1],
						callbackArray[i]);
			} else {
				tempDialog = new Dialog(strings[i][0], strings[i][1]);

			}

			temp.add(tempDialog);

		}

		return temp.toArray(new Dialog[0]);

	}

	/**
	 * @return the text contained in this dialog
	 */
	public String getDialogue() {
		return dialogue;
	}

	/**
	 * @return the speaker of this dialog
	 */
	public String getSpeaker() {
		return speaker;
	}

	/**
	 * @return the callback code of this dialog
	 */
	public Enum<?> getCallbackCode() {
		return callbackCode;
	}
}
