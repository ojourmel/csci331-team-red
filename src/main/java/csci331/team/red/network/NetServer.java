package csci331.team.red.network;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import csci331.team.red.server.ServerEngine;
import csci331.team.red.shared.Message;

/**
 * Server end for KryoNet network communications
 * 
 * @see https://code.google.com/p/kryonet/
 * @author mariusloots
 */
public class NetServer {
	// protected Connection serverConn;
	protected ServerEngine gameServer;
	protected Server server;

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

		System.out.println("Server up");

		/* Kryo automatically serializes the objects to and from bytes */
		Kryo kryo = server.getKryo();

		/**
		 * For consistency, the classes to be sent over the network are
		 * registered by the same method for both the client and server.
		 */
		Network.register(server);

		/**
		 * Add a listener to handle receiving objects
		 * 
		 * Typically a listener has a series of instanceof checks to decide what
		 * to do with the object received.
		 * 
		 * Note the Listener class has other notification methods that can be
		 * overridden.
		 */
		server.addListener(new Listener() {
			/**
			 * Override received method of Listener to specify game specific
			 * management of received objects
			 */
			public void received(Connection connection, Object object) {
				System.out.println(connection.toString());
				
				if (object instanceof NetMessage) {
					NetMessage netMsg = (NetMessage) object;

					// process message
					switch (netMsg.msg) {
					case CONNECTED:
//						gameServer.onPlayerConnect();
						// receive a connection object from client for server
						break;
					case DISCONNECTED:
						gameServer.onPlayerDisconnect();
						break;
					case START_LEVEL:
						// server should not receive this
						break;
					case START_STAGE:
						// server should not receive this
						break;
					case READY:
						// what is this used for?
						break;
					case PAUSE:
						gameServer.onPlayerPause();
						break;
					case RESUME:
						gameServer.onPlayerResume();
						break;
					case QUIT:
//						gameServer.onPlayerQuit();
						break;
					default:
						break;
					}
				}
			}
		});
	}

	/**
	 * @param msg
	 *            Send an Enumerated {@link Message}
	 */
	public void send(Message msg) {
		send(msg, null);
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
}