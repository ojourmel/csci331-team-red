package csci331.team.red.example;

/**
 * CSCI331T OJ Superclass
 * 
 * 
 * This is an example of a super class. Within my {@author ojourmel} package,
 * server, there was few opportunities to implemented Super/Sub classes. The
 * exist the possibility for Super/Sub classes for {@link Player}. <br>
 * This class could be extended to the two different player roles that can be
 * played.<br>
 * 
 * A short mock-up of that is demonstrated by {@link OJSuper} and {@link OJSub}
 * 
 * <br>
 * <br>
 * 
 * This player class will recored a players score
 * 
 * @author ojourmel
 */
public abstract class OJSuper {

	/**
	 * The method to be called when a {@link OJSuper} player has succeeded
	 * 
	 * Track the positive change in the players score
	 */
	protected abstract void addScore();

	/**
	 * The method to be called when a {@link OJSuper} player has failed
	 * 
	 * Track the negative change in the players score
	 */
	protected abstract void removeScore();

	/**
	 * @return The calculated, integer score this player has.
	 */
	protected abstract int getScore();
}
