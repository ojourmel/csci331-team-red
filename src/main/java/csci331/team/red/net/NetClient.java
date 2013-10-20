
package csci331.team.red.net;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.esotericsoftware.minlog.Log;

public class NetClient {
//	UI ui;
	Client client;
	csci331.team.red.shared.Message msg;
	protected String _host = "127.0.0.1";
	protected int _port1 = 54555;
	protected int _port2 = 54777;
	String name;

	public NetClient(String host) {
		_host = host;
		Client client = new Client();
		client.start();

		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		Network.register(client);

		// ThreadedListener runs the listener methods on a different thread.
		client.addListener(new ThreadedListener(new Listener() {
			public void connected (Connection connection) {
			}

			public void received (Connection connection, Object object) {
//				if (object instanceof RegistrationRequired) {
//					Register register = new Register();
//					register.name = name;
//					register.otherStuff = ui.inputOtherStuff();
//					client.sendTCP(register);
//				}
				if (object instanceof SomeRequest) {
					SomeRequest request = (SomeRequest)object;
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
						case START_WAIT_LEVEL:
							
							break;
						case START_LEVEL_ONE:
							
							break;
						case START_LEVEL_TWO:
							
							break;
						case START_LEVEL_THREE:
							
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

			public void disconnected (Connection connection) {
				System.exit(0);
			}
		}));

//		ui = new UI();

//		String host = ui.inputHost();
		
/***********/
		try {
			client.connect(5000, _host, Network.port);
			// Server communication after connection can go here, or in Listener#connected().
			System.out.println("Client good");
		} catch (IOException e) {
			System.out.println("Client sucked");
			e.printStackTrace();
		}

		// register messages
		Kryo kryo = client.getKryo();
		kryo.register(SomeRequest.class);
		kryo.register(SomeResponse.class);
		
		// send request
		SomeRequest request = new SomeRequest();
		request.text = "Here is the request!";
		client.sendTCP(request);		
	}

	public static void main(String[] args) throws IOException {
		Log.set(Log.LEVEL_DEBUG);
		new NetClient("192.168.0.17");
		System.out.println("Client Connected");
	}
}
