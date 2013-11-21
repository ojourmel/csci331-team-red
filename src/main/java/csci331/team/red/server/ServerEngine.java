package csci331.team.red.server;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.esotericsoftware.kryonet.Connection;

import csci331.team.red.dao.CharacterDAO;
import csci331.team.red.network.NetServer;
import csci331.team.red.shared.Dialogue;
import csci331.team.red.shared.Incident;
import csci331.team.red.shared.Level;
import csci331.team.red.shared.Message;
import csci331.team.red.shared.PlayerState;
import csci331.team.red.shared.Result;
import csci331.team.red.shared.Role;

/**
 * Main game logic class. Manages flow of information between the two clients,
 * and manages loading random game assets, as well as keeping client's in sync.
 * 
 * Implements {@link Thread} and is runnable via {@link ServerEngine#start()}
 * 
 * @author ojourmel
 */
public class ServerEngine extends Thread {

	/**
	 * The maximum number of players supported by this game.
	 */
	public static final int MAX_PLAYERS = 2;

	// Core game assets
	private NetServer network;
	private List<Incident> stages;
	private List<Level> levels;
	private Incident currentStage;
	private CharacterDAO dao;

	// The two players in the game
	private Player playerOne;
	private Player playerTwo;

	private int numPlayerConnected = 0;

	// Value to modify the base fraud probability of a Character
	private double fraudDifficulty = 1;

	// Locks and conditions for blocking on conditions while threading.
	private final Lock lock = new ReentrantLock();
	private final Condition clientsConnected = lock.newCondition();
	private final Condition stageOver = lock.newCondition();
	private final Condition paused = lock.newCondition();

	/**
	 * Create a new {@link ServerEngine} object. Use
	 * {@link ServerEngine#start()} to start the game engine in a new thread.
	 * 
	 * @author ojourmel
	 */
	public ServerEngine() {

		dao = new CharacterDAO();

		levels = new LinkedList<Level>();
		stages = new LinkedList<Incident>();

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
				// game shutting down due to clientDisconect.
				System.err.println("Server thread shutting down");
				return;
			} finally {
				lock.unlock();
			}
		}

		// Set up the first level
		// set up the environment for level one. (Campus)
		Level one = Level.getCampus();
		levels.add(one);

		// start level one.
		network.send(Message.START_LEVEL, one);

		// Set up an incident, with corresponding dialog for field and client

		for (int i = 0; i < 3; i++) {
			// set up a stage. Use the stats from the previous stages to affect
			// the next stage.
			// TODO: Get Characters from the Database Access Objects

			// send the stage to the clients
			// network.send(Message.START_STAGE, stage);

			// since everything that can happen in a stage is purly reactive,
			// (ie.
			// clients initiat calles...), simply wait for the clients to accept
			// or
			// reject this stage, then make a new one!
			try {
				lock.lock();
				stageOver.await();
			} catch (InterruptedException e) {
				// We got interrupted. Game Over. Save nothing.
				return;
			} finally {
				lock.unlock();
			}
		}

		// boss stage time!

		// network.send(Message.START_STAGE, stage);
		try {
			lock.lock();
			stageOver.await();
		} catch (InterruptedException e) {
			return;
		} finally {
			lock.unlock();
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

		query = query.toUpperCase();

		// TODO: Handle database queries
		return Result.INVALID;
	}

	/**
	 * Callback for when a player changes {@link State}
	 * 
	 * @param role
	 *            The role of the player changing state
	 * @param state
	 */
	public void onStateChange(Role role, PlayerState state) {
		if (playerOne.getRole() == role) {
			playerOne.setState(state);
		} else {
			playerTwo.setState(state);
		}

		// TODO: Allow for state to have an impact on actors.
	}

	/**
	 * Callback for when a player connects. If two players connect, then the
	 * main game logic is started.
	 */
	public void onPlayerConnect(Connection connection) {
		numPlayerConnected++;
		// TODO: Assign rols to connections using:

		// network.assignRole(connection, Role.DATABASE);

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
	 * TODO: Consider implementing a reconnect period, where a player could
	 * resume their game
	 */
	public void onPlayerDisconnect() {
		network.send(Message.DISCONNECTED);

		// Doing things this way means that all "Condition.await()" blocks will
		// have to be surrounded with try/catch blocks
		this.interrupt();
	}

	/**
	 * Callback for when a player has quit. Shut down the other clients, and
	 * stop this {@link ServerEngine}
	 */
	public void onPlayerQuit(Role role) {
		network.send(Message.QUIT);
		// TODO: This code *might* have some problems interrupting it'self. See
		// onPlayerDisconnect()
		this.interrupt();
	}

	/**
	 * Callback when a player pauses their game.<br>
	 * TODO: Determine how to keep track of <b> which</b> player paused, if
	 * necessary.
	 */
	public void onPlayerPause() {
		// pause any time-counting variables
		network.send(Message.PAUSE);
	}

	/**
	 * Callback when a player resumes their game. TODO: Determine how to keep
	 * track of <b> which</b> player resumed, if necessary
	 */
	public void onPlayerResume() {
		// resume any time-counting variables
		network.send(Message.RESUME);
	}

	/**
	 * Callback when a stage is completed. Causes a new stage to be sent to the
	 * players
	 */
	public void onStageComplete(boolean decition) {

		// update player scores from the outcome of this stage. Update any
		// environment variables, and tell run() to wake up, and do another
		// stage.

		lock.lock();
		stageOver.signal();
		lock.unlock();
	}

	public void kill() {
		// TODO Auto-generated method stub

	}

	private List<Dialogue> getIntroDialogue(Role role) {
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
		// FieldCallback[] callarr = { null, null,
		// csci331.team.red.clientEngine.callback.approachPerson, null,
		// null, null, null,
		// csci331.team.red.clientEngine.callback.giveDocuments, null,
		// null, null, null, null, null };
		//
		// List<Dialogue> dialogues = Arrays.asList(Dialogue.returnDialogArray(
		// strarr, callarr));

		return null;
	}
}
