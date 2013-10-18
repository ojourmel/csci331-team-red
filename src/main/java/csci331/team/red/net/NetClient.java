
package csci331.team.red.net;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.esotericsoftware.minlog.Log;

import csci331.team.red.net.Network.RegistrationRequired;

public class NetClient {
	UI ui;
	Client client;
	String name;

	public NetClient() {
		Client client = new Client();
		client.start();

		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		Network.register(client);
/***********/

		// ThreadedListener runs the listener methods on a different thread.
		client.addListener(new ThreadedListener(new Listener() {
			public void connected (Connection connection) {
			}

			public void received (Connection connection, Object object) {
				if (object instanceof RegistrationRequired) {
//					Register register = new Register();
//					register.name = name;
//					register.otherStuff = ui.inputOtherStuff();
					client.sendTCP(register);
				}

			}

			public void disconnected (Connection connection) {
				System.exit(0);
			}
		}));

		ui = new UI();

		String host = ui.inputHost();
		
/***********/
		try {
//			client.connect(5000, host, Network.port);
			client.connect(5000, "10.5.16.233", 54555, 54777);
			// Server communication after connection can go here, or in Listener#connected().
			System.out.println("Client good");
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
/***********/		
		client.addListener(new Listener() {
			   public void received (Connection connection, Object object) {
			      if (object instanceof SomeResponse) {
			         SomeResponse response = (SomeResponse)object;
			         System.out.println(response.text);
			      }
			   }
		});

		name = ui.inputName();
		Login login = new Login();
		login.name = name;
		client.sendTCP(login);

		while (true) {
			int ch;
			try {
				ch = System.in.read();
			} catch (IOException ex) {
				ex.printStackTrace();
				break;
			}

			MoveCharacter msg = new MoveCharacter();
			switch (ch) {
			case 'w':
				msg.y = -1;
				break;
			case 's':
				msg.y = 1;
				break;
			case 'a':
				msg.x = -1;
				break;
			case 'd':
				msg.x = 1;
				break;
			default:
				msg = null;
			}
			if (msg != null) client.sendTCP(msg);
		}
	}

	public static void main(String[] args) throws IOException {
		Log.set(Log.LEVEL_DEBUG);
		new NetClient();
		System.out.println("Client Connected");
	}
}
