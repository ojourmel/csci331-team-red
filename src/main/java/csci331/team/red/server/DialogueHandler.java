package csci331.team.red.server;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import csci331.team.red.shared.Character;
import csci331.team.red.shared.Dialogue;
import csci331.team.red.shared.Document;
import csci331.team.red.shared.Role;
import csci331.team.red.shared.callbacks.DBCallback;
import csci331.team.red.shared.callbacks.FieldCallback;

/**
 * 
 * @author ojourmel
 */
public class DialogueHandler {

	private Random random;

	public DialogueHandler(Random random) {
		this.random = random;
	}

	/**
	 * 
	 * @param character
	 * @param documents
	 * @param role
	 * @return A List of {@link Dialogue}s
	 */
	public List<Dialogue> getDialogues(Character character,
			List<Document> documents, Role role) {
		List<Dialogue> dialogues = new LinkedList<Dialogue>();

		// TODO:
		// generate accurate, dialogue, pulling values from character, and
		// documents. Here is where we can corrupt data if we would like

		// build the dialogue for each role

		return dialogues;
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

			dialogues = new LinkedList<Dialogue>(Arrays.asList(Dialogue
					.returnDialogArray(dbStrArr, dbCallArr)));
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

			dialogues = new LinkedList<Dialogue>(Arrays.asList(Dialogue
					.returnDialogArray(faStrArr, faCallArr)));

			break;
		default:
			break;
		}

		return dialogues;
	}
}
