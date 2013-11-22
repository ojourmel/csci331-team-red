package csci331.team.red.shared;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import csci331.team.red.shared.callbacks.DBCallback;
import csci331.team.red.shared.callbacks.FieldCallback;

/**
 * Wrapper class for words said by the players and actors. <br>
 * FIXME: <b>Stub</b>
 * 
 * @author ojourmel , lduperron
 */
public class Dialogue {
	public static final Dialogue GENERIC = new Dialogue(
			"Ha Ha, that was funny", "Diety");

	private String speaker;
	private String dialogue;
	private Enum<?> callbackCode;

	/**
	 * All Serializable classes MUST provide a zero-argument constructor
	 */
	@Deprecated
	public Dialogue() {
		// TODO Auto-generated constructor stub
	}

	public Dialogue(String words, String speaker) {
		this(words, speaker, null);
	}

	public Dialogue(String words, String speaker, Enum<?> c) {
		this.dialogue = words;
		this.speaker = speaker;
		this.callbackCode = c;
	}

	public static Dialogue[] returnDialogArray(String[][] strings) {
		ArrayList<Dialogue> temp = new ArrayList<Dialogue>();

		for (int i = 0; i < strings.length; i++) {
			Dialogue tempDialog = new Dialogue(strings[i][0], strings[i][1]);
			temp.add(tempDialog);

		}

		return temp.toArray(new Dialogue[0]);

	}

	public static Dialogue[] returnDialogArray(String[][] strings,
			Enum<?>[] callbackArray) {
		ArrayList<Dialogue> temp = new ArrayList<Dialogue>();

		for (int i = 0; i < strings.length; i++) {
			Dialogue tempDialog = null;
			if (callbackArray[i] != null) {
				tempDialog = new Dialogue(strings[i][0], strings[i][1],
						callbackArray[i]);
			} else {
				tempDialog = new Dialogue(strings[i][0], strings[i][1]);

			}

			temp.add(tempDialog);

		}

		return temp.toArray(new Dialogue[0]);

	}

	public String getDialogue() {
		return dialogue;
	}

	public String getSpeaker() {
		return speaker;
	}

	public Enum<?> getCallbackCode() {
		return callbackCode;
	}

	public List<Dialogue> introDialogue(Role role) {
		List<Dialogue> dialogues = new LinkedList<Dialogue>();
		switch (role) {
		case DATABASE:
			// used in tutorial dialogue
			String[][] dbStrArr = {
					{ "Well then... (Click to continue)", "Ominious Voice" },
					{ "Welcome to your first day on the job.", "Ominious Voice" },
					{ "Oh no!  The alerts start rolling in!", "Ominious Voice" },
					{
							"Some of them we can ignore, but some of them we should warn our partner about.  Up to you to decide which is which.",
							"Ominious Voice" },
					{ "Others are suspicious.  Like that 'Mary Test' one.",
							"Ominious Voice" },
					{
							"We should do a bit of research on her.\nType 'search prism mary test'\non your phone.",
							"Ominious Voice" },
					{
							"I assume you did it, given I'm just a voice.  That's an inconsistancy!  We should warn our partner about that too.",
							"Ominious Voice" },
					{
							"Your partner will also be reporting suspicious data to you to look up.  For example...",
							"Ominious Voice" },
					{ "Hey, can you look up DL '123456789' for me, buddy?",
							"Jerk" },
					{
							"These can occasionally bring up a large amount of infomation, and again, you must decide what is important to relay.",
							"Ominious Voice" },
					{ "I have no more guidance to give you.", "Ominious Voice" },
					{ "Mom, get out of my office...", "You" },

			};

			DBCallback[] dbCallArr = { null, DBCallback.startAlerts, null,
					DBCallback.MaryTestAlert, null, DBCallback.startAlerts,
					null, null, null, null, null, null };

			dialogues = Arrays.asList(returnDialogArray(dbStrArr, dbCallArr));
			break;
		case FIELDAGENT:

			String[][] faStrArr = {
					{ "Well then... (Click to continue)", "Ominious Voice" },
					{ "Welcome to your first day on the job.", "Ominious Voice" },
					{ "Why don't we approach someone?", "Ominious Voice" },
					{ "Hey, you!  Stop!", "You" },
					{ "...What?", "Girl" },
					{ "...Turn around", "You" },
					{ "...No.", "Girl" },
					{ "...Well, let's see your ID.", "You" },
					{ "Here.", "Girl" },
					{
							"You should call your partner and ask him if the infomation you've recieved is correct.",
							"Ominious Voice" },
					{
							"But I can tell you it is this time.  You should let her go.",
							"Ominious Voice" },

			};
			FieldCallback[] faCallArr = { null, null,
					FieldCallback.approachPerson, null, null, null, null,
					FieldCallback.giveDocuments, null, null, null, null, null,
					null };

			dialogues = Arrays.asList(returnDialogArray(faStrArr, faCallArr));

			break;
		default:
			break;
		}

		return dialogues;
	}

}
