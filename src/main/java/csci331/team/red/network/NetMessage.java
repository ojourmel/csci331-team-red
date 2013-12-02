package csci331.team.red.network;

import csci331.team.red.shared.Message;

/**
 * Wrapper class for sending data over network
 * 
 * @author marius
 * 
 */
public class NetMessage {
	Message msg;
	Object obj;

	/**
	 * Used for Kryonet's serialization
	 */
	@SuppressWarnings("unused")
	private NetMessage() {
	}

	/**
	 * Constructor for NetMessage
	 * 
	 * @param msg
	 * @param obj
	 */
	public NetMessage(Message msg, Object obj) {
		this.msg = msg;
		this.obj = obj;
	}
}
