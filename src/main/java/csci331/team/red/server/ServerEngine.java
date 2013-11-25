package csci331.team.red.server;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.esotericsoftware.kryonet.Connection;

import csci331.team.red.dao.CharacterRepository;
import csci331.team.red.network.NetServer;
import csci331.team.red.shared.Alert;
import csci331.team.red.shared.Boss;
import csci331.team.red.shared.Character;
import csci331.team.red.shared.Decision;
import csci331.team.red.shared.Incident;
import csci331.team.red.shared.Level;
import csci331.team.red.shared.Message;
import csci331.team.red.shared.Posture;
import csci331.team.red.shared.Query;
import csci331.team.red.shared.Role;

/**
 * Main game logic class. Manages flow of information between the two clients,
 * and manages loading random game assets, as well as keeping client's in sync.
 * 
 * Implements {@link Thread} and is runnable via {@link ServerEngine#start()}
 * 
 * 
 * <br>
 * <br>
 * <br>
 * 
 * @author ojourmel
 */
public class ServerEngine extends Thread {

	/**
	 * The maximum number of players supported by this game.
	 */
	public static final int MAX_PLAYERS = 2;

	/**
	 * The size of the queue of incidents that is maintained
	 */
	private static final int MIN_INCIDENTS = 5;

	private static final double FRAUD_CHANCE = 0.25;
	private static final double ERROR_CHANCE = 0.25;

	/**
	 * The probiblity that his fraud is a scrub, and thus, get's caught.
	 */
	private static final double FRAUD_SCRUB_LEVEL = 0.25;
	/**
	 * The probability that the clerk is a beast, and catches a prievious
	 * mistake
	 */
	private static final double CLERK_BEAST_LEVEL = 0.25;

	// Core game assets
	private NetServer network;
	private CharacterRepository repo;
	private LinkedList<Incident> incidents;

	// The two players in the game
	private Player playerOne;
	private Player playerTwo;

	private int numPlayerConnected = 0;

	// Used through the ServerEngine to determine random events.
	private static final Random RANDOM = new Random();

	// Various random game element handlers
	private DialogueHandler dialogueHandler = new DialogueHandler(RANDOM);
	private DocumentHandler documentHandler = new DocumentHandler(RANDOM);
	private AlertHandler alertHandler;

	// Locks and conditions for blocking on conditions while threading.
	private final Lock lock = new ReentrantLock();
	private final Condition clientsConnected = lock.newCondition();
	private final Condition incidentOver = lock.newCondition();

	/**
	 * Create a new {@link ServerEngine} object. Use
	 * {@link ServerEngine#start()} to start the game engine in a new thread.
	 * 
	 * @author ojourmel
	 */
	public ServerEngine() {

		repo = new CharacterRepository();
		alertHandler = new AlertHandler(RANDOM, repo);

		incidents = new LinkedList<Incident>();

		playerOne = new Player();
		playerTwo = new Player();
	}

