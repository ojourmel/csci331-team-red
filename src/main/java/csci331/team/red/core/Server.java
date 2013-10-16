package csci331.team.red.core;

import csci331.team.red.server.People;

/**
 * Main game logic class.
 * 
 * @author jourmeob
 */
public class Server {

	/**
	 * Port to use when connecting to a {@link Server}
	 */
	public static final int INCOMING_PORT = 8585;

	private NetworkEngine network;
	private People people;

	/**
	 * Create a new {@link Server} object
	 * 
	 * @author jourmeob
	 */
	public Server() {
		network = new NetworkEngine();
		people = new People();
	}

	/**
	 * The main game logic method of this class
	 * 
	 * @author jourmeob
	 */
	public void runGame() {

	}
}