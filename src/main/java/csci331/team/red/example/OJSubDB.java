package csci331.team.red.example;

import csci331.team.red.server.Player;

/**
 * CSCI331T OJ Subclass
 * 
 * This is an example of a sub class. Within my {@author ojourmel} package,
 * server, there was few opportunities to implement Super/Sub classes. The exist
 * the possibility for Super/Sub classes for {@link Player}. <br>
 * This class could be extended to the two different player roles that can be
 * played.<br>
 * <br>
 * 
 * A short mock-up of that is demonstrated by {@link OJSuper} and {@link OJSub}
 * 
 * @author ojourmel
 */
public class OJSubDB extends OJSuper {

	private int score;
	private int databaseModifier = 2;

	@Override
	protected void addScore() {
		score++;
	}

	@Override
	protected void removeScore() {
		score--;
	}

	@Override
	protected int getScore() {
		return score * databaseModifier;
	}

}
