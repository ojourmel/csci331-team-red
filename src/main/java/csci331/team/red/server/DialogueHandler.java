package csci331.team.red.server;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import csci331.team.red.dao.CharacterDAO;
import static csci331.team.red.dao.CharacterDAO.*;
import csci331.team.red.shared.Boss;
import csci331.team.red.shared.Dialogue;
import csci331.team.red.shared.Incident;
import csci331.team.red.shared.Character;
import csci331.team.red.shared.Posture;
import csci331.team.red.shared.Role;
import csci331.team.red.shared.callbacks.DBCallback;
import csci331.team.red.shared.callbacks.FieldCallback;

/**
 * 
 * Generates random (ish) dialogue for the player to interact with
 * 
 * @author ojourmel
 */
public class DialogueHandler {

	private final CharacterDAO dao;

	private final Random RANDOM;
	// The likly hood that mom will pop into the office
	private final double MOMS_NOSINESS = 0.25;

	// If people have coffee, they are much more likely to be nice...
	private final double COFFEE_FACTOR = 0.90;

	// Nothing can stop you if you have confidence...
	// Actors will not be fazed by an aggressive player
	private final double CONFIDENCE_IS_KEY = 0.25;

	// 65, OK, right, well, 66% chance that someone under pressure will
	// buckle, and tell the truth
	private final double NO_SPINE = 0.66;

	private enum Voice {
		OMINIOUS("Ominious Voice"), YOU("You"), MOM("Mom"), GIRL("Girl"), THUG(
				"Thug"), OMINIOUS_ALIEN("??0min1os (Squ3eky) V?ish 7?oice??");

		public final String who;

		private Voice(String who) {
			this.who = who;
		}
	}

	private enum Question {
		NAME("What is you name?"), VISIT_TIME(
				"How long are you spending around here?"), WORk(
				"Where do you work? Do you enjoy it there?");

		private String text;

		private Question(String text) {
			this.text = text;
		}
	};

	private String[] MOMMY = {
			"Hello sweetie, how is my Pumpkin Pie?",
			"My poor baby, you forgot your desert in your lunch!",
			"I'm just checking in, are things going OK in here? Do you want me to stay?",
			"Oh, I forgot to say, I folded your laundry for you. <3",
			"Oh honey, have you seen my portable phone?",
			"Ooooh, have you heard of this \"Twipper/Chipper\" internet thing?" };

	private String[] MOMMY_BACK = { "No words....",
			"Mom, I'm trying to work here!",
			"Yes Mom, OK Mom, Whatever Mom, Thanks Mom, I Will Mom, Love You Mom..." };

	private String[] HAPPY_HELLO = { "Good Morning, may I have a word",
			"Excuse me, may I speek with you",
			"Pardon me, I need to ask you a few questsions",
			"Hello there, is there anything I can help you with?",
			"What a fine day it is today!",
			"What a Bright, Beautifully, Blossomus day it is!" };

	private String[] HAPPY_BACK = { "Oh, hello, what was that?", "Hi there",
			"...", "Yes?", "What's up?" };

	private String[] GRUMPY_HELLO = { "You there! A word please.",
			"Stop! Stop I say!", "What do you want?",
			"Hold! I must have a word with you." };

	private String[] GRUMPY_BACK = { "What do you want?", "What's it to you?",
			"Ugg, What?", "..." };

	private String[] SHIFTY_BACK = { "...", "Um, is there a problem?",
			"Oh, um, ah, right.", "Ok, ok, ok... Oh! Hi there.",
			"Yes?? Is there a problem??" };

	private String[] ASK_DOC = { "Name, and ID please",
			"May I see some ID please?", "Do you have an Identification?",
			"You got a driver's licence?", "Licence please" };

	private String[] GET_DOC = { "Sure, here you go",
			"Why? Whatever, here you go",
			"Um, where is it?  Aha! Here you are", "Right away sir.",
			"Of course" };

	private String[] NO_DOC = {
			"Right, so I don't have any ID, I left my wallet at home.",
			"Oh, I must have left my wallet at home, I don't have any",
			"I just took my driver's test, and I left the paper at work" };

	public DialogueHandler(Random RANDOM) {
		this.RANDOM = RANDOM;
		dao = new CharacterDAO();
	}

	public List<Dialogue> WIN() {
		LinkedList<Dialogue> dialogues = new LinkedList<Dialogue>();
		dialogues.add(new Dialogue("Good Job Kiddo", Voice.OMINIOUS.who));
		return dialogues;
	}

