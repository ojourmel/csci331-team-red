package csci331.team.red.core;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import csci331.team.red.server.People;
import csci331.team.red.shared.Person;

/**
 * Main game logic class.
 * 
 * @author jourmeob
 */
public class ServerEngine extends Thread {

	/**
	 * Lock to be used to wake a ServerEngine
	 */
	public final Lock lock = new ReentrantLock();

	/**
	 * Condition to use when waking a ServerEngine
	 */
	public final Condition WAKE = lock.newCondition();

	/**
	 * Port to use when connecting to a {@link ServerEngine}
	 */
	public static final int INCOMING_PORT = 8585;

	private NetworkEngine network;
	private People people;

	/**
	 * Create a new {@link ServerEngine} object
	 * 
	 * @author jourmeob
	 */
	public ServerEngine() {
		network = new NetworkEngine();
		people = new People();
	}

	@Override
	public void run() {

		/** network.listen(); **/
		// lock.lock();
		// try {
		// WAKE.await();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// lock.unlock();

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