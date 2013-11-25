package csci331.team.red.server;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import csci331.team.red.shared.Boss;
import csci331.team.red.shared.Dialogue;
import csci331.team.red.shared.Incident;
import csci331.team.red.shared.callbacks.DBCallback;
import csci331.team.red.shared.callbacks.FieldCallback;

/**
 * 
 * @author ojourmel
 */
public class DialogueHandler {

	private final Random RANDOM;

	private enum Voice {
		OMINIOUS("Ominious Voice"), YOU("You"), MOM("Mom"), GIRL("Girl"), THUG(
				"Thug");

		public final String who;

		private Voice(String who) {
			this.who = who;
		}
	}

	public DialogueHandler(Random RANDOM) {
		this.RANDOM = RANDOM;
	}

	/**
	 * 
	 * @param character
	 * @param documents
	 * @param role
	 * @return A List of {@link Dialogue}s
	 */
	public void initDialogue(Incident incident) {
		List<Dialogue> dbDialogues = new LinkedList<Dialogue>();
		List<Dialogue> faDialogues = new LinkedList<Dialogue>();

		// TODO: Generate dialogue for both roles

		// generate accurate, dialogue, pulling values from character, and
		// documents. Here is where we can corrupt data if we would like

		// build the dialogue for each role

		incident.setDbDialogue(dbDialogues);
		incident.setFieldDialogue(faDialogues);
	}

	/**
	 * 
	 * @param incident
	 * @author ojourmel
	 */
	public void initIntroDialogue(Incident incident) {

		// Database Dialogue
		String[][] dbStrArr = {
				{ "Well then... (Click to continue)", Voice.OMINIOUS.who },
				{ "Welcome to your first day on the job.", Voice.OMINIOUS.who },
				{ "Oh no!  The alerts start rolling in!", Voice.OMINIOUS.who },
				{
						"Some of them we can ignore, but some of them we should warn our partner about.  Up to you to decide which is which.",
						Voice.OMINIOUS.who },
				{ "Others are suspicious.  Like that 'Mary Test' one.",
						Voice.OMINIOUS.who },
				{
						"We should do a bit of research on her.\nType 'search prism mary test'\non your phone.",
						Voice.OMINIOUS.who },
				{
						"I assume you did it, given I'm just a voice.  That's an inconsistancy!  We should warn our partner about that too.",
						Voice.OMINIOUS.who },
				{
						"Your partner will also be reporting suspicious data to you to look up.  For example...",
						Voice.OMINIOUS.who },
				{
						"These can occasionally bring up a large amount of infomation, and again, you must decide what is important to relay.",
						Voice.OMINIOUS.who },
				{ "I have no more guidance to give you.", Voice.OMINIOUS.who },
				{ "Mom, get out of my office...", Voice.YOU.who }, };

		DBCallback[] dbCallArr = { null, DBCallback.startAlerts, null,
				DBCallback.MaryTestAlert, null, DBCallback.startAlerts };

		List<Dialogue> dbDialogues = new LinkedList<Dialogue>(
				Arrays.asList(Dialogue.returnDialogArray(dbStrArr, dbCallArr)));
		incident.setDbDialogue(dbDialogues);

		// Field Agent Dialogue
		String[][] faStrArr = {
				{ "Well then... (Click to continue)", Voice.OMINIOUS.who },
				{ "Welcome to your first day on the job.", Voice.OMINIOUS.who },
				{ "Why don't we approach someone?", Voice.OMINIOUS.who },
				{ "Hey, you!  Stop!", Voice.YOU.who },
				{ "...What?", Voice.GIRL.who },
				{ "...Turn around", Voice.YOU.who },
				{ "...No.", Voice.GIRL.who },
				{ "...Well, let's see your ID.", Voice.YOU.who },
				{ "Here.", Voice.GIRL.who },
				{
						"You should call your partner and ask him if the infomation you've recieved is correct.",
						Voice.OMINIOUS.who },
				{ "I reccomend looking up the girls driver's licence",
						Voice.OMINIOUS.who },
				{ "...", Voice.OMINIOUS.who },
				{
						"Well, if there isn't any problems, you should probibly let her go...",
						Voice.OMINIOUS.who }, };
		FieldCallback[] faCallArr = { null, null, null, null, null, null, null,
				FieldCallback.giveDocuments };

		List<Dialogue> faDialogues = new LinkedList<Dialogue>(
				Arrays.asList(Dialogue.returnDialogArray(faStrArr, faCallArr)));

		incident.setFieldDialogue(faDialogues);
	}

	public void initBossDialogue(Incident incident, Boss boss) {
		switch (boss) {
		case THUGLIFE:

			// Database Dialogue
			String[][] dbStrArr = { { "This is it, your moment to shine",
					Voice.MOM.who }, };

			DBCallback[] dbCallArr = { DBCallback.startAlerts };

			List<Dialogue> dbDialogues = new LinkedList<Dialogue>(
					Arrays.asList(Dialogue.returnDialogArray(dbStrArr,
							dbCallArr)));
			incident.setDbDialogue(dbDialogues);

			// Field Agent Dialogue
			String[][] faStrArr = {
					{ "Who do we have here?", Voice.OMINIOUS.who },
					{ "You should approach him.  He looks suspicious.",
							Voice.OMINIOUS.who },
					{ "420 YOLO SWAG MOFO", Voice.THUG.who },
					{
							"Oh dear.  Quick, switch to AGGRESSIVE and use your ULTRATASER 9000 X-TREME EDITION",
							Voice.OMINIOUS.who },
					{ "...Will you back off, old lady?  I've got this.",
							Voice.YOU.who },
					{ "...Aren't you Harold's mom?", Voice.YOU.who },
					// Long-Term -- : Refactor actor voices to be more flexible
					{
							"I'm just here making sure everything goes okay for him.",
							"Harold's Mom" }, { "...", Voice.YOU.who }, };

			FieldCallback[] faCallArr = { null };

			List<Dialogue> faDialogues = new LinkedList<Dialogue>(
					Arrays.asList(Dialogue.returnDialogArray(faStrArr,
							faCallArr)));

			incident.setFieldDialogue(faDialogues);
			break;
		}
	}
}
