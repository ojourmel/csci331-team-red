package csci331.team.red.core;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import csci331.team.red.server.People;
import csci331.team.red.server.Player;
import csci331.team.red.shared.Dialog;
import csci331.team.red.shared.Level;
import csci331.team.red.shared.Result;
import csci331.team.red.shared.Role;
import csci331.team.red.shared.Stage;

/**
 * Main game logic class.
 * 
 * @author ojourmel
 * 
 */
public class ServerEngine extends Thread {

	// Core game assets
	private NetworkEngine network;
	private People people;
	private List<Stage> stages;
	private List<Level> levels;
	private Stage currentStage;

	// The two players in the game
	private Player playerOne;
	private Player playerTwo;

	private int numClientConnected = 0;

	// Locks and conditions for blocking on conditions while threading.
	private final Lock lock = new ReentrantLock();
	private final Condition clientsConnected = lock.newCondition();
	private final Condition activity = lock.newCondition();

	/**
	 * Create a new {@link ServerEngine} object. Use
	 * {@link ServerEngine#start()} to start the game engine in a new thread.
	 * 
	 * @author ojourmel
	 */
	public ServerEngine() {
		network = new NetworkEngine(this);
		levels = new LinkedList<Level>();
		stages = new LinkedList<Stage>();
		people = new People();

		playerOne = new Player(Role.DATABASE);
		playerTwo = new Player(Role.INTERACTIVE);
	}

	/**
	 * Main entry point to game logic. Called via {@link ServerEngine#start()}
	 */
	@Override
	public void run() {

	}

	public Result onDatabaseSearch(String query) {
		return Result.INVALID;
	}

	public Dialog onDialogRequest(Dialog incoming) {
		return Dialog.GENERIC;
	}

	public void onStateChange(State state) {

	}

	public void onClientConnect() {

	}

	public void onClientDisconnect() {

	}

	public void onClientQuit() {

	}
	
	public void onClientPause(){
		
	}

	public void onStageComplete(boolean decition) {

	}
}
