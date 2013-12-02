package csci331.team.red.network;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import csci331.team.red.client.DatabaseBoundingBoxes;
import csci331.team.red.shared.Alert;
import csci331.team.red.shared.Background;
import csci331.team.red.shared.Character;
import csci331.team.red.shared.Decision;
import csci331.team.red.shared.Dialogue;
import csci331.team.red.shared.Document;
import csci331.team.red.shared.Face;
import csci331.team.red.shared.Gender;
import csci331.team.red.shared.Incident;
import csci331.team.red.shared.Level;
import csci331.team.red.shared.Message;
import csci331.team.red.shared.PersonPicture;
import csci331.team.red.shared.Posture;
import csci331.team.red.shared.Query;
import csci331.team.red.shared.Result;
import csci331.team.red.shared.Role;
import csci331.team.red.shared.SoundTrack;
import csci331.team.red.shared.callbacks.DBCallback;
import csci331.team.red.shared.callbacks.FieldCallback;

/**
 * CSCI331 ML SUPERCLASS
 * 
 * This class is a convenient place to keep things common to both the client and
 * server
 * 
 * @author marius
 * 
 */
public class Network {
	public static final int tcpPort = 54555;
	public static final int udpPort = 54777;

	public static final int BUFFER_SIZE = 16384;

	public Network() {
		super();
	}

	/**
	 * Registers objects that will be sent over the network
	 * 
	 * @param endPoint
	 */
	static public void register(EndPoint endPoint) {

		/*
		 * Instantiate the serializer
		 */
		Kryo kryo = endPoint.getKryo();
		/*
		 * Register the message we'll be passing between the client(s) and
		 * server
		 */
		kryo.register(NetMessage.class);
		/*
		 * Register all classes in csci331.team.red.shared
		 */
		kryo.register(Alert.class);
		kryo.register(Background.class);
		kryo.register(Character.class);
		kryo.register(Decision.class);
		kryo.register(Dialogue.class);
		kryo.register(Document.class);
		kryo.register(Document.Type.class);
		kryo.register(Face.class);
		kryo.register(Gender.class);
		kryo.register(Incident.class);
		kryo.register(Level.class);
		kryo.register(Message.class);
		kryo.register(PersonPicture.class);
		kryo.register(Posture.class);
		kryo.register(Result.class);
		kryo.register(Role.class);
		kryo.register(DatabaseBoundingBoxes.class);
		kryo.register(Query.class);
		kryo.register(SoundTrack.class);

		kryo.register(DBCallback.class);
		kryo.register(FieldCallback.class);

		kryo.register(List.class);
		kryo.register(LinkedList.class);

		kryo.register(HashMap.class);

		kryo.register(String.class);
		kryo.register(String[].class);
	}
}
