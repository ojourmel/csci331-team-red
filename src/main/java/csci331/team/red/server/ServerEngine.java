package csci331.team.red.server;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
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
import csci331.team.red.shared.Posture;
import csci331.team.red.shared.Result;
import csci331.team.red.shared.Role;
import csci331.team.red.shared.callbacks.FieldCallback;

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
	private List<Incident> incident;
	private List<Level> levels;
	private Incident currentIncident;
	private CharacterDAO dao;

	// The two players in the game
	private Player playerOne;
	private Player playerTwo;

	private int numPlayerConnected = 0;

	// Value to modify the base fraud probability of a Character
	private double fraudDifficulty = 1;
	
	private static final Random RANDOM = new Random();

	// Locks and conditions for blocking on conditions while threading.
	private final Lock lock = new ReentrantLock();
	private final Condition clientsConnected = lock.newCondition();
	private final Condition incidentOver = lock.newCondition();
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
		incident = new LinkedList<Incident>();

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
		
		System.err.println("Game Started");

		// Set up the first level
		// set up the environment for level one. (Campus)
		initLevel(Level.getCampus());
	
		
		
		
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
				incidentOver.await();
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
			incidentOver.await();
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
	 * @param posture
	 */

	public void onPostureChange(Role role, Posture posture) {
		if (playerOne.getRole() == role) {
			playerOne.setState(posture);
		} else {
			playerTwo.setState(posture);
		}
	}

	public void onPlayerConnect(Connection connection) {
		numPlayerConnected++;

		// First connection is first player, and
		// get's a random role
		if(playerOne.getRole() == Role.UNDEFINDED ){
			if(RANDOM.nextBoolean()){
				playerOne.setRole(Role.DATABASE);
			}else{
				playerOne.setRole(Role.FIELDAGENT);
			}
			
			System.err.println("Player One has connected with Role: " + playerOne.getRole().toString());
			network.setRole(connection, playerOne.getRole());
			
		}else{
			if(playerOne.getRole() == Role.DATABASE){
				playerTwo.setRole(Role.FIELDAGENT);
			}else{
				playerTwo.setRole(Role.DATABASE);
			}

			System.err.println("Player Two has connected with Role: " + playerTwo.getRole().toString());
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
	public void onPlayerPause(Role role) {
		// pause any time-counting variables
		network.send(Message.PAUSE);
	}

	/**
	 * Callback when a player resumes their game. TODO: Determine how to keep
	 * track of <b> which</b> player resumed, if necessary
	 * 
	 * @param role
	 */
	public void onPlayerResume(Role role) {
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
		incidentOver.signal();
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
		FieldCallback[] callarr = { null, null, FieldCallback.approachPerson,
				null, null, null, null, FieldCallback.giveDocuments, null,
				null, null, null, null, null };

		List<Dialogue> dialogues = Arrays.asList(Dialogue.returnDialogArray(
				strarr, callarr));

		return dialogues;
	}
	
	
	private void initLevel(Level level){

		levels.add(level);

		System.err.println("Starting Level " + level.getName());
		// start level one.
		network.send(Message.START_LEVEL, level);
		// inform each player of the role they will be playing in level one
		network.send(Message.SET_ROLE, playerOne.getRole(), playerOne.getRole());
		network.send(Message.SET_ROLE, playerTwo.getRole(), playerTwo.getRole());
	}
}
