package csci331.team.red.server;

import csci331.team.red.shared.PlayerState;
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
	private PlayerState state;

	/** 
	 * @param role
	 *            {@link Role} to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/** 
	 * @return role
	 *            {@link Role}
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param state
	 *            {@link PlayerState} to set
	 */
	public void setState(PlayerState state) {
		this.state = state;
	}

}
