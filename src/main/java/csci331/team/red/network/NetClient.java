package csci331.team.red.network;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;

import csci331.team.red.client.ClientEngine;
import csci331.team.red.shared.Level;
import csci331.team.red.shared.Message;
import csci331.team.red.shared.Stage;

/**
 * Client end for KryoNet network communications
 * 
 * @see https://code.google.com/p/kryonet/
 * @author marius
 */
public class NetClient {
	private final ClientEngine gameClient;
	private final Client client;
	private String host = "127.0.0.1";
	private static final int timeout = 5000;

	/**
	 * Constructor for NetClient
	 * 
	 * @param gameClient
	 *            Reference to a {@link ClientEngine}
	 * @param host
	 *            Name or ip address of the Server to connect to
	 */
	public NetClient(final ClientEngine gameClient, String host) {
		this.host = host;
		this.gameClient = gameClient;
		// connects to a server
		client = new Client();
		client.start();

		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		Network.register(client);

		try {
			client.connect(timeout, host, Network.tcpPort);
			this.send(Message.CONNECTED);
			/* change timeout to 60 secs so that client will not accidently disconnect */
			setTimeout(60000); 
		} catch (IOException e) {
			// TODO: should I be catching this, or just pass it on to client?
			System.out.println("Client bombed");
			e.printStackTrace();
			client.stop();
		}

		// ThreadedListener runs the listener methods on a different thread.
		client.addListener(new ThreadedListener(new Listener() {
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
					case CONNECTED:
						// client should not receive this message
						break;
					case DISCONNECTED:
						// TODO: Implement call and remove print
						// gameClient.onServerDisconnect();
						System.out.println("Disconnected...");
						break;
					case START_LEVEL:
						if (netMsg.obj instanceof Level) {
							Level level = (Level) netMsg.obj;
							// TODO: Implement call and remove print
							// gameClient.startLevel(level);
							System.out.println("Starting level...");
						}
						break;
					case START_STAGE:
						if (netMsg.obj instanceof Stage) {
							Stage stage = (Stage) netMsg.obj;
							// TODO: Implement call and remove print
							// gameClient.startStage(stage);
							System.out.println("Starting stage...");
						}
						break;
					case READY:
						// TODO: what will this be used for?
						break;
					case PAUSE:
						// TODO: Implement call and remove print
						// gameClient.onPlayerPause();
						break;
					case QUIT:
						// TODO: Implement call and remove print
						// gameClient.onPlayerQuit();
						break;
					case RESUME:
						// TODO: Implement call and remove print
						// gameClient.onPlayerResume();
						break;
					default:
						break;
					}
				}
			}
		})); // end of addListener
	} // end of constructor


	/**
	 * CSCI331 ML STATICBINDING
	 * Explain how the system will decide which method to
	 * invoke/variable to access.
	 */
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
		client.sendTCP(netMsg);
	}

	/**
	 * Sets {@line Connection} timeout to a value between 0 and 60 seconds
	 * 
	 * @param timeout
	 */
	public void setTimeout(int timeout) {
		if ((timeout > 0) && (timeout < 60001))
			client.setTimeout(timeout);
	}
}
