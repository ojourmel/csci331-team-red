package csci331.team.red.shared;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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

	public List<Dialogue> introDialogue(Role role) 
	{
		List<Dialogue> dialogues = new LinkedList<Dialogue>();
		switch (role) 
		{
		case DATABASE:
			String[][] strarr = {
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
			FieldCallback[] callarr = { null, null,
					FieldCallback.approachPerson, null, null, null, null,
					FieldCallback.giveDocuments, null, null, null, null, null,
					null };

			dialogues = Arrays.asList(Dialogue
					.returnDialogArray(strarr, callarr));

			return dialogues;
		case FIELDAGENT:
			break;
		}
		
		
		return null;
	}

}
