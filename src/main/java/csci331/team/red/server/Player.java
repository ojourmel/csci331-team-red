package csci331.team.red.server;

import csci331.team.red.shared.Role;
import csci331.team.red.shared.State;

/**
 * A Server-side representation of the two players. Who they are, what their
 * attributes are, score, etc.<br>
 * FIXME: <b>Stub</b>
 * 
 * @author ojourmel
 */
public class Player {
	private Role role;
	private State state;

	public void setRole(Role role) {
		this.role = role;
	}

	public void setState(State state) {
		this.state = state;
	}
	

}
