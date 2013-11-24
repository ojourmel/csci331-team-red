package csci331.team.red.shared;

import java.util.List;

import javax.xml.ws.Response;

/**
 * Enumerated values for message to be passed from client to server.
 */
public enum Message {

	/**
	 * Send an alert to client, expects {@link Alert} object to be sent with
	 * this message.
	 */
	ALERT,

	/**
	 * Connected to client/server, and waiting for game start
	 */
	CONNECTED,

	/**
	 * Sends string to server to query the database, expects a {@link String}
	 * object to be sent with this message.
	 */
	DBQUERY,

	/**
	 * Sends the result of a {@link Message.DBQUERY} to client, expects a
	 * {@link Result} object to be sent with this message.
	 */
	DBRESULT,

	/**
	 * Sends dialogue to client/server, expects a {@link List} of
	 * {@link Dialogue} objects to be sent with this message.
	 */
	DIALOGUE,

	/**
	 * Disconnected to client/server, and game is reseting
	 */
	DISCONNECTED,

	/**
	 * Sends client Role to client, expects {@link Role} object to be sent with
	 * this message.
	 */
	SET_ROLE,

	/**
	 * Request the start of a level, expects {@link Level} object to be sent
	 * with this message.
	 */
	START_LEVEL,

	/**
	 * Starts a incident, expects {@link Incident} object to be sent with this
	 * message.
	 */
	START_INCIDENT,

	/**
	 * The user has paused the game
	 */
	PAUSE,

	/**
	 * The user has resumed the game
	 */
	RESUME,

	/**
	 * The field agent has changed their posture. Comes with a posture enum.
	 */
	ONPOSTURECHANGE,

	/**
	 * The field agent has made a decision about the stage. Comes with a
	 * decision enum.
	 */
	ONDECISIONEVENT,

	/**
	 * The user has quit the game
	 */
	QUIT
}