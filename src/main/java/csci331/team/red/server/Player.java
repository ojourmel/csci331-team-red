package csci331.team.red.server;

import csci331.team.red.shared.Posture;
import csci331.team.red.shared.Role;

/**
 * A Server-side representation of the two players. Who they are, what their
 * attributes are, score, etc.<br>
 * FIXME: <b>Stub</b>
 * 
 * @author ojourmel
 */
public class Player {
	private Role role;
	private Posture posture;

	/**
	 * @param role
	 *            {@link Role} to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return role {@link Role}
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param state
	 *            {@link Posture} to set
	 */
	public void setPosture(Posture posture) {
		this.posture = posture;
	}
	
	/**
	 * @return posture
	 *            {@link Posture} 
	 */
	public Posture getPosture() {
		return posture;
	}

}
