package csci331.team.red.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import csci331.team.red.server.ServerEngine;
import csci331.team.red.shared.Alert;
import csci331.team.red.shared.Dialogue;
import csci331.team.red.shared.Message;
import csci331.team.red.shared.Role;

/**
 * CSCI331 ML INTERFACE
 */
/**
 * Server end for KryoNet network communications
 * 
 * @see https://code.google.com/p/kryonet/
 * @author marius
 */
public class NetServer {
	/**
	 * for singleton public static Singleton getInstance () { if (uniqueInstance
	 * == null) { uniqueInstance = new Singleton(); } return uniqueInstance; }
	 */
	// private static NetServer uniqueInstance; // only for singleton
	private ServerEngine gameServer;
	private Server server;
	private HashMap<Integer, Role> roles;

	/**
	 * Constructor for NetServer
	 * 
	 * @param gameServer
	 *            Reference to a {@link ServerEngine}
	 * @throws IOException
	 */
	public NetServer(final ServerEngine gameServer) throws IOException {
		this.gameServer = gameServer;
		server = new Server();
		/* start a thread to handle incoming connections */
		server.start();
		server.bind(Network.tcpPort);

		/* Kryo automatically serializes the objects to and from bytes */
		Kryo kryo = server.getKryo();
		/* Create HashMap to map connections to roles */
		roles = new HashMap<Integer, Role>();

		/**
		 * For consistency, the classes to be sent over the network are
		 * registered by the same method for both the client and server.
		 */
		Network.register(server);

		/**
		 * Add a Listener to handle receiving objects
		 * 
		 * Typically a Listener has a series of instanceof checks to decide what
		 * to do with the object received.
		 * 
		 * Note the Listener class has other notification methods that can be
		 * overridden.
		 */
		server.addListener(new Listener() {
			/**
			 * CSCI331 ML OVERRIDING
			 */
			/**
			 * Override received method of Listener to specify game specific
			 * management of received objects
			 */
			public void received(Connection connection, Object object) {

				if (object instanceof NetMessage) {
					NetMessage netMsg = (NetMessage) object;

					// process message
					switch (netMsg.msg) {
					case ALERT:
						// server should not receive this
						break;
					case CONNECTED:
						if (roles.containsKey(connection.getID())) {
							// You are already connected
							send(Message.CONNECTED,
									roles.get(connection.getID()));
						}
						gameServer.onPlayerConnect(connection);
						break;
					case DIALOGUE:
						// server should not receive this
						break;
					case DISCONNECTED:
						gameServer.onPlayerDisconnect(roles.get(connection
								.getID()));
						break;
					case PAUSE:
						gameServer.onPlayerPause(roles.get(connection.getID()));
						break;
					case QUIT:
						gameServer.onPlayerQuit(roles.get(connection.getID()));
						break;
					case RESUME:
						gameServer.onPlayerResume(roles.get(connection.getID()));
						break;
					case SET_ROLE:
						// server should not receive this
						break;
					case START_LEVEL:
						// server should not receive this
						break;
					case START_INCIDENT:
						// server should not receive this
						break;
					default:
						break;
					}
				}
			}

			/**
			 * Called when the remote end is no longer connected. Used to trap
			 * accidental disconnects, cause client won't be able to tell us
			 * that it disconnected
			 */
			public void disconnected(Connection connection) {
				gameServer.onPlayerDisconnect(roles.get(connection.getID()));
			}
		}); // end of addListener
	} // end of constructor

	/**
	 * CSCI331 ML ENCAPSULATION
	 * 
	 * Public access means that anyone outside of this class can modify the
	 * value, outside of the class's control By having it private we can not
	 * only control access, but also the values we allow.
	 * 
	 * In this case I need to fetch the connectionID from the connection before
	 * I add that and the role into a HashMap.
	 */
	/**
	 * Set an Enumerated {@link Role} for {@link Connection}
	 * 
	 * @param connection
	 * @param role
	 */
	public void setRole(Connection connection, Role role) {
		roles.put(connection.getID(), role);
	}

	/**
	 * CSCI331 ML STATICBINDING
	 * 
	 * Explain how the system will decide which method to invoke/variable to
	 * access.
	 */
	/**
	 * @param msg
	 *            Send an Enumerated {@link Message}
	 */
	public void send(Message msg) {
		send(msg, null);
	}

	/**
	 * Send an Enumerated {@link Message} to the client with a specific
	 * {@link Role}
	 * 
	 * @param msg
	 * @param role
	 */
	public void send(Message msg, Role role) {
		send(msg, null, role);
	}

	/**
	 * Send an Enumerated {@link Message} and a registered (
	 * {@link Kryo#register(Class)}) Object
	 * 
	 * @param msg
	 * @param obj
	 */
	public void send(Message msg, Object obj) {
		NetMessage netMsg = new NetMessage(msg, obj);
		server.sendToAllTCP(netMsg);
	}

	/**
	 * Send an Enumerated {@link Message} and a registered (
	 * {@link Kryo#register(Class)}) Object to the client with a specific
	 * {@link Role}
	 * 
	 * @param msg
	 * @param obj
	 * @param role
	 */
	public void send(Message msg, Object obj, Role role) {
		NetMessage netMsg = new NetMessage(msg, obj);
		if (roles.containsValue(role)) {
			for (Integer key : roles.keySet()) {
				if (roles.get(key) == role) {
					server.sendToTCP(key, netMsg);
					break;
				}
			}
		}
	}
}