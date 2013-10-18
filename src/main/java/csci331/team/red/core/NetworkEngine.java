package csci331.team.red.core;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

/**
 * 
 * @author lootsmf
 */
public class NetworkEngine implements Net {
	   protected int _port = 7878;		// default port
	   protected boolean _connected;
	   protected Protocol _protocol = Protocol.TCP;
	   
	   public NetworkEngine(int port) {
	     _connected = false;
	     Server server = new Server();
	     server.start();
	     server.bind(54555, 54777);
	   }
	   
			@Override
			public void dispose() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Protocol getProtocol() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Socket accept(SocketHints hints) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	     this.connectClient(ip);
	     myID = -1;
	     this.c = c; //To call the methods of the the upper level class
	   }
	   
	       //Websocket implementation
	       URI url = null; //URI (url address of the server)
	       try {
	        url = new URI("ws://"+ ip +":"+ port); //We create the URI of the server. Use a port upper than 1024 on Android and Linux!
	       } catch (URISyntaxException e) {
	         e.printStackTrace();
	       } 
	 
	         @Override
	         public void onOpen( ServerHandshake handshake ) {
	           connected = true;
	           requestID();
	         }
	   
	   public void close()
	   {
	     wsc.close();
	     connected = false;
	   }
}
