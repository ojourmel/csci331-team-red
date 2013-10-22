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
import csci331.team.red.shared.Message;
import csci331.team.red.shared.Person;
import csci331.team.red.shared.Result;
import csci331.team.red.shared.Stage;

/**
 * Main game logic class.
 * 
 * @author ojourmel
 * 
 */
public class ServerEngine extends Thread {

	/**
	 * The maximum number of players supported by this game.
	 */
	public static final int MAX_PLAYERS = 2;

	// Core game assets
	private NetworkEngine network;
	private People people;
	private List<Stage> stages;
	private List<Level> levels;
	private Stage currentStage;

	// The two players in the game
	private Player playerOne;
	private Player playerTwo;

	private int numPlayerConnected = 0;

	// Value to modify the base fraud probability of a Person
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
		levels = new LinkedList<Level>();
		stages = new LinkedList<Stage>();
		people = new People();

		playerOne = new Player();
		playerTwo = new Player();
	}

	/**
	 * Main entry point to game logic. Called via {@link ServerEngine#start()}
	 */
	@Override
	public void run() {

		network = new NetworkEngine(this);

		while (numPlayerConnected < 2) {
			try {
				lock.lock();
				clientsConnected.await();
			} catch (InterruptedException e) {
				// game shutting down due to clientDisconect.
				// onClientDisconnect will handle the details, just quit.
				
				System.err.println("Quiting");

				return;
			} finally {
				lock.unlock();
			}
		}
		// we now have two clients connected.
		levels.add(Level.getWait());
		network.sendMessage(Message.START_LEVEL, Level.getWait());

		// set up the environment for level one. (Campus)
		Level one = Level.getCampus();
		levels.add(one);
		// start level one.
		network.sendMessage(Message.START_LEVEL, one);

		for (int i = 0; i < 3; i++) {
			// set up a stage. Use the stats from the previous stages to affect
			// the
			// next stage.
			Person actor = people.getRandom();
			double fraudFactor = (actor.fraud / fraudDifficulty);
			double errorFactor = 0;

			Stage stage = new Stage(actor, fraudFactor, errorFactor);

			// send the stage to the clients
			network.sendMessage(Message.START_STAGE, stage);

			// since everything that can happen in a stage is purly reactive,
			// (ie.
			// clients initiat calles...), simply wait for the clients to accept
			// or
			// reject this stage, then make a new one!
			try {
				lock.lock();
				stageOver.await();
			} catch (InterruptedException e) {
				return;
			} finally {
				lock.unlock();
			}
		}

		// boss stage time!

		Person boss = people.getLevelBoss(1);
		// A boss should have no detail problems, but alerts have to be
		// generated... TODO: Deal with generating alerts.
		Stage stage = new Stage(boss, 0, 0);
		network.sendMessage(Message.START_STAGE, stage);
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
		//                or...
		// Ohw. Level One was Too Hard. Game Over!
		//                Or...
		// Well, you did mediocre, but that won't cut it. Level 1 AGAIN!
	}

	public Result onDatabaseSearch(String query) {
		// TODO: Handle database queries
		return Result.INVALID;
	}

	public Dialog onDialogRequest(Dialog incoming) {
		// TODO: Handle proper dialog requests.
		return Dialog.GENERIC;
	}

	public void onStateChange(State state) {
		
		// TODO: Allow for state to have an impact on actors.

	}

	public void onPlayerConnect() {
		numPlayerConnected++;
		if (numPlayerConnected == MAX_PLAYERS) {
			lock.lock();
			clientsConnected.signal();
			lock.unlock();
		}
	}

	public void onPlayerDisconnect() {
		network.sendMessage(Message.DISCONNECTED);
		
		// Doing things this way means that all "Condition.await()" blocks will
		// have to be surrounded with try/catch blocks
		this.interrupt();
	}

	public void onPlayerQuit() {
		network.sendMessage(Message.QUIT);
		// TODO: This code *might* have some problems interrupting it'self. See
		// onPlayerDisconnect()
		this.interrupt();
	}

	/**
	 * Callback when a player pauses their game.
	 * TODO: Determine how to keep track of <b> which</b> player paused, if necessary.
	 */
	public void onPlayerPause() {
		// pause any time-counting variables
		network.sendMessage(Message.PAUSE);
	}

	public void onPlayerResume() {
		// resume any time-counting variables
		network.sendMessage(Message.RESUME);
	}

	public void onStageComplete(boolean decition) {

		// update player scores from the outcome of this stage. Update any
		// environment variables, and tell run() to wake up, and do another
		// stage.

		lock.lock();
		stageOver.signal();
		lock.unlock();
	}
}
