package csci331.team.red.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import csci331.team.red.shared.Alert;
import csci331.team.red.shared.Background;
import csci331.team.red.shared.Dialogue;
import csci331.team.red.shared.Document;
import csci331.team.red.shared.Face;
import csci331.team.red.shared.Gender;
import csci331.team.red.shared.Level;
import csci331.team.red.shared.Message;
import csci331.team.red.shared.Character;
import csci331.team.red.shared.Result;
import csci331.team.red.shared.Role;
import csci331.team.red.shared.SoundTrack;
import csci331.team.red.shared.Incident;

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
		kryo.register(Alert.class);
		kryo.register(Background.class);
		kryo.register(Character.class);
		kryo.register(Dialogue.class);
		kryo.register(Document.class);
		kryo.register(Face.class);
		kryo.register(Gender.class);
		kryo.register(Incident.class);
		kryo.register(Level.class);
		kryo.register(Message.class);
		kryo.register(Result.class);
		kryo.register(Role.class);
		kryo.register(SoundTrack.class);
	}
}
