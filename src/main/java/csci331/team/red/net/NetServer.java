package csci331.team.red.net;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class NetServer {
	protected Server server;
	protected csci331.team.red.shared.Message msg;

	// protected int _port1 = 54555;
	// protected int _port2 = 54777;
	// HashSet<Character> loggedIn = new HashSet();

	public NetServer() throws IOException {
		Server server = new Server();
		// server = new Server() {
		// protected Connection newConnection () {
		// // By providing our own connection implementation, we can store per
		// // connection state without a connection ID to state look up.
		// return new Connection();
		// }
		// };

		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		Network.register(server);

		// add listener
		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof SomeRequest) {
					SomeRequest request = (SomeRequest) object;
					System.out.println(request.text);

					SomeResponse response = new SomeResponse();
					response.text = "Thanks!";
					connection.sendTCP(response);
				}
				//
				if (object instanceof csci331.team.red.shared.Message) {
					msg = (csci331.team.red.shared.Message) object;
					// process message
					switch (msg) {
					case CONNECTED:

						break;
					case START_LEVEL:

						break;
					case READY:

						break;
					case PAUSE:

						break;
					case QUIT:

						break;
					default:
						break;
					}
				}
			}
		});

		server.start();
		// server.bind(_port1, _port2);
		server.bind(Network.port);
		System.out.println("Server up");
		// register messages
		Kryo kryo = server.getKryo();
		kryo.register(SomeRequest.class);
		kryo.register(SomeResponse.class);
	}

	// // This holds per connection state.
	// static class CharacterConnection extends Connection {
	// public Character character;
	// }

	public static void main(String[] args) throws IOException {
		Log.set(Log.LEVEL_DEBUG);
		new NetServer();
		System.out.println("and running");
	}
}
