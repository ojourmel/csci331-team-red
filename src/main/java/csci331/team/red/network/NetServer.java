package csci331.team.red.network;

import java.io.IOException;
import java.util.HashMap;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import csci331.team.red.server.ServerEngine;
import csci331.team.red.shared.Decision;
import csci331.team.red.shared.Message;
import csci331.team.red.shared.Posture;
import csci331.team.red.shared.Query;
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
	public NetServer(final ServerEngine incomingGameServer) throws IOException {
		this.gameServer = incomingGameServer;
		server = new Server(Network.BUFFER_SIZE, Network.BUFFER_SIZE);
		/* start a thread to handle incoming connections */
		server.start();

		try {
			server.bind(Network.tcpPort);
		} catch (IOException e) {
			throw new IOException("Unable to bind to port");
		}
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
					case CONNECT:
						if (roles.containsKey(connection.getID())) {
							// You are already connected
							sendClient(roles.get(connection.getID()),
									Message.CONNECT);
						} else {
							gameServer.onPlayerConnect(connection);
						}
						break;
					case DBQUERY:
						if (netMsg.obj instanceof Query) {
							gameServer.onDatabaseSearch((Query) netMsg.obj);
						}
						break;
					case DISCONNECT:
						// Only notify Server when this connection is in roles
						if (roles.containsKey(connection.getID())) {
							// Notify Server of disconnect
							gameServer.onPlayerDisconnect(roles.get(connection
									.getID()));
							// Remove connection from list of roles
							roles.remove(connection.getID());
						}
						// ignore disconnects from unregistered connections
						break;
					case ONPOSTURECHANGE:
						if (netMsg.obj instanceof Posture) {
							gameServer.onPostureChange(
									roles.get(connection.getID()),
									(Posture) netMsg.obj);
						}
						break;
					case ONDECISIONEVENT:
						if (netMsg.obj instanceof Decision) {
							gameServer
									.onIncidentComplete((Decision) netMsg.obj);
						}
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
					default:
						// invalid messages are simply ignored
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
				// Only notify Server when this connection is in roles
				if (roles.containsKey(connection.getID())) {
					gameServer.onPlayerDisconnect(roles.get(connection.getID()));
					// Remove connection from list of roles
					roles.remove(connection.getID());
				}
				// ignore disconnects from unregistered connections
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
	 * Stops the Server
	 */
	public void killServer() {
		for (Integer connID : roles.keySet()) {
			// tell client to disconnect
			sendClient(roles.get(connID), Message.DISCONNECT);
		}
		roles.clear();
		server.stop();
	}

	/**
	 * CSCI331 ML STATICBINDING
	 * 
	 * Explain how the system will decide which method to invoke/variable to
	 * access.
	 */
	/**
	 * @param msg
	 *            Send an Enumerated {@link Message} to all {@link Client}s
	 */
	public void sendAll(Message msg) {
		sendAll(msg, null);
	}

	/**
	 * Send an Enumerated {@link Message} and a registered (
	 * {@link Kryo#register(Class)}) Object to all {@link Client}s
	 * 
	 * @param msg
	 * @param obj
	 */
	public void sendAll(Message msg, Object obj) {
		NetMessage netMsg = new NetMessage(msg, obj);
		server.sendToAllTCP(netMsg);
	}

	/**
	 * Send an Enumerated {@link Message} to a specific {@link Connection}
	 * 
	 * @param conn
	 * @param msg
	 */
	public void sendClient(Connection conn, Message msg) {
		server.sendToTCP(conn.getID(), msg);
	}

	/**
	 * Send an Enumerated {@link Message} to the client with a specific
	 * {@link Role}
	 * 
	 * @param role
	 * @param msg
	 */
	public void sendClient(Role role, Message msg) {
		sendClient(role, msg, null);
	}

	/**
	 * Send an Enumerated {@link Message} and a registered (
	 * {@link Kryo#register(Class)}) Object to the client with a specific
	 * {@link Role}
	 * 
	 * @param role
	 * @param msg
	 * @param obj
	 */
	public void sendClient(Role role, Message msg, Object obj) {
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