	/**
	 * Main entry point to game logic. Called via {@link ServerEngine#start()}
	 */
	@Override
	public void run() {

		// The network could not bind a port. Fatal error
		try {
			network = new NetServer(this);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		while (numPlayerConnected < 2) {
			try {
				lock.lock();
				clientsConnected.await();
			} catch (InterruptedException e) {
				tearDown();

				return;
			} finally {
				lock.unlock();
			}
		}

		System.err.println("Game Started");
		// Set up the first level
		// set up the environment for level one. (Campus)
		initLevel(Level.getCampus());
		// Set up the first (scripted incident)

		if (!doIntro()) {
			tearDown();
			return;
		}

		// Now begin the standard, random incidents
		for (int i = 0; i < 3; i++) {

			// TODO: Allow for past events to affect future events

			System.err.println("Starting Incident " + i);
			if (!doIncident()) {
				tearDown();
				return;
			}
		}
		// boss stage time!

		if (!doBoss(Boss.THUGLIFE)) {
			tearDown();
			return;
		}

		// Depending on how the players did...
		// Yah! Level One is done. On to level 2!
		// or...
		// Ohw. Level One was Too Hard. Game Over!
		// Or...
		// Well, you did mediocre, but that won't cut it. Level 1 AGAIN!
	}

	/**
	 * Callback for when a player executes a database query
	 * 
	 * @param query
	 *            executed
	 */
	public void onDatabaseSearch(Query query) {
		System.err.println("onDatabaseSearch: " + query.queryText);
		query.queryText = query.queryText.toUpperCase();

		// network.send(Message.DB_RESULT, Result.INVALID_COMMAND);

		// TODO: Handle database queries
	}

	/**
	 * Callback for when a player changes {@link Posture}
	 * 
	 * @param role
	 *            The role of the player changing Posture
	 * @param posture
	 */
	public void onPostureChange(Role role, Posture posture) {
		System.err.println("onPostureChange " + role.toString() + " "
				+ posture.toString());
		if (playerOne.getRole() == role) {
			playerOne.setPosture(posture);
		} else {
			playerTwo.setPosture(posture);
		}
	}

	/**
	 * Callback for when a player requests connection to the server
	 * 
	 * There are a max of {@link ServerEngine#MAX_PLAYERS} players allowed
	 * 
	 * This method also binds a {@link Connection} to a {@link Role} via
	 * {@link NetServer#setRole(Connection, Role)}
	 * 
	 * @param connection
	 */
	public void onPlayerConnect(Connection connection) {

		// If we are not at capacity, accept the connection
		if (numPlayerConnected < MAX_PLAYERS) {
			numPlayerConnected++;
		} else {
			// Two many players, insta-kick the new connection

			network.sendClient(connection, Message.DISCONNECT);
			return;
		}

		// First connection is first player, and
		// get's a random role
		if (playerOne.getRole() == Role.UNDEFINDED) {
			if (RANDOM.nextBoolean()) {
				playerOne.setRole(Role.DATABASE);
			} else {
				playerOne.setRole(Role.FIELDAGENT);
			}

			System.err.println("Player One has connected with Role: "
					+ playerOne.getRole().toString());
			network.setRole(connection, playerOne.getRole());

		} else {
			if (playerOne.getRole() == Role.DATABASE) {
				playerTwo.setRole(Role.FIELDAGENT);
			} else {
				playerTwo.setRole(Role.DATABASE);
			}

			System.err.println("Player Two has connected with Role: "
					+ playerTwo.getRole().toString());
			network.setRole(connection, playerTwo.getRole());
		}

		// Get the game going if we have enough players
		if (numPlayerConnected == MAX_PLAYERS) {
			lock.lock();
			clientsConnected.signal();
			lock.unlock();
		}
	}

	/**
	 * Callback for when a player has disconnected. Disconnect the other
	 * clients, and stop this {@link ServerEngine} <br>
	 * 
	 * @param role
	 *            {@link Role}
	 */
	public void onPlayerDisconnect(Role role) {
		System.err.println("onPlayerDisconnect " + role.toString());
		network.sendAll(Message.DISCONNECT);

		this.interrupt();
	}

	/**
	 * Callback for when a player has quit. Shut down the other clients, and
	 * stop this {@link ServerEngine}
	 */
	public void onPlayerQuit(Role role) {
		System.err.println("onPlayerQuit " + role.toString());

		network.sendAll(Message.QUIT);
		this.interrupt();
	}

	/**
	 * Callback when a player pauses their game.<br>
	 * Requests all other connected players pause their games
	 */
	public void onPlayerPause(Role role) {
		System.err.println("onPlayerPause " + role.toString());

		network.sendAll(Message.PAUSE);

	}

	/**
	 * Callback when a player resumes their game. Requests all other connected
	 * players to resume their game
	 * 
	 * @param role
	 */
	public void onPlayerResume(Role role) {
		System.err.println("onPlayerResume " + role.toString());

		network.sendAll(Message.RESUME);
	}

	/**
	 * Callback when an incident is completed. Signals the main thread to
	 * continue game play.
	 */
	public void onIncidentComplete(Decision decition) {
		System.err.println("onIncidentCompleate " + decition.toString());

		// TODO: update player scores from the outcome of this incident.

		/*
		 * Update any environment variables, and tell run() to wake up, and do
		 * another incident
		 */

		lock.lock();
		incidentOver.signal();
		lock.unlock();
	}

	/**
	 * Direct method to kill the server from the client, without going through
	 * the network. Useful in cases where no network connection could be
	 * established
	 */
	public void kill() {
		System.err.println("Server Killed by Client");
		network.sendAll(Message.DISCONNECT);
		this.interrupt();
	}

	/**
	 * Init's and communicates all the relevant information to begin the level
	 * 
	 * @param level
	 */
	private void initLevel(Level level) {

		// preload a few incidents, to be used in this level
		for (int i = 0; i < MIN_INCIDENTS; i++) {
			Incident incident = new Incident(repo.getCharacter());
			documentHandler.initDocuments(incident);
			dialogueHandler.initDialogue(incident);
			incidents.add(incident);
		}

		System.err.println("Starting Level " + level.getName());
		// start level
		network.sendAll(Message.START_LEVEL, level);
		// inform each player of the role they will be playing in this level
		network.sendClient(playerOne.getRole(), Message.SET_ROLE,
				playerOne.getRole());
		network.sendClient(playerTwo.getRole(), Message.SET_ROLE,
				playerTwo.getRole());

	}

	/**
	 * Executes the scripted intro incidents
	 * 
	 * @return false if the thread is interrupted
	 */
	private boolean doIntro() {
		Incident incident = new Incident(repo.getIntroCharacter());
		documentHandler.initIntroDocuments(incident);
		dialogueHandler.initIntroDialogue(incident);
		incident.setAlerts(alertHandler.getIntroAlerts(incident));

		System.err.println("Starting First (Scripted) Tutorial Incident");
		network.sendAll(Message.START_INCIDENT, incident);

		// Send in the dialogue for both players
		network.sendClient(Role.DATABASE, Message.DIALOGUE,
				incident.getDbDialogue());
		network.sendClient(Role.FIELDAGENT, Message.DIALOGUE,
				incident.getFieldDialogue());

		// wait for player's decision.
		try {
			lock.lock();
			incidentOver.await();
		} catch (InterruptedException e) {
			return false;
		} finally {
			lock.unlock();
		}

		return true;
	}

	/**
	 * Executes one of the scripted boss incidents
	 * 
	 * @param boss
	 * @return false if the thread is interrupted
	 */
	private boolean doBoss(Boss boss) {

		Character character = repo.getBossCharacter(boss);
		Incident incident = new Incident(character);
		documentHandler.initBossDocuments(incident, boss);
		dialogueHandler.initBossDialogue(incident, boss);
		incident.setAlerts(alertHandler.getBossAlerts(incident, boss));

		System.err.println("Starting (Scripted) Boss Incident "
				+ boss.toString());
		network.sendAll(Message.START_INCIDENT, incident);

		// Send in the dialogue for both players
		network.sendClient(Role.DATABASE, Message.DIALOGUE,
				incident.getDbDialogue());
		network.sendClient(Role.FIELDAGENT, Message.DIALOGUE,
				incident.getFieldDialogue());

		// wait for player's decision.
		try {
			lock.lock();
			incidentOver.await();
		} catch (InterruptedException e) {
			return false;
		} finally {
			lock.unlock();
		}

		return true;
	}

	/**
	 * Pulls an incident from the queue to display.<br>
	 * Gets alerts from <i> an </i> incident in the queue to display.<br>
	 * Adds a new incident to the queue
	 * 
	 * @return false if the thread is interrupted
	 */
	private boolean doIncident() {

		// Save a new incident for later
		Character character = repo.getCharacter();
		Incident incident = new Incident(character);

		// random chance that this person is a fraud, or perhaps, is victim of
		// a clerical error.

		if (RANDOM.nextDouble() < FRAUD_CHANCE) {
			character.setFraud(true);
			incident.fraud = true;

			if (RANDOM.nextDouble() < FRAUD_SCRUB_LEVEL) {
				incident.fraudCaught = true;
			} else {
				incident.fraudCaught = false;
			}
		} else {
			character.setFraud(false);
			incident.fraud = false;
		}

		if (RANDOM.nextDouble() < ERROR_CHANCE) {
			incident.clericalError = true;

			if (RANDOM.nextDouble() < CLERK_BEAST_LEVEL) {
				incident.clericalErrorCaught = true;
			} else {
				incident.clericalErrorCaught = false;
			}
		} else {
			incident.clericalError = false;
		}

		documentHandler.initDocuments(incident);
		dialogueHandler.initDialogue(incident);
		incidents.add(incident);

		List<Alert> alerts = new LinkedList<Alert>();
		// alerts pertain to two future, (or current) incidents
		alerts.addAll(alertHandler.getAlerts(incidents.get(RANDOM
				.nextInt(incidents.size()))));
		alerts.addAll(alertHandler.getAlerts(incidents.get(RANDOM
				.nextInt(incidents.size()))));

		// get one for this incident, removing it from the queue
		incident = incidents.pollFirst();
		// assign the alerts, the only thing not yet initialized.
		incident.setAlerts(alerts);

		// Server/Client responsibilities:
		// Characters, -- Client
		// Alerts, -- Client
		// Dialogue, -- Client
		network.sendAll(Message.START_INCIDENT, incident);
		network.sendClient(Role.DATABASE, Message.DIALOGUE,
				incident.getDbDialogue());
		network.sendClient(Role.FIELDAGENT, Message.DIALOGUE,
				incident.getFieldDialogue());

		// wait for player's decision.
		try {
			lock.lock();
			incidentOver.await();
		} catch (InterruptedException e) {
			return false;
		} finally {
			lock.unlock();
		}

		return true;
	}

	/**
	 * Called in various places when the server thread has been interrupted.
	 */
	private void tearDown() {
		// game shutting down due to clientDisconect/quit, or other reasons.
		network.killServer();
		System.err.println("Server Thread shutting down");
	}
}
