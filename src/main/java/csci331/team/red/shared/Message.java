package csci331.team.red.shared;

/**
 * Enumerated values for message to be passed from client to server.
 */
public enum Message {

	/**
	 * Connected to client/server, and waiting for game start
	 */
	CONNECTED,

	/**
	 * Disconnected to client/server, and game is reseting
	 */
	DISCONNECTED,

	/**
	 * Request the start of a level
	 */
	START_LEVEL,

	/**
	 * Starts a stage
	 */
	START_STAGE,

	/**
	 * Ready to start game
	 */
	READY,

	/**
	 * The user has paused the game
	 */
	PAUSE,

	/**
	 * The user has resumed the game
	 */
	RESUME,
	
	/**
	 * The field agent has changed their posture.  Comes with a posture enum.
	 */
	ONPOSTURECHANGE,
	
	/**
	 * The field agent has made a decision about the stage.  Comes with a decision enum.
	 */
	ONDECISIONEVENT,

	/**
	 * The user has quit the game
	 */
	QUIT
}