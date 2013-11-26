package csci331.team.red.server;

import csci331.team.red.shared.Posture;
import csci331.team.red.shared.Role;

/**
 * A Server-side representation of the two players. Who they are, what their
 * attributes are, score, etc.<br>
 * 
 * @author ojourmel
 */
public class Player {
	private Role role = Role.UNDEFINDED;
	private Posture posture;

	protected int win = 0;
	protected int fail = 0;
	protected int epicWin = 0;
	protected int superFail = 0;

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
	 * @return posture {@link Posture}
	 */
	public Posture getPosture() {
		return posture;
	}
}
