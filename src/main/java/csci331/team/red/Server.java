package csci331.team.red;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main game logic class.
 * 
 * @author jourmeob
 */
public class Server {

	/**
	 * Stub class for compiler TODO: Implement full class
	 */
	private class NetworkEngine {
	}

	/**
	 * Stub class for compiler TODO: Implement full class
	 */
	private class Person {
		// Information about one person
	}

	/**
	 * A wrapper class for all of the currently created, random people to be use
	 * for this play through.
	 * 
	 * @author jourmeob
	 * 
	 */
	private class People {
		private Map<Integer, Person> people;

		public People() {
			// generate some random people.
			// Hard coded for now.
			people = new HashMap<Integer, Person>();
			people.put(2314, new Person());
		}

		public Person getRandom() {
			return null;
		}

		public Person getLevelBoss(int i) {
			switch (i) {
			case 1:

				break;
			case 2:

				break;

			case 3:

				break;

			default:
				// Tried to get a boss from a level that does not exist!!
				break;

			}

			return null;
		}
	}

	/* ******************* REAL(ISH) CODE********************************* */

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
	 * Create a new {@link Server} object.
	 * 
	 * @param clients
	 *            Initial list of clients to connect to.
	 * @author jourmeob
	 */
	public Server(List<String> clients) {
		network = new NetworkEngine();
		people = new People();
		setClients(clients);
	}

	/**
	 * @param clients
	 *            A list of the clients to contact from now on, when sending a
	 *            message
	 * @author jourmeob
	 */
	public void setClients(List<String> clients) {
		// init the network engine, with the clients given
		for (String client : clients) {
			// addClient(client);
		}
	}

	/**
	 * The main game logic method of this class
	 * 
	 * @author jourmeob
	 */
	public void runGame() {
		// get the network to start listening for connections to me.

		/** network.listen("localhost", Server.INCOMING_PORT); **/

		// wait for the two clients to connect to this server.
		// Use callback from network?? How does that work? ?? semaphores??

		// Received connected message from clients
		/** onClientConnect(); **/

		// Kick the clients into a waiting screen while the game is set up.

		/** network.sendMessage(Message.START_WAIT_LEVEL); **/

		// Decide which role each client should play

		// Start up level 1. Tutorial.
		/** network.sendMessage(Message.START_FIRST_LEVEL); **/

		// Set up the first stage. Pick a person;
		Person person = people.getRandom();
		// Pick all the environment variables, and factors that will affect the
		// stage.

		// sync the clients.

		// wait for the clients to respond... This will be done with callback
		// handlers. eg. onDatabaseRequest... etc...

		// Get response from clients as to accept, or reject the first dude.
		// (parking ticket)

		// Update scores for the players. Update any Level wide environment
		// variables that have been changed because of the last stage.

		// Set up the second stage. Pick a person;
		person = people.getRandom();
		// Pick all the environment variables, and factors that will affect the
		// stage.

		// sync the clients.

		// wait for the clients to respond... This will be done with callback
		// handlers. eg. onDatabaseRequest... etc...

		// Get response from clients as to accept, or reject the first dude.
		// (parking ticket)

		// don't forget to fail the users if they screwed up twice in a row.

		
		
		
		// Boss MODE !!!! This is the (scripted) ending to Level 1

		person = people.getLevelBoss(1);

		// Grab the predefined environment variables, but tweak them for the
		// current run through

		// sync the clients.

		// handle client response.

		// if the players passed, initiate Level 2

		// else, fail the players. Force them to restart level 1
	}
}