package csci331.team.red.shared;

/**
 * A basic java class to represent a stage<br>
 * FIXME: <b>Stub</b>
 * 
 * @author ojourmel
 */
public class Stage {

	/**
	 * The actor in this {@link Stage}
	 */
	public final Person actor;

	/**
	 * The probability the {@link Stage#actor} in this {@link Stage} is a fraud
	 */
	public final double fraudFactor;

	/**
	 * The probability of a clerical error with the {@link Stage#actor}'s
	 * information in this {@link Stage}
	 */
	public final double errorFactor;

	/**
	 * Initialize a new @{link Stage}
	 * 
	 * @param {@link Stage#actor}
	 * @param {@link Stage#conFactor}
	 * @param {@link {@link Stage#errorFactor}
	 */
	public Stage(Person actor, double fraudFactor, double errorFactor) {
		this.actor = actor;
		this.fraudFactor = fraudFactor;
		this.errorFactor = errorFactor;
	}
}