	public List<Dialogue> EPIC_WIN() {
		LinkedList<Dialogue> dialogues = new LinkedList<Dialogue>();
		dialogues.add(new Dialogue(
				"Holy 80085 batman, that was ruddy brilliant",
				Voice.OMINIOUS.who));
		return dialogues;
	}

	public List<Dialogue> FAIL() {
		LinkedList<Dialogue> dialogues = new LinkedList<Dialogue>();
		dialogues.add(new Dialogue("Better luck next time. You f***ed it up",
				Voice.OMINIOUS.who));
		return dialogues;
	}

	public List<Dialogue> SUPER_FAIL() {
		LinkedList<Dialogue> dialogues = new LinkedList<Dialogue>();
		dialogues
				.add(new Dialogue(
						"Wow! That was so spectaculary bad, I almost felt bad for you, then I realized that you are a scrub.",
						Voice.OMINIOUS.who));
		return dialogues;
	}

	/**
	 * Generates final dialogues, that end the game
	 * 
	 * @param player
	 * @return
	 */
	public List<Dialogue> GAME_OVER(Player player) {
		// LONG-TERM: count number of arrests, innocents, criminals, etc...

		int score = player.win + player.epicWin * 2 - player.fail - player.fail
				* 2;
		boolean win = false;
		String winText = "";

		win = (score > 0);

		if (win) {
			winText = "SUCCEEDED";
		} else {
			winText = "FAILED!";
		}

		StringBuilder header = new StringBuilder();
		header.append("Well, well, this is it. You have tested your skills against the most average aoung us... and you have ");
		header.append(winText);
		header.append("\n");
		if (win) {
			header.append("Congragulations!");
		} else {
			header.append("What do you have to say for your self?");
		}

		StringBuilder stats = new StringBuilder();
		stats.append("No matter. Here is how you fared:");
		stats.append("\n");

		stats.append("Total number of Wins        = ");
		stats.append(player.win);
		stats.append("\n");
		stats.append("Total number of Epic Wins   = ");
		stats.append(player.epicWin);
		stats.append("\n");
		stats.append("Total number of Fails       = ");
		stats.append(player.fail);
		stats.append("\n");
		stats.append("Total number of Super FAILS = ");
		stats.append(player.superFail);
		stats.append("\n");

		String closer = "Your participation is greatly aprechiated, human.";
		String ps = "...";
		String pps = "So long, and thanks for all the fish.";

		LinkedList<Dialogue> dialogues = new LinkedList<Dialogue>();
		dialogues.add(new Dialogue(header.toString(), Voice.OMINIOUS.who));
		dialogues.add(new Dialogue(stats.toString(), Voice.OMINIOUS.who));
		dialogues.add(new Dialogue(closer, Voice.OMINIOUS_ALIEN.who));
		dialogues.add(new Dialogue(ps, Voice.OMINIOUS_ALIEN.who));

		if (player.getRole() == Role.DATABASE) {
			dialogues.add(new Dialogue(pps, Voice.OMINIOUS_ALIEN.who,
					DBCallback.endGame));
		} else if (player.getRole() == Role.FIELDAGENT) {
			dialogues.add(new Dialogue(pps, Voice.OMINIOUS_ALIEN.who,
					FieldCallback.endGame));
		}
		return dialogues;
	}

