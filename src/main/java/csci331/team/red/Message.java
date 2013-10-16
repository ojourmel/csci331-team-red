package csci331.team.red;

/**
 * Enumerated values for message to be passed from client to server.
 */
public enum Message {

	/**
	 * Connected to client/server, and waiting for game start
	 */
	CONNECTED,

	/**
	 * Requests that clients start a level
	 */
	START_WAIT_LEVEL,
	
	/**
	 * Requests that clients start level 1
	 */
	START_LEVEL_ONE,
	
	/**
	 * Requests that clients start level 2
	 */
	START_LEVEL_TWO,
	
	
	/**
	 * Requests that clients start level 3
	 */
	START_LEVEL_THREE,

	/**
	 * Ready to start game
	 */
	 READY,
	
	 /**
	  * The user has paused the game
	  */
	 PAUSE,
	
	 /**
	  * The user has quit the game
	  */
	 QUIT
}