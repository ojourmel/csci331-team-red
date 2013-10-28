package csci331.team.red.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import csci331.team.red.shared.Background;
import csci331.team.red.shared.Dialog;
import csci331.team.red.shared.Level;
import csci331.team.red.shared.Message;
import csci331.team.red.shared.Person;
import csci331.team.red.shared.Result;
import csci331.team.red.shared.Role;
import csci331.team.red.shared.SoundTrack;
import csci331.team.red.shared.Stage;
import csci331.team.red.shared.State;

/**
 * This class is a convenient place to keep things common to both the client and
 * server
 * 
 * @author marius
 * 
 */
public class Network {
	static public final int tcpPort = 54555;
	static public final int udpPort = 54777;

	/**
	 * Registers objects that will be sent over the network
	 * 
	 * @param endPoint
	 */
	static public void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(NetMessage.class);
		kryo.register(Background.class);
		kryo.register(Dialog.class);
		kryo.register(Level.class);
		kryo.register(Message.class);
		kryo.register(Person.class);
		kryo.register(Result.class);
		kryo.register(Role.class);
		kryo.register(SoundTrack.class);
		kryo.register(Stage.class);
		kryo.register(State.class);
	}
}