	/**
	 * @param incident
	 *            to generate dialogue from
	 * 
	 * @author ojourmel
	 */
	public void initDialogue(Incident incident, Posture posture) {
		// posture is only defined, and thus only has an impact on the
		// faDialogue

		LinkedList<Dialogue> dbDialogues = new LinkedList<Dialogue>();
		
		dbDialogues.add(new Dialogue(null, null, DBCallback.startAlerts));
		
		LinkedList<Dialogue> faDialogues = new LinkedList<Dialogue>();

		/*
		 * *********************** Field **********************
		 */
		// hello
		faDialogues.addAll(getHello(incident, posture));

		int chatty = 0;

		// you (might) get more words, if you are not aggressive,
		// but there is a chance people will tell the truth, when face
		// with an aggressor
		if (posture == Posture.AGGRESSIVE) {
			chatty = 0;
		} else if (posture == Posture.PASSIVE) {
			chatty = 1;
		} else {
			throw new RuntimeException("Invalid Posture: " + posture.toString());
		}

		// get the persons document. This might not actually cal the callback to
		// produce the documents, but it will have words...
		faDialogues.addAll(proudceDocuments(incident, posture));

		for (int i = 0; i <= chatty; i++) {
			faDialogues.addAll(getQA(incident, posture));
		}

		/*
		 * *********************** Database **********************
		 */
		// every so often, lets have "mom" pop back into the office.
		if (RANDOM.nextDouble() < MOMS_NOSINESS) {
			dbDialogues.addAll(momsCuriosity(incident));
		}

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
						"We should do a bit of research on her.\nType \"search prism name mary test\"\non your phone.",
						Voice.OMINIOUS.who },
				{
						"I assume you did it, given I'm just a voice. If you ever have questions about the phone, just type \"help\", and it should give you a hand.",
						Voice.OMINIOUS.who },
				{
						"That's an inconsistancy!  We should warn our partner if we see anything suspicious.",
						Voice.OMINIOUS.who },
				{
						"Your partner will also be reporting suspicious data to you to look up.  You might want to check in on them, and see how their doing...",
						Voice.OMINIOUS.who },
				{ "...", Voice.OMINIOUS.who },
				{
						"Searching for information can occasionally bring up a large amount of infomation, and again, you must decide what is important to relay.",
						Voice.OMINIOUS.who },
				{ "I have no more guidance to give you.", Voice.OMINIOUS.who },
				{ "Mom, get out of my office...", Voice.YOU.who }, };

		// Set up the callbacks, forcing some alerts to trigger on dialogue
		DBCallback[] dbCallArr = { null, DBCallback.startAlerts, null,
				DBCallback.MaryTestAlert, null, DBCallback.startAlerts };

		List<Dialogue> dbDialogues = new LinkedList<Dialogue>(
				Arrays.asList(Dialogue.returnDialogArray(dbStrArr, dbCallArr)));
		incident.setDbDialogue(dbDialogues);

		// Field Agent Dialogue
		String[][] faStrArr = {
				{ "Well then... (Click to continue)", Voice.OMINIOUS.who },
				{ "Welcome to your first day on the job.", Voice.OMINIOUS.who },
				{ "Who do we have here?", Voice.OMINIOUS.who },
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
		// Set up the callbacks, forcing documents to be presented after some
		// piece of dialogue
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
			String[][] dbStrArr = {
					{ "This is it, your moment to shine", Voice.MOM.who },
					{ "I here we are facing quite the bad a**", Voice.MOM.who },
					{ "A little birdy told me that his name is \"icE cOld\"",
							Voice.MOM.who }, { "...", Voice.YOU.who } };

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

	/**
	 * Returns some dialogues based on fraudCaught, posture, and several random.
	 * 
	 * @param incident
	 * @param posture
	 * @return
	 */
	private List<Dialogue> getHello(Incident incident, Posture posture) {
		List<Dialogue> dialogues = new LinkedList<Dialogue>();
		// Several hard coded hello messages. Choose one depending on
		// the posture. Possibly add in

		String hello = "";
		String back = "";

		switch (posture) {
		case PASSIVE:
			hello = HAPPY_HELLO[RANDOM.nextInt(HAPPY_HELLO.length)];

			if (incident.fraudCaught) {
				back = SHIFTY_BACK[RANDOM.nextInt(SHIFTY_BACK.length)];
			} else if (RANDOM.nextDouble() < COFFEE_FACTOR) {
				back = HAPPY_BACK[RANDOM.nextInt(HAPPY_BACK.length)];
			} else {
				back = GRUMPY_BACK[RANDOM.nextInt(GRUMPY_BACK.length)];
			}

			break;
		case AGGRESSIVE:
			hello = GRUMPY_HELLO[RANDOM.nextInt(GRUMPY_HELLO.length)];

			if (incident.fraudCaught) {
				if (RANDOM.nextDouble() < NO_SPINE) {
					back = SHIFTY_BACK[RANDOM.nextInt(SHIFTY_BACK.length)];
				} else if (RANDOM.nextDouble() < CONFIDENCE_IS_KEY) {
					back = HAPPY_BACK[RANDOM.nextInt(HAPPY_BACK.length)];
				}
			} else {
				if (RANDOM.nextDouble() < NO_SPINE) {
					back = HAPPY_BACK[RANDOM.nextInt(HAPPY_BACK.length)];
				} else {
					back = GRUMPY_BACK[RANDOM.nextInt(GRUMPY_BACK.length)];
				}
			}

			break;
		}

		dialogues.add(new Dialogue(hello, Voice.YOU.who));
		dialogues.add(new Dialogue(back, incident.getActor().getGender()
				.toFull()));

		return dialogues;
	}

	/**
	 * ask for, and get documents
	 * 
	 * @param incident
	 * @param posture
	 * @return
	 */
	private List<Dialogue> proudceDocuments(Incident incident, Posture posture) {
		List<Dialogue> dialogues = new LinkedList<Dialogue>();

		// ask for documents...
		String ask = ASK_DOC[RANDOM.nextInt(ASK_DOC.length)];
		String get = "";

		if (incident.fraudCaught) {
			if (posture == Posture.AGGRESSIVE) {
				// If they think they can get away with it...
				if (RANDOM.nextDouble() < CONFIDENCE_IS_KEY) {
					get = GET_DOC[RANDOM.nextInt(GET_DOC.length)];
				} else {
					get = NO_DOC[RANDOM.nextInt(NO_DOC.length)];
				}
			} else if (posture == Posture.PASSIVE) {
				get = GET_DOC[RANDOM.nextInt(GET_DOC.length)];
			}
		} else {
			get = GET_DOC[RANDOM.nextInt(GET_DOC.length)];
		}

		dialogues.add(new Dialogue(ask, Voice.YOU.who));
		dialogues.add(new Dialogue(get, incident.getActor().getGender()
				.toFull(), FieldCallback.giveDocuments));

		return dialogues;
	}

	/**
	 * 
	 * This code is more hard-coded then most, becaues of the need to answer
	 * questions correctly
	 * 
	 * 
	 * @param incident
	 * @param posture
	 * @return
	 */
	private List<Dialogue> getQA(Incident incident, Posture posture) {

		Character character = incident.getActor();

		List<Dialogue> qa = new LinkedList<Dialogue>();

		Question question = Question.values()[RANDOM
				.nextInt(Question.values().length)];

		// reduce the probiblity that VISIT_TIME is called.
		if (question == Question.VISIT_TIME) {
			question = Question.values()[RANDOM
					.nextInt(Question.values().length)];
		}

		String answer = "";
		String name = character.getGender().toFull();

		switch (question) {
		case NAME:

			name = character.getFirstName();

			switch (RANDOM.nextInt(2)) {
			case 0:

				if ((posture == Posture.AGGRESSIVE)
						&& (RANDOM.nextDouble() < CONFIDENCE_IS_KEY)) {
					answer = "It's " + character.getFirstName() + " f***ing "
							+ character.getLastName() + ".";
				} else {
					answer = "I'm " + character.getFirstName();
				}
				break;
			case 1:

				if (incident.fraudCaught && posture == Posture.PASSIVE) {

					answer = "Well, my first name is "
							+ character.getFirstName()
							+ " but everbody just calls me "
							+ java.lang.Character.toUpperCase(character
									.getFirstName().charAt(0));

				} else {
					answer = character.getFirstName();
				}

				break;
			}

			break;
		case WORk:
			if (incident.fraudCaught || incident.clericalError) {

				answer = "Yes, I very much like working at "
						+ dao.getOccupation(dao.randomID(OCCUPATION));
			} else {
				if (posture == Posture.AGGRESSIVE) {
					answer = "Do I go around asking if you like YOUR job? Jeez Louise";
				} else if (posture == Posture.PASSIVE) {

					if (RANDOM.nextBoolean()) {
						answer = "Well, to be honest sometimes working as a "
								+ character.getOccupation()
								+ " can get kind of tedious";
					} else {
						answer = "Oh yes, I love working as a "
								+ character.getOccupation() + ".";
					}
				}
			}

			break;
		case VISIT_TIME:
			// Has no connection to in-game values. Total BS question...
			switch (RANDOM.nextInt(2)) {
			case 0:

				if ((posture == Posture.AGGRESSIVE)
						&& (RANDOM.nextDouble() < CONFIDENCE_IS_KEY)) {

					answer = "For however long I feel like, *sir*";
				} else {

					answer = RANDOM.nextInt(52) + " more weeks, Officer";
				}
				break;
			case 1:

				if (incident.fraudCaught && posture == Posture.PASSIVE) {

					answer = "Meh, I kind of like it here... I'll be sticking around as much as I can...";

				} else {
					answer = "Well, I was planning on travaling to "
							+ dao.getCountry(dao.randomID(COUNTRY))
							+ " sometime soon...";
				}
				break;
			}
		}

		qa.add(new Dialogue(question.text, Voice.YOU.who));
		qa.add(new Dialogue(answer, name));

		return qa;
	}

	protected List<Dialogue> momsCuriosity(Incident incident) {
		List<Dialogue> dialogues = new LinkedList<Dialogue>();

		String mom = MOMMY[RANDOM.nextInt(MOMMY.length)];
		String kiddo = MOMMY_BACK[RANDOM.nextInt(MOMMY_BACK.length)];

		dialogues.add(new Dialogue(mom, Voice.MOM.who));
		dialogues.add(new Dialogue(kiddo, Voice.YOU.who));

		return dialogues;
	}
}
