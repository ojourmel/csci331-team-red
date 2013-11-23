package csci331.team.red.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.esotericsoftware.kryonet.Connection;

import csci331.team.red.dao.CharacterRepository;
import csci331.team.red.network.NetServer;
import csci331.team.red.shared.Character;
import csci331.team.red.shared.Decision;
import csci331.team.red.shared.Incident;
import csci331.team.red.shared.Level;
import csci331.team.red.shared.Message;
import csci331.team.red.shared.Posture;
import csci331.team.red.shared.Result;
import csci331.team.red.shared.Role;

/**
 * Main game logic class. Manages flow of information between the two clients,
 * and manages loading random game assets, as well as keeping client's in sync.
 * 
 * Implements {@link Thread} and is runnable via {@link ServerEngine#start()}
 * 
 * @author ojourmel
 * 
 *         CONTROLLER PATTERN
 * 
 */
public class ServerEngine extends Thread {

	/**
	 * The maximum number of players supported by this game.
	 */
	private static final int MAX_PLAYERS = 2;
	private static final int MIN_INCIDENTS = 5;

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

	private DialogueHandler dialogueHandler = new DialogueHandler(RANDOM);
	private DocumentHandler documentHandler = new DocumentHandler(RANDOM);
	private AlertHandler alertHandler = new AlertHandler(RANDOM);

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

			// TODO: Handle alert generation, and re-factor to generate
			// incidents in advance, so that alerts can be done in a reasonable
			// manner

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
	 * @return the {@link Result} of the query
	 */
	public Result onDatabaseSearch(String query) {
		System.err.println("onDatabaseSearch: " + query);
		query = query.toUpperCase();

		// TODO: Handle database queries
		return Result.INVALID_COMMAND;
	}

	/**
	 * Callback for when a player changes {@link State}
	 * 
	 * @param role
	 *            The role of the player changing state
	 * @param posture
	 */

	public void onPostureChange(Role role, Posture posture) {
		System.err.println("onPostureChange " + role.toString() + " "
				+ posture.toString());
		if (playerOne.getRole() == role) {
			playerOne.setState(posture);
		} else {
			playerTwo.setState(posture);
		}
	}

	public void onPlayerConnect(Connection connection) {
		if (numPlayerConnected < MAX_PLAYERS) {
			numPlayerConnected++;
		} else {
			network.setRole(connection, Role.UNDEFINDED);
			network.send(Message.DISCONNECTED, Role.UNDEFINDED);
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
	 * Callback for when a player has disconnected. Shut down the other clients,
	 * and stop this {@link ServerEngine} <br>
	 * 
	 * @param role
	 *            {@link Role}
	 */
	public void onPlayerDisconnect(Role role) {
		System.err.println("onPlayerDisconnect " + role.toString());
		network.send(Message.DISCONNECTED);

		this.interrupt();
	}

	/**
	 * Callback for when a player has quit. Shut down the other clients, and
	 * stop this {@link ServerEngine}
	 */
	public void onPlayerQuit(Role role) {
		System.err.println("onPlayerQuit " + role.toString());

		network.send(Message.QUIT);
		this.interrupt();
	}

	/**
	 * Callback when a player pauses their game.<br>
	 */
	public void onPlayerPause(Role role) {
		System.err.println("onPlayerPause " + role.toString());

		network.send(Message.PAUSE);

	}

	/**
	 * Callback when a player resumes their game.
	 * 
	 * @param role
	 */
	public void onPlayerResume(Role role) {
		System.err.println("onPlayerResume " + role.toString());

		network.send(Message.RESUME);
	}

	/**
	 * Callback when a stage is completed. Causes a new stage to be sent to the
	 * players
	 */
	public void onIncidentComplete(Decision decition) {
		System.err.println("onIncidentCompleate " + decition.toString());

		// update player scores from the outcome of this stage. Update any
		// environment variables, and tell run() to wake up, and do another
		// stage.

		lock.lock();
		incidentOver.signal();
		lock.unlock();
	}

	/**
	 * Direct method to kill the server from the client, without going through
	 * the network.
	 */
	public void kill() {
		System.err.println("Server Killed by Client");
		network.send(Message.DISCONNECTED);
		this.interrupt();
	}

	private void initLevel(Level level) {

		// preload a few incidents, to be used in this level
		for (int i = 0; i < MIN_INCIDENTS; i++) {
			Incident incident = new Incident(repo.getCharacter());
			documentHandler.initDocuments(incident);
			dialogueHandler.initDialogue(incident);
			incidents.add(incident);
		}

		System.err.println("Starting Level " + level.getName());
		// start level one.
		network.send(Message.START_LEVEL, level);
		// inform each player of the role they will be playing in this level
		network.send(Message.SET_ROLE, playerOne.getRole(), playerOne.getRole());
		network.send(Message.SET_ROLE, playerTwo.getRole(), playerTwo.getRole());

	}

	private boolean doIntro() {
		// TODO: Change these to scripted characters
		Character character = repo.getIntroCharacter();
		Incident incident = new Incident(character);
		documentHandler.initIntroDocuments(incident);
		dialogueHandler.initIntroDialogue(incident);
		incident.setAlerts(alertHandler.introAlerts());

		System.err.println("Starting First (Scripted) Tutorial Incident");

		network.send(Message.START_INCIDENT, incident);

		// Send in the dialogue for both players
		network.send(Message.DIALOGUE, incident.getDbDialogue(), Role.DATABASE);
		network.send(Message.DIALOGUE, incident.getFieldDialogue(),
				Role.FIELDAGENT);

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

	private boolean doBoss(Boss boss) {

		Character character = repo.getBossCharacter(boss);
		Incident incident = new Incident(character);
		documentHandler.initBossDocuments(incident, boss);
		dialogueHandler.initBossDialogue(incident, boss);
		incident.setAlerts(alertHandler.bossAlerts(boss));

		System.err.println("Starting (Scripted) Boss Incident "
				+ boss.toString());
		network.send(Message.START_INCIDENT, incident);

		// Send in the dialogue for both players
		network.send(Message.DIALOGUE, incident.getDbDialogue(), Role.DATABASE);
		network.send(Message.DIALOGUE, incident.getFieldDialogue(),
				Role.FIELDAGENT);

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
	 * @return false if
	 */
	private boolean doIncident() {

		// Save a new incident for later
		Character character = repo.getCharacter();
		Incident incident = new Incident(character);
		documentHandler.initDocuments(incident);
		dialogueHandler.initDialogue(incident);
		incidents.add(incident);

		// get one for this incident
		incident = incidents.pollFirst();
		// generate alerts, using data from a random incident in the list/queue
		incident.setAlerts(alertHandler.getAlerts(incidents.get(RANDOM
				.nextInt(incidents.size()))));

		network.send(Message.START_INCIDENT, incident);
		network.send(Message.DIALOGUE, incident.getDbDialogue(), Role.DATABASE);
		network.send(Message.DIALOGUE, incident.getFieldDialogue(),
				Role.FIELDAGENT);

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

	private void tearDown() {
		// game shutting down due to clientDisconect.
		// TODO: Release network connection
		System.err.println("Server thread shutting down");
	}
}
