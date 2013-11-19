package csci331.team.red.network;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.esotericsoftware.minlog.Log;

import csci331.team.red.client.ClientEngine;
import csci331.team.red.server.ServerEngine;
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
	protected final ClientEngine gameClient;
	protected final Client client;
	protected String host = "127.0.0.1";
	protected int timeout = 5000;

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
			setTimeout(60000); // change timeout to 60 secs
		} catch (IOException e) {
			// TODO: should I be catching this, or just pass it on to client?
			System.out.println("Client bombed");
			e.printStackTrace();
			client.stop();
		}

		// ThreadedListener runs the listener methods on a different thread.
		client.addListener(new ThreadedListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof NetMessage) {
					NetMessage netMsg = (NetMessage) object;
					// process message
					switch (netMsg.msg) {
					case CONNECTED:
						// client should not receive this message
						break;
					case DISCONNECTED:
						// gameClient.onServerDisconnect();
						break;
					case START_LEVEL:
						if (netMsg.obj instanceof Level) {
							Level level = (Level) netMsg.obj;
							// gameClient.startLevel(level);
							System.out.println("Starting level...");
						}
						break;
					case START_STAGE:
						if (netMsg.obj instanceof Stage) {
							Stage stage = (Stage) netMsg.obj;
							// gameClient.startStage(stage);
							System.out.println("Starting stage...");
						}
						break;
					case READY:
						// what will this be used for?
						break;
					case PAUSE:
						// gameClient.onPlayerPause();
						break;
					case QUIT:
						// gameClient.onPlayerQuit();
						break;
					case RESUME:
						// gameClient.onPlayerResume();
						break;
					default:
						// TODO: should I throw an exception or just ignore the
						// message?
						break;
					}
				}
			}

			@Override
			public void connected(Connection arg0) {
				super.connected(arg0);
				// gameClient.onConnected;
			}

			@Override
			public void disconnected(Connection arg0) {
				super.disconnected(arg0);
				// gameClient.onDisconnected;
			}
		})); // end of addListener
	} // end of constructor

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
