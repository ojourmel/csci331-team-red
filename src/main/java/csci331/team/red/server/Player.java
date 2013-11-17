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

	/**
	 * @param role
	 *            {@link Role} to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @param state
	 *            {@link State} to set
	 */
	public void setState(State state) {
		this.state = state;
	}
}
